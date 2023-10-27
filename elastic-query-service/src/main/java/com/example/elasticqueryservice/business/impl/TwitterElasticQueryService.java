package com.example.elasticqueryservice.business.impl;

import com.example.elastic.query.client.service.ElasticQueryClient;
import com.example.elasticmodel.index.impl.TwitterIndexModel;
import com.example.elasticqueryservice.business.ElasticQueryService;
import com.example.elasticqueryservice.model.ElasticQueryServiceResponseModel;
import com.example.elasticqueryservice.model.assembler.ElasticQueryServiceModelAssembler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TwitterElasticQueryService implements ElasticQueryService {
//    without Hateoas
//    private final ElasticToResponseModelTransformer elasticToResponseModelTransformer;
    // with hateoas
    private final ElasticQueryServiceModelAssembler elasticQueryServiceModelAssembler;

    private final ElasticQueryClient<TwitterIndexModel> elasticQueryClient;
    @Override
    public ElasticQueryServiceResponseModel getDocumentById(String id) {
        log.info("Querying elasticsearch by id {}", id);
//        return elasticToResponseModelTransformer.getResponseModel(elasticQueryClient.getIndexModelById(id));
        return elasticQueryServiceModelAssembler.toModel(elasticQueryClient.getIndexModelById(id));
    }

    @Override
    public List<ElasticQueryServiceResponseModel> getDocumentByText(String text) {
        log.info("Querying elasticsearch by text {}", text);
//        return elasticToResponseModelTransformer.getResponseModels(elasticQueryClient.getIndexModelByText(text));
        return elasticQueryServiceModelAssembler.toModels(elasticQueryClient.getIndexModelByText(text));
    }

    @Override
    public List<ElasticQueryServiceResponseModel> getAllDocuments() {
        log.info("Querying all documents in elasticsearch");
//        return elasticToResponseModelTransformer.getResponseModels(elasticQueryClient.getAllIndexModels());
        return elasticQueryServiceModelAssembler.toModels(elasticQueryClient.getAllIndexModels());
    }
}
