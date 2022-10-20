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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    @RequestMapping(path = "pets/{petId}/picture", method = RequestMethod.POST)
    private ResponseEntity<?> createPetPicture(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestParam MultipartFile file, @PathVariable(value = "petId") Long petId) throws Exception {
        if (!jwtUtils.verify(token)) {
            return new ResponseEntity<>("User unauthorized", HttpStatus.UNAUTHORIZED);
        }

        Role role = jwtUtils.getRole(token);

        if ((!role.equals(Role.user) && !role.equals(Role.admin))) {
            return new ResponseEntity<>("User unauthorized", HttpStatus.UNAUTHORIZED);
        }

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

                PictureResponse pictureResponse = pictureService.createPetPicture(petId, path, key);

                return new ResponseEntity<>(pictureResponse, HttpStatus.OK);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    @RequestMapping(path = "pets/{petId}/pictures", method = RequestMethod.POST)
    private ResponseEntity<?> createPetPictures(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                                @RequestParam(required = false, name = "picture1") MultipartFile picture1,
                                                @RequestParam(required = false, name = "picture2") MultipartFile picture2,
                                                @RequestParam(required = false, name = "picture3") MultipartFile picture3,
                                                @RequestParam(required = false, name = "picture4") MultipartFile picture4,
                                                @PathVariable(value = "petId") Long petId) throws Exception {
        if (!jwtUtils.verify(token)) {
            return new ResponseEntity<>("User unauthorized", HttpStatus.UNAUTHORIZED);
        }

        Role role = jwtUtils.getRole(token);

        List<PictureResponse> pictures = new ArrayList<>();

        if ((!role.equals(Role.user) && !role.equals(Role.admin))) {
            return new ResponseEntity<>("User unauthorized", HttpStatus.UNAUTHORIZED);
        }

        String path1 = "";
        String path2 = "";
        String path3 = "";
        String path4 = "";
        PetProfileResponse petProfileResponse = petService.getPetById(petId);

        if (picture1 != null) {
            if (picture1.getContentType().equals("image/webp") || picture1.getContentType().equals("image/jpeg")
                    || picture1.getContentType().equals("image/png")) {
                try {
                    if (picture1.getBytes().length > 1048576)
                        throw new FileSizeExceedException("The size of the file is too large to upload");

                    if (petProfileResponse.getPictures().size() == 4)
                        throw new OutOfBoundUploadFilesException("The number of uploaded files has reached its limit.");

                    String key = s3Service.putObject(picture1);
                    path1 = s3Service.getObjectUrl(key);

                    PictureResponse pictureResponse = pictureService.createPetPicture(petId, path1, key);
                    pictures.add(pictureResponse);

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        if (picture2 != null) {
            if (picture2.getContentType().equals("image/webp") || picture2.getContentType().equals("image/jpeg")
                    || picture2.getContentType().equals("image/png")) {
                try {
                    if (picture2.getBytes().length > 1048576)
                        throw new FileSizeExceedException("The size of the file is too large to upload");

                    if (petProfileResponse.getPictures().size() == 4)
                        throw new OutOfBoundUploadFilesException("The number of uploaded files has reached its limit.");

                    String key = s3Service.putObject(picture2);
                    path2 = s3Service.getObjectUrl(key);

                    PictureResponse pictureResponse = pictureService.createPetPicture(petId, path2, key);
                    pictures.add(pictureResponse);

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        if (picture3 != null) {
            if (picture3.getContentType().equals("image/webp") || picture3.getContentType().equals("image/jpeg")
                    || picture3.getContentType().equals("image/png")) {
                try {
                    if (picture3.getBytes().length > 1048576)
                        throw new FileSizeExceedException("The size of the file is too large to upload");

                    if (petProfileResponse.getPictures().size() == 4)
                        throw new OutOfBoundUploadFilesException("The number of uploaded files has reached its limit.");

                    String key = s3Service.putObject(picture3);
                    path3 = s3Service.getObjectUrl(key);

                    PictureResponse pictureResponse = pictureService.createPetPicture(petId, path3, key);
                    pictures.add(pictureResponse);

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        if (picture4 != null) {
            if (picture4.getContentType().equals("image/webp") || picture4.getContentType().equals("image/jpeg")
                    || picture4.getContentType().equals("image/png")) {
                try {
                    if (picture4.getBytes().length > 1048576)
                        throw new FileSizeExceedException("The size of the file is too large to upload");

                    if (petProfileResponse.getPictures().size() == 4)
                        throw new OutOfBoundUploadFilesException("The number of uploaded files has reached its limit.");

                    String key = s3Service.putObject(picture4);
                    path4 = s3Service.getObjectUrl(key);

                    PictureResponse pictureResponse = pictureService.createPetPicture(petId, path4, key);
                    pictures.add(pictureResponse);

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return new ResponseEntity<>(pictures, HttpStatus.OK);
    }

    @RequestMapping(path = "/avatar", method = RequestMethod.POST)
    private ResponseEntity<?> createAvatar(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestParam MultipartFile file) throws Exception {
        if (!jwtUtils.verify(token)) {
            return new ResponseEntity<>("User unauthorized", HttpStatus.UNAUTHORIZED);
        }

        Role role = jwtUtils.getRole(token);

        if ((!role.equals(Role.user) && !role.equals(Role.admin))) {
            return new ResponseEntity<>("User unauthorized", HttpStatus.UNAUTHORIZED);
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

                PictureResponse pictureResponse = pictureService.createAvatar(userId, path, key);
                return new ResponseEntity<>(pictureResponse, HttpStatus.OK);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return null;
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

    @RequestMapping(path = "/pets/pictures", method = RequestMethod.PUT)
    private Map<String, String> updatePictures(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                               @RequestParam(required = false, name = "picture1") MultipartFile picture1,
                                               @RequestParam(required = false, name = "picture2") MultipartFile picture2,
                                               @RequestParam(required = false, name = "picture3") MultipartFile picture3,
                                               @RequestParam(required = false, name = "picture4") MultipartFile picture4,
                                               @RequestParam(value = "pictureId1", required = false) Long pictureId1,
                                               @RequestParam(value = "pictureId2", required = false) Long pictureId2,
                                               @RequestParam(value = "pictureId3", required = false) Long pictureId3,
                                               @RequestParam(value = "pictureId4", required = false) Long pictureId4) throws Exception {
        Map<String, String> result = new HashMap<>();
        if (!jwtUtils.verify(token)) {
            result.put("error", "Unhautorized");
        }
        //return new ResponseEntity<>("User unauthorized", HttpStatus.UNAUTHORIZED);

        Role role = jwtUtils.getRole(token);

        if ((!role.equals(Role.user) && !role.equals(Role.admin))) {
            result.put("error", "Unhautorized");
        }

        String path1 = "";
        String path2 = "";
        String path3 = "";
        String path4 = "";

        if (picture1 != null && pictureId1 != null) {
            if (picture1.getContentType().equals("image/webp") || picture1.getContentType().equals("image/jpeg")
                    || picture1.getContentType().equals("image/png")) {
                try {
                    if (picture1.getBytes().length > 1048576)
                        throw new FileSizeExceedException("The size of the file is too large to upload");

                    String key = s3Service.putObject(picture1);
                    path1 = s3Service.getObjectUrl(key);
                    result.put("path1", path1);

                    pictureService.updatePicture(pictureId1, path1, key);

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        if (picture2 != null && pictureId2 != null) {
            if (picture2.getContentType().equals("image/webp") || picture2.getContentType().equals("image/jpeg")
                    || picture2.getContentType().equals("image/png")) {
                try {
                    if (picture2.getBytes().length > 1048576)
                        throw new FileSizeExceedException("The size of the file is too large to upload");

                    String key = s3Service.putObject(picture2);
                    path2 = s3Service.getObjectUrl(key);
                    result.put("path2", path2);

                    pictureService.updatePicture(pictureId2, path2, key);

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        if (picture3 != null && pictureId3 != null) {
            if (picture3.getContentType().equals("image/webp") || picture3.getContentType().equals("image/jpeg")
                    || picture3.getContentType().equals("image/png")) {
                try {
                    if (picture3.getBytes().length > 1048576)
                        throw new FileSizeExceedException("The size of the file is too large to upload");

                    String key = s3Service.putObject(picture3);
                    path3 = s3Service.getObjectUrl(key);
                    result.put("path3", path3);

                    pictureService.updatePicture(pictureId3, path3, key);

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        if (picture4 != null && pictureId4 != null) {
            if (picture4.getContentType().equals("image/webp") || picture4.getContentType().equals("image/jpeg")
                    || picture4.getContentType().equals("image/png")) {
                try {
                    if (picture4.getBytes().length > 1048576)
                        throw new FileSizeExceedException("The size of the file is too large to upload");

                    String key = s3Service.putObject(picture4);
                    path4 = s3Service.getObjectUrl(key);
                    result.put("path4", path4);

                    pictureService.updatePicture(pictureId4, path4, key);

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return result;
    }

    @RequestMapping(path = "/pictures/{pictureId}", method = RequestMethod.DELETE)
    private ResponseEntity<?> deletePicture(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable(value = "pictureId") Long pictureId) throws Exception {
        if (!jwtUtils.verify(token)) {
            return new ResponseEntity<>("User unauthorized", HttpStatus.UNAUTHORIZED);
        }

        Role role = jwtUtils.getRole(token);

        if ((!role.equals(Role.user) && !role.equals(Role.admin))) {
            return new ResponseEntity<>("User unauthorized", HttpStatus.UNAUTHORIZED);
        }

        Picture picture = pictureService.getPicture(pictureId);
        PictureResponse pictureResponse = new PictureResponse(pictureId, picture.getPath());
        s3Service.deleteObject(picture.getKeyNumber());

        pictureService.deletePicture(pictureId);

        return new ResponseEntity<>(pictureResponse, HttpStatus.OK);
    }

    @RequestMapping(path = "/pictures", method = RequestMethod.DELETE)
    private ResponseEntity<String> deletePictures(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
            @RequestParam(value = "pictureId1", required = false) Long pictureId1,
            @RequestParam(value = "pictureId2", required = false) Long pictureId2,
            @RequestParam(value = "pictureId3", required = false) Long pictureId3,
            @RequestParam(value = "pictureId4", required = false) Long pictureId4) throws Exception {
        Map<String, String> result = new HashMap<>();
        if (!jwtUtils.verify(token)) {
            result.put("error", "Unhautorized");
        }
        //return new ResponseEntity<>("User unauthorized", HttpStatus.UNAUTHORIZED);

        Role role = jwtUtils.getRole(token);

        if ((!role.equals(Role.user) && !role.equals(Role.admin))) {
            result.put("error", "Unhautorized");
        }


        if (pictureId1 != null) {
            Picture picture = pictureService.getPicture(pictureId1);
            s3Service.deleteObject(picture.getKeyNumber());
            pictureService.deletePicture(pictureId1);
        }

        if (pictureId2 != null) {
            Picture picture = pictureService.getPicture(pictureId2);
            s3Service.deleteObject(picture.getKeyNumber());
            pictureService.deletePicture(pictureId2);
        }

        if (pictureId3 != null) {
            Picture picture = pictureService.getPicture(pictureId3);
            s3Service.deleteObject(picture.getKeyNumber());
            pictureService.deletePicture(pictureId3);
        }

        if (pictureId4 != null) {
            Picture picture = pictureService.getPicture(pictureId4);
            s3Service.deleteObject(picture.getKeyNumber());
            pictureService.deletePicture(pictureId4);
        }

        return new ResponseEntity<>("Pictures was deleted", HttpStatus.OK);
    }
}
