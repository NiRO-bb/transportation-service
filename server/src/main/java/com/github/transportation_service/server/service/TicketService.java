package com.github.transportation_service.server.service;

import com.github.transportation_service.server.repository.Result;
import com.github.transportation_service.server.repository.SearchRouteRepository;
import com.github.transportation_service.server.repository.TicketRepository;
import com.github.transportation_service.server.repository.entity.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketService {

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    SearchRouteRepository searchRouteRepository;

    // забронировать билет
    public boolean bookTicket(Ticket ticket) {
        // проверка на наличие свободных мест
        if (searchRouteRepository.getPlaceByRouteId(ticket.getRoute()) > 0)
            return ticketRepository.addTicket(ticket) > 0;
        return false;
    }

    // отменить бронь
    public boolean cancelTicket(int ticketId) {
        return ticketRepository.removeTicket(ticketId) > 0;
    }

    // получить список забронированных билетов пользователя
    public Result getTickets(String userLogin) {
        return ticketRepository.getTicketByUserLogin(userLogin);
    }
}
