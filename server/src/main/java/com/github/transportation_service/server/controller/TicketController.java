package com.github.transportation_service.server.controller;

import com.github.transportation_service.server.repository.TicketRepository;
import com.github.transportation_service.server.repository.entity.Route;
import com.github.transportation_service.server.repository.entity.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class TicketController {

    @Autowired
    TicketRepository ticketRepository;

    // забронировать билет
    @PostMapping("/ticket/book")
    public void bookTicket(@RequestBody Ticket ticket) {
        ticketRepository.addTicket(ticket);
    }

    // отменить бронь
    @GetMapping("ticket/cancel")
    public boolean cancelTicket(@RequestParam int ticketId) {
        return ticketRepository.removeTicket(ticketId);
    }

    // получить список забронированных билетов пользователя
    @GetMapping("/ticket/list")
    public List<Ticket> getTickets(@RequestParam String userLogin) {
        return ticketRepository.getUserTickets(userLogin);
    }
}
