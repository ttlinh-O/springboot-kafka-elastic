package com.example.twittertokafkaservice.runner;

import twitter4j.TwitterException;

import java.io.IOException;
import java.net.URISyntaxException;

public interface StreamRunner {

    void start() throws TwitterException, IOException, URISyntaxException;
}
