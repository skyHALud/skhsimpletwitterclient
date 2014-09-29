package com.codepath.apps.skhsimpletwitterclient;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

public class CreateTweetActivity extends Activity {

	EditText etTweetBody;
	Button btnTweet;
	TwitterClient client;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_tweet);
		
		etTweetBody = (EditText) findViewById(R.id.etTweetBody);
		btnTweet = (Button) findViewById(R.id.btnTweet);
		
		client = TwitterApplication.getRestClient();
		
		btnTweet.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String tweet = etTweetBody.getText().toString();
				
				if(!tweet.isEmpty()) {
					client.setUpdate(tweet, new JsonHttpResponseHandler() {
						public void onSuccess(int arg0, org.json.JSONObject obj) {
							Toast.makeText(CreateTweetActivity.this, "Tweet posted", Toast.LENGTH_SHORT).show();
						};
						
						public void onFailure(Throwable arg0, org.json.JSONObject arg1) {
							Log.e("error", arg0.getMessage(), arg0);
							
							Toast.makeText(CreateTweetActivity.this, "Could not post tweet :-(", Toast.LENGTH_LONG).show();
						};
					});
				}
			}
			
		});
	}
}
