package com.codepath.apps.skhsimpletwitterclient.fragments;

import com.codepath.apps.skhsimpletwitterclient.models.User;

public class UserTimelineFragment extends TweetsListFragment {

	private User user;

	public UserTimelineFragment() {
		super();
	}
	
	public UserTimelineFragment(User u) {
		this.user = u;
	}
	
	@Override
	protected void populateTweetsList(long maxId) {
		client.getUserTimeline(user != null ? user.getUid() : null, maxId, getDefaultResponseHandler(maxId));
	}

}
