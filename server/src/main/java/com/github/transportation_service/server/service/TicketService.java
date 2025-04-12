package com.github.transportation_service.server.service;

import com.github.transportation_service.server.repository.TicketRepository;
import com.github.transportation_service.server.repository.entity.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TicketService {

    @Autowired
    TicketRepository ticketRepository;

    // забронировать билет
    public boolean bookTicket(Ticket ticket) {
        return ticketRepository.addTicket(ticket) > 0;
    }

    // отменить бронь
    public boolean cancelTicket(int ticketId) {
        return ticketRepository.removeTicket(ticketId) > 0;
    }

    // получить список забронированных билетов пользователя
    public List<Ticket> getTickets(String userLogin) {
        return ticketRepository.getTicketByUserLogin(userLogin);
    }
}
