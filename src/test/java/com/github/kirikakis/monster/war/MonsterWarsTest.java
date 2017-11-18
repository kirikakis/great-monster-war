package com.github.kirikakis.monster.war;

import com.github.kirikakis.monster.war.exceptions.MonsterAlreadyInCityException;
import com.github.kirikakis.monster.war.exceptions.MonstersMoreThanCitiesException;
import com.github.kirikakis.monster.war.exceptions.NoMonstersLeftException;
import com.github.kirikakis.monster.war.model.City;
import com.github.kirikakis.monster.war.model.Monster;
import com.github.kirikakis.monster.war.model.NeighborCity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class MonsterWarsTest {

    private Map<String, City> citiesMap;
    private List<Monster> monsters;
    private List<NeighborCity> neighborCities;

    @Mock
    private City firstCity;

    @Mock
    private City secondCity;

    @Mock
    private NeighborCity neighborCity1;

    @Mock
    private NeighborCity neighborCity2;

    @Mock
    private Monster firstMonster;

    @Mock
    private Monster secondMonster;

    @Before
    public void setUp() throws Exception {
        citiesMap = new HashMap<>();
        citiesMap.put("firstCity", firstCity);
        citiesMap.put("secondCity", secondCity);

        neighborCities = new ArrayList<>();
        neighborCities.add(neighborCity1);
        neighborCities.add(neighborCity2);

        monsters = new ArrayList<>();
        monsters.add(firstMonster);
        monsters.add(secondMonster);
    }

    @Test
    public void initializeFields() {
        HashMap<String, City> givenCityMap = new HashMap<>();
        List<Monster> givenMonsters = new ArrayList<>();
        MonsterWars monsterWars = new MonsterWars(givenCityMap, givenMonsters);

        assertEquals(givenCityMap, monsterWars.getCitiesMap());
        assertEquals(givenMonsters, monsterWars.getMonsters());
    }

    @Test
    public void removeAllMonstersFromCitiesEnRouteToTheNextOne()
            throws MonstersMoreThanCitiesException {
        MonsterWars monsterWars = new MonsterWars(citiesMap, monsters);

        monsterWars.removeAllMonstersFromCitiesEnRouteToTheNextOne();

        verify(firstCity, times(1)).setMonster(isNull(Monster.class));
        verify(secondCity, times(1)).setMonster(isNull(Monster.class));
    }

    @Test
    public void moveMonsterToNextCity()
            throws MonsterAlreadyInCityException {
        when(firstMonster.getCurrentCity()).thenReturn(firstCity);
        when(firstCity.getNeighborCities()).thenReturn(Collections.singletonList(neighborCity1));
        when(secondCity.getMonster()).thenReturn(null);
        when(neighborCity1.getCity()).thenReturn(secondCity);

        MonsterWars monsterWars = new MonsterWars(citiesMap, Collections.singletonList(firstMonster));

        monsterWars.moveMonsterToNextCityRandomly(firstMonster);

        verify(firstMonster, times(1)).setCurrentCity(secondCity);
    }

    @Test(expected = MonsterAlreadyInCityException.class)
    public void moveMonsterToNextCityThrowsMonsterAlreadyInCityException()
            throws MonsterAlreadyInCityException {
        when(firstMonster.getCurrentCity()).thenReturn(firstCity);
        when(firstCity.getNeighborCities()).thenReturn(Collections.singletonList(neighborCity1));
        when(secondCity.getMonster()).thenReturn(firstMonster);
        when(neighborCity1.getCity()).thenReturn(secondCity);

        MonsterWars monsterWars = new MonsterWars(citiesMap, Collections.singletonList(firstMonster));

        monsterWars.moveMonsterToNextCityRandomly(firstMonster);
    }

    @Test
    public void moveAllMonstersToNextCityRandomly() throws MonstersMoreThanCitiesException {
        when(firstMonster.getCurrentCity()).thenReturn(firstCity);
        when(firstCity.getNeighborCities()).thenReturn(Collections.singletonList(neighborCity1));
        when(neighborCity1.getCity()).thenReturn(secondCity);
        when(secondCity.getMonster()).thenReturn(null);

        when(secondMonster.getCurrentCity()).thenReturn(secondCity);
        when(secondCity.getNeighborCities()).thenReturn(Collections.singletonList(neighborCity2));
        when(neighborCity2.getCity()).thenReturn(firstCity);
        when(firstCity.getMonster()).thenReturn(null);

        MonsterWars monsterWars = new MonsterWars(citiesMap, monsters);

        monsterWars.moveAllMonstersToNextCityRandomlyAndFight();

        //removeAllMonstersFromCitiesEnRouteToTheNextOne()
        verify(firstCity, times(1)).setMonster(isNull(Monster.class));
        verify(secondCity, times(1)).setMonster(isNull(Monster.class));

        verify(firstMonster, times(1)).setCurrentCity(secondCity);
        verify(secondMonster, times(1)).setCurrentCity(firstCity);

        assertEquals(2, monsters.size());
        assertEquals(2, citiesMap.size());
    }

    @Test
    public void moveAllMonstersToNextCityRandomlyAndFight() throws MonstersMoreThanCitiesException {
        when(firstMonster.getCurrentCity()).thenReturn(firstCity);
        when(firstCity.getNeighborCities()).thenReturn(Collections.singletonList(neighborCity1));
        when(neighborCity1.getCity()).thenReturn(secondCity);
        when(secondCity.getMonster()).thenReturn(secondMonster);
        when(secondCity.getName()).thenReturn("secondCity");
        when(firstMonster.getName()).thenReturn("firstMonster");
        when(secondMonster.getName()).thenReturn("secondMonster");
        when(secondCity.getName()).thenReturn("secondCity");

        when(secondMonster.getCurrentCity()).thenReturn(secondCity);
        when(secondCity.getNeighborCities()).thenReturn(Collections.singletonList(neighborCity2));
        when(neighborCity2.getCity()).thenReturn(firstCity);
        when(firstCity.getMonster()).thenReturn(firstMonster);
        when(firstCity.getName()).thenReturn("firstCity");
        when(secondMonster.getName()).thenReturn("secondMonster");
        when(firstMonster.getName()).thenReturn("firstMonster");
        when(firstCity.getName()).thenReturn("firstCity");



        MonsterWars monsterWars = new MonsterWars(citiesMap, monsters);

        monsterWars.moveAllMonstersToNextCityRandomlyAndFight();

        //removeAllMonstersFromCitiesEnRouteToTheNextOne()
        verify(firstCity, times(1)).setMonster(isNull(Monster.class));
        verify(secondCity, times(1)).setMonster(isNull(Monster.class));

        assertEquals(0, monsters.size());
        assertEquals(0, citiesMap.size());
    }

    @Test
    public void startTheWar() throws NoMonstersLeftException {
        when(firstMonster.getCurrentCity()).thenReturn(firstCity);
        when(firstCity.getNeighborCities()).thenReturn(Collections.singletonList(neighborCity1));
        when(neighborCity1.getCity()).thenReturn(secondCity);
        when(secondCity.getMonster()).thenReturn(null);

        when(secondMonster.getCurrentCity()).thenReturn(secondCity);
        when(secondCity.getNeighborCities()).thenReturn(Collections.singletonList(neighborCity2));
        when(neighborCity2.getCity()).thenReturn(firstCity);
        when(firstCity.getMonster()).thenReturn(null);

        MonsterWars monsterWars = new MonsterWars(citiesMap, monsters);

        monsterWars.startTheWar(10);

        //removeAllMonstersFromCitiesEnRouteToTheNextOne()
        verify(firstCity, times(10)).setMonster(isNull(Monster.class));
        verify(secondCity, times(10)).setMonster(isNull(Monster.class));

        verify(firstMonster, times(10)).setCurrentCity(secondCity);
        verify(secondMonster, times(10)).setCurrentCity(firstCity);

        assertEquals(2, monsters.size());
        assertEquals(2, citiesMap.size());
    }

    @Test(expected = NoMonstersLeftException.class)
    public void startTheWarThrowsNoMonstersLeftException() throws NoMonstersLeftException {
        MonsterWars monsterWars = new MonsterWars(citiesMap, Collections.emptyList());
        monsterWars.startTheWar(10);
    }
}