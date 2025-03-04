package com.github.transportation_service.server.controller;

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

    // зарегистрироваться
    @PostMapping("/sign_up")
    public boolean createAccount(@RequestBody User user) {

        boolean result = false;

        if (isCorrect(user)) {
            signUpRepository.addUser(user);
            result = true;
        }

        return result;
    }

    // проверить корректность данных
    private boolean isCorrect(User user) {
        boolean result = true;

        // проверка
        // 1. проверить логин на уникальность
        if (signUpRepository.isUserExist(user.getLogin())) {
            result = false;
        }

        System.out.println("isCorrect() method:\nuserLogin: '%s'\nresult: '%s'".formatted(user.getLogin(), result));
        return result;
    }
}