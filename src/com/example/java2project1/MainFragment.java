package com.example.java2project1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

@SuppressLint("NewApi")
public class MainFragment extends Fragment implements OnClickListener{
	
	Button bSubmit;
	EditText _search;
	
	// creates listener for messages
	onMessageListener listener;
	
	public interface onMessageListener{
		public void onMessage(String message);
	}
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		
		// implement listener
		if(activity instanceof onMessageListener){
			listener = (onMessageListener) activity;
		} else {
			throw new ClassCastException(activity.toString() + " must implement my listener");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		// use inflater for layout
		View view = inflater.inflate(R.layout.fragment_main, container);
		
		// set up button
		bSubmit = (Button) view.findViewById(R.id.fbSubmit);
		bSubmit.setOnClickListener(this);
		
		_search = (EditText) view.findViewById(R.id.fsearchbox);
		
		return view;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		// send message
		listener.onMessage(_search.getText().toString());
	}
	
	public void updateResponse(String msg)
	{
		// update
		_search.setText(msg);
	}

}
