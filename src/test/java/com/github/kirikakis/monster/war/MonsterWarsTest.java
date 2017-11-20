package com.github.kirikakis.monster.war;

import com.github.kirikakis.monster.war.exceptions.MonstersMoreThanCitiesException;
import com.github.kirikakis.monster.war.model.City;
import com.github.kirikakis.monster.war.model.Monster;
import com.github.kirikakis.monster.war.model.NeighborCity;
import com.github.kirikakis.monster.war.utilities.WarUtilities;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class MonsterWarsTest {

    private Map<String, City> citiesMap;
    private Set<Monster> monsters;
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

        monsters = new HashSet<>();
        monsters.add(firstMonster);
        monsters.add(secondMonster);
    }

    @Test
    public void initializeFields() {
        HashMap<String, City> givenCityMap = new HashMap<>();
        Set<Monster> givenMonsters = new HashSet<>();
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
    public void moveMonsterToNextCity() {
        when(firstMonster.getCurrentCity()).thenReturn(firstCity);
        when(firstCity.getNeighborCities()).thenReturn(Collections.singletonList(neighborCity1));
        when(secondCity.getMonster()).thenReturn(null);
        when(neighborCity1.getCity()).thenReturn(secondCity);

        MonsterWars monsterWars = new MonsterWars(citiesMap, Collections.singleton(firstMonster));

        City nextMonsterCity = monsterWars.chooseNextRandomCity(firstMonster);

        assertEquals(secondCity, nextMonsterCity);
    }

    @Test
    public void moveMonsterToNextCityThrowsMonsterAlreadyInCityException() {
        when(firstMonster.getCurrentCity()).thenReturn(firstCity);
        when(firstCity.getNeighborCities()).thenReturn(Collections.emptyList());

        MonsterWars monsterWars = new MonsterWars(citiesMap, Collections.singleton(firstMonster));

        assertEquals(null, monsterWars.chooseNextRandomCity(firstMonster));
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

        assertEquals(0, monsters.size());
        assertEquals(1, citiesMap.size());
    }

    @Test
    public void startTheWar() {
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

    @Test
    public void realWarTest() throws IOException, MonstersMoreThanCitiesException {
        Map<String, City> returnedCities =
                WarUtilities.FetchMapDataFromFile("src/main/resources/map.txt");
        Set<Monster> monsterSet =
                WarUtilities.InitializeMonstersAndChooseThemCity(returnedCities, 2000);

        MonsterWars monsterWars = new MonsterWars(returnedCities, monsterSet);
        monsterWars.startTheWar(10000);

        //6763 records in src/main/resources/map.txt
        Integer destroyedCities = 6763 - monsterWars.getCitiesMap().size();

        //For every destroyed city 2 monsters should have been killed + remaining
        assertEquals(2000, (destroyedCities * 2) + monsterWars.getMonsters().size());
    }
}