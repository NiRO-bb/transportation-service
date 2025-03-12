package com.github.transportation_service.server.controller;

import com.github.transportation_service.server.repository.SignInRepository;
import com.github.transportation_service.server.repository.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
public class SignInController {

    @Autowired
    SignInRepository signInRepository;

    // авторизоваться
    @GetMapping("/sign_in")
    public boolean isUserExist(@RequestParam String login, @RequestParam String password) {
        return signInRepository.isUserExist(new User(login, password));
    }
}