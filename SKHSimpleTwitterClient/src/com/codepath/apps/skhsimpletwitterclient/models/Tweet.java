package com.codepath.apps.skhsimpletwitterclient.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Tweet implements Serializable {
	private static final long serialVersionUID = -7886702499074133575L;

	private String body;
	private long id;
	private String createdAt;
	private User user;
	
	public String getBody() {
		return body;
	}

	public long getId() {
		return id;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public User getUser() {
		return user;
	}

	public static Tweet fromJSON(JSONObject jsonObject) {
		Tweet tweet = new Tweet();
		try {
			tweet.body = jsonObject.getString("text");
			tweet.id = jsonObject.getLong("id");
			tweet.createdAt = jsonObject.getString("created_at");
			tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
		} catch(JSONException ex) {
			Log.e("error", ex.getMessage(), ex);
		}
		
		return tweet;
	}

	public static List<Tweet> fromJSONArray(JSONArray jsonArray) {
		List<Tweet> tweets = new ArrayList<Tweet>(jsonArray.length());
	      // Process each result in json array, decode and convert to Tweet object
	      for (int i=0; i < jsonArray.length(); i++) {
	          JSONObject tweetJson = null;
	          try {
	          	tweetJson = jsonArray.getJSONObject(i);
	          } catch (Exception e) {
	              Log.e("error", e.getMessage(), e);
	              continue;
	          }

	          Tweet tweet = Tweet.fromJSON(tweetJson);
	          if (tweet != null) {
	          	tweets.add(tweet);
	          }
	      }

	      return tweets;
	}

	@Override
	public String toString() {
		return "Tweet [body=" + body + ", id=" + id + ", createdAt="
				+ createdAt + ", user=" + user + "]";
	}
	
	
}
