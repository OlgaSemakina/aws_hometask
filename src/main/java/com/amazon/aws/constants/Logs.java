package com.amazon.aws.constants;

public enum Logs {
    EVENT("Received event: "), FILENAME("Received fileName: "), BUCKET("Received bucketName: "),
    RECEIVED_OBJECT("Received object: "), REMOVED_OBJECT("Removed object: ");

    private String text;

    Logs(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return this.text;
    }
}
