package com.example.webjavaserver.controller;

import com.example.webjavaserver.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ThymeleafStyleController {
    List<User> users = new ArrayList<>(List.of(
            User.builder()
                    .id(1)
                    .name("Роман Шиян")
                    .age(22)
                    .city("Запоріжжя")
                    .email("roman@gmail.com")
                    .favWaifu(List.of("Місато Кацураґі", "Ріцуко Акаґі"))
                    .build(),
            User.builder()
                    .id(2)
                    .name("Ґендо Ікарі")
                    .age(48)
                    .city("Токіо-3")
                    .email("gendoikari@gmail.com")
                    .favWaifu(List.of("Адам", "Юй Ікарі"))
                    .build(),
            User.builder()
                    .id(3)
                    .name("Шінджі Ікарі")
                    .age(22)
                    .city("Токіо-3")
                    .email("shinikari@gmail.com")
                    .favWaifu(List.of("Пен-Пен"))
                    .build()
    ));

    @GetMapping("/")
    public ModelAndView showUsersByParam(@RequestParam (value = "age", required = false) Integer age,
                              @RequestParam (value = "city", required = false) String city) {
        var filteredUsers = users.stream()
                .filter(user -> age == null || user.getAge().equals(age))
                .filter(user -> city == null || city.isEmpty() || user.getCity().equals(city))
                .toList();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        modelAndView.addObject("users", filteredUsers);
        return modelAndView;
    }

    @GetMapping("/users/{id}")
    public ModelAndView getUserById(@PathVariable Integer id){
        User userSearch = users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElse(null);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("fullUserInfo");
        modelAndView.addObject("user", userSearch);
        return modelAndView;
    }

    @GetMapping("/createuser")
    public ModelAndView createUser(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("createUser");
        return modelAndView;
    }

    @GetMapping("/search")
    public ModelAndView searchUserFormPage(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("searchByParameters");
        return modelAndView;
    }

    public User findUserById(Integer id){
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @GetMapping("/edituser/{id}")
    public ModelAndView editUser(@PathVariable Integer id){
        var userSearch = findUserById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("editUser");
        modelAndView.addObject("user", userSearch);
        return modelAndView;
    }

    @GetMapping("/deleteuser/{id}")
    public ModelAndView deleteUser(@PathVariable Integer id){
        var userSearch = findUserById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/");
        users.remove(userSearch);
        //modelAndView.addObject("user", userSearch);
        return modelAndView;
    }

    @PostMapping("/users/{id}")
    public ModelAndView editUser(@PathVariable Integer id, User user){
        var searchUser = findUserById(id);
        searchUser.setName(user.getName());
        searchUser.setAge(user.getAge());
        searchUser.setCity(user.getCity());
        searchUser.setEmail(user.getEmail());
        List<String> favWaifuList = user.getFavWaifu();
        List<String> favWaifuSplit = favWaifuList.stream()
                .flatMap(s -> Arrays.stream(s.split(",")))
                .toList();
        searchUser.setFavWaifu(favWaifuSplit);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/users/{id}");
        System.out.println(searchUser);
        return modelAndView;
    }

    @PostMapping("/users")
    public ModelAndView addUser(User user){
        System.out.println(user);
        var generateId = users.stream()
                .mapToInt(User::getId)
                .max()
                .orElse(0);
        user.setId(generateId + 1);
        users.add(user);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/");
        System.out.println(user);
        return modelAndView;
    }

}
