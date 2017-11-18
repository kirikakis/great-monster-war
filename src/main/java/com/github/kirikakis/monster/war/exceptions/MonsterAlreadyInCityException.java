package com.github.kirikakis.monster.war.exceptions;

import com.github.kirikakis.monster.war.model.City;

public class MonsterAlreadyInCityException extends Exception {
    private City city;

    public  MonsterAlreadyInCityException(City city) {
        super();
        this.city = city;
    }

    public City getCity() {
        return city;
    }
}
