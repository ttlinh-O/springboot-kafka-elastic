package com.example.elasticindexclient.client.service.impl;

import com.example.appconfigdata.ElasticConfigData;
import com.example.elasticindexclient.client.service.ElasticIndexClient;
import com.example.elasticindexclient.client.util.ElasticIndexUtil;
import com.example.elasticmodel.index.impl.TwitterIndexModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexedObjectInformation;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TwitterElasticIndexClient implements ElasticIndexClient<TwitterIndexModel> {
    private final ElasticConfigData elasticConfigData;
    private final ElasticsearchOperations elasticsearchOperations;
    private final ElasticIndexUtil<TwitterIndexModel> elasticIndexUtil;

    @Override
    public List<String> save(List<TwitterIndexModel> documents) {
        List<IndexQuery> indexQueries = elasticIndexUtil.getIndexQueries(documents);

        List<String> documentIds = elasticsearchOperations.bulkIndex(
                indexQueries,
                IndexCoordinates.of(elasticConfigData.getIndexName())
        ).stream().map(IndexedObjectInformation::id).toList();

        log.info("Documents indexed successfully with type: {} and ids: {}", TwitterIndexModel.class.getName(),
                documentIds);
        return documentIds;
    }
}
