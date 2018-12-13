package com.amazon.aws.constants;

import com.amazonaws.regions.Regions;


public class Constants {
    public static final String pathToCreateFileMethod = "com.amazon.aws.utils.Utils#createFile";

    public static String INDEX = "Index";
    public static String FILE_TEXT = "abcdefghijklmnopqrstuvwxyz\n01234567890112345678901234\n";
    public static String FILE_EXTENSION = ".txt";
    public static String LOCATION = "US";

    // Properties for lambda
    public static Regions region = Regions.US_EAST_1;
    public static String tableName = "files";
    public static String hashKey = "packageId";
    public static String sortKey = "originTimeStamp";
    public static String secondaryIndex1 = "filePath";
    public static String secondaryIndex2 = "fileType";
}
