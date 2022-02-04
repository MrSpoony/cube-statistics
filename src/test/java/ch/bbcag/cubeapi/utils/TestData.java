//package ch.bbcag.cubeapi.utils;
//
//import ch.bbcag.cubeapi.models.*;
//
//import java.sql.Date;
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//
//public class TestData {
//
//    public static final int NUMBER_OF_TEST_SAMPLES_EACH = 5;
//
//    public static List<Country> getTestCountries() {
//        List<Country> countries = new ArrayList<>();
//
//        for (int i = 0; i < NUMBER_OF_TEST_SAMPLES_EACH; i++) {
//            Country country = new Country();
//            country.setName("Country " + i);
//            countries.add(country);
//        }
//        return countries;
//    }
//
//    public static List<Town> getTestTowns() {
//        List<Town> towns = new ArrayList<>();
//        List<Country> countries = getTestCountries();
//
//        for (int i = 0; i < NUMBER_OF_TEST_SAMPLES_EACH; i++) {
//            Town town = new Town();
//            town.setPostcode(i);
//            town.setName("Town " + i);
//            town.setCountry(countries.get(i));
//            towns.add(town);
//        }
//        return towns;
//    }
//
//    public static List<Location> getTestLocations() {
//        List<Location> locations = new ArrayList<>();
//        List<Town> towns = getTestTowns();
//        List<Country> countries = getTestCountries();
//
//        for (int i = 0; i < NUMBER_OF_TEST_SAMPLES_EACH; i++) {
//            Location location = new Location();
//            location.setLocation("Location " + i);
//            location.setTown(towns.get(i));
//            locations.add(location);
//        }
//        return locations;
//    }
//
//    public static List<Manufacture> getTestManufactures() {
//        List<Manufacture> manufactures = new ArrayList<>();
//        List<Country> countries = getTestCountries();
//
//        for (int i = 0; i < NUMBER_OF_TEST_SAMPLES_EACH; i++) {
//            Manufacture manufacture = new Manufacture();
//            manufacture.setName("Manufacture " + i);
//            manufacture.setCountry(countries.get(i));
//            manufactures.add(manufacture);
//        }
//        return manufactures;
//    }
//
//    public static List<Event> getTestEvents() {
//        List<Event> events = new ArrayList<>();
//
//        for (int i = 0; i < NUMBER_OF_TEST_SAMPLES_EACH; i++) {
//            Event event = new Event();
//            event.setName("Event " + i);
//            event.setCubersWithThisMainevent( );               // TODO
//            event.setWcalegal(i % 2 == 1);
//            events.add(event);
//        }
//        return events;
//    }
//
//    public static List<Cube> getTestCubes() {
//        List<Cube> cubes = new ArrayList<>();
//        List<Event> events = getTestEvents();
//        List<Manufacture> manufactures = getTestManufactures();
//
//        for (int i = 0; i < 5; i++) {
//            Cube cube = new Cube();
//            cube.setName("Cube " + i);
//            cube.setEvent(events.get(i));
//            cube.setManufacture(manufactures.get(i));
//            cubes.add(cube);
//        }
//        return cubes;
//    }
//
//    public static List<Cuber> getTestCubers() {
//        List<Cuber> cubers = new ArrayList<>();
//        List<Cube> cubes = getTestCubes();
//        List<Country> countries = getTestCountries();
//        List<Event> events = getTestEvents();
//
//        for (int i = 0; i < 5; i++) {
//            Cuber cuber = new Cuber();
//            cuber.setFirstname("Cuber" + i);
//            cuber.setLastname("Cuber" + i);
//            cuber.setCountry(countries.get(i));
//            cuber.setMainevents(events);
//            cuber.setBirthdate(Date.valueOf("2020-01-01"));
//        }
//    }
//
//}