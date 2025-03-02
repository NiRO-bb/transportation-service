package com.github.transportation_service.server.controller;

import com.github.transportation_service.server.repository.TicketRepository;
import com.github.transportation_service.server.repository.entity.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TicketController {

    @Autowired
    TicketRepository ticketRepository;

    // забронировать билет
    @PostMapping("/booking")
    public void bookTicket(@RequestBody Ticket ticket) {
        ticketRepository.addTicket(ticket);
    }
}
