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

        // получить список маршрутов из БД по логину пользователя
        List<Route> routes = searchRouteRepository.getRouteList(userLogin);

        return routes;
    }

    // получить список маршрутов на определенную дату
    @PostMapping("/search/specific")
    public List<Route> searchSpecific(@RequestBody Route route) {

        // получить список маршрутов из БД на основе введенных параметров
        List<Route> routes = searchRouteRepository.searchRoutes(route);

        // сортировать список маршрутов (по времени)
        Collections.sort(routes, Comparator.comparing(Route::getDepartureTime));

        return routes;
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

    // проверить количество свободных мест на рейсе
    @GetMapping("/search/place_checking")
    public int isTherePlace(@RequestParam int routeId) {
        return searchRouteRepository.checkPlaces(routeId);
    }
}
