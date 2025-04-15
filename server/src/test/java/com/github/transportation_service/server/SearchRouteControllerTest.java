package com.github.transportation_service.server;

import com.github.transportation_service.server.controller.SearchRouteController;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class SearchRouteControllerTest {

    @Autowired
    SearchRouteController c;

    @BeforeAll
    public static void setUp(@Autowired JdbcTemplate jdbcTemplate) {
        jdbcTemplate.update("INSERT INTO ROUTE VALUES (?, 'Авиа', 0, 'москва', 'казань', '2025-02-02', '02:00:00', '2025-02-03', '03:00:00')", 1);
        jdbcTemplate.update("INSERT INTO ROUTE VALUES (?, 'Авиа', 1, 'москва', 'казань', '2025-01-01', '01:00:00', '2025-01-02', '02:00:00')", 2);
        jdbcTemplate.update("INSERT INTO TICKET VALUES (?, 'login', 2)", 1);
    }

    @Test
    public void testGetRoute() {
        // корректные параметры
        ResponseEntity<?> result = c.getRoute(1);
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());

        // некорректные параметры
        result = c.getRoute(0);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    public void testGetRouteByLogin() {
        // корректные параметры
        ResponseEntity<?> result = c.getRouteByLogin("login");
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());

        // некорректные параметры
        result = c.getRouteByLogin("invalid login");
        Assertions.assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    public void testSearchCustom() {
        // корректные параметры
        ResponseEntity<?> result = c.searchCustom("", "москва", "казань", "", "");
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());

        // некорректные параметры
        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            c.searchCustom("", "", "", "", "");
        });

        result = c.searchCustom("", "москва", "казань", "123", "");
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());

        result = c.searchCustom("", "москва", "казань", "", "123");
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());

        result = c.searchCustom("", "wqer", "asdf", "", "");
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    public void testSearchGlobal() {
        // корректные параметры
        ResponseEntity<?> result = c.searchGlobal("");
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());

        // некорректные параметры
        result = c.searchGlobal("2026-01-01");
        Assertions.assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    public void testGetFreePlaces() {
        // корректные параметры
        ResponseEntity<?> result = c.getFreePlaces(1);
        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @AfterAll
    public static void tearDown(@Autowired JdbcTemplate jdbcTemplate) {
        jdbcTemplate.update("DELETE FROM ROUTE");
        jdbcTemplate.update("DELETE FROM TICKET");
    }
}
