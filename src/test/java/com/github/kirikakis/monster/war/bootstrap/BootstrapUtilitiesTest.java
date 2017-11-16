package com.github.kirikakis.monster.war.bootstrap;

import com.github.kirikakis.monster.war.model.City;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class BootstrapUtilitiesTest {
    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void fetchMapDataFromFile() throws Exception {
        Map<String, City> returnedCities =
                new BootstrapUtilities().FetchMapDataFromFile("src/main/resources/map.txt");


        assertEquals(6764, returnedCities.size());

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

}