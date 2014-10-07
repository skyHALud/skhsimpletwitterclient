package com.codepath.apps.skhsimpletwitterclient.fragments;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.codepath.apps.skhsimpletwitterclient.EndlessScrollListener;
import com.codepath.apps.skhsimpletwitterclient.R;
import com.codepath.apps.skhsimpletwitterclient.TweetArrayAdapter;
import com.codepath.apps.skhsimpletwitterclient.TwitterApplication;
import com.codepath.apps.skhsimpletwitterclient.TwitterClient;
import com.codepath.apps.skhsimpletwitterclient.models.Tweet;

public abstract class TweetsListFragment extends Fragment {
	private List<Tweet> tweets;
	private ArrayAdapter<Tweet> aTweets;
	private ListView lvTweets;
	protected TwitterClient client;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Non-view initialization
		tweets = new ArrayList<Tweet>();
		aTweets = new TweetArrayAdapter(getActivity(), tweets);
		
		client = TwitterApplication.getRestClient();
		populateTweetsList();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_tweets_list, container, false);
		
		lvTweets = (ListView) v.findViewById(R.id.lvTweets);
		lvTweets.setAdapter(aTweets);
		
		lvTweets.setOnScrollListener(new EndlessScrollListener() {
		    @Override
		    public void onLoadMore(int page, int totalItemsCount) {
		    	if(page == 1) {
		    		aTweets.clear();
		    	}
		    	
		    	if(!tweets.isEmpty()) {
		    		Tweet last = tweets.get(tweets.size() - 1);
		    		populateTweetsList(last.getRemoteId()); 
		    	}
		    }
	        });
		
		SwipeRefreshLayout swipeContainer = (SwipeRefreshLayout) v.findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
            	populateTweetsList();
            	
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
            } 
        });
		
		return v;
	}
	
	public void populateTweetsList() {
		populateTweetsList(-1);
	}
	
	protected abstract void populateTweetsList(long maxId);
	
	public void addAll(Collection<Tweet> newTweets) {
		aTweets.addAll(newTweets);
	}

	public void insertFirst(Tweet firstTweet) {
		aTweets.insert(firstTweet, 0);
	}
}
