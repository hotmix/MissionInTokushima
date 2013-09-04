package com.example.missionintokushima;

import org.json.JSONObject;

public class JsonParser {
	private String mJson;
	private boolean flag = false;
	
	public JsonParser(String json){
		mJson = json;
		getResult();
	}
	public boolean getResult(){
		
		try{
			JSONObject rootObject = new JSONObject(mJson);
			if(rootObject.getString("result").equals("success")){
				flag = true;
			
			}else {
				flag = false;
			}
		} catch (Exception e){
			return false;
		}
		
		return flag;
	}
	
	public String getAPIKey(){
		String key = "";
		try{
			JSONObject rootObject = new JSONObject(mJson);
			key = rootObject.getString("key");
			
		} catch (Exception e){
			return null;
		}
		return key;
	}

}
