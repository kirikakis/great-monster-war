package com.github.kirikakis.monster.war;

import com.github.kirikakis.monster.war.exceptions.MonstersMoreThanCitiesException;
import com.github.kirikakis.monster.war.model.City;
import com.github.kirikakis.monster.war.model.Monster;
import com.github.kirikakis.monster.war.utilities.WarUtilities;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class Main {

    public static void main(String[] args) throws IOException {
        Map<String, City> returnedCities;
        try {
            returnedCities =
                WarUtilities.FetchMapDataFromFile("src/main/resources/map.txt");
        }
        catch (IOException e) {
            returnedCities =
                    WarUtilities.FetchMapDataFromFile("config/map.txt");
        }
        Integer citiesCountBeforeTheWar = returnedCities.size();
        Integer monstersStartedTheWar = 0;
        try {
            Set<Monster> monsterList =
                    WarUtilities.InitializeMonstersAndChooseThemCity(returnedCities, Integer.parseInt(args[0]));
            monstersStartedTheWar = monsterList.size();
            MonsterWars monsterWars = new MonsterWars(returnedCities, monsterList);
            System.out.println("War started total cities = " + citiesCountBeforeTheWar +
                    " total monsters = " + monstersStartedTheWar);
            System.out.println("----------------------------------------------");
            monsterWars.startTheWar(10000);
            System.out.println("----------------------------------------------");
            System.out.println("Map after the Great Monster War");
            System.out.println("----------------------------------------------");
            WarUtilities.printCitiesMapData(returnedCities);
            StringBuilder sb = new StringBuilder();
            sb.append("----------------------------------------------\n");
            sb.append(monstersStartedTheWar);
            sb.append(" monsters destroyed ");
            sb.append(citiesCountBeforeTheWar - returnedCities.size());
            sb.append(" out of ");
            sb.append(citiesCountBeforeTheWar);
            sb.append(" cities. Monsters left = ");
            sb.append(monsterList.size());
            System.out.println(sb.toString());
        } catch (MonstersMoreThanCitiesException e) {
            e.printStackTrace();
        }
    }
}
