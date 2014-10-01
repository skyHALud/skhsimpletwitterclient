package com.codepath.apps.skhsimpletwitterclient.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Column.ForeignKeyAction;
import com.activeandroid.annotation.Table;

import android.util.Log;

@Table(name="Tweet")
public class Tweet extends Model implements Serializable {
	private static final long serialVersionUID = -7886702499074133575L;

	@Column(name = "Body") private String body;
	
	@Column(name = "uid", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
	private long uid;
	
	@Column(name = "createdAt") private String createdAt;
	
	@Column(name = "User", onUpdate = ForeignKeyAction.CASCADE, onDelete = ForeignKeyAction.CASCADE)
	private User user;
	
	public String getBody() {
		return body;
	}

	public long getRemoteId() {
		return uid;
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
			tweet.uid = jsonObject.getLong("id");
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
		return "Tweet [body=" + body + ", id=" + uid + ", createdAt="
				+ createdAt + ", user=" + user + "]";
	}
	
	
}
