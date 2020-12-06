package com.number.app.repertoire.model;

public class model_number {

    long number;
    String name;
    long last_update;

    public model_number(long number, String name, long last_update) {
        this.number = number;
        this.name = name;
        this.last_update = last_update;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getLast_update() {
        return last_update;
    }

    public void setLast_update(long last_update) {
        this.last_update = last_update;
    }
}
