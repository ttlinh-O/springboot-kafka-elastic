package com.example.kafkatoelasticservice.transformer;

import com.example.elasticmodel.index.impl.TwitterIndexModel;
import com.microservices.demo.kafka.avro.model.TwitterAvroModel;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Component
public class AvroElasticModelTransformer {
    public List<TwitterIndexModel> getElasticModels(List<TwitterAvroModel> twitterAvroModels) {
        return twitterAvroModels.stream()
                .map(twitterAvroModel -> TwitterIndexModel.builder()
                        .userId(twitterAvroModel.getUserId())
                        .text(twitterAvroModel.getText())
                        .createdAt(ZonedDateTime.ofInstant(Instant.ofEpochMilli(twitterAvroModel.getCreatedAt()),
                                ZoneId.systemDefault()))
                        .build())
                .toList();
    }
}
