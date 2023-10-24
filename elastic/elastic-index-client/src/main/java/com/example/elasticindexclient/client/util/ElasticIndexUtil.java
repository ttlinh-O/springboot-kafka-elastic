package com.example.elasticindexclient.client.util;

import com.example.elasticmodel.index.IndexModel;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ElasticIndexUtil <T extends IndexModel> {
    public List<IndexQuery> getIndexQueries(List<T> documents) {
        return documents.stream()
                .map(document -> new IndexQueryBuilder()
                        .withIndex(document.getId())
                        .withObject(document)
                        .build())
                .toList();
    }
}
