package ch.bbcag.cubeapi.repositories;

import ch.bbcag.cubeapi.models.Cuber;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface CuberRepository extends CrudRepository<Cuber, Integer> {

    @Query("SELECT c FROM Cuber c " +
            "WHERE c.firstname LIKE CONCAT('%', :name, '%') " +
            "OR c.lastname LIKE CONCAT('%', :name, '%')")
    Iterable<Cuber> findByName(@Param("name") String name);

    @Query("SELECT c FROM Cuber c " +
            "WHERE c.firstname LIKE CONCAT('%', :first, '%') " +
            "AND c.lastname LIKE CONCAT('%', :last, '%')")
    Iterable<Cuber> findByFirstNameAndLastName(@Param("first") String first, @Param("last") String last);

    @Query("SELECT cr FROM Cuber cr " +
            "JOIN cr.cubes cc " +
            "JOIN cc.cube c " +
            "WHERE cc.maincube IS true " +
            "AND c.name LIKE CONCAT('%', :cubename, '%')")
    Iterable<Cuber> findByMaincube(@Param("cubename") String cubename);
}