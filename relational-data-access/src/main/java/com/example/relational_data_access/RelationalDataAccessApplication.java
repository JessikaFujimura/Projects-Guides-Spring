package com.example.relational_data_access;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@SpringBootApplication
public class RelationalDataAccessApplication implements CommandLineRunner {
	
	private static final Logger log = LoggerFactory.getLogger(RelationalDataAccessApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(RelationalDataAccessApplication.class, args);
	}
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Override
	public void run(final String... args) throws Exception {
		log.info("Creating tables");
		
		jdbcTemplate.execute("DROP TABLE customers IF EXISTS");
		jdbcTemplate.execute("CREATE TABLE customers(id SERIAL, first_name VARCHAR(255), last_name VARCHAR(255))");
		
		List<Object[]> splitUpNames = Arrays.asList("John Woo", "Jeff Dean", "Josh Bloch", "Josh Long")
				.stream().map(name -> name.split(" "))
				.collect(Collectors.toList());
		
		jdbcTemplate.batchUpdate("INSERT INTO customers(first_name, last_name) VALUES (?,?)", splitUpNames);
		
		log.info("Querying for customer records where the first_name is Josh");
		
		jdbcTemplate.query("SELECT * FROM customers WHERE first_name = ?", (rs, row) -> new Customer(rs.getLong("id"),
				rs.getString("first_name"),
				rs.getString("last_name")),"Josh")
				.forEach(each -> log.info(each.toString()));
		
		List<Object[]> updateTable = Arrays.asList(new Object[]{"Jane", "Banana"}, new Object[]{});
		
		log.info("Updating the table to female names");
		
		jdbcTemplate.batchUpdate("UPDATE customers SET first_name=?, last_name=? WHERE id=1",updateTable);
		
		log.info("Printing the table");
		
		jdbcTemplate.query("SELECT * FROM customers", (rs, row) -> new Customer(rs.getLong("id"),
						rs.getString("first_name"),
						rs.getString("last_name")))
				.forEach(each -> log.info(each.toString()));
		
	}
}
