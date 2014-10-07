package com.codepath.apps.skhsimpletwitterclient;

import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.skhsimpletwitterclient.fragments.UserTimelineFragment;
import com.codepath.apps.skhsimpletwitterclient.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ProfileActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		
		User u = (User) getIntent().getSerializableExtra("user");
		
		if(u != null) {
			populateUI(u);
		} else {
			loadProfileInfo();
		}
	}

	public void loadProfileInfo() {
		TwitterApplication.getRestClient().getMyInfo(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject json) {
				User u = User.fromJson(json);
				
				getActionBar().setTitle("@" + u.getScreenName());
				
				populateUI(u);
			}
		});
	}

	protected void populateUI(User u) {
		TextView tvName = (TextView) findViewById(R.id.tvName);
		TextView tvTagline = (TextView) findViewById(R.id.tvTagline);
		TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
		TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);
		ImageView ivMyProfileImage = (ImageView) findViewById(R.id.ivMyProfileImage);
		
		tvName.setText(u.getName());
		tvTagline.setText(u.getTagline());
		tvFollowers.setText(u.getFollowersCount() + " Followers");
		tvFollowing.setText(u.getFriendsCount() + " Following");

		// load profile image
		ImageLoader.getInstance().displayImage(u.getProfileImageUrl(), ivMyProfileImage);
		
		// Instantiate user timeline fragment with the current user ID
		// Create a transaction
    	FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
    	// Hide, show, add, remove fragments
    	
    	ft.replace(R.id.flContainer, new UserTimelineFragment(u));
    	
    	// Execute the transaction
    	ft.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
