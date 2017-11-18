package com.github.kirikakis.monster.war.model;

public class Monster implements Cloneable {
    private String name;
    private City currentCity;

    public Monster(String name, City currentCity) {
        this.name = name;
        this.currentCity = currentCity;
    }

    public String getName() {
        return name;
    }

    public City getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(City currentCity) {
        this.currentCity = currentCity;
    }

    @Override
    public Monster clone() {
        return new Monster(name, currentCity);
    }
}
