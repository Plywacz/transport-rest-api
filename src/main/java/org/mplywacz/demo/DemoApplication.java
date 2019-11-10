package org.mplywacz.demo;


//todo: fix tests
//todo: adjust tests to the new Driver related functionality
//
//todo: 1. ADD RESPONSE MESSAGES TO SWAGGER DOC
//TODO: 2. FIX ERROR HANDLING IN SWAGGER DOC (given wrong json to the endpoint, swagger doesnt show proper errors)

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
