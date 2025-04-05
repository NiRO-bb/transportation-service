package com.github.transportation_service.server.repository;

import com.github.transportation_service.server.repository.entity.Ticket;
import com.github.transportation_service.server.repository.mapper.TicketRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TicketRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // добавить билет в базу данных
    public boolean addTicket(Ticket ticket) {
        int result = jdbcTemplate.update("INSERT INTO TICKET(USER_LOGIN, ROUTE) VALUES(?, ?)", ticket.getUserLogin(), ticket.getRoute());
        return result != 0;
    }

    // удалить билет из базы данных
    public boolean removeTicket(int ticketId) {
        int result = jdbcTemplate.update("DELETE FROM TICKET WHERE ID = ?", ticketId);
        return result != 0;
    }

    // получить билеты по id пользователя
    public List<Ticket> getTicketByUserLogin(String userLogin) {
        return jdbcTemplate.query("SELECT * FROM TICKET WHERE USER_LOGIN = ?", new TicketRowMapper(), userLogin);
    }
}
