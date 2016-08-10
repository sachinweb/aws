package com.sachin.example.aws.s3;

public class TestS3 {

	public static void main(String[] args) {

		S3Gateway s3Gateway = new S3Gateway();
//		AmazonS3 s3client =  gateway.getAmazonS3();

		// create bucket - name must be unique for all S3 users
//		String bucketName = "aws-sachin-test";
		// s3client.createBucket(bucketName);
		
		s3Gateway.listS3BucketName();



		// create folder into bucket
//		String folderName = "testfolder";
//		createFolder(bucketName, folderName, s3client);

		// // upload file to folder and set it to public
		// String fileName = folderName + SUFFIX + "testvideo.mp4";
		// s3client.putObject(
		// new PutObjectRequest(bucketName, fileName, new
		// File("C:\\Users\\user\\Desktop\\testvideo.mp4"))
		// .withCannedAcl(CannedAccessControlList.PublicRead));
		//
		// deleteFolder(bucketName, folderName, s3client);
		//
		// // deletes bucket
		// s3client.deleteBucket(bucketName);
	}

}
