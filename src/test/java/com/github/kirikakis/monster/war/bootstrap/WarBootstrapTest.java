package com.github.kirikakis.monster.war.bootstrap;

import com.github.kirikakis.monster.war.exceptions.MonsterAlreadyInCityException;
import com.github.kirikakis.monster.war.exceptions.MonstersMoreThanCitiesException;
import com.github.kirikakis.monster.war.model.City;
import com.github.kirikakis.monster.war.model.Monster;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class WarBootstrapTest {

    private static Map<String, City> returnedCities;

    @BeforeClass
    public static void setUp() throws Exception {
        returnedCities =
                WarBootstrap.FetchMapDataFromFile("src/main/resources/map.txt");
    }

    @Test
    public void fetchMapDataFromFile() throws Exception {

        assertEquals(6763, returnedCities.size());

        assertFalse(returnedCities.containsKey(""));

        assertThat(returnedCities.get("Mosnino").getName(), is("Mosnino"));
        assertThat(returnedCities.get("Mosnino").getEasternCity().getName(), is("Minimixixe"));
        assertThat(returnedCities.get("Mosnino").getNorthernCity().getName(), is("Mulemo"));
        assertThat(returnedCities.get("Mosnino").getSouthernCity().getName(), is("Esmesme"));
        assertThat(returnedCities.get("Mosnino").getWesternCity().getName(), is("Magaxila"));

        assertThat(returnedCities.get("Esmesme").getName(), is("Esmesme"));
        assertThat(returnedCities.get("Esmesme").getWesternCity().getName(), is("Disme"));
        assertThat(returnedCities.get("Esmesme").getSouthernCity().getName(), is("Emaxegu"));
        assertThat(returnedCities.get("Esmesme").getEasternCity().getName(), is("Menosmila"));
        assertThat(returnedCities.get("Esmesme").getNorthernCity().getName(), is("Mosnino"));

        assertThat(returnedCities.get("Disme").getName(), is("Disme"));
        assertThat(returnedCities.get("Disme").getEasternCity().getName(), is("Esmesme"));
        assertThat(returnedCities.get("Disme").getNorthernCity().getName(), is("Magaxila"));
        assertThat(returnedCities.get("Disme").getSouthernCity().getName(), is("Digegixixa"));
        assertThat(returnedCities.get("Disme").getWesternCity().getName(), is("Alime"));

        assertThat(returnedCities.get("Magaxila").getName(), is("Magaxila"));
        assertThat(returnedCities.get("Magaxila").getEasternCity().getName(), is("Mosnino"));
        assertThat(returnedCities.get("Magaxila").getNorthernCity().getName(), is("Amisno"));
        assertThat(returnedCities.get("Magaxila").getSouthernCity().getName(), is("Disme"));
        assertThat(returnedCities.get("Magaxila").getWesternCity().getName(), is("Egixano"));

        assertThat(returnedCities.get("Menisnile").getName(), is("Menisnile"));
        assertThat(returnedCities.get("Menisnile").getEasternCity().getName(), is("Dudisne"));
        assertThat(returnedCities.get("Menisnile").getNorthernCity().getName(), is("Molusnu"));
        assertThat(returnedCities.get("Menisnile").getSouthernCity().getName(), is("Egolma"));
        assertThat(returnedCities.get("Menisnile").getWesternCity().getName(), is("Exexalinu"));
    }

    @Test
    public void initializeMonstersAndChooseThemCity() throws IOException, MonstersMoreThanCitiesException,
            MonsterAlreadyInCityException {
        List<Monster> monsterList =
                WarBootstrap.initializeMonstersAndChooseThemCity(returnedCities, 6763);

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
            throws IOException, MonstersMoreThanCitiesException, MonsterAlreadyInCityException {
        WarBootstrap.initializeMonstersAndChooseThemCity(returnedCities, 6764);
    }

}