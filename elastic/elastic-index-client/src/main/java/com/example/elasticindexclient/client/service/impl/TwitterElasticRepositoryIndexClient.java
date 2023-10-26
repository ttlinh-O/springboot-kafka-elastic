package com.example.elasticindexclient.client.service.impl;

import com.example.elasticindexclient.client.repository.TwitterElasticsearchIndexRepository;
import com.example.elasticindexclient.client.service.ElasticIndexClient;
import com.example.elasticmodel.index.impl.TwitterIndexModel;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@ConditionalOnProperty(name = "elastic-config.is-repository", havingValue = "true", matchIfMissing = true)
@RequiredArgsConstructor
public class TwitterElasticRepositoryIndexClient implements ElasticIndexClient<TwitterIndexModel> {
    private static final Logger LOG = LoggerFactory.getLogger(TwitterElasticRepositoryIndexClient.class);

    private final TwitterElasticsearchIndexRepository twitterElasticsearchIndexRepository;

    @Override
    public List<String> save(List<TwitterIndexModel> documents) {
        List<TwitterIndexModel> repositoryResponse =
                (List<TwitterIndexModel>) twitterElasticsearchIndexRepository.saveAll(documents);
        List<String> ids = repositoryResponse.stream().map(TwitterIndexModel::getId).toList();
        LOG.info("Documents indexed successfully with type: {} and ids: {}", TwitterIndexModel.class.getName(), ids);
        return ids;
    }
}
