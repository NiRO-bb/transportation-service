package com.github.transportation_service.server.controller;

import com.github.transportation_service.server.repository.Result;
import com.github.transportation_service.server.repository.entity.User;
import com.github.transportation_service.server.service.SignUpService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@CrossOrigin(origins = "*")
@Validated
@RestController
public class SignUpController {

    @Autowired
    SignUpService signUpService;

    // зарегистрироваться
    @PostMapping("/sign_up")
    public ResponseEntity<?> createAccount(@Valid @RequestBody User user) {
        Result result = signUpService.createAccount(user);

        if (result.isCorrect()) {
            if ((boolean) result.getData())
                return new ResponseEntity<>(true, HttpStatus.OK);
            return new ResponseEntity<>(new ErrorResponse(Collections.singletonList("Пользователь с таким логином уже существует!"), HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ErrorResponse(Collections.singletonList("Не удалось зарегистрировать нового пользователя (ошибка сервера)"), HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}