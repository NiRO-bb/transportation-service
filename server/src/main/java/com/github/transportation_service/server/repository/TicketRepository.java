package com.github.transportation_service.server.repository;

import com.github.transportation_service.server.repository.entity.Ticket;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    // удалить билет из базы данных
    public boolean removeTicket(int ticketId) {
        boolean isRemoved = false;

        try {
            // open connection
            connection = DriverManager.getConnection(url);
            ps = connection.prepareStatement("DELETE FROM TICKET WHERE ID = '%s'".formatted(ticketId));

            ps.executeUpdate();
            isRemoved = true;

            // close connection
            ps.close();
            connection.close();
        }
        catch (SQLException exception) {
            System.out.println(exception.getMessage() + " - error caused in TicketRepository.removeTicket() method.");
        }

        return isRemoved;
    }

    // получить билеты по id пользователя
    public List<Ticket> getUserTickets(String userLogin) {

        List<Ticket> tickets = new ArrayList<>();

        try {
            // open connection
            connection = DriverManager.getConnection(url);
            s = connection.createStatement();
            resultSet = s.executeQuery("SELECT * FROM TICKET WHERE USER_LOGIN = '%s'".formatted(userLogin));

            while (resultSet.next()) {
                tickets.add(new Ticket(
                        resultSet.getInt("ID"),
                        resultSet.getString("USER_LOGIN"),
                        resultSet.getInt("ROUTE")));
            }

            // close connection
            resultSet.close();
            s.close();
            connection.close();
        }
        catch (SQLException exception) {
            System.out.println(exception.getMessage() + " - error caused in TicketRepository.getUserTickets() method.");
        }

        return tickets;
    }
}
