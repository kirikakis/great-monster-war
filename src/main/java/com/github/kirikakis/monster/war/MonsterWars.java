package com.github.kirikakis.monster.war;

import com.github.kirikakis.monster.war.exceptions.MonsterAlreadyInCityException;
import com.github.kirikakis.monster.war.exceptions.NoMonstersLeftException;
import com.github.kirikakis.monster.war.model.City;
import com.github.kirikakis.monster.war.model.Monster;
import com.github.kirikakis.monster.war.model.NeighborCity;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

class MonsterWars {
    private Map<String, City> citiesMap;
    private Set<Monster> monsters;

    MonsterWars(Map<String, City> citiesMap, Set<Monster> monsters) {
        this.citiesMap = citiesMap;
        this.monsters = monsters;
    }

    Map<String, City> getCitiesMap() {
        return citiesMap;
    }

    Set<Monster> getMonsters() {
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
        Set<Monster> diedMonsters = new HashSet<>();

        for(Monster monster : monsters) {
            if(!diedMonsters.contains(monster)) {
                try {
                    moveMonsterToNextCityRandomly(monster);
                } catch (MonsterAlreadyInCityException e) {
                    diedMonsters.addAll(monsterFight(monster, e.getCity()));
                }
            }
        }
        monsters.removeAll(diedMonsters);
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

    void removeAllMonstersFromCitiesEnRouteToTheNextOne() {
        citiesMap.forEach((cityName, city) -> city.setMonster(null));
    }

    Set<Monster> monsterFight(Monster monster, City cityToDestroy) {
        Set<Monster> diedMonsters = new HashSet<>();
        String cityNameToRemove = cityToDestroy.getName();
        Monster otherCityMonster = cityToDestroy.getMonster();
        System.out.println(
                cityNameToRemove + " has been destroyed by monster " +
                        monster.getName() + " and monster " +
                        otherCityMonster.getName() + "!");
        diedMonsters.add(monster);
        diedMonsters.add(otherCityMonster);
        citiesMap.remove(cityNameToRemove);
        cityToDestroy.getNeighborCities().forEach(
                neighborCity -> neighborCity.getCity().removeConnection(cityToDestroy));
        return diedMonsters;
    }
}
