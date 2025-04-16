package com.github.transportation_service.server.controller;

import com.github.transportation_service.server.repository.Result;
import com.github.transportation_service.server.repository.entity.Route;
import com.github.transportation_service.server.service.SearchRouteService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@CrossOrigin(origins = "*")
@Validated
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
            return new ResponseEntity<>(new ErrorResponse(Collections.singletonList("Не удалось найти маршрут с указанным ID"), HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
    }

    // получить список маршрутов по логину пользователя
    @GetMapping("/search/getRouteByLogin")
    public ResponseEntity<?> getRouteByLogin(@RequestParam String userLogin) {
        List<Route> routes = searchRouteService.getRouteByLogin(userLogin);
        if (!routes.isEmpty())
            return new ResponseEntity<>(routes, HttpStatus.OK);
        else
            return new ResponseEntity<>(new ErrorResponse(Collections.singletonList("Не удалось получить маршруты (ошибка сервера)"), HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
    }

    // получить список маршрутов на основе заполненных данных
    @GetMapping("/search/custom")
    public ResponseEntity<?> searchCustom(
            @RequestParam String transport,
            @RequestParam
            @NotBlank(message = "Необходимо заполнить поле \"ОТКУДА\"!")
            String departurePoint,
            @RequestParam
            @NotBlank(message = "Необходимо заполнить поле \"КУДА\"!")
            String arrivalPoint,
            @RequestParam String departureDate, @RequestParam String departureTime) {

        // проверка на соответствие шаблонам
        List<String> messages = new ArrayList<>();
        if (!departureDate.trim().equals("") && !departureDate.trim().matches("\\d{4}-\\d{2}-\\d{2}"))
            messages.add("Указанная дата не соответствует формату ГГГГ-ММ-ДД (например, 2024-12-31)");
        if (!departureTime.trim().equals("") && !departureTime.trim().matches("\\d{2}:\\d{2}"))
            messages.add("Указанное время не соответствует формату ЧЧ:ММ (например, 09:00)");
        if (!messages.isEmpty())
            return new ResponseEntity<>(new ErrorResponse(messages, HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);

        Result result = searchRouteService.searchCustom(transport, departurePoint.trim(), arrivalPoint.trim(), departureDate.trim(), departureTime.trim());
        if (result.isCorrect()) {
            List<Route> routes = (List<Route>) result.getData();
            if (!routes.isEmpty())
                return new ResponseEntity<>(routes, HttpStatus.OK);
            else
                return new ResponseEntity<>(new ErrorResponse(Collections.singletonList("Не удалось найти маршруты, соответствующие вашему запросу"), HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
        }
        else
            return new ResponseEntity<>(new ErrorResponse(Collections.singletonList("Не удалось получить маршруты (ошибка сервера)"), HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // получить список всех маршрутов по дате
    @GetMapping("/search/global")
    public ResponseEntity<?> searchGlobal(@RequestParam String date) {
        Result result = searchRouteService.searchGlobal(date);

        if (result.isCorrect()) {
            List<Route> routes = (List<Route>) result.getData();
            if (!routes.isEmpty())
                return new ResponseEntity<>(routes, HttpStatus.OK);
            else
                return new ResponseEntity<>(new ErrorResponse(Collections.singletonList("Маршрутов нет"), HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
        }
        else
            return new ResponseEntity<>(new ErrorResponse(Collections.singletonList("Не удалось получить маршруты (ошибка сервера)"), HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // получить количество свободных мест маршрута
    @GetMapping("/search/getFreePlaces")
    public ResponseEntity<?> getFreePlaces(@RequestParam int routeId) {
        int places = searchRouteService.getFreePlaces(routeId);
        if (places >= 0)
            return new ResponseEntity<>(places, HttpStatus.OK);
        else
            return new ResponseEntity<>(new ErrorResponse(Collections.singletonList("Не удалось получить количество свободных мест (ошибка сервера)"), HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
