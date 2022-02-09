package ch.bbcag.cubeapi.repositories;

import ch.bbcag.cubeapi.models.Country;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


public interface CountryRepository extends CrudRepository<Country, Integer> {
}