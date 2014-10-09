package com.codepath.apps.skhsimpletwitterclient.fragments;

public class MentionsTimelineFragment extends TweetsListFragment {

	@Override
	protected void populateTweetsList(final long maxId) {
		client.getMentionsTimeline(maxId, getDefaultResponseHandler(maxId));
	}

}
