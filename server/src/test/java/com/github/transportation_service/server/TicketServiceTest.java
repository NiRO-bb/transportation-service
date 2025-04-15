package com.github.transportation_service.server;

import com.github.transportation_service.server.repository.Result;
import com.github.transportation_service.server.repository.SignUpRepository;
import com.github.transportation_service.server.repository.entity.Ticket;
import com.github.transportation_service.server.repository.entity.User;
import com.github.transportation_service.server.service.TicketService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class TicketServiceTest {

    @Autowired
    TicketService s;

    @BeforeAll
    public static void setUp(@Autowired JdbcTemplate jdbcTemplate) {
        jdbcTemplate.update("INSERT INTO ROUTE VALUES (?, 'Авиа', 0, 'москва', 'казань', '2025-02-02', '02:00:00', '2025-02-03', '03:00:00')", 1);
        jdbcTemplate.update("INSERT INTO ROUTE VALUES (?, 'Авиа', 1, 'москва', 'казань', '2025-01-01', '01:00:00', '2025-01-02', '02:00:00')", 2);
        jdbcTemplate.update("INSERT INTO USER VALUES ('login', 'password')");
    }

    @Test
    public void testBookTicket() {
        // корректные параметры
        boolean result = s.bookTicket(new Ticket(0, "login", 2));
        Assertions.assertTrue(result);

        // неуникальный логин
        result = s.bookTicket(new Ticket(0, "login", 1));
        Assertions.assertFalse(result);
    }

    @AfterEach
    public void tearDown(@Autowired JdbcTemplate jdbcTemplate) {
        jdbcTemplate.update("DELETE FROM USER");
        jdbcTemplate.update("DELETE FROM ROUTE");
    }
}