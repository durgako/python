package com.pack1.kendrautil;




import com.pack1.KendraConstants;

import clients.AWSClients;
import software.amazon.awssdk.services.kendra.model.CreateIndexRequest;
import software.amazon.awssdk.services.kendra.model.CreateIndexResponse;

public class KendraUtil {
	public static String kendraIndexId = null;

	public static String createKendraIndex() throws InterruptedException {
		String indexDescription = "SalesforceIndex";
		String indexName = "salesforceIndex";
		System.out.println(String.format("Creating an index named %s", indexName));
		CreateIndexRequest createIndexRequest = CreateIndexRequest.builder().description(indexDescription)
				.name(indexName).roleArn(KendraConstants.IAM_ROLE).build();
		CreateIndexResponse createIndexResponse = AWSClients.kendraClient().createIndex(createIndexRequest);
		kendraIndexId = createIndexResponse.id();
		System.out.println(String.format("Waiting until the index with index ID %s is created", kendraIndexId));
		return kendraIndexId;
	}
}
