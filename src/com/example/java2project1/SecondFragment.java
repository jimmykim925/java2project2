package com.example.java2project1;

import com.example.java2project1.MainFragment.onMessageListener;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class SecondFragment extends Fragment implements OnClickListener{
	
	Button fback;
	Button fwiki;
	Button about;
	TextView cityV;
	String url;
	String name;
	
	// creates listener for messages
	responseListener listener;
		
	public interface responseListener{
		void updateResponse(String message);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		// set url for wikipedia
		url = "http://en.wikipedia.org";
		
		// use inflater for layout
		View view = inflater.inflate(R.layout.fragment_second, container);
		
		// set text view
		cityV = (TextView)view.findViewById(R.id.fcityv);
		
		// finished button
		fback = (Button)view.findViewById(R.id.fbBack);
		fback.setOnClickListener(this);
		
		// wikipedia button
		fwiki = (Button)view.findViewById(R.id.fbDetails);
		fwiki.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				// Implicit intent to load web browser with assigned url
				Intent web = new Intent(Intent.ACTION_VIEW);
				web.setData(Uri.parse(url));
				startActivity(web);
			}
		});
		
		about = (Button)view.findViewById(R.id.fbAbout);
		about.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				// set string to pass using explicit intent
				name = "Jimmy Kim\n" + "Java 2 Project 2\n" + "Term: 1304";
				
				// Explicit Intent sending string to activity
				Intent intent = new Intent("com.example.java2project1.INFOACTIVITY");
				intent.putExtra("Name", name);
				startActivityForResult(intent, 0);
			}
		});
		
		return view;
	}
	

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		
		// listener
		if(activity instanceof responseListener){
			listener = (responseListener) activity;
		} else {
			throw new ClassCastException(activity.toString() + " must implement my listener");
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		// send string from second to main activity
		String returnString = "Thanks for trying this app!";
		listener.updateResponse(returnString);
		
	}
	
	public void updateMessage(String msg){
		// update text view
		cityV.setText(msg);
	}

}
