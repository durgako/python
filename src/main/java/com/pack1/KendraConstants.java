package com.pack1;

import java.util.Map;

import software.amazon.awssdk.regions.Region;


public class KendraConstants {

	

	public static Map<String, String> salesforcedata= YamlReading.Data();
	public static final String ACCESS_KEY =salesforcedata.get("Access_key");
	public static final Region AWS_Region =Region.AP_SOUTH_1 ;

    public static final String SECRET_KEY = salesforcedata.get("Secret_Key");

	public static final String url=salesforcedata.get("URL");
    public	static  final String  userName=salesforcedata.get("Username");
    public	static  final String password=salesforcedata.get("Password");
     public	static  final String granttype=salesforcedata.get("Grant_Type");
    public	static final  String clientid=salesforcedata.get("Client_id");
    public	static final String clientsecret=salesforcedata.get("Client_Secret");
    public static final String geturl=salesforcedata.get("Geturl");
    public static final String IAM_ROLE=salesforcedata.get("IAmrole");
    
    public static String SLASH="/";

	
   // https://partnersand-047.cfn.mykronos.com/api/authentication/access_token?grant_type=password&client_id=3SxPWG8OBGFsc5s0zzHqstd1Yo8f1qvR&client_secret=JlbEQy12wGyvfEwr&username=SeanIvan&password=S4lt&auth_chain=OAuthLdapService&appkey=Xm285iTy1ynAeQYnqvg6GANceaIMvIkn
}
