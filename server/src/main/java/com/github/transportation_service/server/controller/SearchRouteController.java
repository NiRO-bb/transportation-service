package com.github.transportation_service.server.controller;

import com.github.transportation_service.server.repository.entity.Route;
import com.github.transportation_service.server.service.SearchRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class SearchRouteController {

    @Autowired
    SearchRouteService searchRouteService;

    // получить информация о маршруте по его id
    @GetMapping("/search/getRoute")
    public ResponseEntity<?> getRoute(@RequestParam int routeId) {
        Route route = searchRouteService.getRoute(routeId);
        if (route != null)
            return new ResponseEntity<>(route, HttpStatus.OK);
        else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Не удалось найти маршрут (ошибка сервера)");
    }

    // получить список маршрутов по логину пользователя
    @GetMapping("/search/getRouteByLogin")
    public ResponseEntity<?> getRouteByLogin(@RequestParam String userLogin) {
        List<Route> routes = searchRouteService.getRouteByLogin(userLogin);
        if (!routes.isEmpty())
            return new ResponseEntity<>(routes, HttpStatus.OK);
        else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Не удалось получить маршруты (ошибка сервера)");
    }

    // получить список маршрутов на основе заполненных данных
    @GetMapping("/search/custom")
    public ResponseEntity<?> searchCustom(@RequestParam String transport, @RequestParam String departurePoint, @RequestParam String arrivalPoint, @RequestParam String departureDate, @RequestParam String departureTime) {
        List<Route> routes = searchRouteService.searchCustom(transport, departurePoint, arrivalPoint, departureDate, departureTime);
        if (!routes.isEmpty())
            return new ResponseEntity<>(routes, HttpStatus.OK);
        else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Не удалось получить маршруты (ошибка сервера)");
    }

    // получить список всех маршрутов по дате
    @GetMapping("/search/global")
    public ResponseEntity<?> searchGlobal(@RequestParam String date) {
        List<Route> routes = searchRouteService.searchGlobal(date);
        if (!routes.isEmpty())
            return new ResponseEntity<>(routes, HttpStatus.OK);
        else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Не удалось получить маршруты (ошибка сервера)");
    }

    // получить количество свободных мест маршрута
    @GetMapping("/search/getFreePlaces")
    public ResponseEntity<?> getFreePlaces(@RequestParam int routeId) {
        int places = searchRouteService.getFreePlaces(routeId);
        if (places >= 0)
            return new ResponseEntity<>(places, HttpStatus.OK);
        else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Не удалось получить количество свободных мест (ошибка сервера)");
    }
}
