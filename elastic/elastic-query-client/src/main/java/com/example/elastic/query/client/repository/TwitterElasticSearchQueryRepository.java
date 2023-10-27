package com.example.elastic.query.client.repository;

import com.example.elasticmodel.index.impl.TwitterIndexModel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TwitterElasticSearchQueryRepository extends ElasticsearchRepository<TwitterIndexModel, String> {
    List<TwitterIndexModel> findByText(String text);
}
