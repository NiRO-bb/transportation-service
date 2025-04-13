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

    // getRouteById()
    @Test
    public void shouldReturnRoute() {
        Route route = searchRouteRepository.getRouteById(1);
        Assertions.assertEquals(10, route.getPlaces());
    }

    // getRouteByUserLogin()
    @Test
    public void shouldReturnRouteListByLogin() {
        List<Route> routes = searchRouteRepository.getRouteByUserLogin("test");
        Assertions.assertEquals(3, routes.size());
    }

    // getRouteByParams()
    @Test
    public void shouldReturnRouteListByRouteInfo() {
        List<Route> routes = (List<Route>) searchRouteRepository.getRouteByParams(searchRouteRepository.getRouteById(1)).getData();

        Assertions.assertEquals(1, routes.size());
    }

    // getRouteByDate()
    @Test
    public void shouldReturnRouteListByDate() {
        List<Route> routes = (List<Route>) searchRouteRepository.getRouteByDate("").getData();
        Assertions.assertEquals(1, routes.size());

        routes = (List<Route>) searchRouteRepository.getRouteByDate("2026-01-01").getData();
        Assertions.assertEquals(0, routes.size());
    }

    // getPlaceByRouteId()
    @Test
    public void shouldReturnPlacesByRouteId() {
        int places = searchRouteRepository.getPlaceByRouteId(2);
        Assertions.assertEquals(19, places);
    }
}