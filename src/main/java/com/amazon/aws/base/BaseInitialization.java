package com.amazon.aws.base;
import com.amazon.aws.TestProperties;
import com.amazon.aws.objects.DynamoDatabase;
import com.amazon.aws.objects.FileObject;
import com.amazon.aws.objects.Lambda;
import com.amazon.aws.objects.S3;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import org.junit.jupiter.api.BeforeAll;

import java.io.IOException;

import static com.amazon.aws.constants.Constants.region;

public abstract class BaseInitialization {
    protected static FileObject fileObject;
    protected static Lambda lambda;

    public static String bucketName;
    public static String tableName;
    public static String hashKey;

    public static S3 s3;
    protected static DynamoDatabase database;
    protected static TransferManager transferManager;

    @BeforeAll
    public static void setUp() throws IOException {

        // Reading properties
        TestProperties properties = new TestProperties();
        bucketName = properties.getProperty("bucketName");
        tableName = properties.getProperty("tableName");
        hashKey = properties.getProperty("hashKey");

        //Initializing com.amazon.aws.objects
        s3 = new S3(region);
        database = new DynamoDatabase(region);
        transferManager = TransferManagerBuilder
                .standard()
                .withS3Client(s3.getS3())
                .build();
        fileObject = new FileObject();
        lambda = new Lambda();


    }

}
