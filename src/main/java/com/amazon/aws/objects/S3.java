package com.amazon.aws.objects;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import java.io.File;

import static com.amazon.aws.base.BaseInitialization.bucketName;
import static com.amazon.aws.constants.Constants.LOCATION;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class S3 {
    private AmazonS3 s3;

    public S3(Regions region) {
        s3 = AmazonS3ClientBuilder.standard()
                .withRegion(region)
                .build();
    }

    public AmazonS3 getS3() {
        return s3;
    }

    //================================methods===================================

    void deleteObject(String key) {
        s3.deleteObject(bucketName, key);
    }

    public void cleanBucket() {
        ObjectListing objectListing = s3.listObjects(new ListObjectsRequest()
                .withBucketName(bucketName));
        for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
            s3.deleteObject(bucketName, objectSummary.getKey());
        }
    }


    boolean containsObject(File file) {
        return s3.doesObjectExist(bucketName, file.getName());
    }
    //================================checks===================================

    public void checkBucketExists() {
        assertTrue(s3.doesBucketExistV2(bucketName));
    }

    public void checkAmountOfBuckets(int number) {
        assertEquals(s3.listBuckets().size(), number);
    }

    public void checkBucketLocation() {
        assertEquals(s3.getBucketLocation(bucketName), LOCATION);
    }

}
