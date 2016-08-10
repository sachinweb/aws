package com.sachin.example.aws.ec2;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.AuthorizeSecurityGroupIngressRequest;
import com.amazonaws.services.ec2.model.CreateSecurityGroupResult;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.IpPermission;
import com.amazonaws.services.ec2.model.Reservation;

public class TestEC2 {
	
	

	public static void main(String[] args) {
		
		EC2Gateway ec2Gateway = new EC2Gateway();
		final String groupName=ec2Gateway.getTestSecurityGroup();
		final String keyName="testThisKeySachin";
		ec2Gateway.runEC2Instance(keyName, groupName, 1, 1);
		DescribeInstancesResult  diR = ec2Gateway.getAmazonEC2Client().describeInstances();
        List<Reservation> reservations = diR.getReservations();
        Set<Instance> instances = new HashSet<Instance>();

        for (Reservation reservation : reservations) {
        	System.out.println(reservation);
        }
		
	}
	
	private void kjfdghkdj(){

		// TODO Auto-generated method stub
		
		EC2Gateway ec2Gateway = new EC2Gateway();
		
//		AmazonEC2Client amazonEC2Client = ec2Gateway.getAmazonEC2Client();
		final String groupName = "SachinSecurityGroup5";
		
		CreateSecurityGroupResult createSecurityGroupResult = ec2Gateway.createAndInitializeSecurityGroup(groupName, "Some desc");
		IpPermission ipPermission = ec2Gateway.getIPPermission();
		
		ec2Gateway.createAuthorizeSecurityGroup(ipPermission, groupName);
		
		AuthorizeSecurityGroupIngressRequest authorizeSecurityGroupIngressRequest = ec2Gateway.createAuthorizeSecurityGroup(ipPermission, groupName);
		AmazonEC2Client amazonEC2Client = ec2Gateway.getAmazonEC2Client();
		amazonEC2Client.authorizeSecurityGroupIngress(authorizeSecurityGroupIngressRequest);
		
		final String keyName="testThisKeySachin";
//		String privateKey = ec2Gateway.getPrivateKey(keyName);
//		System.out.println("privateKey: " + privateKey);
		
		
		ec2Gateway.runEC2Instance(keyName, groupName, 4, 8);
		


	
	}

}
