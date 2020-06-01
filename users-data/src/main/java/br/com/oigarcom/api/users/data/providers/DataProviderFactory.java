package br.com.oigarcom.api.users.data.providers;

import br.com.oigarcom.api.users.data.mappers.UserDocumentMapper;
import br.com.oigarcom.api.users.data.repositories.UserRepository;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;

import static br.com.oigarcom.api.users.data.utils.UserTableConstants.USER_TABLE_NAME;

public class DataProviderFactory {

    public static UserDataProvider getUserDataProvider() {
        DynamoDB dynamoDB = new DynamoDB(AmazonDynamoDBClientBuilder.standard()
                .withRegion(Regions.SA_EAST_1)
                .build());
        return new UserDataProvider(dynamoDB, new UserDocumentMapper());
    }

    public static UserDataProvider getEmbeddedUserDataProvider() {
        DynamoDB dynamoDB = new DynamoDB(
            AmazonDynamoDBClientBuilder.standard()
                    .withEndpointConfiguration(new AwsClientBuilder
                            .EndpointConfiguration(
                            "http://localhost:8000",
                            Regions.SA_EAST_1.getName())
                    ).build());
        return new UserDataProvider(dynamoDB, new UserDocumentMapper());
    }

}
