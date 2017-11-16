package com.github.kirikakis.monster.war.model;

public class City {
    private final String name;
    private City northernCity;
    private City easternCity;
    private City southernCity;
    private City westernCity;

    public City(String name) {
        this.name = name;
        this.northernCity = northernCity;
        this.easternCity = easternCity;
        this.southernCity = southernCity;
        this.westernCity = westernCity;
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
}
