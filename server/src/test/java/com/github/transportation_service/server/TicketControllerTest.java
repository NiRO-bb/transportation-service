package com.github.transportation_service.server;

import com.github.transportation_service.server.controller.TicketController;
import com.github.transportation_service.server.repository.entity.Ticket;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class TicketControllerTest {

    @Autowired
    TicketController c;

    @BeforeAll
    public static void setUp(@Autowired JdbcTemplate jdbcTemplate) {
        jdbcTemplate.update("INSERT INTO ROUTE VALUES (?, 'Авиа', 0, 'москва', 'казань', '2025-02-02', '02:00:00', '2025-02-03', '03:00:00')", 1);
        jdbcTemplate.update("INSERT INTO ROUTE VALUES (?, 'Авиа', 5, 'москва', 'казань', '2025-01-01', '01:00:00', '2025-01-02', '02:00:00')", 2);
        jdbcTemplate.update("INSERT INTO TICKET VALUES (?, 'login', 2)", 1);
        jdbcTemplate.update("INSERT INTO USER VALUES ('login', 'password')");
        jdbcTemplate.update("INSERT INTO USER VALUES ('new login', 'password')");
    }

    @Test
    public void testBookTicket() {
        // корректные параметры
        ResponseEntity<?> result = c.bookTicket(new Ticket(0, "login", 2));
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());

        // некорректные параметры
        result = c.bookTicket(new Ticket(1, "login", 1));
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    }

    @Test
    public void testCancelTicket() {
        // корректные параметры
        ResponseEntity<?> result = c.cancelTicket(1);
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());

        // некорректные параметры
        result = c.cancelTicket(0);
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    }

    @Test
    public void testGetTickets() {
        // корректные параметры
        ResponseEntity<?> result = c.getTickets("login");
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());

        // некорректные параметры
        result = c.getTickets("new login");
        Assertions.assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @AfterAll
    public static void tearDown(@Autowired JdbcTemplate jdbcTemplate) {
        jdbcTemplate.update("DELETE FROM ROUTE");
        jdbcTemplate.update("DELETE FROM TICKET");
        jdbcTemplate.update("DELETE FROM USER");
    }
}
