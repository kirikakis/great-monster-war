package com.github.kirikakis.monster.war.model;

public class NeighborCity {

    private Direction direction;
    private City city;

    public NeighborCity(Direction direction, City city) {
        this.direction = direction;
        this.city = city;
    }

    public Direction getDirection() {
        return direction;
    }

    public City getCity() {
        return city;
    }
}
