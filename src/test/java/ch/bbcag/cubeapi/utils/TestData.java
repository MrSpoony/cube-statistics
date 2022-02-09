package ch.bbcag.cubeapi.utils;

import ch.bbcag.cubeapi.models.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


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

    public static List<Cuber> getTestCuber(int index) {
        List<Cuber> cuber = new ArrayList<>();
        cuber.add(getTestCubers().get(index));
        return cuber;
    }

    public static List<Cuber> getTestCubers() {
        List<Cuber> cubers = new ArrayList<>();
        Set<Event> mainevents = getTestEvents();

        for (int i = 0; i < 5; i++) {
            Cuber cuber = new Cuber();
            cuber.setFirstname("CuberFirstname " + i);
            cuber.setLastname("CuberLastname " + i);
            cuber.setMainevents(mainevents);
            Country country = new Country();
            country.setName("Country " + i);
            cuber.setCountry(country);
            cuber.setBirthdate(new Date(2022, 1, 1));
            cubers.add(cuber);
        }
        return cubers;
    }

    private static Set<Event> getTestEvents() {
        Set<Event> events = new HashSet<>();
        for (int i = 0; i < 5; i++) {
            Event event = new Event();
            event.setWcalegal(true);
            event.setName("Event " + i);
            events.add(event);
        }
        return events;
    }
}