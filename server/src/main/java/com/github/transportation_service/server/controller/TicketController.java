package com.github.transportation_service.server.controller;

import com.github.transportation_service.server.repository.Result;
import com.github.transportation_service.server.repository.entity.Ticket;
import com.github.transportation_service.server.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class TicketController {

    @Autowired
    TicketService ticketService;

    // забронировать билет
    @PostMapping("/ticket/book")
    public ResponseEntity<?> bookTicket(@RequestBody Ticket ticket) {
        if (ticketService.bookTicket(ticket))
            return new ResponseEntity<>(true, HttpStatus.OK);
        else
            return new ResponseEntity<>(new ErrorResponse(Collections.singletonList("Не удалось забронировать билет (ошибка сервера)"), HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // отменить бронь
    @GetMapping("ticket/cancel")
    public ResponseEntity<?> cancelTicket(@RequestParam int ticketId) {
        if (ticketService.cancelTicket(ticketId))
            return new ResponseEntity<>(true, HttpStatus.OK);
        else
            return new ResponseEntity<>(new ErrorResponse(Collections.singletonList("Не удалось забронировать билет (ошибка сервера)"), HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // получить список забронированных билетов пользователя
    @GetMapping("/ticket/getTickets")
    public ResponseEntity<?> getTickets(@RequestParam String userLogin) {
        Result result = ticketService.getTickets(userLogin);
        if (result.isCorrect()) {
            List<Ticket> tickets = (List<Ticket>) result.getData();
            if (!tickets.isEmpty())
                return new ResponseEntity<>(tickets, HttpStatus.OK);
            return new ResponseEntity<>(new ErrorResponse(Collections.singletonList("Список ваших забронированных билетов пуст"), HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new ErrorResponse(Collections.singletonList("Не удалось получить список забронированных билетов (ошибка сервера)"), HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}