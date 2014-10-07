package com.codepath.apps.skhsimpletwitterclient.fragments;

import java.util.List;

import org.json.JSONArray;

import android.util.Log;

import com.codepath.apps.skhsimpletwitterclient.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class MentionsTimelineFragment extends TweetsListFragment {

	@Override
	protected void populateTweetsList(long maxId) {
		// TODO reduce code duplication
		client.getMentionsTimeline(maxId, new JsonHttpResponseHandler() {
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
				addAll(newTweets);

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
