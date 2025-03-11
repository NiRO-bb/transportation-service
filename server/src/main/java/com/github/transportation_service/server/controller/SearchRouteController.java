package com.github.transportation_service.server.controller;

import com.github.transportation_service.server.repository.SearchRouteRepository;
import com.github.transportation_service.server.repository.entity.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class SearchRouteController {

    @Autowired
    SearchRouteRepository searchRouteRepository;

    // получить информация о маршруте по id
    @GetMapping("/search/route_info")
    public Route getRouteInfo(@RequestParam int routeId) {
        return searchRouteRepository.getRoute(routeId);
    }

    // получить список маршрутов по имени пользователя
    @GetMapping("/search/route_list")
    public List<Route> getRouteList(@RequestParam String userLogin) {
        return searchRouteRepository.getRouteList(userLogin);
    }

    // получить список маршрутов с разбивкой по датам времени
    @PostMapping("/search/global")
    public List<Route> searchGlobal(@RequestBody Route route) {

        // получить список маршрутов из БД на основе введенных параметров
        List<Route> routes = searchRouteRepository.searchRoutes(route);

        // сортировать список маршрутов (по времени - датам)
        Collections.sort(routes, Comparator.comparing(Route::getDepartureTime));
        Collections.sort(routes, Comparator.comparing(Route::getDepartureDate));

        return routes;
    }

    // получить все маршруты
    @GetMapping("/search/all")
    public List<Route> searchAll(@RequestParam String date) {

        // получить список маршрутов из БД
        List<Route> routes = searchRouteRepository.getRoutesByDate(date);

        // сортировать список маршрутов (по времени - датам)
        Collections.sort(routes, Comparator.comparing(Route::getDepartureTime));
        Collections.sort(routes, Comparator.comparing(Route::getDepartureDate));

        return routes;
    }

    // проверить количество свободных мест на рейсе
    @GetMapping("/search/place_checking")
    public int isTherePlace(@RequestParam int routeId) {
        return searchRouteRepository.checkPlaces(routeId);
    }
}
