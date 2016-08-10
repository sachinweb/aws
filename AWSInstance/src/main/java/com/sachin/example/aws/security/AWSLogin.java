package com.sachin.example.aws.security;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;

public class AWSLogin {

	
	public ClientConfiguration getProxyPass(){
		ClientConfiguration clientConfig = new ClientConfiguration();
		clientConfig.setProtocol(Protocol.HTTP);
		clientConfig.setProxyHost("noida-tz-ps2");
		clientConfig.setProxyPort(80);
//		 clientConfig.setProxyUsername("sachin.mehra");
//		 clientConfig.setProxyPassword("fdjkhdfjk");
		return clientConfig;
	}
	
	public AWSCredentials getAWSCredentials(String accessKey, String secretKey){
		return new BasicAWSCredentials(accessKey, secretKey);
	}

	public AWSCredentials getAWSCredentials(){
		return getAWSCredentials("AKIAI3YI2RKEL6YQZFHQ",
				"A+IYVIAbI2hc4PyM8EUAIa3JJCxlCiJcA0OWqtwk");
	}
}
