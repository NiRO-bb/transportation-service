package com.github.transportation_service.server;

import com.github.transportation_service.server.repository.TicketRepository;
import com.github.transportation_service.server.repository.entity.Ticket;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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

    @BeforeEach
    public void setUp() {
        jdbcTemplate.update("DELETE FROM TICKET");
        jdbcTemplate.update("INSERT INTO TICKET VALUES (1, 'test', 1)");
        jdbcTemplate.update("INSERT INTO TICKET VALUES (2, 'test', 2)");
    }

    // addTicket()
    @Test
    public void shouldAddTicketToDB() {
        int result = ticketRepository.addTicket(new Ticket(0, "test", 3));
        Assertions.assertTrue(result > 0);

        List<Ticket> tickets = (List<Ticket>) ticketRepository.getTicketByUserLogin("test").getData();
        Assertions.assertEquals(3, tickets.size());
    }

    // removeTicket()
    @Test
    public void shouldRemoveTicketFromDB() {
        int result = ticketRepository.removeTicket(2);
        Assertions.assertTrue(result > 0);

        List<Ticket> tickets = (List<Ticket>) ticketRepository.getTicketByUserLogin("test").getData();
        Assertions.assertEquals(1, tickets.size());
    }

    // getTicketByUserLogin()
    @Test
    public void shouldReturnTicketList() {
        List<Ticket> tickets = (List<Ticket>) ticketRepository.getTicketByUserLogin("test").getData();

        Assertions.assertEquals(2, tickets.size());
    }

    @AfterEach
    public void tearDown() {
        jdbcTemplate.update("DELETE FROM TICKET");
    }
}