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
            // проверка на существование введенных логина и маршрута
            String userLogin = jdbcTemplate.queryForObject("SELECT LOGIN FROM USER WHERE LOGIN = ?", String.class, ticket.getUserLogin());
            int route = jdbcTemplate.queryForObject("SELECT ID FROM ROUTE WHERE ID = ?", Integer.class, ticket.getRoute());

            return jdbcTemplate.update("INSERT INTO TICKET(USER_LOGIN, ROUTE) VALUES(?, ?)", userLogin, route);
        } catch (DataAccessException exception) {
            return 0;
        }
    }

    // удалить билет из базы данных
    public int removeTicket(int ticketId) {
        try {
            return jdbcTemplate.update("DELETE FROM TICKET WHERE ID = ?", ticketId);
        } catch (DataAccessException exception) {
            return 0;
        }
    }

    // получить билеты по id пользователя
    public Result getTicketByUserLogin(String userLogin) {
        try {
            // проверка на существование логина
            String login = jdbcTemplate.queryForObject("SELECT LOGIN FROM USER WHERE LOGIN = ?", String.class, userLogin);

            List<Ticket> tickets = jdbcTemplate.query("SELECT * FROM TICKET WHERE USER_LOGIN = ?", new TicketRowMapper(), login);
            return new Result(tickets, true);
        } catch (DataAccessException exception) {
            return new Result(null, false);
        }
    }
}