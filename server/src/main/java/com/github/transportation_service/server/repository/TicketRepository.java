    package com.github.transportation_service.server.repository;

import com.github.transportation_service.server.repository.entity.Ticket;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class TicketRepository extends Repository{

    // добавить билет в базу данных
    public boolean addTicket(Ticket ticket) {

        boolean result = false;

        try {
            connection = DriverManager.getConnection(url);
            ps = connection.prepareStatement("INSERT INTO TICKET(USER_LOGIN, ROUTE) VALUES('%s', '%s')".formatted(ticket.getUserLogin(), ticket.getRoute()));

            if(ps.executeUpdate() > 0)
                result = true;

            ps.close();
            connection.close();
        }
        catch (SQLException exception) {
            System.out.println(exception.getMessage() + " - error caused in TicketRepository.addTicket() method.");
        }

        return result;
    }

    // удалить билет из базы данных
    public boolean removeTicket(int ticketId) {
        boolean isRemoved = false;

        try {
            connection = DriverManager.getConnection(url);
            ps = connection.prepareStatement("DELETE FROM TICKET WHERE ID = '%s'".formatted(ticketId));

            if (ps.executeUpdate() > 0)
                isRemoved = true;

            ps.close();
            connection.close();
        }
        catch (SQLException exception) {
            System.out.println(exception.getMessage() + " - error caused in TicketRepository.removeTicket() method.");
        }

        return isRemoved;
    }

    // получить билеты по id пользователя
    public List<Ticket> getTicketByUserLogin(String userLogin) {

        List<Ticket> tickets = new ArrayList<>();

        try {
            connection = DriverManager.getConnection(url);
            s = connection.createStatement();
            resultSet = s.executeQuery("SELECT * FROM TICKET WHERE USER_LOGIN = '%s'".formatted(userLogin));

            while (resultSet.next()) {
                tickets.add(new Ticket(
                        resultSet.getInt("ID"),
                        resultSet.getString("USER_LOGIN"),
                        resultSet.getInt("ROUTE")));
            }

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
