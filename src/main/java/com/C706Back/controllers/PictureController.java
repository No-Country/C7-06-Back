package com.C706Back.controllers;

import com.C706Back.exception.FileSizeExceedException;
import com.C706Back.exception.OutOfBoundUploadFilesException;
import com.C706Back.models.dto.response.PetProfileResponse;
import com.C706Back.models.dto.response.PictureResponse;
import com.C706Back.models.dto.response.UserProfileResponse;
import com.C706Back.models.entity.Picture;
import com.C706Back.models.enums.Role;
import com.C706Back.service.PetService;
import com.C706Back.service.PictureService;
import com.C706Back.service.S3Service;
import com.C706Back.service.UserService;
import com.C706Back.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PictureController {

    private final S3Service s3Service;

    private final PictureService pictureService;

    private final UserService userService;

    private final PetService petService;

    private final JwtUtils jwtUtils;

    @RequestMapping(path = "pets/{petId}/pictures", method = RequestMethod.POST)
    private Map<String, String> createPetPicture(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestParam MultipartFile file, @PathVariable(value = "petId") Long petId) throws Exception {
        Map<String, String> result = new HashMap<>();
        if (!jwtUtils.verify(token)) {
            result.put("error", "Unhautorized");
        }
        //return new ResponseEntity<>("User unauthorized", HttpStatus.UNAUTHORIZED);

        Role role = jwtUtils.getRole(token);

        if ((!role.equals(Role.user) && !role.equals(Role.admin))) {
            result.put("error", "Unhautorized");
        }
        //return new ResponseEntity<>("Yo do not have permissions", HttpStatus.UNAUTHORIZED);

        String path = "";
        String key = "";
        PetProfileResponse petProfileResponse = petService.getPetById(petId);

        if (file.getContentType().equals("image/webp") || file.getContentType().equals("image/jpeg")
                || file.getContentType().equals("image/png")) {
            try {
                if (file.getBytes().length > 1048576)
                    throw new FileSizeExceedException("The size of the file is too large to upload");

                if (petProfileResponse.getPictures().size() == 4)
                    throw new OutOfBoundUploadFilesException("The number of uploaded files has reached its limit.");

                key = s3Service.putObject(file);
                path = s3Service.getObjectUrl(key);
                result.put("key", key);
                result.put("path", path);
                System.out.println("aqui");

                pictureService.createPetPicture(petId, path, key);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    @RequestMapping(path = "/avatar", method = RequestMethod.POST)
    private Map<String, String> createAvatar(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestParam MultipartFile file) throws Exception {
        Map<String, String> result = new HashMap<>();
        if (!jwtUtils.verify(token)) {
            result.put("error", "Unhautorized");
        }
        //return new ResponseEntity<>("User unauthorized", HttpStatus.UNAUTHORIZED);

        Role role = jwtUtils.getRole(token);

        if ((!role.equals(Role.user) && !role.equals(Role.admin))) {
            result.put("error", "Unhautorized");
        }

        Long userId = jwtUtils.getUserId(token);
        UserProfileResponse userProfileResponse = userService.getUserById(userId);

        String path = "";
        String key = "";

        if (file.getContentType().equals("image/webp") || file.getContentType().equals("image/jpeg")
                || file.getContentType().equals("image/png")) {
            try {
                if (file.getBytes().length > 1048576)
                    throw new FileSizeExceedException("The size of the file is too large to upload");

                key = s3Service.putObject(file);
                path = s3Service.getObjectUrl(key);
                result.put("key", key);
                result.put("path", path);

                pictureService.createAvatar(userId, path, key);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return result;
    }

    /*
    @RequestMapping(path = "/avatar", method = RequestMethod.POST)
    private ResponseEntity<?> createAvatar(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestParam MultipartFile file) throws Exception {
        Map<String, String> result = new HashMap<>();
        if (!jwtUtils.verify(token)) {
            result.put("error", "Unhautorized");
        }
        //return new ResponseEntity<>("User unauthorized", HttpStatus.UNAUTHORIZED);

        Role role = jwtUtils.getRole(token);

        if ((!role.equals(Role.USER) && !role.equals(Role.ADMIN))) {
            result.put("error", "Unhautorized");
        }

        Long userId = jwtUtils.getUserId(token);
        UserProfileResponse userProfileResponse = userService.getUserById(userId);

        String path = "";
        String key = "";

        if (file.getContentType().equals("image/webp") || file.getContentType().equals("image/jpeg")
                || file.getContentType().equals("image/png")) {
            try {
                if (file.getBytes().length > 1048576)
                    throw new FileSizeExceedException("The size of the file is too large to upload");

                key = s3Service.putObject(file);
                path = s3Service.getObjectUrl(key);
                result.put("key", key);
                result.put("path", path);

                pictureService.createAvatar(userId, path, key);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return new ResponseEntity<result, HttpStatus.OK>;
    }*/



/*
    @RequestMapping(path = "/pictures/{pictureId}", method = RequestMethod.PUT)
    private ResponseEntity<?> updatePicture(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestParam MultipartFile file, @PathVariable(value = "pictureId") Long pictureId) throws Exception {
        Map<String, String> result = new HashMap<>();
        if (!jwtUtils.verify(token)) {
            result.put("error", "Unhautorized");
        }
        return new ResponseEntity<>("User unauthorized", HttpStatus.UNAUTHORIZED);

        Role role = jwtUtils.getRole(token);

        if ((!role.equals(Role.USER) && !role.equals(Role.ADMIN))) {
            result.put("error", "Unhautorized");
        }

        String path = "";
        String key = "";

        if (file.getContentType().equals("image/webp") || file.getContentType().equals("image/jpeg")
                || file.getContentType().equals("image/png")) {
            try {
                if (file.getBytes().length > 1048576)
                    throw new FileSizeExceedException("The size of the file is too large to upload");

                key = s3Service.putObject(file);
                path = s3Service.getObjectUrl(key);
                result.put("key", key);
                result.put("path", path);

                pictureService.updatePicture(pictureId, path, key);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }*/

    @RequestMapping(path = "/pictures/{pictureId}", method = RequestMethod.PUT)
    private Map<String, String> updatePicture(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestParam MultipartFile file, @PathVariable(value = "pictureId") Long pictureId) throws Exception {
        Map<String, String> result = new HashMap<>();
        if (!jwtUtils.verify(token)) {
            result.put("error", "Unhautorized");
        }
        //return new ResponseEntity<>("User unauthorized", HttpStatus.UNAUTHORIZED);

        Role role = jwtUtils.getRole(token);

        if ((!role.equals(Role.user) && !role.equals(Role.admin))) {
            result.put("error", "Unhautorized");
        }

        String path = "";
        String key = "";

        if (file.getContentType().equals("image/webp") || file.getContentType().equals("image/jpeg")
                || file.getContentType().equals("image/png")) {
            try {
                if (file.getBytes().length > 1048576)
                    throw new FileSizeExceedException("The size of the file is too large to upload");

                key = s3Service.putObject(file);
                path = s3Service.getObjectUrl(key);
                result.put("key", key);
                result.put("path", path);

                pictureService.updatePicture(pictureId, path, key);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return result;
    }

    @RequestMapping(path = "/pictures/{pictureId}", method = RequestMethod.DELETE)
    private ResponseEntity<String> deletePicture(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable(value = "pictureId") Long pictureId) throws Exception {
        Map<String, String> result = new HashMap<>();
        if (!jwtUtils.verify(token)) {
            result.put("error", "Unhautorized");
        }
        //return new ResponseEntity<>("User unauthorized", HttpStatus.UNAUTHORIZED);

        Role role = jwtUtils.getRole(token);

        if ((!role.equals(Role.user) && !role.equals(Role.admin))) {
            result.put("error", "Unhautorized");
        }


        Picture picture = pictureService.getPicture(pictureId);
        s3Service.deleteObject(picture.getKeyNumber());

        pictureService.deletePicture(pictureId);

        return new ResponseEntity<>("Picture was deleted", HttpStatus.OK);
    }
}
