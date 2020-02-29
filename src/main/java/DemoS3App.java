import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.File;
import java.util.List;

public class DemoS3App {

    public static void main(String[] args) {

        System.out.println("Application to work with Amazon's S3");
        // Bucket name where objects will be uploaded
        String bucketName = "viveknaskar";
        try {
            // Providing the AWS_ACCESS_KEY_ID and AWS_SECRET_ACCESS_KEY (dummy values provided below)
            AWSCredentials credentials = new BasicAWSCredentials(
                    "AKIAI2YBXUMDLWP6JMXQ",
                    "pvdZEMgsIDsYtvQyrwI2IxieXEylyPCqO3Sj0s46"
            );
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withRegion(Regions.US_EAST_2)
                    .withCredentials(new AWSStaticCredentialsProvider(credentials))
                    .build();
            // List All bucket names
            List<Bucket> buckets = s3Client.listBuckets();
            for (Bucket bucket : buckets) {
                System.out.println("List of buckets available: " + bucket.getName());
            }
            // Upload a file as a new object with ContentType and title specified.
            PutObjectRequest request = new PutObjectRequest(bucketName, "aws-rootkey.txt", new File("C:\\Users\\Vivek\\Desktop\\aws-rootkey.txt"));
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("plain/text");
            metadata.addUserMetadata("x-amz-meta-title", "someTitle");
            request.setMetadata(metadata);
            s3Client.putObject(request);
        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process it, so it returned an error response.
            e.printStackTrace();
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client couldn't parse the response from Amazon S3.
            e.printStackTrace();
        }
    }
}
