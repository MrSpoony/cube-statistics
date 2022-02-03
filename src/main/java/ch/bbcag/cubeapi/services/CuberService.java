package ch.bbcag.cubeapi.services;

import ch.bbcag.cubeapi.models.Cuber;
import ch.bbcag.cubeapi.repositories.CuberRepository;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CuberService {

    @Autowired
    private final CuberRepository cuberRepository;

    public CuberService(CuberRepository cuberRepository) {
        this.cuberRepository = cuberRepository;
    }

    public void deleteById(Integer id) {
        try {
            cuberRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public void update(Cuber cuber) {
        if (cuber == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        try {
            cuberRepository.save(cuber);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    public void insert(Cuber cuber) {
        if (cuber == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        try {
            cuberRepository.save(cuber);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    public Cuber findById(Integer id) {
        return cuberRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Iterable<Cuber> findByName(String name) {
        if (Strings.isBlank(name)) return cuberRepository.findAll();
        else if(name.contains(" ")) {
            return cuberRepository.findByFirstNameAndLastName(
                    name.split(" ")[0],
                    name.split(" ")[1]);
        }
        return cuberRepository.findByName(name);
    }
}