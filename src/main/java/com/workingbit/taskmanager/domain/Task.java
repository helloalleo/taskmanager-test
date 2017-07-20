package com.workingbit.taskmanager.domain;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Data;

import java.util.Set;

/**
 * Created by Aleksey Popryaduhin on 09:39 15/07/2017.
 */
@DynamoDBTable(tableName = "Task")
@Data
public class Task {

    @DynamoDBHashKey(attributeName = "Id")
    private String id;

    @DynamoDBAttribute(attributeName = "Username")
    private String username;

    @DynamoDBAttribute(attributeName = "Email")
    private String email;

    @DynamoDBAttribute(attributeName = "Content")
    private String content;

    @DynamoDBAttribute(attributeName = "Completed")
    private boolean completed;

    @DynamoDBAttribute(attributeName = "Pictures")
    private Set<String> pictures;
}
