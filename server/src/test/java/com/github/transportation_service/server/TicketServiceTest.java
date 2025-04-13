package com.github.transportation_service.server;

import com.github.transportation_service.server.repository.entity.Ticket;
import com.github.transportation_service.server.service.TicketService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class TicketServiceTest {

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    TicketService ticketService;

    // bookTicket()
    @Test
    public void shouldFailBooking() {
        Assertions.assertFalse(ticketService.bookTicket(new Ticket(3, "test", 5)));
    }
    @Test
    public void shouldSuccessBooking() {
        Assertions.assertTrue(ticketService.bookTicket(new Ticket(0, "test", 1)));
    }

    // cancelTicket()
    @Test
    public void shouldCancelBooking() {
        Assertions.assertTrue(ticketService.cancelTicket(3));
    }

    @AfterEach
    public void tearDown() {
        jdbcTemplate.update("DELETE FROM TICKET");
        jdbcTemplate.update("INSERT INTO TICKET VALUES (1, 'test', 1)");
        jdbcTemplate.update("INSERT INTO TICKET VALUES (2, 'test', 2)");
        jdbcTemplate.update("INSERT INTO TICKET VALUES (3, 'test', 5)");
    }
}