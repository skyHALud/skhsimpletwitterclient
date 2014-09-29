package com.codepath.apps.skhsimpletwitterclient.models;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class User implements Serializable {
	private static final long serialVersionUID = 9090509290234433384L;

	private String name;
	private long uid;
	private String screenName;
	private String profileImageUrl;
	
	
	public String getName() {
		return name;
	}


	public long getUid() {
		return uid;
	}


	public String getScreenName() {
		return screenName;
	}


	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	public static User fromJson(JSONObject jsonObject) {
		User u = new User();
		
		try {
			u.name = jsonObject.getString("name");
			u.uid = jsonObject.getLong("id");
			u.screenName = jsonObject.getString("screen_name");
			u.profileImageUrl = jsonObject.getString("profile_image_url");
		} catch(JSONException ex) {
			Log.e("error", ex.getMessage(), ex);
		}
		
		return u;
	}

}
