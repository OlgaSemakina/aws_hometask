package com.amazon.aws.objects;

import com.amazon.aws.constants.Constants;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.xspec.ExpressionSpecBuilder;
import com.amazonaws.services.dynamodbv2.xspec.ScanExpressionSpec;

import static com.amazon.aws.base.BaseInitialization.hashKey;
import static com.amazon.aws.base.BaseInitialization.tableName;
import static com.amazonaws.services.dynamodbv2.xspec.ExpressionSpecBuilder.S;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DynamoDatabase {

    private DynamoDB dynamoDB;

    public DynamoDatabase(Regions region) {
        dynamoDB = new DynamoDB(AmazonDynamoDBClientBuilder.standard()
                .withRegion(region)
                .build());
    }

    //================================methods===================================

    boolean findRowTable(String key) {
        Table table = dynamoDB.getTable(tableName);
        ScanExpressionSpec xspec = new ExpressionSpecBuilder()
                .withCondition(
                        S(hashKey).eq(key))
                .buildForScan();
        for (Item item : table.scan(xspec)) {
            if (item.getString(hashKey).equals(key))
                return true;
        }
        return false;
    }

    public void loadData(String hashKeyValue, long sortKeyValue, String secondaryIndex1Value, String secondaryIndex2Value) {
        Table table = dynamoDB.getTable(Constants.tableName);
        Item item = new Item().withPrimaryKey(Constants.hashKey, hashKeyValue).withNumber(Constants.sortKey, sortKeyValue)
                .withString(Constants.secondaryIndex1, secondaryIndex1Value).withString(Constants.secondaryIndex2,
                        secondaryIndex2Value);
        try {
            table.putItem(item);
            table.waitForActive();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }


    public void removeData(String hashKeyValue) {
        Table table = dynamoDB.getTable(Constants.tableName);
        try {
            ScanExpressionSpec xspec = new ExpressionSpecBuilder()
                    .withCondition(
                            S(Constants.hashKey).eq(hashKeyValue))
                    .buildForScan();

            for (Item item : table.scan(xspec)) {
                table.deleteItem(Constants.hashKey, hashKeyValue, Constants.sortKey, item.getNumber(Constants.sortKey));
            }
            table.waitForActive();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    //================================checks===================================

    public void checkTableExists() {
        assertNotNull(dynamoDB.getTable(tableName));
    }
}
