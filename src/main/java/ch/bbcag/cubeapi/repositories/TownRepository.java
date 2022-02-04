package ch.bbcag.cubeapi.repositories;

import ch.bbcag.cubeapi.models.Town;
import org.springframework.data.repository.CrudRepository;


public interface TownRepository extends CrudRepository<Town, Integer> {
}