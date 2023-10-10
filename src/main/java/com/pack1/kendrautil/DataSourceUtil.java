package com.pack1.kendrautil;



import com.pack1.GetSalesforceData;


import clients.AWSClients;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.kendra.model.BatchPutDocumentRequest;
import software.amazon.awssdk.services.kendra.model.BatchPutDocumentResponse;
import software.amazon.awssdk.services.kendra.model.CreateDataSourceRequest;
import software.amazon.awssdk.services.kendra.model.CreateDataSourceResponse;
import software.amazon.awssdk.services.kendra.model.DataSourceType;
import software.amazon.awssdk.services.kendra.model.Document;
import software.amazon.awssdk.services.kendra.model.StartDataSourceSyncJobRequest;
import software.amazon.awssdk.services.kendra.model.StartDataSourceSyncJobResponse;
import software.amazon.awssdk.services.kendra.model.StopDataSourceSyncJobRequest;
import software.amazon.awssdk.services.kendra.model.StopDataSourceSyncJobResponse;

public class DataSourceUtil {

	static String dataSourceId = null;

	public static String addKendraDatasource() throws Exception {
		CreateDataSourceRequest createDataSourceRequest = CreateDataSourceRequest.builder().name("Salesforce-APIs-data")
				.type(DataSourceType.CUSTOM).description("description of sample-custom-data-source1")
				.indexId(CheckIndexName.listKendraIndexName()).build();
		CreateDataSourceResponse createDataSourceResponse = AWSClients.kendraClient()
				.createDataSource(createDataSourceRequest);
		dataSourceId = createDataSourceResponse.id();
		byte[] stringbytes = GetSalesforceData.get_Salesforce_data().getBytes();
		SdkBytes payload = SdkBytes.fromByteArray(stringbytes);
		Document testDoc = Document.builder().title("data_insertion").id("a_doc_id").blob(payload).contentType("JSON")
				.build();
		BatchPutDocumentRequest batchPutDocumentRequest = BatchPutDocumentRequest.builder()
				.indexId(CheckIndexName.listKendraIndexName()).documents(testDoc).build();
		BatchPutDocumentResponse result = AWSClients.kendraClient().batchPutDocument(batchPutDocumentRequest);
		System.out.println(String.format("BatchPutDocument Result: %s", result));
		return dataSourceId;
	}

	public static void dataSourceSyncJob() {
		StartDataSourceSyncJobRequest startDataSourceSyncJobRequest = StartDataSourceSyncJobRequest.builder()
				.indexId(CheckIndexName.listKendraIndexName()).id(dataSourceId).build();
		StartDataSourceSyncJobResponse startDataSourceSyncJobResponse = AWSClients.kendraClient()
				.startDataSourceSyncJob(startDataSourceSyncJobRequest);
		String executionId = startDataSourceSyncJobResponse.executionId();
		StopDataSourceSyncJobResponse stopDataSourceSyncJobResult = AWSClients.kendraClient()
				.stopDataSourceSyncJob(StopDataSourceSyncJobRequest.builder()
						.indexId(CheckIndexName.listKendraIndexName()).id(dataSourceId).build());
	}
}
