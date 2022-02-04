package ch.bbcag.cubeapi.repositories;

import ch.bbcag.cubeapi.models.Competition;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface CompetitionRepository extends CrudRepository<Competition, Integer> {

    @Query("SELECT DISTINCT co FROM Time t " +
            "JOIN t.cuber c " +
            "JOIN t.competition co " +
            "WHERE c.firstname LIKE CONCAT('%', :name, '%') " +
            "OR c.lastname LIKE CONCAT('%', :name, '%')")
    Iterable<Competition> findByCuberName(@Param("name") String name);


    @Query("SELECT c FROM Competition c " +
            "WHERE c.name LIKE CONCAT('%', :name, '%')")
    Iterable<Competition> findByName(@Param("name") String name);

    @Query("SELECT c FROM Competition c " +
            "JOIN c.location l " +
            "JOIN l.town t " +
            "JOIN t.country co " +
            "WHERE co.name LIKE CONCAT('%', :name, '%')")
    Iterable<Competition> findByCountry(@Param("name") String name);
}