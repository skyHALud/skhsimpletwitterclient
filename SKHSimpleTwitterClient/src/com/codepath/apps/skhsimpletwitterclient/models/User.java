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

	private String tagline;

	private int followersCount;

	private int friendsCount;
	
	
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
			u.tagline = jsonObject.getString("description");
			u.followersCount = jsonObject.getInt("followers_count");
			u.friendsCount = jsonObject.getInt("friends_count");
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


	public String getTagline() {
		return tagline;
	}


	public void setTagline(String tagline) {
		this.tagline = tagline;
	}


	public int getFollowersCount() {
		return followersCount;
	}


	public void setFollowersCount(int followersCount) {
		this.followersCount = followersCount;
	}


	public int getFriendsCount() {
		return friendsCount;
	}


	public void setFriendsCount(int friendsCount) {
		this.friendsCount = friendsCount;
	}
}
