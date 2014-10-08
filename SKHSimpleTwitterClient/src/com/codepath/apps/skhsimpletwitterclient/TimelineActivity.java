package com.codepath.apps.skhsimpletwitterclient;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;

import com.codepath.apps.skhsimpletwitterclient.fragments.HomeTimelineFragment;
import com.codepath.apps.skhsimpletwitterclient.fragments.MentionsTimelineFragment;
import com.codepath.apps.skhsimpletwitterclient.fragments.TweetsListFragment;
import com.codepath.apps.skhsimpletwitterclient.listeners.FragmentTabListener;
import com.codepath.apps.skhsimpletwitterclient.models.Tweet;

public class TimelineActivity extends FragmentActivity {
	public static final int REQUEST_CODE_NEWTWEET = 666;
	private TweetsListFragment fragmentTweetsList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// MUST request the feature before setting content view
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS); 
		
		setContentView(R.layout.activity_timeline);
		
		fragmentTweetsList = (TweetsListFragment) getSupportFragmentManager().findFragmentById(R.layout.fragment_tweets_list);
		
		setupTabs();
	}
	
	// Should be called manually when an async task has started
    public void showProgressBar() {
        setProgressBarIndeterminateVisibility(true); 
    }
    
    // Should be called when an async task has finished
    public void hideProgressBar() {
    	setProgressBarIndeterminateVisibility(false); 
    }
	
	private void setupTabs() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);

		Tab tab1 = actionBar
			.newTab()
			.setText("Home")
			.setIcon(R.drawable.ic_action_home)
			.setTag(HomeTimelineFragment.class.getSimpleName())
			.setTabListener(
				new FragmentTabListener<HomeTimelineFragment>(R.id.flContainer, this, "home",
								HomeTimelineFragment.class));

		actionBar.addTab(tab1);
		actionBar.selectTab(tab1);

		Tab tab2 = actionBar
			.newTab()
			.setText("Mentions")
			.setIcon(R.drawable.ic_action_mention)
			.setTag(MentionsTimelineFragment.class.getSimpleName())
			.setTabListener(
			    new FragmentTabListener<MentionsTimelineFragment>(R.id.flContainer, this, "mentions",
								MentionsTimelineFragment.class));

		actionBar.addTab(tab2);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.tweets, menu);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.action_createtweet:
			Intent i = new Intent(TimelineActivity.this, CreateTweetActivity.class);
        	startActivityForResult(i, REQUEST_CODE_NEWTWEET);
		break;
		
		case R.id.action_profile:
			i = new Intent(this, ProfileActivity.class);
			startActivity(i);
		break;
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
