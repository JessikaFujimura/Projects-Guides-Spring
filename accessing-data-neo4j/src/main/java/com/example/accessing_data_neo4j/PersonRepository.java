package com.example.accessing_data_neo4j;

import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;

public interface PersonRepository extends Neo4jRepository<Person, Long> {
    
    Person findByName(String name);
    
    List<Person> findByTeammateName(String name);
}
