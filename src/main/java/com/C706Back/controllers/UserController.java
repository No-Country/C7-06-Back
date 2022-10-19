package com.C706Back.controllers;

import com.C706Back.models.enums.Role;
import com.C706Back.service.UserService;
import com.C706Back.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    private final JwtUtils jwtUtils;

    @RequestMapping(path = "/users", method = RequestMethod.GET)
    private ResponseEntity<?> getUserById(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) throws Exception {

        if (!jwtUtils.verify(token))
            return new ResponseEntity<>("User unauthorized", HttpStatus.UNAUTHORIZED);

        Role role = jwtUtils.getRole(token);

        if ((!role.equals(Role.user) && !role.equals(Role.admin)))
            return new ResponseEntity<>("Yo do not have permissions", HttpStatus.UNAUTHORIZED);

        Long userId = jwtUtils.getUserId(token);

        return ResponseEntity.ok(userService.getUserById(userId));

    }
}
