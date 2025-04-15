package com.github.transportation_service.server.service;

import com.github.transportation_service.server.repository.Result;
import com.github.transportation_service.server.repository.SignInRepository;
import com.github.transportation_service.server.repository.SignUpRepository;
import com.github.transportation_service.server.repository.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SignUpService {

    @Autowired
    SignUpRepository signUpRepository;

    // зарегистрироваться
    public Result createAccount(User user) {
        return signUpRepository.addUser(user);
    }
}
