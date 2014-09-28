package com.codepath.apps.skhsimpletwitterclient;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.skhsimpletwitterclient.models.Tweet;
import com.nostra13.universalimageloader.core.ImageLoader;

public class TweetArrayAdapter extends ArrayAdapter<Tweet> {
	private static class ViewHolder {
		public ImageView ivProfileImage;
		public TextView tvUserName;
		public TextView tvBody;
	}
	
	public TweetArrayAdapter(Context context, List<Tweet> tweets) {
		super(context, 0, tweets);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Tweet tweet = getItem(position);
		ViewHolder h;
		
		if(convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.tweet_item, parent, false);
			
			h = new ViewHolder();
			h.ivProfileImage = (ImageView) convertView.findViewById(R.id.ivProfileImage);
			h.tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
			h.tvBody = (TextView) convertView.findViewById(R.id.tvBody);
			
			convertView.setTag(h);
		} else {
			h = (ViewHolder) convertView.getTag();
		}
		
		h.ivProfileImage.setImageResource(android.R.color.transparent);
		
		ImageLoader il = ImageLoader.getInstance();
		il.displayImage(tweet.getUser().getProfileImageUrl(), h.ivProfileImage);
		h.tvUserName.setText(tweet.getUser().getScreenName());
		h.tvBody.setText(tweet.getBody());
		
		return convertView;
	}
}
