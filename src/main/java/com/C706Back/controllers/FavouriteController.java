package com.C706Back.controllers;

import com.C706Back.models.dto.response.FavouriteCardListResponse;
import com.C706Back.models.dto.response.FavouriteCardResponse;
import com.C706Back.models.enums.Role;
import com.C706Back.service.FavouriteService;
import com.C706Back.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class FavouriteController {

    private final FavouriteService favouriteService;

    private final JwtUtils jwtUtils;

    @RequestMapping(path = "users/{userId}/favourites", method = RequestMethod.GET)
    private FavouriteCardListResponse listFavouritesByUser(@PathVariable(value = "userId") Long userId,
                                                     @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
                                                     @RequestParam(value = "pageSize", defaultValue = "3", required = false) int pageSize,
                                                     @RequestParam(value = "sortBy", defaultValue = "id", required = false) String orderBy,
                                                     @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {

        return favouriteService.listFavouritesByUser(userId, pageNumber, pageSize, orderBy, sortDir);
    }

    @RequestMapping(path = "/favourites/{favouriteId}", method = RequestMethod.GET)
    private ResponseEntity<FavouriteCardResponse> getFavourite(@PathVariable(value = "favouriteId") Long favouriteId) {
        return ResponseEntity.ok(favouriteService.getFavouriteById(favouriteId));
    }

    @RequestMapping(path = "/pets/{petId}/favourites", method = RequestMethod.POST)
    private ResponseEntity<?> createFavourite(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable(value = "petId") Long petId) throws Exception {

        if (!jwtUtils.verify(token))
            return new ResponseEntity<>("User unauthorized", HttpStatus.UNAUTHORIZED);

        Role role = jwtUtils.getRole(token);

        if ((!role.equals(Role.USER) && !role.equals(Role.ADMIN)))
            return new ResponseEntity<>("Yo do not have permissions", HttpStatus.UNAUTHORIZED);

        Long userId = jwtUtils.getUserId(token);

        return ResponseEntity.ok(favouriteService.createFavourite(userId, petId));
    }

    @RequestMapping(path = "/favourites/{favouriteId}", method = RequestMethod.DELETE)
    private ResponseEntity<String> deleteFavourite(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable(value = "favouriteId") Long favouriteId) throws Exception {

        if (!jwtUtils.verify(token))
            return new ResponseEntity<>("User unauthorized", HttpStatus.UNAUTHORIZED);

        Role role = jwtUtils.getRole(token);

        if ((!role.equals(Role.USER) && !role.equals(Role.ADMIN)))
            return new ResponseEntity<>("Yo do not have permissions", HttpStatus.UNAUTHORIZED);

        favouriteService.deleteFavourite(favouriteId);

        return new ResponseEntity<>("Pet was deleted", HttpStatus.OK);
    }
}
