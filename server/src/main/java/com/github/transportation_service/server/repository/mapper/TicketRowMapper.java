package com.github.transportation_service.server.repository.mapper;

import com.github.transportation_service.server.repository.entity.Ticket;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TicketRowMapper implements RowMapper<Ticket> {
    @Override
    public Ticket mapRow(ResultSet rs, int rowNum) throws SQLException {

        return new Ticket(
                rs.getInt("ID"),
                rs.getString("USER_LOGIN"),
                rs.getInt("ROUTE"));
    }
}
