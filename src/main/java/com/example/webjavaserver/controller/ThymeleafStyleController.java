package com.example.webjavaserver.controller;

import com.example.webjavaserver.model.User;
import com.example.webjavaserver.model.Waifu;
import com.example.webjavaserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ThymeleafStyleController {
    // suggestion 1 (important!): As we now have a service layer, we can reuse it here to avoid code duplication
    // the idea behind service layer is to have a single place where we can put all the business logic
    // and then reuse it in different controllers/other services/websockets/etc.
    private final UserRepository userRepository;

    @GetMapping("/")
    public ModelAndView showUsersByParam(@RequestParam (value = "age", required = false) Integer age,
                              @RequestParam (value = "city", required = false) String city) {
        var filteredUsers = userRepository.findWithParam(age, city);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        modelAndView.addObject("users", filteredUsers);
        return modelAndView;
    }

    @GetMapping("/users/{id}")
    public ModelAndView getUserById(@PathVariable Integer id){
        var userSearch = userRepository.findById(id).orElse(null);
        if (userSearch == null) {
            return new ModelAndView("redirect:/");
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("fullUserInfo");
        modelAndView.addObject("user", userSearch);
        return modelAndView;
    }

    @GetMapping("/edituser/{id}")
    public ModelAndView editUser(@PathVariable Integer id){
        var userSearch = userRepository.findById(id).orElse(null);
        assert userSearch != null;
        List<String> userWaifu = userSearch.getFavWaifu().stream()
                .map(Waifu::getName)
                .toList();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("editUser");
        modelAndView.addObject("user", userSearch);
        modelAndView.addObject("waifu", userWaifu);
        return modelAndView;
    }

    @GetMapping("/deleteuser/{id}")
    public ModelAndView deleteUser(@PathVariable Integer id){
        userRepository.deleteById(id);
        return new ModelAndView("redirect:/");
    }

    @PostMapping("/users/{id}")
    public ModelAndView editUser(@PathVariable Integer id, @RequestParam Map<String, String> formData){
        var user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return new ModelAndView("redirect:/");
        }
        user.setName(formData.get("name"));
        user.setAge(Integer.parseInt(formData.get("age")));
        user.setCity(formData.get("city"));
        user.setEmail(formData.get("email"));
        user.getFavWaifu().clear();
        List<Waifu> waifus = Arrays.stream(formData.get("favWaifu").split(","))
                .map(String::trim)
                .map(waifuName -> new Waifu(null, waifuName, user))
                .toList();
        user.getFavWaifu().addAll(waifus);
        userRepository.save(user);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/users/{id}");
        return modelAndView;
    }

@PostMapping("/users")
public ModelAndView addUser(@RequestParam Map<String, String> formData){
    User user = User.builder()
            .name(formData.get("name"))
            .age(Integer.parseInt(formData.get("age")))
            .city(formData.get("city"))
            .email(formData.get("email"))
            .build();
    List<Waifu> waifus = Arrays.stream(formData.get("favWaifu").split(","))
            .map(String::trim)
            .map(waifuName -> new Waifu(null, waifuName, user))
            .collect(Collectors.toList());
    user.setFavWaifu(waifus);
    userRepository.save(user);
    return new ModelAndView("redirect:/");
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
}
