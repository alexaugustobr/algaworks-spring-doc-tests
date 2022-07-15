
package com.algaworks.example;

import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Configuration
public class SpringDocConfig {

    public static final String BAD_REQUEST_VALUE = "400";
    public static final String NOT_FOUND_VALUE = "404";
    public static final String NOT_ACCEPTABLE_VALUE = "415";
    public static final String INTERNAL_SERVER_ERROR_VALUE = "500";

    public static final String BAD_REQUEST_RESPONSE = "BadRequest";
    public static final String NOT_FOUND_RESPONSE = "NotFound";
    public static final String NOT_ACCEPTABLE_RESPONSE = "NotAcceptable";
    public static final String INTERNAL_SERVER_ERROR_RESPONSE = "InternalServerError";

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
            .info(new Info().title("Book API")
                    .description("REST API")
                    .version("v1")
                    .license(new License().name("Apache 2.0").url("http://springdoc.org")))
            .components(new Components()
                    .schemas(createSchemas())
                    .responses(createDefaultResponses())
            );
    }

    @Bean
    public OpenApiCustomiser globalOpenApiCustomiser() {
        return openApi -> {
            openApi.getPaths().values()
                    .forEach(pathItem -> pathItem.readOperationsMap().forEach((httpMethod, operation) -> {
                        Consumer<ApiResponses> atribuirRespostasPadrao =
                                switch (httpMethod) {
                                    case GET -> createDefaultGetResponses();
                                    case POST -> createDefaultPostResponses();
                                    case PUT -> createDefaultPutResponses();
                                    case DELETE -> createDefaultDeleteResponses();
                                    default -> responses -> {};
                                };
                        atribuirRespostasPadrao.accept(operation.getResponses());
                    }));
        };
    }

    private Map<String, Schema> createSchemas() {
        final Map<String, Schema> schemas = new LinkedHashMap<>();

        Map<String, Schema> problemSchema = ModelConverters.getInstance().read(Problem.class);
        Map<String, Schema> problemObjectSchema = ModelConverters.getInstance().read(Problem.Object.class);

        schemas.putAll(problemSchema);
        schemas.putAll(problemObjectSchema);

        return schemas;
    }

    private Map<String, ApiResponse> createDefaultResponses() {
        final Map<String, ApiResponse> responses = new LinkedHashMap<>();

        Content problemaContent = new Content()
                .addMediaType(APPLICATION_JSON_VALUE, new MediaType().schema(new Schema<Problem>().$ref("Problem")));

        responses.put(BAD_REQUEST_RESPONSE, new ApiResponse()
                .description("Invalid request")
                .content(problemaContent));

        responses.put(NOT_FOUND_RESPONSE, new ApiResponse()
                .description("Resource not found")
                .content(problemaContent));

        responses.put(NOT_ACCEPTABLE_RESPONSE, new ApiResponse()
                .description("Resource has no representation that could be accepted by the consumer")
                .content(problemaContent));

        responses.put(INTERNAL_SERVER_ERROR_RESPONSE, new ApiResponse().description("Internal Server error")
                .content(problemaContent));

        return responses;
    }

    private Consumer<ApiResponses> createDefaultGetResponses() {
        return responses -> {
            responses.addApiResponse(NOT_FOUND_VALUE, new ApiResponse().$ref(NOT_FOUND_RESPONSE));
            responses.addApiResponse(NOT_ACCEPTABLE_VALUE, new ApiResponse().$ref(NOT_ACCEPTABLE_RESPONSE));
            responses.addApiResponse(INTERNAL_SERVER_ERROR_VALUE, new ApiResponse().$ref(INTERNAL_SERVER_ERROR_RESPONSE));
        };
    }

    private Consumer<ApiResponses> createDefaultPutResponses() {
        return responses -> {
            responses.addApiResponse(BAD_REQUEST_VALUE, new ApiResponse().$ref(BAD_REQUEST_RESPONSE));
            responses.addApiResponse(NOT_FOUND_VALUE, new ApiResponse().$ref(NOT_FOUND_RESPONSE));
            responses.addApiResponse(NOT_ACCEPTABLE_VALUE, new ApiResponse().$ref(NOT_ACCEPTABLE_RESPONSE));
            responses.addApiResponse(INTERNAL_SERVER_ERROR_VALUE, new ApiResponse().$ref(INTERNAL_SERVER_ERROR_RESPONSE));
        };
    }

    private Consumer<ApiResponses> createDefaultPostResponses() {
        return responses -> {
            responses.addApiResponse(BAD_REQUEST_VALUE, new ApiResponse().$ref(BAD_REQUEST_RESPONSE));
            responses.addApiResponse(NOT_FOUND_VALUE, new ApiResponse().$ref(NOT_FOUND_RESPONSE));
            responses.addApiResponse(INTERNAL_SERVER_ERROR_VALUE, new ApiResponse().$ref(INTERNAL_SERVER_ERROR_RESPONSE));
        };
    }

    private Consumer<ApiResponses> createDefaultDeleteResponses() {
        return responses -> {
            responses.addApiResponse(NOT_FOUND_VALUE, new ApiResponse().$ref(NOT_FOUND_RESPONSE));
            responses.addApiResponse(INTERNAL_SERVER_ERROR_VALUE, new ApiResponse().$ref(INTERNAL_SERVER_ERROR_RESPONSE));
        };
    }

}
