package com.mycompany.user.demo.repository;

import com.mycompany.user.demo.model.UserProfile;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;

import java.util.Map;
import java.util.Optional;

@Repository
public class UserRepository {
    private final DynamoDbClient dynamoDbClient;
    private final String usersTableName;

    public UserRepository() {
        this.dynamoDbClient = DynamoDbClient.builder()
                .region(Region.of(System.getenv().getOrDefault("AWS_REGION",
                        System.getenv().getOrDefault("AWS_DEFAULT_REGION", "us-west-2"))))
                .build();
        this.usersTableName = System.getenv().getOrDefault("USERS_TABLE_NAME", "demo-users-tf");
    }

    public Optional<UserProfile> findById(int userId) {
        Map<String, AttributeValue> item = dynamoDbClient.getItem(GetItemRequest.builder()
                        .tableName(usersTableName)
                        .key(Map.of("id", AttributeValue.fromN(Integer.toString(userId))))
                        .build())
                .item();

        if (item == null || item.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(toUserProfile(item));
    }

    private UserProfile toUserProfile(Map<String, AttributeValue> item) {
        return new UserProfile(
                Integer.parseInt(item.get("id").n()),
                item.get("name").s(),
                item.get("membershipLevel").s(),
                item.get("region").s()
        );
    }
}
