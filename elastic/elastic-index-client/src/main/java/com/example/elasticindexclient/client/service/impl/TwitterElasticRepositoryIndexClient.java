package com.example.elasticindexclient.client.service.impl;

import com.example.elasticindexclient.client.repository.TwitterElasticsearchIndexRepository;
import com.example.elasticindexclient.client.service.ElasticIndexClient;
import com.example.elasticmodel.index.impl.TwitterIndexModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Primary
@Service
@Slf4j
@RequiredArgsConstructor
@ConditionalOnProperty(name = "elastic-config.is-repository", havingValue = "false")
public class TwitterElasticRepositoryIndexClient implements ElasticIndexClient<TwitterIndexModel> {
    private final TwitterElasticsearchIndexRepository twitterElasticsearchIndexRepository;

    @Override
    public List<String> save(List<TwitterIndexModel> documents) {
        List<TwitterIndexModel> repositoryResponse = (List<TwitterIndexModel>) twitterElasticsearchIndexRepository.saveAll(documents);
        List<String> ids = repositoryResponse.stream().map(TwitterIndexModel::getId).toList();
        log.info("Documents indexed successfully with type: {} and ids: {}", TwitterIndexModel.class.getCanonicalName(),
                ids);
        return ids;
    }
}
