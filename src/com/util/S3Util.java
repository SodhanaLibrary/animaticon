package com.util;

import java.io.IOException;
import java.io.InputStream;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.DeleteObjectRequest;

public class S3Util {
	
	public static void uploadImage(InputStream filecontent, String fileName) throws IOException {
		String bucket_name = "doodlemars";
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(Setup.AWS_CRED_CLIENT_ID, Setup.AWS_CRED_CLIENT_SECRET);
        final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(awsCreds)).withRegion(Regions.US_WEST_2).build();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("image/png");
        metadata.setContentLength(filecontent.available());
        metadata.setCacheControl("public, max-age=31536000");
        PutObjectRequest por = new PutObjectRequest(bucket_name, fileName, filecontent, metadata).withCannedAcl(CannedAccessControlList.PublicRead);
        s3.putObject(por);
	}
	
	public static void deleteImage(String fileName) {
		String bucket_name = "doodlemars";
		BasicAWSCredentials awsCreds = new BasicAWSCredentials(Setup.AWS_CRED_CLIENT_ID, Setup.AWS_CRED_CLIENT_SECRET);
        final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(awsCreds)).withRegion(Regions.US_WEST_2).build();
        DeleteObjectRequest por = new DeleteObjectRequest(bucket_name, fileName);
        s3.deleteObject(por);
	}
}
