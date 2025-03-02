package com.github.transportation_service.server.controller;

import com.github.transportation_service.server.repository.TicketRepository;
import com.github.transportation_service.server.repository.entity.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class TicketController {

    @Autowired
    TicketRepository ticketRepository;

    // забронировать билет
    @PostMapping("/booking")
    public void bookTicket(@RequestBody Ticket ticket) {
        ticketRepository.addTicket(ticket);
    }

    // получить список забронированных билетов пользователя
    @PostMapping("/tickets")
    public List<Ticket> getTickets(@RequestParam String userLogin) {

        List<Ticket> tickets;

        tickets = ticketRepository.getUserTickets(userLogin);

        return tickets;
    }
}
