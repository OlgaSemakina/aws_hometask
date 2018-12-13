import com.amazon.aws.base.BaseInitialization;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;

import static com.amazon.aws.constants.Constants.pathToCreateFileMethod;

public class LambdaSuite extends BaseInitialization {

    @ParameterizedTest
    @MethodSource(pathToCreateFileMethod)
    void fileUpload(File file) throws InterruptedException {

        // Upload file to a bucket
        fileObject.upload(transferManager, file);

        // Check that lambda is triggered and there is a database row for uploaded file
        lambda.checkLambdaTriggeredAdd(database, file);
    }

    @ParameterizedTest
    @MethodSource(pathToCreateFileMethod)
    void fileUploadAndDelete(File file) throws InterruptedException {

        // Upload file to a bucket
        fileObject.upload(transferManager, file);

        // Delete file from bucket
        fileObject.delete(s3, file);

        // Check that lambda is triggered and there is no database row for deleted file
        lambda.checkLambdaTriggeredDelete(database, file);
    }

    @AfterEach
    private void tearDown() {
        s3.cleanBucket();
    }

    @AfterAll
    public static void closeAll() {
        transferManager.shutdownNow();
    }
}
