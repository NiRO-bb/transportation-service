package com.github.transportation_service.server.repository.mapper;

import com.github.transportation_service.server.repository.entity.Ticket;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TicketRowMapper implements RowMapper<List<Ticket>> {
    @Override
    public List<Ticket> mapRow(ResultSet rs, int rowNum) throws SQLException {
        List<Ticket> tickets = new ArrayList<>();

        do {
            tickets.add(new Ticket(
                    rs.getInt("ID"),
                    rs.getString("USER_LOGIN"),
                    rs.getInt("ROUTE")));
        } while (rs.next());

        return tickets;
    }
}
