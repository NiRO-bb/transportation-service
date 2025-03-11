package com.github.transportation_service.server;

import com.github.transportation_service.server.controller.SearchRouteController;
import com.github.transportation_service.server.repository.entity.Route;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class SearchRouteControllerTest {

    @Autowired
    private SearchRouteController searchRouteController;

    // searchGlobal() searchAll()
    @Test
    public void shouldReturnSortedRouteList() {
        Route route = new Route(0, "", 0, "москва", "саратов", null, null, null, null);

        List<Route> routes = searchRouteController.searchGlobal(route);

        LocalDate date1 = routes.get(0).getDepartureDate();
        LocalDate date2 = routes.get(1).getDepartureDate();
        Assertions.assertTrue(date1.compareTo(date2) < 0);

        routes = searchRouteController.searchAll("");

        date1 = routes.get(0).getDepartureDate();
        date2 = routes.get(1).getDepartureDate();
        Assertions.assertTrue(date1.compareTo(date2) < 0);
    }
}
