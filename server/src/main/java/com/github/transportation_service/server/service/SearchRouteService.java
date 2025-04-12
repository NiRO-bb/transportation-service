package com.github.transportation_service.server.service;

import com.github.transportation_service.server.repository.SearchRouteRepository;
import com.github.transportation_service.server.repository.entity.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class SearchRouteService {

    @Autowired
    SearchRouteRepository searchRouteRepository;

    // получить информация о маршруте по его id
    public Route getRoute(int routeId) {
        return searchRouteRepository.getRouteById(routeId);
    }

    // получить список маршрутов по логину пользователя
    public List<Route> getRouteByLogin(String userLogin) {
        return searchRouteRepository.getRouteByUserLogin(userLogin);
    }

    // получить список маршрутов на основе заполненных данных
    public List<Route> searchCustom(
            String transport,
            String departurePoint,
            String arrivalPoint,
            String departureDate,
            String departureTime) {

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
    public List<Route> searchGlobal(String date) {

        // получить список маршрутов из БД
        List<Route> routes = searchRouteRepository.getRouteByDate(date);

        // сортировать список маршрутов (по времени - датам)
        Collections.sort(routes, Comparator.comparing(Route::getDepartureTime));
        Collections.sort(routes, Comparator.comparing(Route::getDepartureDate));
        return routes;
    }

    // получить количество свободных мест маршрута
    public int getFreePlaces(int routeId) {
        return searchRouteRepository.getPlaceByRouteId(routeId);
    }
}
