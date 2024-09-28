package com.example.accessing_data_neo4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@EnableNeo4jRepositories
public class AccessingDataNeo4jApplication {
	
	private static final Logger log = LoggerFactory.getLogger(AccessingDataNeo4jApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(AccessingDataNeo4jApplication.class, args);
		System.exit(0);
	}
	
	@Bean
	CommandLineRunner start(PersonRepository personRepository){
		return args -> {
			personRepository.deleteAll();
			
			Person greg = new Person("Greg");
			Person roy = new Person("roy");
			Person craig = new Person("craig");
			
			List<Person> team = Arrays.asList(greg, roy, craig);
			
			log.info("Antes de relacionar com o neo4j ...");
			
			team.stream().forEach(p -> log.info("\t" + p.toString()));
			
			personRepository.save(greg);
			personRepository.save(roy);
			personRepository.save(craig);
			
			greg = personRepository.findByName(greg.getName());
			greg.worksWith(roy);
			greg.worksWith(craig);
			personRepository.save(greg);
			
			
			roy = personRepository.findByName(roy.getName());
			roy.worksWith(craig);
			personRepository.save(roy);
			
			log.info("Vamos analisar cada pessoa por nome");
			team.stream().forEach(p -> log.info("\t" + personRepository.findByName(p.getName()).toString()));
			
			List<Person> teammates = personRepository.findByTeammateName(greg.getName());
			log.info("Os colegas de trabalho do Greg");
			teammates.stream().forEach(p -> log.info("\t" + p.getName()));
			
		};
	}

}
