package com.github.transportation_service.server.repository;

import com.github.transportation_service.server.repository.entity.Ticket;
import org.springframework.stereotype.Component;

import java.sql.*;

@Component
public class TicketRepository extends Repository{

    // добавить билет в базу данных
    public void addTicket(Ticket ticket) {

        try {
            // open connection
            connection = DriverManager.getConnection(url);

            ps = connection.prepareStatement("INSERT INTO TICKET(USER_LOGIN, ROUTE) VALUES('%s', '%s')".formatted(ticket.getUserLogin(), ticket.getRoute()));
            ps.execute();

            // close connection
            ps.close();
            connection.close();
        }
        catch (SQLException exception) {
            System.out.println(exception.getMessage() + " - error caused in TicketRepository.addTicket() method.");
        }
    }
}
