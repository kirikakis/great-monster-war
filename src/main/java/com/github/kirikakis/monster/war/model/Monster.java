package com.github.kirikakis.monster.war.model;

public class Monster {
    public String name;
    public City currentCity;

    public Monster(String name, City currentCity) {
        this.name = name;
        this.currentCity = currentCity;
    }
}
