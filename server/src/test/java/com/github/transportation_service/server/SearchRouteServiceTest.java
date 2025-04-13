package com.github.transportation_service.server;

import com.github.transportation_service.server.repository.Result;
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
public class SearchRouteServiceTest {

    @Autowired
    SearchRouteService searchRouteService;

    @Test
    public void shouldReturnSortedRouteList() {
        Result result = searchRouteService.searchCustom("", "москва", "саратов", "", "");
        Assertions.assertTrue(result.isCorrect());

        List<Route> routes = (List<Route>) result.getData();
        LocalDate date1 = routes.get(0).getDepartureDate();
        LocalDate date2 = routes.get(1).getDepartureDate();
        Assertions.assertTrue(date1.compareTo(date2) < 0);
    }
}