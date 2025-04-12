package com.github.transportation_service.server.controller;

import com.github.transportation_service.server.repository.entity.User;
import com.github.transportation_service.server.service.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
public class SignUpController {

    @Autowired
    SignUpService signUpService;

    // зарегистрироваться
    @PostMapping("/sign_up")
    public ResponseEntity<?> createAccount(@RequestBody User user) {
        if (signUpService.createAccount(user))
            return new ResponseEntity<>(true, HttpStatus.OK);
        else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Не удалось зарегистрировать нового пользователя (ошибка сервера)");
    }
}