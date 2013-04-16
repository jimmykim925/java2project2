package com.example.java2project1;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class SecondActivity extends Activity{
	// buttons and string for url
	Button back;
	Button wiki;
	String url;
	TextView cityV;
	TextView eleV;
	TextView latV;
	TextView lonV;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// set layout for second activity
		setContentView(R.layout.second);
		
		cityV = (TextView)findViewById(R.id.cityv);
		eleV = (TextView)findViewById(R.id.elevationv);
		latV = (TextView)findViewById(R.id.latv);
		lonV = (TextView)findViewById(R.id.lonv);
		
		// Get passed value from intent 
		Intent i = getIntent();
		String cityVal = i.getStringExtra("City");
		String elevationVal = i.getStringExtra("Elevation");
		String latVal = i.getStringExtra("Latitude");
		String lonVal = i.getStringExtra("Longitude");
		
		// set url for wikipedia
		url = "http://en.wikipedia.org/wiki/"+cityVal;
		
		cityV.setText("City and State: " + cityVal);
		eleV.setText("Elevation: " + elevationVal);
		latV.setText("Latitude: " + latVal);
		lonV.setText("Longitude: " + lonVal);
		
		// back button
		back = (Button)findViewById(R.id.bBack);
		
		// set listener for back button
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				// intent to finish activity and return data back to original activity
				Intent resultIntent = new Intent();
				resultIntent.putExtra("resultIntent", "Yo yo");
				setResult(Activity.RESULT_OK, resultIntent);
				finish();
			}
		});
		
		// wikipedia button
		wiki = (Button)findViewById(R.id.bDetails);
		// set listener for yahoo button
		wiki.setOnClickListener(new OnClickListener() {
		
			@Override
			public void onClick(View v) {
				
				// Implicit intent to load web browser with assigned url
				Intent web = new Intent(Intent.ACTION_VIEW);
				web.setData(Uri.parse(url));
				startActivity(web);
			}
		});
		
	}
	
	
	
	
}
