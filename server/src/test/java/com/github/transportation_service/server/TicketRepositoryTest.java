package com.github.transportation_service.server;

import com.github.transportation_service.server.repository.TicketRepository;
import com.github.transportation_service.server.repository.entity.Ticket;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class TicketRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private TicketRepository ticketRepository;
    List<Ticket> tickets;

    // addTicket()
    @Test
    public void shouldAddTicketToDB() {
        boolean result = ticketRepository.addTicket(new Ticket(0, "test", 3));

        Assertions.assertTrue(result);
        Assertions.assertEquals(3, ticketRepository.getTicketByUserLogin("test").size());
    }

    // removeTicket()
    @Test
    public void shouldRemoveTicketFromDB() {
        boolean result = ticketRepository.removeTicket(2);

        Assertions.assertTrue(result);
        Assertions.assertEquals(1, ticketRepository.getTicketByUserLogin("test").size());
    }

    // getTicketByUserLogin()
    @Test
    public void shouldReturnTicketList() {
        tickets = ticketRepository.getTicketByUserLogin("test");

        Assertions.assertEquals(2, tickets.size());
    }

    @AfterEach
    public void tearDown() {
        jdbcTemplate.update("DELETE FROM TICKET");
        jdbcTemplate.update("INSERT INTO TICKET VALUES (1, 'test', 1)");
        jdbcTemplate.update("INSERT INTO TICKET VALUES (2, 'test', 2)");
    }
}