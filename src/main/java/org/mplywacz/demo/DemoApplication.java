package org.mplywacz.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

    //TODO : deal with double validation as app validates in json deserializers and then in Dto classes  same things.
    //Idea: move validation from deserializers to DTO setters !
@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {

        SpringApplication.run(DemoApplication.class, args);
    }

}
