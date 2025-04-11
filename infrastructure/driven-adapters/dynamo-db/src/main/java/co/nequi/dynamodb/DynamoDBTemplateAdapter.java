package co.nequi.dynamodb;

import co.nequi.dynamodb.entity.UserEntity;
import co.nequi.dynamodb.helper.TemplateAdapterOperations;
import co.nequi.model.user.User;
import co.nequi.model.user.gateways.IMessageGateway;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;

import java.util.List;


@Repository
public class DynamoDBTemplateAdapter extends TemplateAdapterOperations<User /*domain model*/, String, UserEntity
        /*adapter model*/>  implements IMessageGateway/* implements Gateway from domain */ {

    public DynamoDBTemplateAdapter(DynamoDbEnhancedAsyncClient connectionFactory, ObjectMapper mapper, com.fasterxml.jackson.databind.ObjectMapper mapper2) {
        /**
         *  Could be use mapper.mapBuilder if your domain model implement builder pattern
         *  super(repository, mapper, d -> mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
         *  Or using mapper.map with the class of the object model
         */
        super(connectionFactory, mapper, d -> mapper.map(d, User.class /*domain model*/), "UsersTable", mapper2);
    }

    public Mono<List<User /*domain model*/>> getEntityBySomeKeys(String partitionKey, String sortKey) {
        QueryEnhancedRequest queryExpression = generateQueryExpression(partitionKey, sortKey);
        return query(queryExpression);
    }

    public Mono<List<User /*domain model*/>> getEntityBySomeKeysByIndex(String partitionKey, String sortKey) {
        QueryEnhancedRequest queryExpression = generateQueryExpression(partitionKey, sortKey);
        return queryByIndex(queryExpression);
    }

    private QueryEnhancedRequest generateQueryExpression(String partitionKey, String sortKey) {
        return QueryEnhancedRequest.builder()
                .queryConditional(QueryConditional.keyEqualTo(Key.builder().partitionValue(partitionKey).build()))
                .queryConditional(QueryConditional.sortGreaterThanOrEqualTo(Key.builder().sortValue(sortKey).build()))
                .build();
    }
}
