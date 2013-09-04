package com.example.missionintokushima;

import org.apache.http.client.methods.HttpGet;

import android.util.Log;

public class UriBuilder {
	public static final int SIGN_IN = 0;
	public static final int WHAT_IS_KEY = 1;
	public static final int USER_INFO = 2;
	public static final int UPDATE_USER_INFO = 3;
	public static final int PLEASE_CALL_ME = 4;
	public static final int MY_MISSIONS = 5;
	public static final int MISSION_INFO = 6;
	public static final int CHECKIN_BY_POSITION = 7;
	public static final int WEBVIEW_PROFILE = 8;
	
	private static final String BASE_URL = "http://mit.tatkeshi.jp/api/";
	private static final String[] URL_PARAME = {
		"signin",
		"whatiskey",
		"userinfo",
		"update_userinfo",
		"please_call_me",
		"my_missions",
		"mission_info",
		"chechin_by_position",
		"profile"
	};
	public int requestStatus;
	private String uri;
	
	public UriBuilder(int status){
		requestStatus = status;
		StringBuilder sb = new StringBuilder(BASE_URL + URL_PARAME[status]);
		uri = sb.toString();
		Log.d("", uri + "------------------------");
		 
	}

	public HttpGet createRequestUri(String account, String pass) {
		// TODO Auto-generated method stub
		uri += "?account=" + account;
		uri += "&password=" + pass;
		HttpGet request = new HttpGet(uri);
       
		return request;
	}
	
	public HttpGet createRequestUri(String userkey) {
		// TODO Auto-generated method stub
		uri += "?key=" + userkey;
		HttpGet request = new HttpGet(uri);
        
		return request;
	}
	
	public HttpGet createRequestUri(String key, String name,
			String home_lat, String home_lng,String phone,String email) {
		// TODO Auto-generated method stub
		uri += "?key=" + key;
		uri += "&name=" + name;
		uri += "&home_lat=" + home_lat;
		uri += "&home_lng=" + home_lng;
		// uri += "&phone=" + phone;
		uri += "&email=" + email;
		
		HttpGet request = new HttpGet(uri);
       
		return request;
	}
	
	public HttpGet createRequestUri(String key, int missionId) {
		// TODO Auto-generated method stub
		uri += "?key=" + key;
		uri += "&mission_id=" + missionId;
		HttpGet request = new HttpGet(uri);
        
		return request;
	}

	public String createStringUri(String userkey) {
		// TODO Auto-generated method stub
		uri += "?key=" + userkey;
		return uri;
	}

}
