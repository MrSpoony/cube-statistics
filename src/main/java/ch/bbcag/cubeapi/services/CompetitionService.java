package ch.bbcag.cubeapi.services;

import ch.bbcag.cubeapi.utils.IterableHelper;
import ch.bbcag.cubeapi.models.Competition;
import ch.bbcag.cubeapi.repositories.CompetitionRepository;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;


@Service
public class CompetitionService {

    @Autowired
    private final CompetitionRepository competitionRepository;

    private final IterableHelper<Competition> iterableHelper = new IterableHelper<>();

    public CompetitionService(CompetitionRepository competitionRepository) {
        this.competitionRepository = competitionRepository;
    }

    public void deleteById(Integer id) {
        try {
            competitionRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public void update(Competition competition) {
        if (competition == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        try {
            competitionRepository.save(competition);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    public void insert(Competition competition) {
        if (competition == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        try {
            competitionRepository.save(competition);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    public Competition findById(Integer id) {
        return competitionRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Iterable<Competition> findCompetitions(String name, String cuber, String country) {
        if (Strings.isBlank(name) && Strings.isBlank(cuber) && Strings.isBlank(country)) {
            return competitionRepository.findAll();
        }
        Iterable<Competition> competitionsByName = new HashSet<>();
        Iterable<Competition> competitionsByCuber = new HashSet<>();
        Iterable<Competition> competitionsByCountry = new HashSet<>();
        if (Strings.isNotBlank(name)) competitionsByName = competitionRepository.findByName(name);
        if (Strings.isNotBlank(cuber)) competitionsByCuber = competitionRepository.findByCuberName(cuber);
        if (Strings.isNotBlank(country)) competitionsByCountry = competitionRepository.findByCountry(country);

        iterableHelper.clearStored();
        for (Iterable<Competition> competitionsByX : new Iterable[]{
                competitionsByName,
                competitionsByCuber,
                competitionsByCountry}) {
            if (iterableHelper.sizeOfIterable(competitionsByX) > 0) {
                iterableHelper.addIterableToIntersectionOfStored(competitionsByX);
            }
        }
        return iterableHelper.getStored();
    }
}