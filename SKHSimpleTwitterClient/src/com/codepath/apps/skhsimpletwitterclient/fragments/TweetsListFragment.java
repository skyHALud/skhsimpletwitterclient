package com.codepath.apps.skhsimpletwitterclient.fragments;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.json.JSONArray;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
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
import com.loopj.android.http.JsonHttpResponseHandler;

public abstract class TweetsListFragment extends Fragment {
	private List<Tweet> tweets;
	private ArrayAdapter<Tweet> aTweets;
	private ListView lvTweets;
	protected TwitterClient client;
	private SwipeRefreshLayout swipeContainer;
	
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
		
		swipeContainer = (SwipeRefreshLayout) v.findViewById(R.id.swipeContainer);
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

	public JsonHttpResponseHandler getDefaultResponseHandler(final long maxId) {
			return new JsonHttpResponseHandler() {
				@Override
				public void onSuccess(JSONArray json) {
					swipeContainer.setRefreshing(false);
	//				if(maxId == -1) {
	//					long start = System.currentTimeMillis();
	//					Log.d("debug", "Delete offline tweets");
	//					// Refreshing the whole data set so delete all local data. Honor foreign key relationships.
	//					new Delete().from(Tweet.class).execute();
	//					new Delete().from(User.class).execute(); 
	//					Log.d("debug", "DONE in " + (System.currentTimeMillis() - start) + "ms");
	//				}
					
					List<Tweet> newTweets = Tweet.fromJSONArray(json);
					if(newTweets.size() > 0 && newTweets.get(0).getRemoteId() == maxId) {
						newTweets.remove(0);
					}
					
					if(maxId == -1) {
						// This is either a refresh or the first load
						aTweets.clear();
					}
					
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
					
					swipeContainer.setRefreshing(false);
				}
			};
		}
}
