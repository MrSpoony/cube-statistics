package ch.bbcag.cubeapi.repositories;

import ch.bbcag.cubeapi.models.Time;
import org.springframework.data.repository.CrudRepository;


public interface TimeRepository extends CrudRepository<Time, Integer> {
}