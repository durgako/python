package com.pack1.kendrautil;

import java.util.ArrayList;
import java.util.List;

import clients.AWSClients;
import software.amazon.awssdk.services.kendra.model.IndexConfigurationSummary;
import software.amazon.awssdk.services.kendra.model.ListIndicesRequest;
import software.amazon.awssdk.services.kendra.model.ListIndicesResponse;

public class CheckIndexName {
	static String indexNameFromResponse = "";
	static String indexId = "";
	static List<String> listOfKendraIndexNames = new ArrayList<>();

	public static String listKendraIndexName() {
		try {
			ListIndicesRequest listIndicesRequest = ListIndicesRequest.builder().build();
			ListIndicesResponse listIndicesResponse = AWSClients.kendraClient().listIndices(listIndicesRequest);
			for (IndexConfigurationSummary index : listIndicesResponse.indexConfigurationSummaryItems()) {
				String indexName = index.name();
				listOfKendraIndexNames.add(indexName);
				if (indexNameFromResponse.equalsIgnoreCase(indexName)) {
					indexId = index.id();
					return indexId;
				}
			}
			if (listOfKendraIndexNames.contains(indexNameFromResponse)) {
				CheckDataSourceName.listKendraDataSourceName();
			} else {
				indexId = KendraUtil.createKendraIndex();
				CheckDataSourceName.listKendraDataSourceName();
			}
			AWSClients.kendraClient().close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return indexId;
	}
}
