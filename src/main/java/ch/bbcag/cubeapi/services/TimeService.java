package ch.bbcag.cubeapi.services;

import ch.bbcag.cubeapi.models.Time;
import ch.bbcag.cubeapi.repositories.TimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class TimeService {


    @Autowired
    private final TimeRepository timeRepository;

    public TimeService(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    public void deleteById(Integer id) {
        try {
            timeRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public void update(Time time) {
        if (time == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        try {
            timeRepository.save(time);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    public void insert(Time time) {
        if (time == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        try {
            timeRepository.save(time);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    public Time findById(Integer id) {
        return timeRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Iterable<Time> findAll() {
        return timeRepository.findAll();
    }
}