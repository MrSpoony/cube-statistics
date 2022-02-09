package ch.bbcag.cubeapi.services;

import ch.bbcag.cubeapi.models.Country;
import ch.bbcag.cubeapi.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class CountryService {


    @Autowired
    private final CountryRepository countryRepository;

    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public void deleteById(Integer id) {
        try {
            countryRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public void update(Country country) {
        if (country == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        try {
            countryRepository.save(country);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    public void insert(Country country) {
        if (country == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        try {
            countryRepository.save(country);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    public Country findById(Integer id) {
        return countryRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}