package com.supercoding.chickendoner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChickenDonerApplication {

    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(ChickenDonerApplication.class);
        SpringApplication.run(ChickenDonerApplication.class, args);
    }

}
