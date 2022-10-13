package com.C706Back.controllers;

import com.C706Back.exception.ErrorMessage;
import com.C706Back.models.dto.request.PetProfileRequest;
import com.C706Back.models.dto.response.PetCardListResponse;
import com.C706Back.models.dto.response.PetProfileResponse;
import com.C706Back.models.enums.Role;
import com.C706Back.service.PetService;
import com.C706Back.service.PictureService;
import com.C706Back.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PetController {
    private final PetService petService;

    private final PictureService pictureService;

    private final JwtUtils jwtUtils;

    @RequestMapping(path = "users/{userId}/pets", method = RequestMethod.GET)
    private PetCardListResponse listPetsByUser(@PathVariable(value = "userId") Long userId,
                                               @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
                                               @RequestParam(value = "pageSize", defaultValue = "3", required = false) int pageSize,
                                               @RequestParam(value = "sortBy", defaultValue = "updatedDate", required = false) String orderBy,
                                               @RequestParam(value = "sortDir", defaultValue = "desc", required = false) String sortDir) {

        return petService.listPetsByUser(userId, pageNumber, pageSize, orderBy, sortDir);
    }

    @RequestMapping(path = "/petsByAnimalType", method = RequestMethod.GET)
    private PetCardListResponse listPetsByAnimalType(
            @RequestParam(value = "animal") String animalType,
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "4", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "updatedDate", required = false) String orderBy,
            @RequestParam(value = "sortDir", defaultValue = "desc", required = false) String sortDir) {

        return petService.listPetsByAnimalType(animalType, pageNumber, pageSize, orderBy, sortDir);
    }

    @RequestMapping(path = "/pets", method = RequestMethod.GET)
    private ResponseEntity<PetProfileResponse> getPet(@PathVariable(value = "petId") Long petId) {
        return ResponseEntity.ok(petService.getPetById(petId));
    }

    @RequestMapping(path = "/pets", method = RequestMethod.POST)
    private ResponseEntity<?> createPet(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @Valid @RequestBody PetProfileRequest petProfileRequest, BindingResult result) throws Exception {

        if (!jwtUtils.verify(token))
            return new ResponseEntity<>("User unauthorized", HttpStatus.UNAUTHORIZED);

        Role role = jwtUtils.getRole(token);

        if ((!role.equals(Role.USER) && !role.equals(Role.ADMIN)))
            return new ResponseEntity<>("Yo do not have permissions", HttpStatus.UNAUTHORIZED);

        Long userId = jwtUtils.getUserId(token);

        if (result.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ErrorMessage.formatMessage(result));
        }

        return new ResponseEntity<>(petService.createPet(userId, petProfileRequest), HttpStatus.CREATED);
    }

    @RequestMapping(path = "/pets/{petId}", method = RequestMethod.PUT)
    private ResponseEntity<?> createPet(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable(value = "petId") Long petId, @Valid @RequestBody PetProfileRequest petProfileRequest, BindingResult result) throws Exception {

        if (!jwtUtils.verify(token))
            return new ResponseEntity<>("User unauthorized", HttpStatus.UNAUTHORIZED);

        Role role = jwtUtils.getRole(token);

        if ((!role.equals(Role.USER) && !role.equals(Role.ADMIN)))
            return new ResponseEntity<>("Yo do not have permissions", HttpStatus.UNAUTHORIZED);

        if (result.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ErrorMessage.formatMessage(result));
        }

        return new ResponseEntity<>(petService.updatePet(petId, petProfileRequest), HttpStatus.CREATED);
    }

    @RequestMapping(path = "/pets/{petId}", method = RequestMethod.DELETE)
    private ResponseEntity<String> deletePet(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable(value = "petId") Long petId) throws Exception {

        if (!jwtUtils.verify(token))
            return new ResponseEntity<>("User unauthorized", HttpStatus.UNAUTHORIZED);

        Role role = jwtUtils.getRole(token);

        if ((!role.equals(Role.USER) && !role.equals(Role.ADMIN)))
            return new ResponseEntity<>("Yo do not have permissions", HttpStatus.UNAUTHORIZED);

        PetProfileResponse petProfileResponse = petService.getPetById(petId);
        if (!petProfileResponse.getPictures().isEmpty())
            petProfileResponse.getPictures().forEach(picture -> pictureService.deletePicture(picture.getId()));

        petService.deletePet(petId);

        return new ResponseEntity<>("Pet was deleted", HttpStatus.OK);
    }
}
