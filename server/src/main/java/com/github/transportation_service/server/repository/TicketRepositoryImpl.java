package com.github.transportation_service.server.repository;

import com.github.transportation_service.server.repository.entity.Ticket;
import com.github.transportation_service.server.repository.mapper.TicketRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TicketRepositoryImpl implements TicketRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // добавить билет в базу данных
    public int addTicket(Ticket ticket) {
        try {
            return jdbcTemplate.update("INSERT INTO TICKET(USER_LOGIN, ROUTE) VALUES(?, ?)", ticket.getUserLogin(), ticket.getRoute());
        } catch (DataAccessException exception) {
            System.out.println(exception.getMessage());
            return 0;
        }
    }

    // удалить билет из базы данных
    public int removeTicket(int ticketId) {
        try {
            return jdbcTemplate.update("DELETE FROM TICKET WHERE ID = ?", ticketId);
        } catch (DataAccessException exception) {
            System.out.println(exception.getMessage());
            return 0;
        }
    }

    // получить билеты по id пользователя
    public List<Ticket> getTicketByUserLogin(String userLogin) {
        try {
            return jdbcTemplate.query("SELECT * FROM TICKET WHERE USER_LOGIN = ?", new TicketRowMapper(), userLogin);
        } catch (DataAccessException exception) {
            System.out.println(exception.getMessage());
            return null;
        }
    }

}