package com.github.kirikakis.monster.war.model;

import java.util.ArrayList;
import java.util.List;

public class City {
    private final String name;
    private List<NeighborCity> neighborCities;
    private Monster monster;

    public City(String name) {
        this.name = name;
        neighborCities = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<NeighborCity> getNeighborCities() {
        return neighborCities;
    }

    public Monster getMonster() {
        return monster;
    }

    public void setMonster(Monster monster) {
        this.monster = monster;
    }

    public void removeConnection(City city) {
        List<NeighborCity> neighborCitiesToDelete = new ArrayList<>();
        for (NeighborCity neighborCity: neighborCities) {
            if(neighborCity.getCity() == city) {
                neighborCitiesToDelete.add(neighborCity);
            }
        }
        neighborCitiesToDelete.forEach(neighborCity -> neighborCities.remove(neighborCity));
    }
}
