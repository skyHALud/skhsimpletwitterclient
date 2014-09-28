package com.codepath.apps.skhsimpletwitterclient;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.codepath.apps.skhsimpletwitterclient.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TimelineActivity extends Activity {
	private TwitterClient client;
	private List<Tweet> tweets;
	private ArrayAdapter<Tweet> aTweets;
	private ListView lvTweets;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		
		lvTweets = (ListView) findViewById(R.id.lvTweets);
		tweets = new ArrayList<Tweet>();
		aTweets = new TweetArrayAdapter(this, tweets);
		lvTweets.setAdapter(aTweets);
		
		client = TwitterApplication.getRestClient();
		populateTimeline();
	}
	
	public void populateTimeline() {
		client.getHomeTimeline(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray json) {
				aTweets.addAll(Tweet.fromJSONArray(json));
			}
			
			@Override
			public void onFailure(Throwable arg0, String arg1) {
				Log.d("debug", arg0.toString(), arg0);
				Log.d("debug", arg1);
			}
		});
	}
}
