package com.example.twittertokafkaservice;

import com.example.twittertokafkaservice.init.StreamInitializer;
import com.example.twittertokafkaservice.runner.StreamRunner;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
@ComponentScan(basePackages = {"com.example", "com.microservices.demo"})
public class TwitterToKafkaServiceApplication implements CommandLineRunner {
    private final StreamRunner streamRunner;
    private final StreamInitializer streamInitializer;

    public static void main(String[] args) {
        SpringApplication.run(TwitterToKafkaServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("initializing TwitterToKafka");
        streamInitializer.init( );
        streamRunner.start();
    }
}
