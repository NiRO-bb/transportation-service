package com.github.transportation_service.server.controller;

import com.github.transportation_service.server.repository.SignInRepository;
import com.github.transportation_service.server.repository.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
public class SignInController {

    @Autowired
    SignInRepository signInRepository;

    // авторизоваться
    @PostMapping("/sign_in")
    public boolean isUserExist(@RequestBody User user) {
        return signInRepository.checkUser(user);
    }
}