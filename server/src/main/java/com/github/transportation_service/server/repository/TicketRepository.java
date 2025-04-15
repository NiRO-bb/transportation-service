package com.github.transportation_service.server.repository;

import com.github.transportation_service.server.repository.entity.Ticket;

public interface TicketRepository {

    // добавить билет в базу данных
    int addTicket(Ticket ticket);

    // удалить билет из базы данных
    int removeTicket(int ticketId);

    // получить билеты по id пользователя
    Result getTicketByUserLogin(String userLogin);
}
