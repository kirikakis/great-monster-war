package com.github.kirikakis.monster.war.utilities;

import com.github.kirikakis.monster.war.exceptions.MonstersMoreThanCitiesException;
import com.github.kirikakis.monster.war.model.City;
import com.github.kirikakis.monster.war.model.Monster;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class WarUtilitiesTest {

    private static Map<String, City> returnedCities;

    @BeforeClass
    public static void setUp() throws Exception {
        returnedCities =
                WarUtilities.FetchMapDataFromFile("src/main/resources/map.txt");
    }

    @Test
    public void fetchMapDataFromFile() throws Exception {

        assertEquals(6763, returnedCities.size());

        assertFalse(returnedCities.containsKey(""));

        assertEquals("Mosnino", returnedCities.get("Mosnino").getName());
        assertEquals(4, returnedCities.get("Mosnino").getNeighborCities().size());
        assertEquals("Mulemo", returnedCities.get("Mosnino").getNeighborCities().get(0).getCity().getName());
        assertEquals("Minimixixe", returnedCities.get("Mosnino").getNeighborCities().get(1).getCity().getName());
        assertEquals("Esmesme", returnedCities.get("Mosnino").getNeighborCities().get(2).getCity().getName());
        assertEquals("Magaxila", returnedCities.get("Mosnino").getNeighborCities().get(3).getCity().getName());

        assertEquals("Esmesme", returnedCities.get("Esmesme").getName());
        assertEquals(4, returnedCities.get("Esmesme").getNeighborCities().size());
        assertEquals("Mosnino", returnedCities.get("Esmesme").getNeighborCities().get(0).getCity().getName());
        assertEquals("Menosmila", returnedCities.get("Esmesme").getNeighborCities().get(1).getCity().getName());
        assertEquals("Emaxegu", returnedCities.get("Esmesme").getNeighborCities().get(2).getCity().getName());
        assertEquals("Disme", returnedCities.get("Esmesme").getNeighborCities().get(3).getCity().getName());

        assertEquals("Disme", returnedCities.get("Disme").getName());
        assertEquals(4, returnedCities.get("Disme").getNeighborCities().size());
        assertEquals("Magaxila", returnedCities.get("Disme").getNeighborCities().get(0).getCity().getName());
        assertEquals("Esmesme", returnedCities.get("Disme").getNeighborCities().get(1).getCity().getName());
        assertEquals("Digegixixa", returnedCities.get("Disme").getNeighborCities().get(2).getCity().getName());
        assertEquals("Alime", returnedCities.get("Disme").getNeighborCities().get(3).getCity().getName());

        assertEquals("Magaxila", returnedCities.get("Magaxila").getName());
        assertEquals(4, returnedCities.get("Magaxila").getNeighborCities().size());
        assertEquals("Amisno", returnedCities.get("Magaxila").getNeighborCities().get(0).getCity().getName());
        assertEquals("Mosnino", returnedCities.get("Magaxila").getNeighborCities().get(1).getCity().getName());
        assertEquals("Disme", returnedCities.get("Magaxila").getNeighborCities().get(2).getCity().getName());
        assertEquals("Egixano", returnedCities.get("Magaxila").getNeighborCities().get(3).getCity().getName());

        assertEquals("Menisnile", returnedCities.get("Menisnile").getName());
        assertEquals(4, returnedCities.get("Magaxila").getNeighborCities().size());
        assertEquals("Molusnu", returnedCities.get("Menisnile").getNeighborCities().get(0).getCity().getName());
        assertEquals("Dudisne", returnedCities.get("Menisnile").getNeighborCities().get(1).getCity().getName());
        assertEquals("Egolma", returnedCities.get("Menisnile").getNeighborCities().get(2).getCity().getName());
        assertEquals("Exexalinu", returnedCities.get("Menisnile").getNeighborCities().get(3).getCity().getName());
    }

    @Test
    public void initializeMonstersAndChooseThemCity() throws IOException, MonstersMoreThanCitiesException {
        List<Monster> monsterList =
                WarUtilities.InitializeMonstersAndChooseThemCity(returnedCities, 6763);

        assertEquals(6763, monsterList.size());

        monsterList.forEach(monster -> {
            boolean isMonsterCityFromCitiesMap = false;
            for(City city: returnedCities.values()) {
                if(monster.getCurrentCity().equals(city)) {
                    //returnedCities has to have unique values
                    assertFalse(isMonsterCityFromCitiesMap);
                    isMonsterCityFromCitiesMap = true;
                }
            }
            //Any of the returnedCities assigned to monster
            assertTrue(isMonsterCityFromCitiesMap);
            assertTrue(monster.getName().endsWith("r"));
        });

        returnedCities.values().forEach(city -> {
            boolean isCityBelongsToMoreThanOneMonster = false;
            for(Monster monster : monsterList) {
                if(city.equals(monster.getCurrentCity())) {
                    //The monster can't be in more than one city
                    assertFalse(isCityBelongsToMoreThanOneMonster);
                    isCityBelongsToMoreThanOneMonster = true;
                }
            }
        });
    }

    @Test(expected = MonstersMoreThanCitiesException.class)
    public void initializeMonstersAndChooseThemCityThrowsMonstersMoreThanCitiesException()
            throws IOException, MonstersMoreThanCitiesException {
        WarUtilities.InitializeMonstersAndChooseThemCity(returnedCities, 6764);
    }

}