import com.amazon.aws.base.BaseInitialization;
import org.junit.jupiter.api.Test;


public class ParametersSuite extends BaseInitialization {

    @Test
    void checkParameters() {

        // Check that there is a bucket in S3
        s3.checkBucketExists();

        // Check that there is only one bucket
        s3.checkAmountOfBuckets(1);

        // Check that bucket's location is correct
        s3.checkBucketLocation();

        // Check db table exists
        database.checkTableExists();
    }
}
