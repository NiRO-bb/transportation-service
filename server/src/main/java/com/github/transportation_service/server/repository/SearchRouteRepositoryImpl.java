package com.github.transportation_service.server.repository;

import com.github.transportation_service.server.repository.entity.Route;
import com.github.transportation_service.server.repository.mapper.RouteRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SearchRouteRepositoryImpl implements SearchRouteRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final long hours = 2;

    // получить маршрут по id
    public Route getRouteById(int routeId) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM ROUTE WHERE ID = ?", new RouteRowMapper(), routeId);
        } catch (DataAccessException exception) {
            return null;
        }
    }

    // получить маршруты по логину пользователя
    public List<Route> getRouteByUserLogin(String userLogin) {
        try {
            return jdbcTemplate.query("SELECT ROUTE.* FROM ROUTE JOIN TICKET ON ROUTE.ID = TICKET.ROUTE WHERE TICKET.USER_LOGIN = ?", new RouteRowMapper(), userLogin);
        } catch (DataAccessException exception) {
            return null;
        }
    }

    // получить маршруты согласно заданным параметрам
    public Result getRouteByParams(Route route) {
        List<Object> params = new ArrayList<>();

        String query = "SELECT * FROM ROUTE WHERE DEPARTURE_POINT = ? AND ARRIVAL_POINT = ?";
        params.add(route.getDeparturePoint().toLowerCase());
        params.add(route.getArrivalPoint().toLowerCase());

        if (!route.getTransport().equals("")) {
            query += " AND TRANSPORT = ?";
            params.add(route.getTransport());
        }

        if (route.getDepartureDate() != null) {
            query += " AND DEPARTURE_DATE = ?";
            params.add(route.getDepartureDate());
        }

        if (route.getDepartureTime() != null) {
            LocalTime lowTimeBorder = route.getDepartureTime().minusHours(2);
            LocalTime highTimeBorder = route.getDepartureTime().plusHours(2);

            // если вышли за временные рамки одного дня
            if (route.getDepartureTime().minusHours(hours).getHour() > route.getDepartureTime().getHour()) {
                lowTimeBorder = LocalTime.parse("00:00:00");
            }
            if (route.getDepartureTime().plusHours(hours).getHour() < route.getDepartureTime().getHour()) {
                highTimeBorder = LocalTime.parse("23:59:59");
            }

            query += " AND DEPARTURE_TIME BETWEEN ? AND ?";
            params.add(lowTimeBorder);
            params.add(highTimeBorder);
        }

        try {
            List<Route> routes = jdbcTemplate.query(query, new RouteRowMapper(), params.toArray());
            return new Result(routes, true);
        } catch (DataAccessException exception) {
            return new Result(null, false);
        }
    }

    // получить маршруты по дате
    public Result getRouteByDate(String date) {
        List<Route> routes;

        try {
            if (date.length() == 0)
                routes = jdbcTemplate.query("SELECT * FROM ROUTE WHERE DEPARTURE_DATE = (SELECT MIN(DEPARTURE_DATE) FROM ROUTE)", new RouteRowMapper());
            else
                routes = jdbcTemplate.query("SELECT * FROM ROUTE WHERE DEPARTURE_DATE = ?", new RouteRowMapper(), LocalDate.parse(date).plusDays(1));

            // маршруты не найдены
            if (routes.isEmpty()) {
                String maxDate = jdbcTemplate.queryForObject("SELECT MAX(DEPARTURE_DATE) FROM ROUTE", String.class);

                if (!LocalDate.parse(maxDate).isBefore(LocalDate.parse(date).plusDays(1))) {
                    routes = (List<Route>) getRouteByDate(String.valueOf(LocalDate.parse(date).plusDays(1))).getData();
                }
            }

            return new Result(routes, true);
        } catch (DataAccessException exception) {
            return new Result(null, false);
        }
    }

    // проверить количество свободных мест
    public int getPlaceByRouteId(int routeId) {
        try {
            return jdbcTemplate.queryForObject("SELECT (ROUTE.PLACES - COUNT(TICKET.ID)) AS FREE_PLACES FROM ROUTE LEFT JOIN TICKET ON ROUTE.ID = TICKET.ROUTE WHERE ROUTE.ID = ?", Integer.class, routeId);
        } catch (DataAccessException exception) {
            return -1;
        }
    }
}