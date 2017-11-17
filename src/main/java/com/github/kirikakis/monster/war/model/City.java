package com.github.kirikakis.monster.war.model;

import com.github.kirikakis.monster.war.exceptions.MonsterAlreadyInCityException;

public class City {
    private final String name;
    private City northernCity;
    private City easternCity;
    private City southernCity;
    private City westernCity;
    private Monster monster;

    public City(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public City getNorthernCity() {
        return northernCity;
    }

    public City getEasternCity() {
        return easternCity;
    }

    public City getSouthernCity() {
        return southernCity;
    }

    public City getWesternCity() {
        return westernCity;
    }

    public Monster getMonster() {
        return monster;
    }

    public void setNorthernCity(City northernCity) {
        this.northernCity = northernCity;
    }

    public void setEasternCity(City easternCity) {
        this.easternCity = easternCity;
    }

    public void setSouthernCity(City southernCity) {
        this.southernCity = southernCity;
    }

    public void setWesternCity(City westernCity) {
        this.westernCity = westernCity;
    }

    public void setMonster(Monster monster) throws MonsterAlreadyInCityException {
        if(this.monster != null) throw new MonsterAlreadyInCityException();
        this.monster = monster;
    }
}
