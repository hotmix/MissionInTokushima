package com.example.missionintokushima;

import org.apache.http.client.methods.HttpGet;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebView;

public class WebViewActivity extends Activity {
	private static final int APP_STATUS_0 = 0;
	private static final int APP_STATUS_UPDATE = 1;
	private static final int APP_STATUS_MISSION = 2;
	private static final int APP_STATUS_FREE = 3;
	
	private String mKey = "";

	private String webUrl = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		
		setContentView(R.layout.web_view_activity);
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		mKey = sp.getString("APIKey", "error");
		if(mKey.equals("error")){
			Intent it = new Intent(this, StartActivity.class);
			startActivity(it);
		}
		
		WebView wv = (WebView)findViewById(R.id.webView1);
		UriBuilder ub = new UriBuilder(UriBuilder.WEBVIEW_PROFILE);
		webUrl = ub.createStringUri(mKey);
		Log.d("URL------", webUrl);
		wv.loadUrl(webUrl);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.start, menu);
		
		return super.onCreateOptionsMenu(menu);
		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		
		switch(item.getItemId()){
		case R.id.action_settings:
			Intent it = new Intent(this, SettingActivity.class);
			startActivity(it);
			break;
		case R.id.action_request_mission:
			UriBuilder ub = new UriBuilder(UriBuilder.PLEASE_CALL_ME);
			HttpGet uri = ub.createRequestUri(mKey);
			ServerConnectTask task = new ServerConnectTask(UriBuilder.PLEASE_CALL_ME, getApplicationContext());
			task.execute(uri);
			
		}
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		
		return super.onMenuItemSelected(featureId, item);
	}
	
	
	
	

}
