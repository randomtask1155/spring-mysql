package com.example.springmysql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.http.ResponseEntity;
//import org.springframework.http.HttpStatus;

import java.util.Arrays;
//import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//import com.fasterxml.jackson.databind.ObjectMapper;



@RestController
public class MysqlController {

    private static final Logger log = LoggerFactory.getLogger(MysqlController.class);

    @Autowired
	JdbcTemplate jdbcTemplate;
    @PostMapping("/init")
    void initMYSQL() {

        log.info("drop and create customers table");
        jdbcTemplate.execute("DROP TABLE IF EXISTS customers");
        jdbcTemplate.execute("CREATE TABLE customers(" +
        "id SERIAL, first_name VARCHAR(255), last_name VARCHAR(255))");

        log.info("inserting names into database");
        List<Object[]> splitUpNames = Arrays.asList("John Woo", "Jeff Dean", "Josh Bloch", "Josh Long").stream()
				.map(name -> name.split(" "))
				.collect(Collectors.toList());
        jdbcTemplate.batchUpdate("INSERT INTO customers(first_name, last_name) VALUES (?,?)", splitUpNames);
    }

    @GetMapping("/connect")
    public  List<Map<String, Object>> getMYSQL() {
        return jdbcTemplate.queryForList("select first_name from customers");      
    }
}
