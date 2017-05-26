package com.mark.repository;

import com.mark.model.Person;
import org.springframework.data.neo4j.repository.GraphRepository;

/**
 * Created by kerse on 29.03.2017.
 */
public interface PersonRepository extends GraphRepository<Person> {

    Person findByEmailAndPassword(String email,String password);

    Person findByUniqueID(String uniqueID);

    Person findByAuhtToken(String authToken);

    Person findByEmail(String email);

}
