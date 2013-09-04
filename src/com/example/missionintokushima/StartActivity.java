package com.example.missionintokushima;

import org.apache.http.client.methods.HttpGet;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class StartActivity extends Activity implements OnClickListener {
	
	EditText acountText;
	EditText passText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		
		acountText = (EditText)findViewById(R.id.etName);
		passText = (EditText)findViewById(R.id.etPass);
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(this);
		Log.d("ststst","------------------------");
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Log.d("stst2","------------------------");
		UriBuilder ub = new UriBuilder(UriBuilder.SIGN_IN);
		
		
		HttpGet uri = ub.createRequestUri(
				acountText.getText().toString()
				,passText.getText().toString());
		ServerConnectTask task = new ServerConnectTask(ub.requestStatus, this);
		task.execute(uri);	
	}

}
