package com.pack1;



import java.io.IOException;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.util.EntityUtils;
import org.apache.http.client.ClientProtocolException;
import org.json.simple.JSONObject;

import com.amazonaws.services.clouddirectory.model.CreateIndexRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import kong.unirest.Unirest;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.kendra.KendraClient;
import software.amazon.awssdk.services.kendra.model.BatchPutDocumentRequest;
import software.amazon.awssdk.services.kendra.model.BatchPutDocumentResponse;
import software.amazon.awssdk.services.kendra.model.ContentType;
import software.amazon.awssdk.services.kendra.model.CreateDataSourceRequest;
import software.amazon.awssdk.services.kendra.model.CreateDataSourceResponse;
import software.amazon.awssdk.services.kendra.model.CreateIndexResponse;
import software.amazon.awssdk.services.kendra.model.DataSourceType;
import software.amazon.awssdk.services.kendra.model.Document;
import software.amazon.awssdk.services.kendra.model.StartDataSourceSyncJobRequest;
import software.amazon.awssdk.services.kendra.model.StartDataSourceSyncJobResponse;
import software.amazon.awssdk.services.kendra.model.StopDataSourceSyncJobRequest;
import software.amazon.awssdk.services.kendra.model.StopDataSourceSyncJobResponse;




public class GetSalesforceData {
	

	
	
    static  Object  instanceurl="";
    static  Object  accesstoken="";
    
public static Object  getSalesforceaccesstoken()
{
	HttpClient client=HttpClientBuilder.create().build();
	HttpResponse resp=null;
	String loginURL = KendraConstants.url +"grant_type="+
           KendraConstants.granttype  +
            "&client_id=" + KendraConstants.clientid +
            "&client_secret=" + KendraConstants.clientsecret +
            "&username=" + KendraConstants.userName +
            "&password=" + KendraConstants.password;

// Login requests must be POSTs
HttpPost httpPost = new HttpPost(loginURL);

try {
	 resp=client.execute(httpPost);

String getResult = null;

    
		getResult = EntityUtils.toString(resp.getEntity());
		System.out.println(getResult+"getting result");
	

ObjectMapper mapper=new ObjectMapper();
JSONObject jsonObject=null;

	jsonObject = mapper.readValue(getResult, JSONObject.class);

	
  accesstoken = jsonObject.get("access_token");
  instanceurl=jsonObject.get("instance_url");
 System.out.println("accesstoken"+accesstoken);
 System.out.println("instanceurl"+instanceurl);
}
catch (Exception e) {
	e.printStackTrace();
	
	
	
	
}
return instanceurl;


}



public static String get_Salesforce_data() throws IOException, InterruptedException {
	String uri=getSalesforceaccesstoken()+KendraConstants.SLASH+KendraConstants.geturl;
	System.out.println(uri+"uri for get");

	java.net.http.HttpClient client = java.net.http.HttpClient.newHttpClient();
	HttpRequest request = HttpRequest.newBuilder().uri(URI.create(uri)).header("Accept", "application/json")
			.header("Authorization", "Bearer " +accesstoken)
			
			.GET().build();
	java.net.http.HttpResponse<String> response = client.send(request, java.net.http.HttpResponse.BodyHandlers.ofString());
	JSONObject resp = new JSONObject();
	resp.put("", response.body());
	System.out.println("response----->"+resp);
	
	return resp.toString();
	
}
//public static void datasourceingestion() throws IOException, InterruptedException
//{
//	AwsCredentialsProvider credentialsprovider=StaticCredentialsProvider.create(AwsBasicCredentials.create(KendraConstants.ACCESS_KEY,KendraConstants.SECRET_KEY));
//	KendraClient client=KendraClient.builder().region(Region.AP_SOUTH_1).credentialsProvider(credentialsprovider)
//			.build();
//  
//	final String indexId = "51452267-4ce8-4e7f-9799-af4bdf8ce2b0";
//	
//
//        CreateDataSourceRequest createDataSourceRequest = CreateDataSourceRequest.builder()
//              .name("sample-custom-data-sources-of-java")
//              
//              .type(DataSourceType.CUSTOM)
//              .description("description of sample-custom-data-source")
//              .indexId(indexId)
//              .build();
//	
//	CreateDataSourceResponse createDataSourceResponse = client.createDataSource(createDataSourceRequest);
//
//	String datasourceId = createDataSourceResponse.id();
//    String resp=get_Salesforce_data();
//	byte[] stringbytes = resp.getBytes();
//	SdkBytes payload = SdkBytes.fromByteArray(stringbytes);
//	System.out.println(payload.getClass() + "classtype");
//
//	Document testDoc = Document.builder().title("salesforce_data_insertion").id("a_doc_id").blob(payload)
//			.contentType(ContentType.JSON).build();
//
//	BatchPutDocumentRequest batchPutDocumentRequest = BatchPutDocumentRequest.builder().indexId(indexId)
//			.documents(testDoc).build();
//
//	BatchPutDocumentResponse result = client.batchPutDocument(batchPutDocumentRequest);
//
//	System.out.println(String.format("BatchPutDocument Result: %s", result));
//	StartDataSourceSyncJobRequest startDataSourceSyncJobRequest = StartDataSourceSyncJobRequest.builder()
//			.indexId(indexId).id(datasourceId).build();
//	StartDataSourceSyncJobResponse startDataSourceSyncJobResponse = client
//			.startDataSourceSyncJob(startDataSourceSyncJobRequest);
//	String executionId = startDataSourceSyncJobResponse.executionId();
//
//	StopDataSourceSyncJobResponse stopDataSourceSyncJobResult = client.stopDataSourceSyncJob(
//			StopDataSourceSyncJobRequest.builder().indexId(indexId).id(datasourceId).build());
//
//
//	System.out.println("program endsss");
//
//}
//




public static void main(String[] args) throws IOException, InterruptedException {
	 System.out.println(get_Salesforce_data());	
	//getSalesforceaccesstoken();
	
}

}

