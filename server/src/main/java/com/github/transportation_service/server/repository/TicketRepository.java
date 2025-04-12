package com.github.transportation_service.server.repository;

import com.github.transportation_service.server.repository.entity.Ticket;

import java.util.List;

public interface TicketRepository {

    // добавить билет в базу данных
    int addTicket(Ticket ticket);

    // удалить билет из базы данных
    int removeTicket(int ticketId);

    // получить билеты по id пользователя
    List<Ticket> getTicketByUserLogin(String userLogin);
}
