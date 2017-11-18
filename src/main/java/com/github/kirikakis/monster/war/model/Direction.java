package com.github.kirikakis.monster.war.model;

public enum Direction {
    NORTH("north"),
    EAST("east"),
    SOUTH("south"),
    WEST("west");

    private String direction;

    Direction(String direction) {
        this.direction = direction;
    }

    @Override
    public String toString() {
        return direction;
    }
}
