package ch.bbcag.cubeapi.services;

import ch.bbcag.cubeapi.models.Cube;
import ch.bbcag.cubeapi.repositories.CubeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class CubeService {


    @Autowired
    private final CubeRepository cubeRepository;

    public CubeService(CubeRepository cubeRepository) {
        this.cubeRepository = cubeRepository;
    }

    public void deleteById(Integer id) {
        try {
            cubeRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public void update(Cube cube) {
        if (cube == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        try {
            cubeRepository.save(cube);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    public void insert(Cube cube) {
        if (cube == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        try {
            cubeRepository.save(cube);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    public Cube findById(Integer id) {
        return cubeRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Iterable<Cube> findAll() {
        return cubeRepository.findAll();
    }
}