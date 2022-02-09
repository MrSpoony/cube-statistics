package ch.bbcag.cubeapi.utils;

import ch.bbcag.cubeapi.models.Competition;
import ch.bbcag.cubeapi.models.Country;
import ch.bbcag.cubeapi.models.Location;
import ch.bbcag.cubeapi.models.Town;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


public class TestData {


    public static List<Competition> getTestCompetition() {
        List<Competition> competitions = new ArrayList<>();
        competitions.add(getTestCompetitions().get(0));
        return competitions;
    }

    public static List<Competition> getTestCompetitions() {
        List<Competition> competitions = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            Competition competition = new Competition();
            competition.setDate(new Date(2022, 1, 1));
            Location location = new Location();
            Town town = new Town();
            Country country = new Country();
            country.setName("Country " + i);
            town.setCountry(country);
            town.setPostcode(i);
            town.setName("Town " + i);
            location.setTown(town);
            location.setLocation("Location " + i);
            competition.setLocation(location);
            competition.setName("Competition " + i);
            competitions.add(competition);
        }
        return competitions;
    }
}