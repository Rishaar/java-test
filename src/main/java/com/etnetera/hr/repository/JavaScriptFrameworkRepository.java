package com.etnetera.hr.repository;

import com.etnetera.hr.data.JavaScriptFramework;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Spring data repository interface used for accessing the data in database.
 * 
 * @author Etnetera
 *
 */

public interface JavaScriptFrameworkRepository extends CrudRepository<JavaScriptFramework, Long> {

    @Query(value = "SELECT * FROM JAVA_SCRIPT_FRAMEWORK WHERE NAME LIKE %?1%", nativeQuery = true)
    Optional<ArrayList<JavaScriptFramework>> findByName(String name);

    @Query(value = "SELECT * FROM JAVA_SCRIPT_FRAMEWORK WHERE hype >= ?1", nativeQuery = true)
    Optional<ArrayList<JavaScriptFramework>> findByHype(double hype);
}
