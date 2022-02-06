package ch.bbcag.cubeapi.repositories;

import ch.bbcag.cubeapi.models.Cube;
import org.springframework.data.repository.CrudRepository;


public interface CubeRepository extends CrudRepository<Cube, Integer> {
}