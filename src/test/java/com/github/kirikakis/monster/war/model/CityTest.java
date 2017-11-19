package com.github.kirikakis.monster.war.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class CityTest {

    @Test
    public void removeConnection() throws Exception {
        City city1 = new City("a");
        City city2 = new City("b");
        NeighborCity neighborCity1 = new NeighborCity(Direction.NORTH, city1);
        NeighborCity neighborCity2 = new NeighborCity(Direction.NORTH, city2);

        City cityUnderTest = new City("cityUnderTest");
        cityUnderTest.getNeighborCities().add(neighborCity1);
        cityUnderTest.getNeighborCities().add(neighborCity2);

        cityUnderTest.removeConnection(city1);

        assertEquals(1, cityUnderTest.getNeighborCities().size());
        assertEquals(neighborCity2, cityUnderTest.getNeighborCities().get(0));
    }

}