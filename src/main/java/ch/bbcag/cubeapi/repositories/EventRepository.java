package ch.bbcag.cubeapi.repositories;

import ch.bbcag.cubeapi.models.Event;
import org.springframework.data.repository.CrudRepository;


public interface EventRepository extends CrudRepository<Event, Integer> {
}