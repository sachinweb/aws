package com.sachin.example.aws.ec2;

import java.util.List;
import java.util.logging.Logger;

import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.AuthorizeSecurityGroupIngressRequest;
import com.amazonaws.services.ec2.model.CreateKeyPairRequest;
import com.amazonaws.services.ec2.model.CreateKeyPairResult;
import com.amazonaws.services.ec2.model.CreateSecurityGroupRequest;
import com.amazonaws.services.ec2.model.CreateSecurityGroupResult;
import com.amazonaws.services.ec2.model.IpPermission;
import com.amazonaws.services.ec2.model.KeyPair;
import com.amazonaws.services.ec2.model.RunInstancesRequest;
import com.amazonaws.services.ec2.model.RunInstancesResult;
import com.amazonaws.services.ec2.model.SecurityGroup;
import com.sachin.example.aws.security.AWSLogin;

public class EC2Gateway {

	public EC2Gateway() {
		init();
	}

	private static final Logger LOGGER = Logger.getLogger(EC2Gateway.class.getName());
	private static AmazonEC2Client amazonEC2Client = null;
	
	/*	US East (N. Virginia)	us-east-1	ec2.us-east-1.amazonaws.com	HTTPS
	US West (N. California)	us-west-1	ec2.us-west-1.amazonaws.com	HTTPS
	US West (Oregon)	us-west-2	ec2.us-west-2.amazonaws.com	HTTPS
	Asia Pacific (Mumbai)	ap-south-1	ec2.ap-south-1.amazonaws.com	HTTPS
	Asia Pacific (Seoul)	ap-northeast-2	ec2.ap-northeast-2.amazonaws.com	HTTPS
	Asia Pacific (Singapore)	ap-southeast-1	ec2.ap-southeast-1.amazonaws.com	HTTPS
	Asia Pacific (Sydney)	ap-southeast-2	ec2.ap-southeast-2.amazonaws.com	HTTPS
	Asia Pacific (Tokyo)	ap-northeast-1	ec2.ap-northeast-1.amazonaws.com	HTTPS*/

	
	
	private static void init(){
		AWSLogin login = new AWSLogin();
		amazonEC2Client = new AmazonEC2Client(login.getAWSCredentials(), login.getProxyPass());
		amazonEC2Client.setEndpoint("ec2.us-west-2.amazonaws.com");
//		return amazonEC2Client;
	}
	
	public void checkAvailabilityZones(){
		
	}
	
	public CreateSecurityGroupResult  createAndInitializeSecurityGroup(String name, String description){
		CreateSecurityGroupRequest csgr = new CreateSecurityGroupRequest();
		csgr.withGroupName(name).withDescription(description);
		
		CreateSecurityGroupResult createSecurityGroupResult =
				amazonEC2Client.createSecurityGroup(csgr);
		
		return createSecurityGroupResult;
	}

	public void  getSecurityGroup(){
		List<SecurityGroup> securityGroupList = amazonEC2Client.describeSecurityGroups().getSecurityGroups();
		for(SecurityGroup securityGroup : securityGroupList){
			LOGGER.info(securityGroup.getGroupName());
		}
	}

	public String  getTestSecurityGroup(){
		List<SecurityGroup> securityGroupList = amazonEC2Client.describeSecurityGroups().getSecurityGroups();
		for(SecurityGroup securityGroup : securityGroupList){
			LOGGER.info(securityGroup.getGroupName());
			if(securityGroup.getGroupName().indexOf("SachinSecurityGroup") != -1){
				List <IpPermission> list = securityGroup.getIpPermissions();
				for(IpPermission ipPermission : list){
					LOGGER.info(ipPermission.toString());
				}

				return securityGroup.getGroupName();
			}
		}
		return "SachinSecurityGroup5"; //default
	}

	public AuthorizeSecurityGroupIngressRequest createAuthorizeSecurityGroup(IpPermission ipPermission, String groupName){
		AuthorizeSecurityGroupIngressRequest authorizeSecurityGroupIngressRequest =
			    new AuthorizeSecurityGroupIngressRequest();

			authorizeSecurityGroupIngressRequest.withGroupName(groupName)
			                                    .withIpPermissions(ipPermission);
			
			return authorizeSecurityGroupIngressRequest;
		
	}
	
	public IpPermission getIPPermission(){
		IpPermission ipPermission =
			    new IpPermission();

			ipPermission.withIpRanges("111.111.111.111/32", "150.150.150.150/32")
			            .withIpProtocol("tcp")
			            .withFromPort(22)
			            .withToPort(22);
			
			return ipPermission;
	}
	
	public CreateKeyPairResult  getCreateKeyPair(String keyName){
		CreateKeyPairRequest createKeyPairRequest = new CreateKeyPairRequest();
		createKeyPairRequest.withKeyName(keyName);
		
		CreateKeyPairResult createKeyPairResult =
				amazonEC2Client.createKeyPair(createKeyPairRequest);
		
		return createKeyPairResult;
	}
	
	public String getPrivateKey(String keyName){
		KeyPair keyPair = new KeyPair();

		keyPair = getCreateKeyPair(keyName).getKeyPair();
		String privateKey = keyPair.getKeyMaterial();
		
		return privateKey;
	}
	
	public void runEC2Instance(String keyName, String groupName, int min, int max){
		RunInstancesRequest runInstancesRequest =
			      new RunInstancesRequest();

//		  runInstancesRequest.withImageId("ami-7172b611")
		  runInstancesRequest.withImageId("ami-775e4f16")
			                     .withInstanceType("t2.micro")
			                     .withMinCount(min)
			                     .withMaxCount(max)
			                     .withKeyName(keyName)
			                     .withSecurityGroups(groupName);

			  RunInstancesResult runInstancesResult =
					  amazonEC2Client.runInstances(runInstancesRequest);
			  
			  
			  
	}

	public static AmazonEC2Client getAmazonEC2Client() {
		return amazonEC2Client;
	}
	
}
