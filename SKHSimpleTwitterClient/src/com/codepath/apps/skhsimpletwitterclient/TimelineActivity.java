package com.codepath.apps.skhsimpletwitterclient;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.activeandroid.query.Delete;
import com.codepath.apps.skhsimpletwitterclient.models.Tweet;
import com.codepath.apps.skhsimpletwitterclient.models.User;
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
		    	if(page == 1) {
		    		aTweets.clear();
		    	}
		    	
		    	if(!tweets.isEmpty()) {
		    		Tweet last = tweets.get(tweets.size() - 1);
		    		populateTimeline(last.getRemoteId()); 
		    	}
		    }
	        });
		
		SwipeRefreshLayout swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
            	populateTimeline();
            	
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
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
		populateTimeline(-1);
	}
	
	public void populateTimeline(final long maxId) {
		client.getHomeTimeline(maxId, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray json) {
//				if(maxId == -1) {
//					long start = System.currentTimeMillis();
//					Log.d("debug", "Delete offline tweets");
//					// Refreshing the whole data set so delete all local data. Honor foreign key relationships.
//					new Delete().from(Tweet.class).execute();
//					new Delete().from(User.class).execute(); 
//					Log.d("debug", "DONE in " + (System.currentTimeMillis() - start) + "ms");
//				}
				
				List<Tweet> newTweets = Tweet.fromJSONArray(json);
				aTweets.addAll(newTweets);

//				long start = System.currentTimeMillis();
//				Log.d("debug", "Saving tweets offline");
//				for(Tweet t : newTweets) {
//					if(!t.getUser().exists()) {
//						t.getUser().save();
//					}
//					t.save();
//				}
//				Log.d("debug", "DONE in " + (System.currentTimeMillis() - start) + "ms");
			}
			
			@Override
			public void onFailure(Throwable arg0, String arg1) {
				Log.d("debug", arg0.toString(), arg0);
				Log.d("debug", arg1);
			}
		});
	}
}
