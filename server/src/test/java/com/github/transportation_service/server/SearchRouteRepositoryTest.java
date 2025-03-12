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

    // getRouteById()
    @Test
    public void shouldReturnRoute() {
        route = searchRouteRepository.getRouteById(1);

        Assertions.assertEquals(10, route.getPlaces());
    }

    // getRouteByUserLogin()
    @Test
    public void shouldReturnRouteListByLogin() {
        routes = searchRouteRepository.getRouteByUserLogin("test");

        Assertions.assertEquals(2, routes.size());
    }

    // getRouteByParams()
    @Test
    public void shouldReturnRouteListByRouteInfo() {
        routes = searchRouteRepository.getRouteByParams(searchRouteRepository.getRouteById(1));

        Assertions.assertEquals(1, routes.size());
    }

    // getRouteByDate()
    @Test
    public void shouldReturnRouteListByDate() {
        routes = searchRouteRepository.getRouteByDate("");

        Assertions.assertEquals(1, routes.size());

        routes = searchRouteRepository.getRouteByDate("2026-01-01");

        Assertions.assertEquals(0, routes.size());
    }

    // getPlaceByRouteId()
    @Test
    public void shouldReturnPlacesByRouteId() {
        int places = searchRouteRepository.getPlaceByRouteId(2);

        Assertions.assertEquals(19, places);
    }
}