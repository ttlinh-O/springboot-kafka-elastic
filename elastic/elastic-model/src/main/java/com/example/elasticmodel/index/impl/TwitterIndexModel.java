package com.example.elasticmodel.index.impl;

import com.example.elasticmodel.index.IndexModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Data
@Builder
@Document(indexName = "#{@elasticConfigData.getIndexName()}")
public class TwitterIndexModel implements IndexModel {

    @JsonProperty
    private String id;
    @JsonProperty
    private Long userId;
    @JsonProperty
    private String text;

    @Field(type = FieldType.Date, format = {}, pattern = "uuuu-MM-dd'T'HH:mm:ssZZ")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "uuuu-MM-dd'T'HH:mm:ssZZ")
    @JsonProperty
    private ZonedDateTime createdAt;
}
