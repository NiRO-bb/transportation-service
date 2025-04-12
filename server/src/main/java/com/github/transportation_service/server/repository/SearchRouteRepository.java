package com.github.transportation_service.server.repository;

import com.github.transportation_service.server.repository.entity.Route;
import java.util.List;

public interface SearchRouteRepository {

    // получить маршрут по id
    Route getRouteById(int routeId);

    // получить маршруты по логину пользователя
    List<Route> getRouteByUserLogin(String userLogin);

    // получить маршруты согласно заданным параметрам
    List<Route> getRouteByParams(Route route);

    // получить маршруты по дате
    List<Route> getRouteByDate(String date);

    // проверить количество свободных мест
    int getPlaceByRouteId(int routeId);
}