package com.github.transportation_service.server;

import com.github.transportation_service.server.repository.TicketRepository;
import com.github.transportation_service.server.repository.entity.Ticket;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class TicketRepositoryTest {

    @Autowired
    private TicketRepository ticketRepository;
    List<Ticket> tickets;

    @Value("${dbURL}")
    private String url;
    private Connection connection;
    private PreparedStatement ps;

    // addTicket()
    @Test
    public void shouldAddTicketToDB() {
        ticketRepository.addTicket(new Ticket(0, "test", 3));

        Assertions.assertEquals(3, ticketRepository.getUserTickets("test").size());
    }

    // removeTicket()
    @Test
    public void shouldRemoveTicketFromDB() {
        ticketRepository.removeTicket(2);

        Assertions.assertEquals(1, ticketRepository.getUserTickets("test").size());
    }

    // getUserTickets()
    @Test
    public void shouldReturnTicketList() {
        tickets = ticketRepository.getUserTickets("test");

        Assertions.assertEquals(2, tickets.size());
    }

    @AfterEach
    public void tearDown() {
        try {
            // open connection
            connection = DriverManager.getConnection(url);

            ps = connection.prepareStatement("DELETE FROM TICKET");
            ps.executeUpdate();

            ps = connection.prepareStatement("INSERT INTO TICKET VALUES (1, 'test', 1)");
            ps.executeUpdate();
            ps = connection.prepareStatement("INSERT INTO TICKET VALUES (2, 'test', 2)");
            ps.executeUpdate();

            // close connection
            ps.close();
            connection.close();
        }
        catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }
}