package com.example.elasticqueryservice.model.assembler;

import com.example.elasticmodel.index.impl.TwitterIndexModel;
import com.example.elasticqueryservice.api.ElasticDocumentController;
import com.example.elasticqueryservice.model.ElasticQueryServiceResponseModel;
import com.example.elasticqueryservice.transformer.ElasticToResponseModelTransformer;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Component
public class ElasticQueryServiceModelAssembler extends RepresentationModelAssemblerSupport<TwitterIndexModel, ElasticQueryServiceResponseModel> {

    private final ElasticToResponseModelTransformer elasticToResponseModelTransformer;

    /**
     * Creates a new {@link RepresentationModelAssemblerSupport} using the given controller class and resource type.
     *
     * @param elasticToResponseModelTransformer    must not be {@literal null}.
     */
    public ElasticQueryServiceModelAssembler(ElasticToResponseModelTransformer elasticToResponseModelTransformer) {
        super(ElasticDocumentController.class, ElasticQueryServiceResponseModel.class);
        this.elasticToResponseModelTransformer = elasticToResponseModelTransformer;
    }

    @Override
    public ElasticQueryServiceResponseModel toModel(TwitterIndexModel twitterIndexModel) {
        ElasticQueryServiceResponseModel responseModel =
                elasticToResponseModelTransformer.getResponseModel(twitterIndexModel);
        responseModel.add(
                linkTo(methodOn(ElasticDocumentController.class)
                        .getDocumentById((twitterIndexModel.getId())))
                        .withSelfRel());
        responseModel.add(
                linkTo(ElasticDocumentController.class)
                        .withRel("documents"));
        return responseModel;
    }

    public List<ElasticQueryServiceResponseModel> toModels(List<TwitterIndexModel> twitterIndexModels) {
        return twitterIndexModels.stream().map(this::toModel).collect(Collectors.toList());
    }
}
