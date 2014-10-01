package com.codepath.apps.skhsimpletwitterclient.models;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.ClipData.Item;
import android.util.Log;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

@Table(name = "User")
public class User extends Model implements Serializable {
	private static final long serialVersionUID = 9090509290234433384L;

	@Column(name = "Name") private String name;
	
	@Column(name = "uid", unique = true) 
	private long uid;
	
	@Column(name = "ScreenName") private String screenName;
	@Column(name = "ProfileImageUrl") private String profileImageUrl;
	
	
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

	public boolean exists() {
	    return new Select()
	        .from(User.class)
	        .where("uid = ?", uid)
		        .execute().isEmpty() == false;
	}
}
