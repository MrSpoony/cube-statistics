package ch.bbcag.cubeapi.services;

import ch.bbcag.cubeapi.models.Town;
import ch.bbcag.cubeapi.repositories.TownRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class TownService {


    @Autowired
    private final TownRepository townRepository;

    public TownService(TownRepository townRepository) {
        this.townRepository = townRepository;
    }

    public void deleteById(Integer id) {
        try {
            townRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public void update(Town town) {
        if (town == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        try {
            townRepository.save(town);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    public void insert(Town town) {
        if (town == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        try {
            townRepository.save(town);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    public Town findById(Integer id) {
        return townRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Iterable<Town> findAll() {
        return townRepository.findAll();
    }
}