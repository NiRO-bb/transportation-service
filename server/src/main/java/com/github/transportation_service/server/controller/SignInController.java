package com.github.transportation_service.server.controller;

import com.github.transportation_service.server.repository.Result;
import com.github.transportation_service.server.service.SignInService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@CrossOrigin(origins = "*")
@Validated
@RestController
public class SignInController {

    @Autowired
    SignInService signInService;

    // авторизоваться
    @GetMapping("/sign_in")
    public ResponseEntity<?> isUserExist(
            @RequestParam
            @NotBlank(message = "Необходимо заполнить поле \"ЛОГИН\"!")
            String login,
            @RequestParam
            @NotBlank(message = "Необходимо заполнить поле \"ПАРОЛЬ\"!")
            String password) {
        Result result = signInService.isUserExist(login, password);

        if (result.isCorrect()) {
            if ((boolean) result.getData())
                return new ResponseEntity<>(true, HttpStatus.OK);
            return new ResponseEntity<>(new ErrorResponse(Collections.singletonList("Вы неверно указали логин и/или пароль!"), HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ErrorResponse(Collections.singletonList("При попытке авторизоваться произошла ошибка (ошибка сервера)"), HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}