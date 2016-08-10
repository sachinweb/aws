package com.sachin.example.aws.s3;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.sachin.example.aws.security.AWSLogin;

public class S3Gateway {

	// public static void main(String[] args) {
	// // TODO Auto-generated method stub
	//
	// }

	private static final String SUFFIX = "/";
	
	private AmazonS3 getAmazonS3(){
		AWSLogin login = new AWSLogin();
		// create a client connection based on credentials
		return new AmazonS3Client(login.getAWSCredentials(), login.getProxyPass());
	}

	
	
	public void listS3BucketName(){
		AmazonS3 s3client =  getAmazonS3();
		
		// list buckets
		for (Bucket bucket : s3client.listBuckets()) {
			System.out.println(" - " + bucket.getName());
		}
	}

	public static void createFolder(String bucketName, String folderName, AmazonS3 client) {
		// create meta-data for your folder and set content-length to 0
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(0);

		// create empty content
		InputStream emptyContent = new ByteArrayInputStream(new byte[0]);

		// create a PutObjectRequest passing the folder name suffixed by /
		PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, folderName + SUFFIX, emptyContent,
				metadata);

		// send request to S3 to create folder
		client.putObject(putObjectRequest);
	}

	/**
	 * This method first deletes all the files in given folder and than the
	 * folder itself
	 */
	public static void deleteFolder(String bucketName, String folderName, AmazonS3 client) {
		List<S3ObjectSummary> fileList = client.listObjects(bucketName, folderName).getObjectSummaries();
		for (S3ObjectSummary file : fileList) {
			client.deleteObject(bucketName, file.getKey());
		}
		client.deleteObject(bucketName, folderName);
	}
}
