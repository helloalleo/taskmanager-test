package com.workingbit.taskmanager.service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.amazonaws.services.dynamodbv2.model.*;
import com.workingbit.taskmanager.config.AWSProperties;
import com.workingbit.taskmanager.domain.Task;
import com.workingbit.taskmanager.exception.DataAccessException;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;

/**
 * Created by Aleksey Popryaduhin on 20:11 10/06/2017.
 */
@Service
public class TaskManagerService {


    private final AWSProperties awsProperties;
    private final DynamoDBMapper dynamoDBMapper;

    @Inject
    public TaskManagerService(AWSProperties awsProperties) {
        this.awsProperties = awsProperties;
        AmazonDynamoDB ddb = AmazonDynamoDBClientBuilder.defaultClient();
        dynamoDBMapper = new DynamoDBMapper(ddb);
        try {
            if (!doesTableExist(awsProperties.getUserTable(), ddb)) {
                createDeviceTable(ddb);
            }
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to create device table.", e);
        }
    }

    public void save(Task task) {
        dynamoDBMapper.save(task);
    }

    public PaginatedScanList find() {
        DynamoDBScanExpression dynamoDBQueryExpression = new DynamoDBScanExpression();
        return dynamoDBMapper.scan(Task.class, dynamoDBQueryExpression);
    }

    protected boolean doesTableExist(String tableName, AmazonDynamoDB ddb) throws DataAccessException {
        try {
            DescribeTableRequest request = new DescribeTableRequest().withTableName(tableName);
            DescribeTableResult result = ddb.describeTable(request);
            return "ACTIVE".equals(result.getTable().getTableStatus());
        } catch (ResourceNotFoundException e) {
            return false;
        } catch (AmazonClientException e) {
            throw new DataAccessException("Failed to get status of table: " + tableName, e);
        }
    }

    protected void createDeviceTable(AmazonDynamoDB ddb) throws DataAccessException {
        ProvisionedThroughput provisionedThroughput = new ProvisionedThroughput()
                .withReadCapacityUnits(awsProperties.getReadCapacityUnits())
                .withWriteCapacityUnits(awsProperties.getWriteCapacityUnits());

        ArrayList<AttributeDefinition> attributeDefinitions = new ArrayList<>();
        attributeDefinitions.add(new AttributeDefinition().withAttributeName(
                "Id").withAttributeType("S"));

        ArrayList<KeySchemaElement> tableKeySchema = new ArrayList<>();
        tableKeySchema.add(new KeySchemaElement().withAttributeName("Id")
                .withKeyType(KeyType.HASH));

        CreateTableRequest createTableRequest = new CreateTableRequest()
                .withTableName(awsProperties.getUserTable())
                .withProvisionedThroughput(provisionedThroughput)
                .withAttributeDefinitions(attributeDefinitions)
                .withKeySchema(tableKeySchema);

        try {
            ddb.createTable(createTableRequest);
        } catch (AmazonClientException e) {
            throw new DataAccessException("Failed to create table: " + awsProperties.getUserTable(), e);
        }
    }
}
