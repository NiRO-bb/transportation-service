package com.github.transportation_service.server.controller;

import com.github.transportation_service.server.repository.SearchRouteRepository;
import com.github.transportation_service.server.repository.entity.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class SearchRouteController {

    @Autowired
    SearchRouteRepository searchRouteRepository;

    // получить информация о маршруте по его id
    @GetMapping("/search/getRoute")
    public Route getRoute(@RequestParam int routeId) {
        return searchRouteRepository.getRouteById(routeId);
    }

    // получить список маршрутов по логину пользователя
    @GetMapping("/search/getRouteByLogin")
    public List<Route> getRouteByLogin(@RequestParam String userLogin) {
        return searchRouteRepository.getRouteByUserLogin(userLogin);
    }

    // получить список маршрутов на основе заполненных данных
    @GetMapping("/search/custom")
    public List<Route> searchCustom(
            @RequestParam String transport,
            @RequestParam String departurePoint,
            @RequestParam String arrivalPoint,
            @RequestParam String departureDate,
            @RequestParam String departureTime) {

        Route route = new Route();
        route.setDeparturePoint(departurePoint);
        route.setArrivalPoint(arrivalPoint);
        route.setDepartureDate(departureDate.equals("") ? null : LocalDate.parse(departureDate));
        route.setDepartureTime(departureTime.equals("") ? null : LocalTime.parse(departureTime));
        route.setTransport(URLDecoder.decode(transport));

        // получить список маршрутов из БД на основе введенных параметров
        List<Route> routes = searchRouteRepository.getRouteByParams(route);

        // сортировать список маршрутов (по времени - датам)
        Collections.sort(routes, Comparator.comparing(Route::getDepartureTime));
        Collections.sort(routes, Comparator.comparing(Route::getDepartureDate));

        return routes;
    }

    // получить список всех маршрутов по дате
    @GetMapping("/search/global")
    public List<Route> searchGlobal(@RequestParam String date) {

        // получить список маршрутов из БД
        List<Route> routes = searchRouteRepository.getRouteByDate(date);

        // сортировать список маршрутов (по времени - датам)
        Collections.sort(routes, Comparator.comparing(Route::getDepartureTime));
        Collections.sort(routes, Comparator.comparing(Route::getDepartureDate));
        return routes;
    }

    // получить количество свободных мест маршрута
    @GetMapping("/search/getFreePlaces")
    public int getFreePlaces(@RequestParam int routeId) {
        return searchRouteRepository.getPlaceByRouteId(routeId);
    }
}
