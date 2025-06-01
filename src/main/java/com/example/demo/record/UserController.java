package com.example.demo.record;

import java.net.URI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ResponseStatus(HttpStatus.CREATED)
public class UserController {

    @PostMapping("/api/users")
    public ResponseEntity<String> createUser(@RequestBody User newUser) {
        System.out.println(newUser);

        return ResponseEntity.created(URI.create("/temp/location")).body("success");
    }


    @PostMapping("/api/users/record")
    public ResponseEntity<String> createUserRecord(@RequestBody UserRecord newUser) {
        System.out.println(newUser);

        return ResponseEntity.created(URI.create("/temp/location")).body("success");
    }
}
