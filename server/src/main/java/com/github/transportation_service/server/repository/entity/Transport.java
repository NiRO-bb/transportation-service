package com.github.transportation_service.server.repository.entity;

public enum Transport {

    AIRPLANE("airplane"),
    TRAIN("train"),
    BUS("bus"),
    MIX("mix");

    private String type;

    Transport(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
