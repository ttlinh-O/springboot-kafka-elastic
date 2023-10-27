package com.example.elasticqueryservice.transformer;

import com.example.elasticmodel.index.impl.TwitterIndexModel;
import com.example.elasticqueryservice.model.ElasticQueryServiceResponseModel;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ElasticToResponseModelTransformer {

    public ElasticQueryServiceResponseModel getResponseModel(TwitterIndexModel twitterIndexModel) {
        return ElasticQueryServiceResponseModel
                .builder()
                .id(twitterIndexModel.getId())
                .userId(twitterIndexModel.getUserId())
                .text(twitterIndexModel.getText())
                .createdAt( twitterIndexModel.getCreatedAt().toLocalDateTime())
                .build();
    }

    public List<ElasticQueryServiceResponseModel> getResponseModels(List<TwitterIndexModel> twitterIndexModels) {
        return twitterIndexModels.stream().map(this::getResponseModel).toList();
    }
}
