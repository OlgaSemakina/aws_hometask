package com.amazon.aws.objects;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Lambda {

    //================================checks===================================

    public void checkLambdaTriggeredAdd(DynamoDatabase database, File file) {
        assertTrue(database.findRowTable(file.getName()));
    }

    public void checkLambdaTriggeredDelete(DynamoDatabase database, File file) {
        assertFalse(database.findRowTable(file.getName()));
    }
}
