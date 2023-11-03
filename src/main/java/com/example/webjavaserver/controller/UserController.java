package com.example.webjavaserver.controller;

import com.example.webjavaserver.model.User;
import com.example.webjavaserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User request){
        var createdUser = userService.createUser(request);
        if (createdUser == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(createdUser);
    }


    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers(@RequestParam (value = "age", required = false) Integer age,
                                                  @RequestParam (value = "city", required = false) String city) {
        return ResponseEntity.ok(userService.getUsers(age, city));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable Integer id){
        var resp = userService.findUserById(id);
        if (resp == null){
            return ResponseEntity.badRequest().build();
        }
        else {
            return ResponseEntity.ok(resp);
        }
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User request){
        var resp = userService.updateUser(id, request);
        if (resp == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(resp);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        var resp = userService.deleteUser(id);
        if (resp == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
