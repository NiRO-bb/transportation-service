package com.github.transportation_service.server;

import com.github.transportation_service.server.repository.Result;
import com.github.transportation_service.server.repository.SearchRouteRepository;
import com.github.transportation_service.server.repository.entity.Route;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class SearchRouteRepositoryTest {

    @Autowired
    private SearchRouteRepository r;

    @BeforeAll
    public static void setUp(@Autowired JdbcTemplate jdbcTemplate) {
        jdbcTemplate.update("INSERT INTO ROUTE VALUES (?, 'Авиа', 0, 'москва', 'казань', '2025-02-02', '02:00:00', '2025-02-03', '03:00:00')", 1);
        jdbcTemplate.update("INSERT INTO ROUTE VALUES (?, 'Авиа', 1, 'москва', 'казань', '2025-01-01', '01:00:00', '2025-01-02', '02:00:00')", 2);
        jdbcTemplate.update("INSERT INTO TICKET VALUES (?, 'login', 2)", 1);
    }

    @Test
    public void testGetRouteById() {
        // корректные параметры
        Route result = r.getRouteById(1);
        Assertions.assertNotNull(result);

        // некорректный маршрут
        result = r.getRouteById(0);
        Assertions.assertNull(result);
    }

    @Test
    public void testGetRouteByUserLogin() {
        // корректные параметры
        List<Route> result = r.getRouteByUserLogin("login");
        Assertions.assertNotNull(result);

        result = r.getRouteByUserLogin("login2");
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void testGetRouteByParams() {
        // корректные параметры
        Result result = r.getRouteByParams(new Route(0, "", 0, "москва", "казань", null, null, null, null));
        List<Route> routes = (List<Route>) result.getData();
        Assertions.assertEquals(2, routes.size());
        Assertions.assertTrue(routes.get(0).getDepartureDate().isBefore(routes.get(1).getDepartureDate()));


        result = r.getRouteByParams(new Route(0, "Авиа", 0, "москва", "казань", LocalDate.parse("2025-01-01"), LocalTime.parse("01:00:00"), null, null));
        Assertions.assertEquals(1, ((List<Route>) result.getData()).size());

        // отсутствующий маршрут
        result = r.getRouteByParams(new Route(0, "", 0, "уфа", "пермь", null, null, null, null));
        Assertions.assertEquals(0, ((List<Route>) result.getData()).size());
    }

    @Test
    public void testGetRouteByDate() {
        // первые по дате маршруты
        Result result = r.getRouteByDate("");
        Assertions.assertEquals(1, ((List<Route>) result.getData()).size());

        // маршруты по дате
        result = r.getRouteByDate("2025-01-01");
        Assertions.assertEquals(1, ((List<Route>) result.getData()).size());

        // дата, не соответствующая ни одному маршруту
        result = r.getRouteByDate("2026-01-01");
        Assertions.assertEquals(0, ((List<Route>) result.getData()).size());
    }

    @Test
    public void testGetPlaceByRouteId() {
        // корректные параметры
        int result = r.getPlaceByRouteId(1);
        Assertions.assertEquals(0, result);

        result = r.getPlaceByRouteId(2);
        Assertions.assertEquals(0, result);

        // некорректные параметры
        result = r.getPlaceByRouteId(0);
        Assertions.assertEquals(-1, result);
    }

    @AfterAll
    public static void tearDown(@Autowired JdbcTemplate jdbcTemplate) {
        jdbcTemplate.update("DELETE FROM ROUTE");
        jdbcTemplate.update("DELETE FROM TICKET");
    }
}