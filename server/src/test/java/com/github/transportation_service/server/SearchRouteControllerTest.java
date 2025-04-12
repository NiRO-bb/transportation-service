package com.github.transportation_service.server;

import com.github.transportation_service.server.controller.SearchRouteController;
import com.github.transportation_service.server.repository.entity.Route;
import com.github.transportation_service.server.service.SearchRouteService;
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
    private SearchRouteService searchRouteController;

    // searchCustom()
    @Test
    public void shouldReturnSortedRouteList() {

        List<Route> routes = searchRouteController.searchCustom("", "москва", "саратов", "", "");

        LocalDate date1 = routes.get(0).getDepartureDate();
        LocalDate date2 = routes.get(1).getDepartureDate();
        Assertions.assertTrue(date1.compareTo(date2) < 0);
    }
}
