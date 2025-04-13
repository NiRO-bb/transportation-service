package com.github.transportation_service.server.repository;

public class Result<T> {
    private T data;
    private boolean isCorrect;

    public Result() {}

    public Result(T data, boolean isCorrect) {
        this.data = data;
        this.isCorrect = isCorrect;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }
}
