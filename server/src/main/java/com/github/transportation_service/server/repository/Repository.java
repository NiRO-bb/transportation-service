package com.github.transportation_service.server.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public abstract class Repository {

    protected String url = "jdbc:sqlite:src/main/resources/transportation_service.db";
    protected Connection connection;

    protected Statement s;
    protected PreparedStatement ps;
    protected ResultSet resultSet;
}
