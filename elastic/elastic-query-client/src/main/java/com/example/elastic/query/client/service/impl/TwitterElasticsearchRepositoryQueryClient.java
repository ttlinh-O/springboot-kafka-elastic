package com.example.elastic.query.client.service.impl;

import com.example.commonutil.CollectionUtil;
import com.example.elastic.query.client.exception.ElasticQueryClientException;
import com.example.elastic.query.client.repository.TwitterElasticSearchQueryRepository;
import com.example.elasticmodel.index.impl.TwitterIndexModel;
import com.example.elastic.query.client.service.ElasticQueryClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Primary
@Service
@Slf4j
@RequiredArgsConstructor
public class TwitterElasticsearchRepositoryQueryClient implements ElasticQueryClient<TwitterIndexModel> {
    private final TwitterElasticSearchQueryRepository twitterElasticSearchQueryRepository;

    @Override
    public TwitterIndexModel getIndexModelById(String id) {
        Optional<TwitterIndexModel> searchResult = twitterElasticSearchQueryRepository.findById(id);
        log.info("Document with id {} retrived successfully",
                searchResult.orElseThrow(() ->
                        new ElasticQueryClientException("No document found at elasticsearch with id " + id)).getId());
        return searchResult.get();
    }

    @Override
    public List<TwitterIndexModel> getIndexModelByText(String text) {
        List<TwitterIndexModel> searchResults = twitterElasticSearchQueryRepository.findByText(text);
        log.info("{} of document with text {} retrieved successfully", searchResults.size(), text);
        return searchResults;
    }

    @Override
    public List<TwitterIndexModel> getAllIndexModels() {
        Iterable<TwitterIndexModel> searchResults = twitterElasticSearchQueryRepository.findAll();
        return CollectionUtil.getInstance().getListFromIterable(searchResults);
    }
}
