package com.example.elasticindexclient.client.service;

import com.example.elasticmodel.index.IndexModel;

import java.util.List;

public interface ElasticIndexClient <T extends IndexModel> {
    List<String> save(List<T> documents);
}
