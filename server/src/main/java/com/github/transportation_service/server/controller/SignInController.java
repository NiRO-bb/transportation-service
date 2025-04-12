package com.github.transportation_service.server.controller;

import com.github.transportation_service.server.service.SignInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
public class SignInController {

    @Autowired
    SignInService signInService;

    // авторизоваться
    @GetMapping("/sign_in")
    public ResponseEntity<?> isUserExist(@RequestParam String login, @RequestParam String password) {
        if (signInService.isUserExist(login, password))
            return new ResponseEntity<>(true, HttpStatus.OK);
        else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Не удалось авторизоваться (ошибка сервера)");
    }
}