package com.codepath.apps.skhsimpletwitterclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.codepath.apps.skhsimpletwitterclient.fragments.TweetsListFragment;
import com.codepath.apps.skhsimpletwitterclient.models.Tweet;

public class TimelineActivity extends FragmentActivity {
	public static final int REQUEST_CODE_NEWTWEET = 666;
	private TweetsListFragment fragmentTweetsList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		
		fragmentTweetsList = (TweetsListFragment) getSupportFragmentManager().findFragmentById(R.layout.fragment_tweets_list);
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
         
    	 fragmentTweetsList.insertFirst(value); // Add the new tweet to the top of the list
      }
    } 

	
}
