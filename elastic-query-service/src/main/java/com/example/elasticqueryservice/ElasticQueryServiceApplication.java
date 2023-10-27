package com.example.elasticqueryservice;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.example")
@EnableEncryptableProperties
public class ElasticQueryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElasticQueryServiceApplication.class, args);
    }

}
