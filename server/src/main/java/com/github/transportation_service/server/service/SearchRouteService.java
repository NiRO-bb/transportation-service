package com.github.transportation_service.server.service;

import com.github.transportation_service.server.repository.Result;
import com.github.transportation_service.server.repository.SearchRouteRepository;
import com.github.transportation_service.server.repository.entity.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
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
    public Result searchCustom(
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
        route.setTransport(transport);

        // получить список маршрутов из БД на основе введенных параметров
        return searchRouteRepository.getRouteByParams(route);
    }

    // получить список всех маршрутов по дате
    public Result searchGlobal(String date) {
        return searchRouteRepository.getRouteByDate(date);
    }

    // получить количество свободных мест маршрута
    public int getFreePlaces(int routeId) {
        return searchRouteRepository.getPlaceByRouteId(routeId);
    }
}
