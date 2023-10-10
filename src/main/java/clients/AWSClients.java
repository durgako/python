package clients;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;

import com.pack1.KendraConstants;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.kendra.KendraClient;

public class AWSClients {
	private static AmazonDynamoDB amazonDynamoDBClient = null;
	public static KendraClient kendraClient() {
		AwsCredentialsProvider credentialsProvider = StaticCredentialsProvider
				.create(AwsBasicCredentials.create(KendraConstants.ACCESS_KEY, KendraConstants.SECRET_KEY));
		return KendraClient.builder().region(Region.AP_SOUTH_1).credentialsProvider(credentialsProvider)
				.build();
	}

	public static AmazonDynamoDB dynamoDBClient() {
		if (amazonDynamoDBClient == null) {
			BasicAWSCredentials credentials = new BasicAWSCredentials(KendraConstants.ACCESS_KEY,
				KendraConstants.SECRET_KEY);
			amazonDynamoDBClient = AmazonDynamoDBClientBuilder.standard()
					.withCredentials(new AWSStaticCredentialsProvider(credentials))
					.withRegion(Regions.AP_SOUTH_1).build();
		}
		return amazonDynamoDBClient;
	}

}
