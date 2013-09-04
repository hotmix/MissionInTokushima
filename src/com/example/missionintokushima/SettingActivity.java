package com.example.missionintokushima;

import java.util.List;

import org.apache.http.client.methods.HttpGet;

import android.app.Activity;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class SettingActivity extends Activity 
		implements OnClickListener{
	
	LocationManager lm;
	List<String> providers;
	
	TextView tv;
	
	String mLat;
	String mLon;
	
	EditText etName;
	EditText etPhone;
	EditText etEMail;
//	EditText etLat;
//	EditText etLon;
	
	
    private static final int LOCATION_TIME_OUT = 10000;
	
	private CustomLocationManager mCustomLocationManager;

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.button1:
			mCustomLocationManager.getNowLocationData(LOCATION_TIME_OUT, new CustomLocationManager.LocationCallback() {

	            @Override
	            public void onTimeout() {
	                Toast.makeText(getApplicationContext(), R.string.toast_timeout, Toast.LENGTH_SHORT).show();
	            }

	            @Override
	            public void onComplete(Location location) {
	            	mLat = Double.toString(location.getLatitude());
	            	mLon = Double.toString(location.getLongitude());
	            	if(mLat.isEmpty() || mLon.isEmpty()){
	            		tv.setText("‚à‚¤ˆê“xŽæ“¾‚µ‚È‚¨‚µ‚Ä‚­‚¾‚³‚¢");
	            	}
	    
	            }
	        });
			Log.d("location", "-----Žæ“¾Š®—¹" + mLon);

			break;
			
		case R.id.button2:
			Log.d("stst2","------------------------");
			UriBuilder ub = new UriBuilder(UriBuilder.UPDATE_USER_INFO);
			
			SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
			String str = sp.getString("APIKey", "error");
			HttpGet uri = ub.createRequestUri(
					str
					,etName.getText().toString()
					,mLat
					,mLon
					,etPhone.getText().toString()
					,etEMail.getText().toString());
					
			ServerConnectTask task = new ServerConnectTask(ub.requestStatus, this);
			task.execute(uri);

		default:
			break;
		}
		
		
		
		
		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_activity);
		
		mCustomLocationManager = new CustomLocationManager(this);
		if(mCustomLocationManager.checkGpsMode()){
			mCustomLocationManager.startLocation(LocationManager.GPS_PROVIDER);
			
		}
		
		tv = (TextView)findViewById(R.id.textView1);
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(this);
		Button btn2 = (Button)findViewById(R.id.button2);
		btn2.setOnClickListener(this);
		
		
		etName = (EditText)findViewById(R.id.etName);
		etPhone = (EditText)findViewById(R.id.etPhone);
		etEMail = (EditText)findViewById(R.id.etPass);
		
	}
}

	