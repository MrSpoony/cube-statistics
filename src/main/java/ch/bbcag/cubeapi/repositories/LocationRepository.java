package ch.bbcag.cubeapi.repositories;

import ch.bbcag.cubeapi.models.Location;
import org.springframework.data.repository.CrudRepository;


public interface LocationRepository extends CrudRepository<Location, Integer> {
}