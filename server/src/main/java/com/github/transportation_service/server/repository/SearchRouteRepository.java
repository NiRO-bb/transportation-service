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

    private final long hours = 2; //

    // получить маршрут по id
    public Route getRoute(int routeId) {

        Route route = null;

        try {
            // open connection
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

            // close connection
            resultSet.close();
            s.close();
            connection.close();
        }
        catch (SQLException exception) {
            System.out.println(exception.getMessage() + " - error caused in SearchTicketRepository.getRoute() method.");
        }

        return route;
    }

    // получить маршруты согласно заданным параметрам
    public List<Route> searchRoutes(Route route) {

        List<Route> routes = new ArrayList<>();

        try {
            // open connection
            connection = DriverManager.getConnection(url);
            s = connection.createStatement();

            String query = "SELECT * FROM ROUTE WHERE DEPARTURE_POINT = '%s' AND ARRIVAL_POINT = '%s'".formatted(route.getDeparturePoint().toLowerCase(), route.getArrivalPoint().toLowerCase());

            // check transport field
            if (!route.getTransport().equals(""))
                query += " AND TRANSPORT = '%s'".formatted(route.getTransport());

            // check dates field
            if (route.getDepartureDate() != null)
                query += " AND DEPARTURE_DATE = '%s'".formatted(route.getDepartureDate());

            // check time field
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

            // close connection
            resultSet.close();
            s.close();
            connection.close();
        }
        catch (SQLException exception) {
            System.out.println(exception.getMessage() + " - error caused in SearchTicketRepository.searchRoutes() method.");
        }

        return routes;
    }

    // проверить количество свободных мест
    public int checkPlaces(int routeId) {

        int placeAmount = 0;

        try {
            // open connection
            connection = DriverManager.getConnection(url);
            s = connection.createStatement();
            resultSet = s.executeQuery("SELECT (ROUTE.PLACES - COUNT(TICKET.ID)) AS FREE_PLACES FROM ROUTE LEFT JOIN TICKET ON ROUTE.ID = TICKET.ROUTE WHERE ROUTE.ID = '%s'".formatted(routeId, routeId));

            while (resultSet.next()) {
                placeAmount = resultSet.getInt("FREE_PLACES");
            }

            // close connection
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