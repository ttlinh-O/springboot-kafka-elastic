package com.example.kafkatoelasticservice.consumer.impl;

import com.example.appconfigdata.KafkaConfigData;
import com.example.appconfigdata.KafkaConsumerConfigData;
import com.example.elasticindexclient.client.service.ElasticIndexClient;
import com.example.elasticmodel.index.impl.TwitterIndexModel;
import com.example.kafka.admin.client.KafkaAdminClient;
import com.example.kafkatoelasticservice.consumer.kafkaConsumer;
import com.example.kafkatoelasticservice.transformer.AvroElasticModelTransformer;
import com.microservices.demo.kafka.avro.model.TwitterAvroModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class TwitterKafkaConsumer implements kafkaConsumer<Long, TwitterAvroModel> {
    private final KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;
    private final KafkaAdminClient kafkaAdminClient;
    private final KafkaConfigData kafkaConfigData;
    private final AvroElasticModelTransformer avroElasticModelTransformer;
    private final ElasticIndexClient<TwitterIndexModel> twitterIndexModelElasticIndexClient;
    private final KafkaConsumerConfigData kafkaConsumerConfigData;

    @EventListener
    public void onAppStarted(ApplicationStartedEvent applicationStartedEvent) {
        kafkaAdminClient.checkTopicsCreated();
        log.info("Topics with name {} is ready for operations!", kafkaConfigData.getTopicNamesToCreate().toArray());
        Objects.requireNonNull(kafkaListenerEndpointRegistry.getListenerContainer(kafkaConsumerConfigData.getConsumerGroupId())).start();
    }

    @Override
    @KafkaListener(id = "${kafka-consumer-config.consumer-group-id}", topics = "${kafka-config.topic-name}")
    public void receive(@Payload List<TwitterAvroModel> messages,
                        @Header(KafkaHeaders.RECEIVED_KEY) List<Integer> keys,
                        @Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
        log.info("{} number if message received with key {}, partitions {}, offsets {}, " +
                "sending it to elastic: Thread id {}",
                messages.size(),
                keys.toString(),
                partitions.toString(),
                offsets.toString(),
                Thread.currentThread().getId());
        List<TwitterIndexModel> elasticModels = avroElasticModelTransformer.getElasticModels(messages);
        List<String> documentIds = twitterIndexModelElasticIndexClient.save(elasticModels);
        log.info("Documents saved to elastic search with ids: {}", documentIds);
    }
}
