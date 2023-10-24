package com.example.webjavaserver.service;

import com.example.webjavaserver.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserServiceImpl implements UserService {
    List<User> users = new ArrayList<>(List.of(
            User.builder()
                    .id(1)
                    .name("Roman Shyian")
                    .age(22)
                    .city("Zaporizhzhia")
                    .email("roman@gmail.com")
                    .favWaifu(List.of("Misato Katsuragi", "Ritsuko Akagi"))
                    .build(),
            User.builder()
                    .id(2)
                    .name("Gendo Ikari")
                    .age(48)
                    .city("Tokyo-3")
                    .email("gendoikari@gmail.com")
                    .favWaifu(List.of("Adam", "Yui Ikari"))
                    .build(),
            User.builder()
                    .id(3)
                    .name("Shinji Ikari")
                    .age(22)
                    .city("Tokyo-3")
                    .email("shinikari@gmail.com")
                    .favWaifu(List.of("Pen-Pen"))
                    .build()
    ));

    @Override
    public User findUserById(Integer id){
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public User createUser(User user){
        System.out.println(user);
        if (findUserById(user.getId()) != null){
            return null;
        }
        users.add(user);
        return user;
    }

    @Override
    public List<User> getUsers(Integer age, String city){
        return this.users.stream()
                .filter(user -> age == null || user.getAge().equals(age))
                .filter(user -> city == null || user.getCity().equals(city))
                .toList();
    }

    @Override
    public User updateUser(Integer id, User request){
        var user = findUserById(id);
        if (user == null){
            return null;
        }
        user.setName(request.getName());
        user.setAge(request.getAge());
        user.setCity(request.getCity());
        user.setEmail(request.getEmail());
        user.setFavWaifu(request.getFavWaifu());
        return user;
    }

    @Override
    public User deleteUser(Integer id) {
        var user = findUserById(id);
        if (user == null) {
            return null;
        }
        users.remove(user);
        return user;
    }
}
