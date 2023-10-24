package com.example.webjavaserver.service;

import com.example.webjavaserver.model.User;

import java.util.List;

public interface UserService {
    User findUserById(Integer id);

    User createUser(User user);

    List<User> getUsers(Integer age, String city);

    User updateUser(Integer id, User request);

    User deleteUser(Integer id);
}
