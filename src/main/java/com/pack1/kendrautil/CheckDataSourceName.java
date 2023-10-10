package com.pack1.kendrautil;

import java.util.ArrayList;
import java.util.List;

import clients.AWSClients;
import software.amazon.awssdk.services.kendra.model.DataSourceSummary;
import software.amazon.awssdk.services.kendra.model.ListDataSourcesRequest;
import software.amazon.awssdk.services.kendra.model.ListDataSourcesResponse;

public class CheckDataSourceName {
	public static String dataSourceNameFromResponse = "";
	public static String dataSourceId = null;
	static List<String> listOfKendraDataSourceNames = new ArrayList<>();

	public static void listKendraDataSourceName() {
		try {
			ListDataSourcesRequest listDataSourcesRequest = ListDataSourcesRequest.builder()
					.indexId(CheckIndexName.listKendraIndexName()).build();
			ListDataSourcesResponse listDataSourcesResponse = clients.AWSClients.kendraClient()
					.listDataSources(listDataSourcesRequest);
			for (DataSourceSummary dataSourceConfiguration : listDataSourcesResponse.summaryItems()) {
				String dataSourceName = dataSourceConfiguration.name();
				listOfKendraDataSourceNames.add(dataSourceName);
			}
			if (listOfKendraDataSourceNames.contains(dataSourceNameFromResponse)) {
				DataSourceUtil.dataSourceSyncJob();
			} else {
				dataSourceId = DataSourceUtil.addKendraDatasource();
				DataSourceUtil.dataSourceSyncJob();
			}
			AWSClients.kendraClient().close();
		} catch (

		Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
