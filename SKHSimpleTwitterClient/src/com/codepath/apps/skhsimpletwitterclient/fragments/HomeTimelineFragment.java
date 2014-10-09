package com.codepath.apps.skhsimpletwitterclient.fragments;

import android.os.Bundle;

public class HomeTimelineFragment extends TweetsListFragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void populateTweetsList(final long maxId) {
		client.getHomeTimeline(maxId, getDefaultResponseHandler(maxId));
	}
}
