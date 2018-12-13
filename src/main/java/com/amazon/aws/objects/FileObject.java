package com.amazon.aws.objects;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;

import java.io.File;

import static com.amazon.aws.base.BaseInitialization.bucketName;
import static com.amazon.aws.base.BaseInitialization.s3;

public class FileObject {

    //================================methods===================================

    public void upload(TransferManager transferManager, File file) throws InterruptedException {
        try {
            Upload uploading = transferManager.upload(bucketName, file.getName(), file);
            while(!s3.containsObject(file)) {}
            uploading.waitForCompletion();
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
        }
    }

    public void delete(S3 s3, File file) throws InterruptedException {
        s3.deleteObject(file.getName());
        while(s3.containsObject(file)) {}
    }

}
