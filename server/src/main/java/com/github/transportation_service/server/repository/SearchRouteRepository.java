package com.github.transportation_service.server.repository;

import com.github.transportation_service.server.repository.entity.Route;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class SearchRouteRepository extends Repository {

    private final long hours = 2;

    // получить маршрут по id
    public Route getRouteById(int routeId) {

        Route route = null;

        try {
            connection = DriverManager.getConnection(url);
            s = connection.createStatement();
            resultSet = s.executeQuery("SELECT * FROM ROUTE WHERE ID = '%s'".formatted(routeId));

            while (resultSet.next()) {
                route = new Route(
                        resultSet.getInt("ID"),
                        resultSet.getString("TRANSPORT"),
                        resultSet.getInt("PLACES"),
                        resultSet.getString("DEPARTURE_POINT").toUpperCase(),
                        resultSet.getString("ARRIVAL_POINT").toUpperCase(),
                        LocalDate.parse(resultSet.getString("DEPARTURE_DATE")),
                        LocalTime.parse(resultSet.getString("DEPARTURE_TIME")),
                        LocalDate.parse(resultSet.getString("ARRIVAL_DATE")),
                        LocalTime.parse(resultSet.getString("ARRIVAL_TIME"))
                );
            }

            resultSet.close();
            s.close();
            connection.close();
        }
        catch (SQLException exception) {
            System.out.println(exception.getMessage() + " - error caused in SearchTicketRepository.getRoute() method.");
        }

        return route;
    }

    // получить маршруты по логину пользователя
    public List<Route> getRouteByUserLogin(String userLogin) {
        List<Route> routes = new ArrayList<>();

        try {
            connection = DriverManager.getConnection(url);
            s = connection.createStatement();

            resultSet = s.executeQuery("SELECT ROUTE.* FROM ROUTE JOIN TICKET ON ROUTE.ID = TICKET.ROUTE WHERE TICKET.USER_LOGIN = '%s'".formatted(userLogin));

            while (resultSet.next()) {
                routes.add(new Route(
                        resultSet.getInt("ID"),
                        resultSet.getString("TRANSPORT"),
                        resultSet.getInt("PLACES"),
                        resultSet.getString("DEPARTURE_POINT").toUpperCase(),
                        resultSet.getString("ARRIVAL_POINT").toUpperCase(),
                        LocalDate.parse(resultSet.getString("DEPARTURE_DATE")),
                        LocalTime.parse(resultSet.getString("DEPARTURE_TIME")),
                        LocalDate.parse(resultSet.getString("ARRIVAL_DATE")),
                        LocalTime.parse(resultSet.getString("ARRIVAL_TIME"))
                ));
            }

            resultSet.close();
            s.close();
            connection.close();
        }
        catch (SQLException exception) {
            System.out.println(exception.getMessage() + " - error caused in SearchTicketRepository.getRouteList() method.");
        }

        return routes;
    }

    // получить маршруты согласно заданным параметрам
    public List<Route> getRouteByParams(Route route) {

        List<Route> routes = new ArrayList<>();

        try {
            connection = DriverManager.getConnection(url);
            s = connection.createStatement();

            String query = "SELECT * FROM ROUTE WHERE DEPARTURE_POINT = '%s' AND ARRIVAL_POINT = '%s'".formatted(route.getDeparturePoint().toLowerCase(), route.getArrivalPoint().toLowerCase());

            if (!route.getTransport().equals(""))
                query += " AND TRANSPORT = '%s'".formatted(route.getTransport());

            if (route.getDepartureDate() != null)
                query += " AND DEPARTURE_DATE = '%s'".formatted(route.getDepartureDate());

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

                query += " AND DEPARTURE_TIME BETWEEN '%s' AND '%s'".formatted(lowTimeBorder, highTimeBorder);
            }

            resultSet = s.executeQuery(query);

            while (resultSet.next()) {
                routes.add(new Route(
                        resultSet.getInt("ID"),
                        resultSet.getString("TRANSPORT"),
                        resultSet.getInt("PLACES"),
                        resultSet.getString("DEPARTURE_POINT").toUpperCase(),
                        resultSet.getString("ARRIVAL_POINT").toUpperCase(),
                        LocalDate.parse(resultSet.getString("DEPARTURE_DATE")),
                        LocalTime.parse(resultSet.getString("DEPARTURE_TIME")),
                        LocalDate.parse(resultSet.getString("ARRIVAL_DATE")),
                        LocalTime.parse(resultSet.getString("ARRIVAL_TIME"))
                ));
            }

            resultSet.close();
            s.close();
            connection.close();
        }
        catch (SQLException exception) {
            System.out.println(exception.getMessage() + " - error caused in SearchTicketRepository.searchRoutes() method.");
        }

        return routes;
    }

    // получить маршруты по дате
    public List<Route> getRouteByDate(String date) {
        List<Route> routes = new ArrayList<>();

        try {
            connection = DriverManager.getConnection(url);
            s = connection.createStatement();

            String query = "SELECT * FROM ROUTE WHERE DEPARTURE_DATE = ";

            if (date.length() == 0)
                query += "(SELECT MIN(DEPARTURE_DATE) FROM ROUTE)";
            else {
                query += "'%s'".formatted(LocalDate.parse(date).plusDays(1));
            }

            resultSet = s.executeQuery(query);
            while (resultSet.next()) {
                routes.add(new Route(
                        resultSet.getInt("ID"),
                        resultSet.getString("TRANSPORT"),
                        resultSet.getInt("PLACES"),
                        resultSet.getString("DEPARTURE_POINT").toUpperCase(),
                        resultSet.getString("ARRIVAL_POINT").toUpperCase(),
                        LocalDate.parse(resultSet.getString("DEPARTURE_DATE")),
                        LocalTime.parse(resultSet.getString("DEPARTURE_TIME")),
                        LocalDate.parse(resultSet.getString("ARRIVAL_DATE")),
                        LocalTime.parse(resultSet.getString("ARRIVAL_TIME"))
                ));
            }

            resultSet.close();
            s.close();
            connection.close();

            // маршруты не найдены
            if (routes.isEmpty()) {
                connection = DriverManager.getConnection(url);
                s = connection.createStatement();
                resultSet = s.executeQuery("SELECT MAX(DEPARTURE_DATE) FROM ROUTE");

                String maxDate = "";

                while (resultSet.next()) {
                    maxDate = resultSet.getString("MAX(DEPARTURE_DATE)");
                }

                resultSet.close();
                s.close();
                connection.close();

                if (!LocalDate.parse(maxDate).isBefore(LocalDate.parse(date).plusDays(1))) {
                    routes = getRouteByDate(String.valueOf(LocalDate.parse(date).plusDays(1)));
                }
            }
        }
        catch (SQLException exception) {
            System.out.println(exception.getMessage() + " - error caused in SearchTicketRepository.getRoutesByDate() method.");
        }

        return routes;
    }

    // проверить количество свободных мест
    public int getPlaceByRouteId(int routeId) {

        int placeAmount = 0;

        try {
            connection = DriverManager.getConnection(url);
            s = connection.createStatement();
            resultSet = s.executeQuery("SELECT (ROUTE.PLACES - COUNT(TICKET.ID)) AS FREE_PLACES FROM ROUTE LEFT JOIN TICKET ON ROUTE.ID = TICKET.ROUTE WHERE ROUTE.ID = '%s'".formatted(routeId, routeId));

            while (resultSet.next()) {
                placeAmount = resultSet.getInt("FREE_PLACES");
            }

            resultSet.close();
            s.close();
            connection.close();
        }
        catch (SQLException exception) {
            System.out.println(exception.getMessage() + " - error caused in SearchTicketRepository.checkPlaces() method.");
        }

        return placeAmount;
    }
}