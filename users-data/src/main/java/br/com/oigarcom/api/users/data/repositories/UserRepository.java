package br.com.oigarcom.api.users.data.repositories;

import br.com.oigarcom.api.users.data.documents.UserDocument;
import br.com.oigarcom.api.users.data.mappers.UserDocumentMapper;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.*;
import com.amazonaws.services.dynamodbv2.document.utils.NameMap;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static br.com.oigarcom.api.users.data.utils.RepositoryExceptionUtils.handleAmazonClientException;
import static br.com.oigarcom.api.users.data.utils.RepositoryExceptionUtils.handleAmazonServiceException;
import static br.com.oigarcom.api.users.data.utils.UserTableConstants.*;

public class UserRepository {

    public final static String GET_USER_ERROR = "An error occurred while searching for user in AWS DynamoDB. ";
    public final static String SAVE_USER_ERROR = "An error occurred while saving an user in AWS DynamoDB. ";
    public final static String UPDATE_USER_ERROR = "An error occurred while updating an user in AWS DynamoDB. ";
    public final static String DELETE_USER_ERROR = "An error occurred while deleting an user in AWS DynamoDB. ";
    public final static String LIST_USER_ERROR = "An error occurred while listing users in AWS DynamoDB. ";

    private static final Logger log = LoggerFactory.getLogger(UserRepository.class);
    public static final String ERROR_CREATING_USERS_TABLE = "Error creating Users table. ";
    private final Table userTable;
    private final UserDocumentMapper userDocumentMapper;

    public UserRepository(Table table, UserDocumentMapper userDocumentMapper){
        this.userTable = table;
        this.userDocumentMapper = userDocumentMapper;
    }

    public UserDocument get(String email) {

        GetItemSpec spec = new GetItemSpec()
                .withPrimaryKey(EMAIL, email);

        try {

            Item item = userTable.getItem(spec);
            if(Optional.ofNullable(item).isPresent()) {
                log.debug("User document retrieved: " + item.toJSONPretty());
                return userDocumentMapper.mapToUserDocument(item);
            } else {
                log.debug("Could not find User [Email=" + email + "]");
                return null;
            }

        } catch (AmazonServiceException ase) {
            throw handleAmazonServiceException(ase, GET_USER_ERROR);
        } catch (AmazonClientException ace) {
            throw handleAmazonClientException(ace, GET_USER_ERROR);
        }
    }

    public void save(UserDocument userDocument) {
        try{

            Item userItem = userDocumentMapper.mapToItem(userDocument);
            userTable.putItem(userItem);
            log.debug("New user created: " + userItem.toJSONPretty());

        } catch (AmazonServiceException ase) {
            throw handleAmazonServiceException(ase, SAVE_USER_ERROR);
        } catch (AmazonClientException ace) {
            throw handleAmazonClientException(ace, SAVE_USER_ERROR);
        }
    }

    public void update(UserDocument userDocument) {
        try{

            UpdateItemSpec updateItemSpec = new UpdateItemSpec()
                    .withPrimaryKey(EMAIL, userDocument.getEmail())
                    .withUpdateExpression("set #sa1 = :val1, #sa2 = :val2")
                    .withNameMap(new NameMap()
                            .with("#sa1", NAME)
                            .with("#sa2", PASSWORD))
                    .withValueMap(new ValueMap()
                            .withString(":val1", userDocument.getName())
                            .withString(":val2", userDocument.getPassword()))
                    .withReturnValues(ReturnValue.ALL_NEW);

            UpdateItemOutcome outcome = userTable.updateItem(updateItemSpec);
            log.debug("User updated: " + outcome.getItem().toJSONPretty());

        } catch (AmazonServiceException ase) {
            throw handleAmazonServiceException(ase, UPDATE_USER_ERROR);
        } catch (AmazonClientException ace) {
            throw handleAmazonClientException(ace, UPDATE_USER_ERROR);
        }
    }

    public void delete(String email) {
        try{

            DeleteItemSpec deleteItemSpec = new DeleteItemSpec()
                    .withPrimaryKey(EMAIL, email)
                    .withReturnValues(ReturnValue.ALL_OLD);
            DeleteItemOutcome outcome = userTable.deleteItem(deleteItemSpec);
            if(Optional.ofNullable(outcome.getItem()).isPresent())
                log.info("User deleted: " + outcome.getItem().toJSONPretty());

        } catch (AmazonServiceException ase) {
            throw handleAmazonServiceException(ase, DELETE_USER_ERROR);
        } catch (AmazonClientException ace) {
            throw handleAmazonClientException(ace, DELETE_USER_ERROR);
        }
    }

    public List<UserDocument> list(int pageSize, String lastEvaluatedKey) {
        try{
            ScanSpec scanSpec = new ScanSpec()
                    .withMaxPageSize(pageSize)
                    .withMaxResultSize(pageSize);
            if(Optional.ofNullable(lastEvaluatedKey).isPresent()) {
                scanSpec.withExclusiveStartKey(EMAIL, lastEvaluatedKey);
            }

            ItemCollection<ScanOutcome> items = userTable.scan(scanSpec);
            return userDocumentMapper.mapToUserDocuments(items);

        } catch (AmazonServiceException ase) {
            throw handleAmazonServiceException(ase, LIST_USER_ERROR);
        } catch (AmazonClientException ace) {
            throw handleAmazonClientException(ace, LIST_USER_ERROR);
        }
    }

    public void createUserTable(DynamoDB dynamoDB) {
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

            Table table = dynamoDB.createTable(request);
            table.waitForActive();
        } catch (Exception e) {
            log.error(ERROR_CREATING_USERS_TABLE, e);
        }
    }
}
