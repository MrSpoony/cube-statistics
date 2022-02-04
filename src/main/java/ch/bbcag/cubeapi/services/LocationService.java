package ch.bbcag.cubeapi.services;

import ch.bbcag.cubeapi.models.Location;
import ch.bbcag.cubeapi.repositories.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class LocationService {

    @Autowired
    private final LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public void deleteById(Integer id) {
        try {
            locationRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public void update(Location location) {
        if (location == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        try {
            locationRepository.save(location);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    public void insert(Location location) {
        if (location == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        try {
            locationRepository.save(location);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    public Location findById(Integer id) {
        return locationRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}