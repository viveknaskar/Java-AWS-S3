# AWS S3 Java Demo
This project demonstrates how to interact with Amazon S3 using the AWS SDK for Java (v2). It includes functionalities for:
- Listing all available S3 buckets
- Uploading a file to a specified S3 bucket

## Prerequisites
Before running this application, ensure that:
- **Java 17** installed
- **Maven** installed
- An **AWS account** with necessary IAM permissions

#### AWS credentials set up via environment variables:
  
  ```sh
  export AWS_ACCESS_KEY_ID=your-access-key
  export AWS_SECRET_ACCESS_KEY=your-secret-key
  ```

## Configuration
Modify `application.properties` for custom configurations (if needed):
```properties
aws.region=us-east-2
aws.s3.bucket-name=your-bucket-name
```

## License
This project is licensed under the MIT License.
