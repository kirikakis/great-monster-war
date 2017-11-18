package com.github.kirikakis.monster.war.utilities;

import com.github.kirikakis.monster.war.exceptions.MonstersMoreThanCitiesException;
import com.github.kirikakis.monster.war.model.City;
import com.github.kirikakis.monster.war.model.Direction;
import com.github.kirikakis.monster.war.model.Monster;
import com.github.kirikakis.monster.war.model.NeighborCity;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Stream;

public class WarUtilities {

    public static Map<String, City> FetchMapDataFromFile(String fileName) throws IOException {
        Map<String, City> cityMap = new HashMap<>();
        Stream<String> citiesFileStream = Files.lines(Paths.get(fileName));
        citiesFileStream.filter(cityLine -> !cityLine.isEmpty()).forEach(cityLine -> {
            String currentCityName = "";
            String northernCityName = "";
            String southernCityName = "";
            String easternCityName = "";
            String westernCityName = "";

            String[] cityArgs = cityLine.split(" ");
            for (String cityArg : cityArgs) {
                String[] arg = cityArg.split("=");
                if (arg.length < 2) {
                    currentCityName = arg[0];
                } else {
                    if (arg[0].equals("north")) {
                        northernCityName = arg[1];
                        continue;
                    }
                    if (arg[0].equals("east")) {
                        easternCityName = arg[1];
                        continue;
                    }
                    if (arg[0].equals("south")) {
                        southernCityName = arg[1];
                        continue;
                    }
                    if (arg[0].equals("west")) {
                        westernCityName = arg[1];
                    }
                }
            }

            City currentCity = cityMap.get(currentCityName);
            City northernCity = cityMap.get(northernCityName);
            City easternCity = cityMap.get(easternCityName);
            City southernCity = cityMap.get(southernCityName);
            City westernCity = cityMap.get(westernCityName);

            if (currentCity == null) {
                currentCity = new City(currentCityName);
                cityMap.putIfAbsent(currentCityName, currentCity);
            }
            if (northernCity == null && !northernCityName.equals("")) {
                northernCity = new City(northernCityName);
                cityMap.putIfAbsent(northernCityName, northernCity);
            }
            if (easternCity == null && !easternCityName.equals("")) {
                easternCity = new City(easternCityName);
                cityMap.putIfAbsent(easternCityName, easternCity);
            }
            if (southernCity == null && !southernCityName.equals("")) {
                southernCity = new City(southernCityName);
                cityMap.putIfAbsent(southernCityName, southernCity);
            }
            if (westernCity == null && !westernCityName.equals("")) {
                westernCity = new City(westernCityName);
                cityMap.putIfAbsent(westernCityName, westernCity);
            }

            if(!northernCityName.equals("")) {
                currentCity.getNeighborCities().add(new NeighborCity(Direction.NORTH, northernCity));
            }
            if(!easternCityName.equals("")) {
                currentCity.getNeighborCities().add(new NeighborCity(Direction.EAST, easternCity));
            }
            if(!southernCityName.equals("")) {
                currentCity.getNeighborCities().add(new NeighborCity(Direction.SOUTH, southernCity));
            }
            if(!westernCityName.equals("")) {
                currentCity.getNeighborCities().add(new NeighborCity(Direction.WEST, westernCity));
            }
        });
        return cityMap;
    }

    public static List<Monster> InitializeMonstersAndChooseThemCity(
            Map<String, City> citiesMap, int monstersToInitialize)
            throws MonstersMoreThanCitiesException {
        if (citiesMap.size() < monstersToInitialize) {
            throw new MonstersMoreThanCitiesException();
        }
        List<Monster> monsters = new ArrayList<>();
        Random random = new Random();
        Set<Integer> allocatedCitiesIndex = new HashSet<>();
        Integer citiesRandomIndex = random.nextInt(citiesMap.size());
        for (int i = 0; i < monstersToInitialize; i++) {
            while (allocatedCitiesIndex.contains(citiesRandomIndex)) {
                citiesRandomIndex = random.nextInt(citiesMap.size());
            }
            allocatedCitiesIndex.add(citiesRandomIndex);
            City randomCity = (City) citiesMap.values().toArray()[citiesRandomIndex];
            Monster monster = new Monster(randomCity.getName() + 'r', randomCity);
            randomCity.setMonster(monster);
            monsters.add(monster);
        }
        return monsters;
    }

    public static void printCitiesMapData(Map<String, City> citiesMap) {
        citiesMap.forEach((cityName, city) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(cityName);
            sb.append(' ');
            city.getNeighborCities().forEach(neighborCity -> {
                sb.append(neighborCity.getDirection());
                sb.append('=');
                sb.append(neighborCity.getCity().getName());
                sb.append(' ');
            });
            System.out.println(sb.toString());
        });
    }
}
