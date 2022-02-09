package ch.bbcag.cubeapi.services;

import ch.bbcag.cubeapi.models.Manufacture;
import ch.bbcag.cubeapi.repositories.ManufactureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class ManufactureService {


    @Autowired
    private final ManufactureRepository manufactureRepository;

    public ManufactureService(ManufactureRepository manufactureRepository) {
        this.manufactureRepository = manufactureRepository;
    }

    public void deleteById(Integer id) {
        try {
            manufactureRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public void update(Manufacture manufacture) {
        if (manufacture == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        try {
            manufactureRepository.save(manufacture);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    public void insert(Manufacture manufacture) {
        if (manufacture == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        try {
            manufactureRepository.save(manufacture);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    public Manufacture findById(Integer id) {
        return manufactureRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Iterable<Manufacture> findAll() {
        return manufactureRepository.findAll();
    }
}