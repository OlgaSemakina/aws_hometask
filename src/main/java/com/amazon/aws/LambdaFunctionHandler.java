package com.amazon.aws;

import com.amazon.aws.objects.DynamoDatabase;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.event.S3EventNotification;
import com.amazonaws.services.s3.model.S3Object;

import static com.amazon.aws.constants.Constants.*;
import static com.amazon.aws.constants.Logs.*;
import static com.amazon.aws.utils.Utils.dateToInt;
import static com.amazonaws.services.s3.model.S3Event.ObjectCreatedByPut;
import static com.amazonaws.services.s3.model.S3Event.ObjectRemovedDelete;


public class LambdaFunctionHandler implements RequestHandler<S3Event, String> {

    private AmazonS3 s3 = AmazonS3ClientBuilder.standard()
            .withRegion(region)
            .build();
    private DynamoDatabase database = new DynamoDatabase(region);


    @Override
    public String handleRequest(S3Event event, Context context) {
        try {
            S3EventNotification.S3EventNotificationRecord record = event.getRecords().get(0);
            String eventName = record.getEventName();
            String bucketName = record.getS3().getBucket().getName();
            String key = record.getS3().getObject().getKey();

            context.getLogger().log(EVENT + eventName);
            context.getLogger().log(FILENAME + key);
            context.getLogger().log(BUCKET + bucketName);
            if (eventName.equals(ObjectCreatedByPut.toString().substring(3))) {
                S3Object object = s3.getObject(bucketName, key);

                String objectPath = bucketName + s3.getUrl(bucketName, key).getPath();
                long timestamp = dateToInt(record.getEventTime());
                String fileType = object.getObjectMetadata().getContentType();
                database.loadData(key, timestamp, objectPath, fileType);
                context.getLogger().log(RECEIVED_OBJECT + key);

            } else if (eventName.equals(ObjectRemovedDelete.toString().substring(3))) {
                database.removeData(key);
                context.getLogger().log(REMOVED_OBJECT + key);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "";
    }
}