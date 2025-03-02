package com.github.transportation_service.server.controller;

import com.github.transportation_service.server.repository.SearchRouteRepository;
import com.github.transportation_service.server.repository.entity.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RestController
public class SearchRouteController {

    @Autowired
    SearchRouteRepository searchRouteRepository;

    // получить список маршрутов на определенную дату
    @PostMapping("/search/specific")
    public List<Route> searchSpecific(@RequestBody Route route) {

        List<Route> routes;

        // получить список маршрутов из БД на основе введенных параметров
        routes = searchRouteRepository.searchRoutes(route);

        // сортировать список маршрутов (по времени)
        Collections.sort(routes, Comparator.comparing(Route::getDepartureTime));

        return routes;
    }

    // получить список маршрутов с разбивкой по датам времени
    @PostMapping("/search/global")
    public List<Route> searchGlobal(@RequestBody Route route) {

        List<Route> routes;

        // получить список маршрутов из БД на основе введенных параметров
        routes = searchRouteRepository.searchRoutes(route);

        // сортировать список маршрутов (по времени - датам)
        Collections.sort(routes, Comparator.comparing(Route::getDepartureTime));
        Collections.sort(routes, Comparator.comparing(Route::getDepartureDate));

        return routes;
    }

    // проверить наличие свободных мест на рейсе
    @GetMapping("/booking")
    public boolean isTherePlace(@RequestParam int routeId) {
        return searchRouteRepository.checkPlaces(routeId);
    }
}
