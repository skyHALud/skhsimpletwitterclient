package com.codepath.apps.skhsimpletwitterclient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.sax.StartElementListener;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
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
		public TextView tvAge;
	}
	
	public TweetArrayAdapter(Context context, List<Tweet> tweets) {
		super(context, 0, tweets);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final Tweet tweet = getItem(position);
		ViewHolder h;
		
		if(convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.tweet_item, parent, false);
			
			h = new ViewHolder();
			h.ivProfileImage = (ImageView) convertView.findViewById(R.id.ivProfileImage);
			h.tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
			h.tvBody = (TextView) convertView.findViewById(R.id.tvBody);
			h.tvAge = (TextView) convertView.findViewById(R.id.tvAge);
			
			h.ivProfileImage.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent i = new Intent(getContext(), ProfileActivity.class);
					i.putExtra("user", tweet.getUser());
					getContext().startActivity(i);
				}
			});
			
			convertView.setTag(h);
		} else {
			h = (ViewHolder) convertView.getTag();
		}
		
		h.ivProfileImage.setImageResource(android.R.color.transparent);
		
		ImageLoader il = ImageLoader.getInstance();
		il.displayImage(tweet.getUser().getProfileImageUrl(), h.ivProfileImage);
		h.tvUserName.setText(tweet.getUser().getScreenName());
		h.tvBody.setText(tweet.getBody());
		h.tvAge.setText(getRelativeTimeAgo(tweet.getCreatedAt()));
		
		return convertView;
	}
	
	public String getRelativeTimeAgo(String rawJsonDate) {
		String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
		SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
		sf.setLenient(true);
	 
		String relativeDate = "";
		try {
			long dateMillis = sf.parse(rawJsonDate).getTime();
			relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
					System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS, DateUtils.FORMAT_ABBREV_RELATIVE).toString();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	 
		return relativeDate;
	}
}
