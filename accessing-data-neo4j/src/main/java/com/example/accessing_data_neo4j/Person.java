package com.example.accessing_data_neo4j;

import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import javax.annotation.processing.Generated;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Node
public class Person {
    
    @Id
    @GeneratedValue
    private Long id;
    
    private String name;
    
    public Person() {
    
    }
    
    public Person(String name) {
        this.name = name;
    }
    
    @Relationship(type = "TEAMMATE")
    public Set<Person> teammate;
    
    
    public void worksWith(Person person) {
        if (teammate == null) {
            teammate = new HashSet<>();
        }
        teammate.add(person);
    }
    
    public String toString(){
        return this.name + "'s teammate => " +
                Optional.ofNullable(this.teammate).orElse(Collections.emptySet()).stream().map(Person::getName).collect(Collectors.toList());
    }
    
    public String getName(){
        return this.name;
    }
    
    public void setName(String name){
        this.name = name;
    }
}
