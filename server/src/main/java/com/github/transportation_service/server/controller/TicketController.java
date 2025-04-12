package com.github.transportation_service.server.controller;

import com.github.transportation_service.server.repository.entity.Ticket;
import com.github.transportation_service.server.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Не удалось забронировать билет (ошибка сервера)");
    }

    // отменить бронь
    @GetMapping("ticket/cancel")
    public ResponseEntity<?> cancelTicket(@RequestParam int ticketId) {
        if (ticketService.cancelTicket(ticketId))
            return new ResponseEntity<>(true, HttpStatus.OK);
        else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Не удалось отменить бронь (ошибка сервера)");

    }

    // получить список забронированных билетов пользователя
    @GetMapping("/ticket/getTickets")
    public ResponseEntity<?> getTickets(@RequestParam String userLogin) {
        return new ResponseEntity<>(ticketService.getTickets(userLogin), HttpStatus.OK);
    }
}
