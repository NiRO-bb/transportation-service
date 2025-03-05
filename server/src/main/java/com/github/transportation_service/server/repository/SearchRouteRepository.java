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
            //
            // ...
            //

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
            System.out.println(exception.getMessage() + " - error caused in SearchTicketRepository.searchGlobalRoutes() method.");
        }

        return routes;
    }

    // проверить наличие свободных мест
    public boolean checkPlaces(int routeId) {

        boolean result = false;

        try {
            // open connection
            connection = DriverManager.getConnection(url);
            s = connection.createStatement();
            resultSet = s.executeQuery("SELECT (ROUTE.PLACES - COUNT(TICKET.ID)) AS FREE_PLACES FROM ROUTE, TICKET WHERE ROUTE.ID = '%s' AND TICKET.ROUTE = '%s'".formatted(routeId, routeId));

            int placeAmount = 0;
            while (resultSet.next()) {
                placeAmount = resultSet.getInt("FREE_PLACES");
            }

            if (placeAmount > 0)
                result = true;

            // close connection
            resultSet.close();
            s.close();
            connection.close();
        }
        catch (SQLException exception) {
            System.out.println(exception.getMessage() + " - error caused in SearchTicketRepository.checkPlaces() method.");
        }

        return result;
    }
}