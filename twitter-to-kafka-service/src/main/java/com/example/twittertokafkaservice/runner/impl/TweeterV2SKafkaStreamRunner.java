package com.example.twittertokafkaservice.runner.impl;

import com.example.appconfigdata.TwitterToKafkaServiceConfigData;
import com.example.twittertokafkaservice.exception.TwitterToKafkaServiceException;
import com.example.twittertokafkaservice.runner.StreamRunner;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
@RequiredArgsConstructor
@Slf4j
@ConditionalOnExpression("${twitter-to-kafka-service.enable-v2-tweets} && not ${twitter-to-kafka-service.enable-mock-tweets}")
public class TweeterV2SKafkaStreamRunner implements StreamRunner {
    private final TwitterV2StreamHelper twitterV2StreamHelper;
    private final TwitterToKafkaServiceConfigData twitterToKafkaServiceConfigData;

    @Override
    public void start() {
        String bearerToken = twitterToKafkaServiceConfigData.getTwitterV2BearerToken();
        if(null != bearerToken) {
            try {
                twitterV2StreamHelper.setupRules(bearerToken, getRules());
                twitterV2StreamHelper.connectStream(bearerToken);
            } catch (IOException | URISyntaxException e) {
                log.error("Error streaming tweets!", e);
                throw new TwitterToKafkaServiceException("Error streaming tweets!", e);
            }
        } else {
            log.error("There was a problem getting your bearer token. " +
                    "Please make sure you set the TWITTER_BEARER_TOKEN environment variable");
            throw new TwitterToKafkaServiceException("There was a problem getting your bearer token. +" +
                    "Please make sure you set the TWITTER_BEARER_TOKEN environment variable");
        }
    }

    private Map<String, String> getRules() {
        List<String> keywords = twitterToKafkaServiceConfigData.getTwitterKeywords();
        Map<String, String> rules = new HashMap<>();
        for (String keyword: keywords) {
            rules.put(keyword, "Keyword: " + keyword);
        }
        log.info("Created filter for twitter stream for keywords: {}", keywords);
        return rules;
    }
}
