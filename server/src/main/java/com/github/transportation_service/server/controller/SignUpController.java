package com.github.transportation_service.server.controller;

import com.github.transportation_service.server.repository.SignInRepository;
import com.github.transportation_service.server.repository.SignUpRepository;
import com.github.transportation_service.server.repository.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
public class SignUpController {

    @Autowired
    SignUpRepository signUpRepository;
    @Autowired
    SignInRepository signInRepository;

    // зарегистрироваться
    @PostMapping("/sign_up")
    public boolean createAccount(@RequestBody User user) {

        boolean result = false;

        if (!signInRepository.isUserExist(user.getLogin())) {
            signUpRepository.addUser(user);
            result = true;
        }

        return result;
    }
}