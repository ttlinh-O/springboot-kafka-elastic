package com.example.elasticconfig.config;

import com.example.appconfigdata.ElasticConfigData;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@RequiredArgsConstructor
//@EnableElasticsearchRepositories(basePackages = "com.example.elasticindexclient.client.repository")
@EnableElasticsearchRepositories
public class ElasticSearchConfig extends ElasticsearchConfiguration {
    private final ElasticConfigData elasticConfigData;

    @Override
    public @NonNull ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo(elasticConfigData.getConnectionUrl())
                .withConnectTimeout(elasticConfigData.getConnectTimeoutMs())
                .withSocketTimeout(elasticConfigData.getSocketTimeoutMs())
                .build();
    }
}
