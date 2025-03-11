package com.github.transportation_service.server;

import com.github.transportation_service.server.repository.SignInRepository;
import com.github.transportation_service.server.repository.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class SignInRepositoryTest {

    @Autowired
    private SignInRepository signInRepository;

    // checkUser()
    @Test
    public void shouldReturnTrueIfUserExists() {
        boolean result = signInRepository.checkUser(new User("test", "test"));
        Assertions.assertTrue(result);

        result = signInRepository.checkUser(new User("test2", "test2"));
        Assertions.assertFalse(result);
    }
}
