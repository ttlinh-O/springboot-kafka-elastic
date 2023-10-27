package com.example.elasticqueryservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ElasticQueryServiceResponseModel extends RepresentationModel<ElasticQueryServiceResponseModel> {
    public String id;
    private Long userId;
    private String text;
    private LocalDateTime createdAt;
}
