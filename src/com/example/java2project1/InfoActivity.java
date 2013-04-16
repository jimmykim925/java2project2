package com.example.java2project1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class InfoActivity extends Activity {
	
	TextView info;
	String name;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// set layout for second activity
		setContentView(R.layout.info);
		
		info = (TextView)findViewById(R.id.devinfo);
		
		// Get passed value from explicit intent 
		Intent i = getIntent();
		name = i.getStringExtra("Name");
		
		// set text
		info.setText(name);
	}
}
