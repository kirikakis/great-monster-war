package com.github.kirikakis.monster.war.bootstrap;

import com.github.kirikakis.monster.war.model.City;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class BootstrapUtilities {
    public Map<String, City> FetchMapDataFromFile(String fileName) throws IOException {
        Map<String, City> cityMap = new HashMap<>();
        Stream<String> citiesFileStream =  Files.lines(Paths.get(fileName));
        citiesFileStream.forEach(cityLine -> {
            String currentCityName = "";
            String northernCityName = "";
            String southernCityName = "";
            String easternCityName = "";
            String westernCityName = "";

            String[] cityArgs = cityLine.split(" ");
            for (String cityArg : cityArgs) {
                String[] arg = cityArg.split("=");
                if(arg.length < 2) {
                    currentCityName = arg[0];
                    continue;
                }
                else {
                    if(arg[0].equals("north")) {
                        northernCityName = arg[1];
                        continue;
                    }
                    if(arg[0].equals("east")) {
                        easternCityName = arg[1];
                        continue;
                    }
                    if(arg[0].equals("south")) {
                        southernCityName = arg[1];
                        continue;
                    }
                    if(arg[0].equals("west")) {
                        westernCityName = arg[1];
                        continue;
                    }
                }
            }

            City currentCity = cityMap.get(currentCityName);
            City northernCity = cityMap.get(northernCityName);
            City easternCity = cityMap.get(easternCityName);
            City southernCity = cityMap.get(southernCityName);
            City westernCity = cityMap.get(westernCityName);

            if(currentCity == null) {
                currentCity = new City(currentCityName);
                cityMap.put(currentCityName, currentCity);
            }
            if(northernCity == null) {
                northernCity = new City(northernCityName);
                cityMap.put(northernCityName, northernCity);
            }
            if(easternCity == null) {
                easternCity = new City(easternCityName);
                cityMap.put(easternCityName, easternCity);
            }
            if(southernCity == null) {
                southernCity = new City(southernCityName);
                cityMap.put(southernCityName, southernCity);
            }
            if(westernCity == null) {
                westernCity = new City(westernCityName);
                cityMap.put(westernCityName, westernCity);
            }

            currentCity.setNorthernCity(northernCity);
            currentCity.setEasternCity(easternCity);
            currentCity.setSouthernCity(southernCity);
            currentCity.setWesternCity(westernCity);
        });
        return cityMap;
    }
}
