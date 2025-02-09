package org.s3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;

public class S3DemoApp {

    private static final Logger logger = LoggerFactory.getLogger(S3DemoApp.class);
    private static final String BUCKET_NAME = "some-bucket";
    private static final Region REGION = Region.US_EAST_2;

    public static void main(String[] args) {
        logger.info("Starting S3 Application...");

        try (S3Client s3Client = initializeS3Client()) {
            if (s3Client == null) {
                logger.error("Failed to initialize S3 client. Exiting...");
                return;
            }

            listBuckets(s3Client);
            uploadFile(s3Client, BUCKET_NAME, "aws-rootkey.txt", "C:\\Users\\Vivek\\Desktop\\aws-rootkey.txt");
        }
    }

    /**
     * Initializes the Amazon S3 client with credentials from environment variables.
     */
    private static S3Client initializeS3Client() {
        try {
            return S3Client.builder()
                    .region(REGION)
                    .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
                    .build();
        } catch (Exception e) {
            logger.error("Error initializing S3 client: {}", e.getMessage(), e);
            return null;
        }
    }

    /**
     * Lists all available S3 buckets.
     */
    static void listBuckets(S3Client s3Client) {
        try {
            ListBucketsResponse response = s3Client.listBuckets();
            List<Bucket> buckets = response.buckets();
            logger.info("Available Buckets:");
            for (Bucket bucket : buckets) {
                logger.info(" - {}", bucket.name());
            }
        } catch (SdkClientException e) {
            logger.error("Error listing buckets: {}", e.getMessage(), e);
        }
    }

    /**
     * Uploads a file to the specified S3 bucket.
     */
    private static void uploadFile(S3Client s3Client, String bucketName, String key, String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            logger.error("File not found: {}", filePath);
            return;
        }

        try {
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();

            s3Client.putObject(request, Paths.get(filePath));
            logger.info("Successfully uploaded '{}' to bucket '{}'", key, bucketName);
        } catch (SdkClientException e) {
            logger.error("SDK error while uploading file: {}", e.getMessage(), e);
        }
    }
}
