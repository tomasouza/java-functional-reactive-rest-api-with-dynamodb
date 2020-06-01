package br.com.oigarcom.api.data;

import br.com.oigarcom.api.users.data.mappers.UserDocumentMapper;
import br.com.oigarcom.api.users.data.providers.DataProviderFactory;
import br.com.oigarcom.api.users.data.providers.UserDataProvider;
import br.com.oigarcom.api.users.data.repositories.UserRepository;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;

import static br.com.oigarcom.api.users.data.utils.UserTableConstants.USER_TABLE_NAME;

public class TestContext {
    public static final DynamoDB dynamoDB = new DynamoDB(
            AmazonDynamoDBClientBuilder.standard()
                    .withEndpointConfiguration(new AwsClientBuilder
                            .EndpointConfiguration(
                            "http://localhost:8000",
                            Regions.SA_EAST_1.getName())
                    ).build());
    public static final UserRepository userRepository = new UserRepository(dynamoDB.getTable(USER_TABLE_NAME), new UserDocumentMapper());
    public static final UserDataProvider userDataProvider = DataProviderFactory.getEmbeddedUserDataProvider();
}
