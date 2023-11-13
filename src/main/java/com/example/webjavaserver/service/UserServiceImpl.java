package com.example.webjavaserver.service;

import com.example.webjavaserver.model.User;
import com.example.webjavaserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User findUserById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User createUser(User user) {
        System.out.println(user);
        user.getFavWaifu().forEach(waifu -> waifu.setOfUser(user));
        return userRepository.save(user);
    }

    @Override
    public List<User> getUsers(Integer age, String city) {
        return userRepository.findWithParam(age, city);
    }

    @Override
    public User updateUser(Integer id, User updatedUser) {
        User existingUser = findUserById(id);
        if (updatedUser == null) {
            return null;
        }
        existingUser.setName(updatedUser.getName());
        existingUser.setAge(updatedUser.getAge());
        existingUser.setCity(updatedUser.getCity());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.getFavWaifu().clear();
        updatedUser.getFavWaifu().forEach(waifu -> waifu.setOfUser(existingUser));
        existingUser.getFavWaifu().addAll(updatedUser.getFavWaifu());
        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }
}
