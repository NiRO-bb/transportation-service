package com.github.transportation_service.server;

import com.github.transportation_service.server.repository.SearchRouteRepository;
import com.github.transportation_service.server.repository.entity.Route;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class SearchRouteRepositoryTest {

    @Autowired
    private SearchRouteRepository searchRouteRepository;
    List<Route> routes;
    Route route;

    // getRoute()
    @Test
    public void shouldReturnRoute() {
        route = searchRouteRepository.getRoute(1);

        Assertions.assertEquals(10, route.getPlaces());
    }

    // getRouteList()
    @Test
    public void shouldReturnRouteListByLogin() {
        routes = searchRouteRepository.getRouteList("test");

        Assertions.assertEquals(2, routes.size());
    }

    // searchRoutes()
    @Test
    public void shouldReturnRouteListByRouteInfo() {
        routes = searchRouteRepository.searchRoutes(searchRouteRepository.getRoute(1));

        Assertions.assertEquals(1, routes.size());
    }

    // getRoutesByDate()
    @Test
    public void shouldReturnRouteListByDate() {
        routes = searchRouteRepository.getRoutesByDate("");

        Assertions.assertEquals(1, routes.size());

        routes = searchRouteRepository.getRoutesByDate("2026-01-01");

        Assertions.assertEquals(0, routes.size());
    }

    // checkPlaces()
    @Test
    public void shouldReturnPlacesByRouteId() {
        int places = searchRouteRepository.checkPlaces(2);

        Assertions.assertEquals(19, places);
    }
}