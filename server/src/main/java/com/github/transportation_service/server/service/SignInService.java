package com.github.transportation_service.server.service;

import com.github.transportation_service.server.repository.Result;
import com.github.transportation_service.server.repository.SignInRepository;
import com.github.transportation_service.server.repository.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SignInService {

    @Autowired
    SignInRepository signInRepository;

    // авторизоваться
    public Result isUserExist(String login, String password) {
        return signInRepository.isUserExist(new User(login, password));
    }
}
