package com.example.missionintokushima;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

public class ServerConnectTask extends AsyncTask<HttpUriRequest, Void, String> {
	Context parentActivity;
	int requestStatus;
	
	private static final String API_SUCCESS = "success";
	private static final String API_FAILED = "failed";
	
	public ServerConnectTask (int status, Context context){
		requestStatus = status;
		parentActivity = context;
	}

	@Override
	protected String doInBackground(HttpUriRequest... request) {
		// TODO Auto-generated method stub
		 AndroidHttpClient httpClient = AndroidHttpClient.newInstance("AndroidHttpClient");
         HttpResponse response = null;
	    try {
             response = httpClient.execute(request[0]);
         } catch (IOException e) {
             e.printStackTrace();
         }
	    String message = "";
        try {
            InputStream content = response.getEntity().getContent();
            message = IOUtils.toString(content); 
            Log.d("message", message);
        }catch(IOException e){
        	
        }
        httpClient.close();
        JsonParser parser = new JsonParser(message);
        switch (requestStatus) {
		case UriBuilder.SIGN_IN:
			message = parser.getAPIKey();
			break;
			
		case UriBuilder.UPDATE_USER_INFO:
		case UriBuilder.PLEASE_CALL_ME:
			if(parser.getResult()){
				message = API_SUCCESS;
				
			}else {
				message = API_FAILED;
			}
			break;
		}
        return message;
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		switch (requestStatus) {
		case UriBuilder.SIGN_IN:
			SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(parentActivity.getApplicationContext());
			SharedPreferences.Editor editor = sp.edit();
			editor.putString("APIKey", result);
			editor.commit();
			Intent it = new Intent(parentActivity, SettingActivity.class);
			parentActivity.startActivity(it);
			break;
			
		case UriBuilder.UPDATE_USER_INFO:
			if(result.equals(API_SUCCESS)){
				Intent it2 = new Intent(parentActivity, WebViewActivity.class);
				parentActivity.startActivity(it2);
			} else {
				Toast toast = Toast.makeText(parentActivity, "“o˜^‚ÉŽ¸”s‚µ‚Ü‚µ‚½", Toast.LENGTH_SHORT);
				toast.show();
			}
			break;
			
		case UriBuilder.PLEASE_CALL_ME:
			if(result.equals(API_SUCCESS)){
				Toast toast = Toast.makeText(parentActivity, "mission‚ðƒŠƒNƒGƒXƒg", Toast.LENGTH_SHORT);
				toast.show();
			}

		default:
			break;
		}
		
        
	}
	
	
}
