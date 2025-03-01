package com.github.transportation_service.server.repository;

import com.github.transportation_service.server.repository.entity.Route;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class SearchTicketRepository {

    private String url = "jdbc:sqlite:src/main/resources/transportation_service.db";
    private Connection connection;

    private Statement s;
    private PreparedStatement ps;
    private ResultSet resultSet;

    // получить маршруты при всех заполненных полях
    public List<Route> searchRoutes(Route route) {

        List<Route> routes = new ArrayList<>();

        try {
            // create connection
            connection = DriverManager.getConnection(url);
            s = connection.createStatement();
            resultSet = s.executeQuery(("SELECT * "
                    + "FROM ROUTE "
                    + "WHERE TRANSPORT = '%s' "
                    + "AND DEPARTURE_POINT = '%s' "
                    + "AND ARRIVAL_POINT = '%s' "
                    + "AND DEPARTURE_DATE = '%s'")
                    .formatted(route.getTransport(), route.getDeparturePoint(), route.getArrivalPoint(), route.getDepartureDate()));

            // add time field
            //
            // ...
            //

            // get query result
            while (resultSet.next()) {
                routes.add(new Route(
                        resultSet.getInt("ID"),
                        resultSet.getString("TRANSPORT"),
                        resultSet.getInt("PLACES"),
                        resultSet.getString("DEPARTURE_POINT"),
                        resultSet.getString("ARRIVAL_POINT"),
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

    // получить маршруты при частично заполненных полях
    public List<Route> searchGlobalRoutes(Route route) {

        List<Route> routes = new ArrayList<>();

        try {
            // open connection
            connection = DriverManager.getConnection(url);
            s = connection.createStatement();

            String query = "SELECT * FROM ROUTE WHERE DEPARTURE_POINT = '%s' AND ARRIVAL_POINT = '%s'".formatted(route.getDeparturePoint(), route.getArrivalPoint());

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
                        resultSet.getString("DEPARTURE_POINT"),
                        resultSet.getString("ARRIVAL_POINT"),
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
}