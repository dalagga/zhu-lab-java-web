package com.example.webjavaserver.controller;

import com.example.webjavaserver.model.User;
import com.example.webjavaserver.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User request) {
        var createdUser = userService.createUser(request);
        return ResponseEntity.ok(createdUser);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers(@RequestParam(value = "age", required = false) Integer age,
                                                  @RequestParam(value = "city", required = false) String city) {
        return ResponseEntity.ok(userService.getUsers(age, city));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable Integer id) {
        var resp = userService.findUserById(id);
        // suggestion 2 (not db layer related):
        // if we return optional, we can do something like this:
        // return resp.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
        // it makes sense here to return optionals to let client know that it's possible that there is no user with such id
        // same for other methods
        if (resp == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(resp);
        }
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User request) {
        var resp = userService.updateUser(id, request);
        if (resp == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(resp);
    }

    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
    }
}