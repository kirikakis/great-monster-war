package com.github.kirikakis.monster.war;

import com.github.kirikakis.monster.war.exceptions.MonsterAlreadyInCityException;
import com.github.kirikakis.monster.war.exceptions.NoMonstersLeftException;
import com.github.kirikakis.monster.war.model.City;
import com.github.kirikakis.monster.war.model.Monster;
import com.github.kirikakis.monster.war.model.NeighborCity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

class MonsterWars {
    private Map<String, City> citiesMap;
    private List<Monster> monsters;

    MonsterWars(Map<String, City> citiesMap, List<Monster> monsters) {
        this.citiesMap = citiesMap;
        this.monsters = monsters;
    }

    Map<String, City> getCitiesMap() {
        return citiesMap;
    }

    List<Monster> getMonsters() {
        return monsters;
    }

    void startTheWar(Integer moves) throws NoMonstersLeftException {
        for (int i = 0; i < moves; i++) {
            if(monsters.size() == 0) {
                throw new NoMonstersLeftException();
            }
            removeAllMonstersFromCitiesEnRouteToTheNextOne();
            moveAllMonstersToNextCityRandomlyAndFight();
        }
    }

    void moveAllMonstersToNextCityRandomlyAndFight() {
        List<Monster> diedMonsters = new ArrayList<>();
        for(Monster monster : monsters) {
            try {
                moveMonsterToNextCityRandomly(monster);
            } catch (MonsterAlreadyInCityException e) {
                String cityNameToRemove = e.getCity().getName();
                System.out.println(
                        cityNameToRemove + " has been destroyed by monster " +
                    monster.getName() + " and monster " +
                    e.getCity().getMonster().getName() + "!");
                diedMonsters.add(monster);
                citiesMap.remove(cityNameToRemove);
                e.getCity().getNeighborCities().forEach(
                        neighborCity -> neighborCity.getCity().removeConnection(e.getCity()));
            }
        }
        diedMonsters.forEach(monster -> monsters.remove(monster));
    }

    void removeAllMonstersFromCitiesEnRouteToTheNextOne() {
        for(City city : citiesMap.values()) {
            city.setMonster(null);
        }
    }

    void moveMonsterToNextCityRandomly(Monster monster) throws MonsterAlreadyInCityException {
        List<NeighborCity> neighborCities = monster.getCurrentCity().getNeighborCities();
        if(neighborCities.size() > 0) {
            Integer neighborCitiesRandomIndex = new Random().nextInt(neighborCities.size());
            City nextMonsterCity = neighborCities.get(neighborCitiesRandomIndex).getCity();
            if (nextMonsterCity.getMonster() != null) {
                throw new MonsterAlreadyInCityException(nextMonsterCity);
            }
            nextMonsterCity.setMonster(monster);
            monster.setCurrentCity(nextMonsterCity);
        }
    }
}
