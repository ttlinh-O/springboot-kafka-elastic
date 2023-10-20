package com.example.twittertokafkaservice.init.impl;

import com.example.appconfigdata.KafkaConfigData;
import com.example.kafka.admin.client.KafkaAdminClient;
import com.example.twittertokafkaservice.init.StreamInitializer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class KafkaStreamingInitializer implements StreamInitializer {
    private final KafkaConfigData kafkaConfigData;
    private final KafkaAdminClient kafkaAdminClient;

    @Override
    public void init() {
        kafkaAdminClient.createTopic();
        kafkaAdminClient.checkSchemaRegistry();
        log.info("Topics with name {} is ready for operation!", kafkaConfigData.getTopicNamesToCreate().toString());
    }
}
