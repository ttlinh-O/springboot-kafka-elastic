package com.example.elasticqueryservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ElasticQueryServiceRequestModel {
    private String id;
    private String text;
}
