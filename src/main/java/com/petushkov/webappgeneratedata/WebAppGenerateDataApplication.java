package com.petushkov.webappgeneratedata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class WebAppGenerateDataApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebAppGenerateDataApplication.class, args);
    }

}
