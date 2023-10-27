package com.example.elastic.query.client.service.impl;

import com.example.appconfigdata.ElasticConfigData;
import com.example.appconfigdata.ElasticQueryConfigData;
import com.example.elastic.query.client.exception.ElasticQueryClientException;
import com.example.elastic.query.client.util.ElasticQueryUtil;
import com.example.elasticmodel.index.impl.TwitterIndexModel;
import com.example.elastic.query.client.service.ElasticQueryClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TwitterElasticQueryClient implements ElasticQueryClient<TwitterIndexModel> {
    private final ElasticConfigData elasticConfigData;
    private final ElasticQueryConfigData elasticQueryConfigData;
    private final ElasticsearchOperations elasticsearchOperations;
    private final ElasticQueryUtil<TwitterIndexModel> elasticQueryUtil;

    @Override
    public TwitterIndexModel getIndexModelById(String id) {
        Query query = elasticQueryUtil.getSearchQueryById(id);
        SearchHit<TwitterIndexModel> searchResult = elasticsearchOperations.searchOne(query, TwitterIndexModel.class,
                IndexCoordinates.of(elasticConfigData.getIndexName()));
        if (searchResult == null) {
            log.info("No document found at elasticsearch with id: {}", id);
            throw new ElasticQueryClientException("No document found at elsaticsearch with id:" + id);
        }

        log.info("Document with id {} retrieved successfully", searchResult.getId());
        return searchResult.getContent();
    }

    @Override
    public List<TwitterIndexModel> getIndexModelByText(String text) {
        Query query = elasticQueryUtil.getSearchQueryByFieldText(elasticQueryConfigData.getTextField(), text);
        return search(query);
    }

    @Override
    public List<TwitterIndexModel> getAllIndexModels() {
        Query query = elasticQueryUtil.getSearchQueryForAll();
        return search(query);
    }

    private List<TwitterIndexModel> search(Query query) {
        SearchHits<TwitterIndexModel> searchResults = elasticsearchOperations.search(query, TwitterIndexModel.class,
                IndexCoordinates.of(elasticConfigData.getIndexName()));
        log.info("{} number of documents retrieved successfully", searchResults.getTotalHits());
        return searchResults.stream().map(SearchHit::getContent).toList();
    }
}
