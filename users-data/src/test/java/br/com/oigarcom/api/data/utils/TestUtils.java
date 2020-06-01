package br.com.oigarcom.api.data.utils;

import br.com.oigarcom.api.data.TestContext;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.*;

import java.util.ArrayList;
import java.util.List;

import static br.com.oigarcom.api.users.data.utils.UserTableConstants.EMAIL;
import static br.com.oigarcom.api.users.data.utils.UserTableConstants.USER_TABLE_NAME;

public class TestUtils {
    public static void createUserTable() {
        try {
            List<AttributeDefinition> attributeDefinitions = new ArrayList<>();
            attributeDefinitions.add(new AttributeDefinition()
                    .withAttributeName(EMAIL)
                    .withAttributeType("S"));

            List<KeySchemaElement> keySchema = new ArrayList<>();
            keySchema.add(new KeySchemaElement()
                    .withAttributeName(EMAIL)
                    .withKeyType(KeyType.HASH));

            CreateTableRequest request = new CreateTableRequest()
                    .withTableName(USER_TABLE_NAME)
                    .withKeySchema(keySchema)
                    .withAttributeDefinitions(attributeDefinitions)
                    .withProvisionedThroughput(
                            new ProvisionedThroughput()
                                    .withReadCapacityUnits(5L)
                                    .withWriteCapacityUnits(5L));

            Table table = TestContext.dynamoDB.createTable(request);
            table.waitForActive();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
