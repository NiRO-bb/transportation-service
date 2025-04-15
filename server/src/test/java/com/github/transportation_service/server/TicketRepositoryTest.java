package com.github.transportation_service.server;

import com.github.transportation_service.server.repository.Result;
import com.github.transportation_service.server.repository.TicketRepository;
import com.github.transportation_service.server.repository.entity.Ticket;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class TicketRepositoryTest {

    @Autowired
    private TicketRepository r;


    @BeforeAll
    public static void setUp(@Autowired JdbcTemplate jdbcTemplate) {
        jdbcTemplate.update("INSERT INTO ROUTE VALUES (?, 'Авиа', 1, 'москва', 'казань', '2025-01-01', '01:00:00', '2025-01-02', '02:00:00')", 1);
        jdbcTemplate.update("INSERT INTO USER VALUES ('login', 'password')");
        jdbcTemplate.update("INSERT INTO USER VALUES ('login2', 'password')");
        jdbcTemplate.update("INSERT INTO TICKET VALUES (?, 'login', 1)", 1);
    }

    @Test
    public void testAddTicket() {
        // корректные параметры
        int result = r.addTicket(new Ticket(0, "login", 1));
        Assertions.assertEquals(1, result);

        // некорректный логин
        result = r.addTicket(new Ticket(0, "invalid login", 1));
        Assertions.assertEquals(0, result);

        // некорректный маршрут
        result = r.addTicket(new Ticket(0, "login", 0));
        Assertions.assertEquals(0, result);
    }

    @Test
    public void testRemoveTicket() {
        // корректные параметры
        int result = r.removeTicket(1);
        Assertions.assertEquals(1, result);

        // некорректный билет
        result = r.removeTicket(0);
        Assertions.assertEquals(0, result);
    }

    @Test
    public void testGetTicketByUserLogin() {
        // корректные параметры
        Result result = r.getTicketByUserLogin("login");
        Assertions.assertEquals(1, ((List<Ticket>) result.getData()).size());

        result = r.getTicketByUserLogin("login2");
        Assertions.assertEquals(0, ((List<Ticket>) result.getData()).size());

        // некорректный логин
        result = r.getTicketByUserLogin("invalid login");
        Assertions.assertFalse(result.isCorrect());
    }

    @AfterAll
    public static void tearDown(@Autowired JdbcTemplate jdbcTemplate) {
        jdbcTemplate.update("DELETE FROM ROUTE");
        jdbcTemplate.update("DELETE FROM USER");
        jdbcTemplate.update("DELETE FROM TICKET");
    }
}