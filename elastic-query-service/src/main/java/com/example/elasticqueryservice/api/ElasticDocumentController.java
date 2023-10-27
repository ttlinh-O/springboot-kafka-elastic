package com.example.elasticqueryservice.api;

import com.example.elasticqueryservice.business.ElasticQueryService;
import com.example.elasticqueryservice.model.ElasticQueryServiceRequestModel;
import com.example.elasticqueryservice.model.ElasticQueryServiceResponseModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/documents")
@Slf4j
@RequiredArgsConstructor
public class ElasticDocumentController {
    private final ElasticQueryService elasticQueryService;

    @GetMapping("/")
    public @ResponseBody ResponseEntity<List<ElasticQueryServiceResponseModel>> getAllDocuments() {
        List<ElasticQueryServiceResponseModel> responses = elasticQueryService.getAllDocuments();
        log.info("Elasticsearch returned {} of documents", responses.size());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public @ResponseBody ResponseEntity<ElasticQueryServiceResponseModel>
    getDocumentById(@PathVariable(value = "id") String id) {
        ElasticQueryServiceResponseModel response = elasticQueryService.getDocumentById(id);
        log.info("Elasticsearch returned document with id {}", response.getId());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/get-document-by-text")
    public @ResponseBody
    ResponseEntity<List<ElasticQueryServiceResponseModel>>
    getDocumentByText(@RequestBody ElasticQueryServiceRequestModel elasticQueryServiceRequestModel) {
        List<ElasticQueryServiceResponseModel> response = elasticQueryService.getDocumentByText(elasticQueryServiceRequestModel.getText());
        log.info("Elasticsearch returned {} of documents", response.size());
        return ResponseEntity.ok(response);
    }
}
