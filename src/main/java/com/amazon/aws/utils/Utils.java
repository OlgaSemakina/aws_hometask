package com.amazon.aws.utils;

import org.joda.time.DateTime;

import java.io.*;
import java.util.UUID;

import static com.amazon.aws.constants.Constants.FILE_EXTENSION;
import static com.amazon.aws.constants.Constants.FILE_TEXT;

public class Utils {

    static Object[] createFile() throws IOException {
        File file = File.createTempFile(UUID.randomUUID().toString(), FILE_EXTENSION);
        file.deleteOnExit();
        Writer writer = new OutputStreamWriter(new FileOutputStream(file));
        writer.write(FILE_TEXT);
        writer.close();
        return new Object[] {file};
    }

    public static long dateToInt(DateTime time) {
        return time.getMillis();
    }

}
