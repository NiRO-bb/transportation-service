package com.github.transportation_service.server.repository;

import org.springframework.beans.factory.annotation.Value;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public abstract class Repository {

    @Value("${dbURL}")
    protected String url;
    protected Connection connection;

    protected Statement s;
    protected PreparedStatement ps;
    protected ResultSet resultSet;
}
