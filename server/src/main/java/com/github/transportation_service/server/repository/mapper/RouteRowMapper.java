package com.github.transportation_service.server.repository.mapper;

import com.github.transportation_service.server.repository.entity.Route;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

public class RouteRowMapper implements RowMapper<Route> {
    @Override
    public Route mapRow(ResultSet rs, int rowNum) throws SQLException {

        return new Route(
                rs.getInt("ID"),
                rs.getString("TRANSPORT"),
                rs.getInt("PLACES"),
                rs.getString("DEPARTURE_POINT").toUpperCase(),
                rs.getString("ARRIVAL_POINT").toUpperCase(),
                LocalDate.parse(rs.getString("DEPARTURE_DATE")),
                LocalTime.parse(rs.getString("DEPARTURE_TIME")),
                LocalDate.parse(rs.getString("ARRIVAL_DATE")),
                LocalTime.parse(rs.getString("ARRIVAL_TIME"))
        );
    }
}
