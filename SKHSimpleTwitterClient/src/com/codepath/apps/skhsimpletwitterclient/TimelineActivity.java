package com.codepath.apps.skhsimpletwitterclient;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.codepath.apps.skhsimpletwitterclient.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TimelineActivity extends Activity {
	public static final int REQUEST_CODE_NEWTWEET = 666;
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
		
		lvTweets.setOnScrollListener(new EndlessScrollListener() {
		    @Override
		    public void onLoadMore(int page, int totalItemsCount) {
		    	if(!tweets.isEmpty()) {
		    		Tweet last = tweets.get(tweets.size() - 1);
		    		populateTimeline(last.getId()); 
		    	}
		    }
	        });
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.create_tweet, menu);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == R.id.action_createtweet) {
			Intent i = new Intent(TimelineActivity.this, CreateTweetActivity.class);
        	startActivityForResult(i, REQUEST_CODE_NEWTWEET);
		}
		
		return super.onOptionsItemSelected(item);
	}

	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
      // REQUEST_CODE is defined above
      if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_NEWTWEET) {
    	 Tweet value = (Tweet) data.getSerializableExtra(CreateTweetActivity.TWEET_KEY);
         
         aTweets.insert(value, 0); // Add the new tweet to the top of the list
      }
    } 

	public void populateTimeline() {
		populateTimeline(1);
	}
	
	public void populateTimeline(long sinceId) {
		client.getHomeTimeline(sinceId, new JsonHttpResponseHandler() {
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
