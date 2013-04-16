/*	Jimmy Kim
 *  Java 2 Project 2
 * 	Term: 1304
 */
package com.example.java2project1;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import org.json.JSONException;
import org.json.JSONObject;
import com.example.lib.WebStuff;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MasterActivity extends Activity implements MainFragment.onMessageListener, SecondFragment.responseListener{
	
	Button fbSubmit;
	Boolean _connected = false;
	Context _context;
	EditText _fsearch;
	String cityData;
	String elevationData;
	String latData;
	String longData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		this.setContentView(R.layout.activity_master);
		
		// set up submit button and edit text
		fbSubmit = (Button)findViewById(R.id.fbSubmit);
		_fsearch = (EditText)findViewById(R.id.fsearchbox);
		
		fbSubmit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// get data
				getQuote(_fsearch.getText().toString());
				
			}
		});
		
		_context = this;
	
		// detect network connection
		_connected = WebStuff.getConnectionStatus(_context);
		
		if(_connected){
			Log.i("NETWORK CONNECTION", WebStuff.getConnectionType(_context));
		}
		
	}
	
private void getQuote(String symbol){
		
		//set up base url for api and pulling back a json file
		String baseURL = "http://api.wunderground.com/api/a64ac4c9694e7d2a/conditions/q/CA/" + symbol + ".json";
		String qs;
		try{
			// pass in baseURL
			qs = URLEncoder.encode(baseURL, "UTF-8");
		} catch (Exception e){
			Log.e("BAD URL", "ENCODING PROBLEM");
			qs="";
		}
		
		// create URL
		URL finalURL;
		try{
			// format json
			finalURL = new URL(baseURL + "?q=" + qs + "&format=json");
			QuoteRequest qr = new QuoteRequest();
			qr.execute(finalURL);
		} catch (MalformedURLException e){
			Log.e("BAD URL", "MALFORMED URL");
			finalURL = null;
		}
	}
	
	// async to get url response in background
	private class QuoteRequest extends AsyncTask<URL, Void, String>{
		@Override
		protected String doInBackground(URL... urls){
			String response = "";
			for(URL url: urls){
				response = WebStuff.getURLStringResponse(url);
			}
			return response;
		}
		
		// check for json and parse
		@Override
		protected void onPostExecute(String result){
			Log.i("URL RESPONSE", result);
			try{
				Log.v("Try onPost", result);
				// use json objects and drill down until desired data
				JSONObject json = new JSONObject(result);
				JSONObject results = json.getJSONObject("current_observation").getJSONObject("display_location");
				
				// get results from JSON set it to string
				cityData = (String) results.get("full");
				elevationData = (String) results.get("elevation");
				latData = (String) results.get("latitude");
				longData = (String) results.get("longitude");
				
				// send to message
				onMessage("City: " + cityData + "\n" + "Elevation: "+ elevationData + "\n" + "Latitude: " + latData + "\n" + "Longitude: " + longData);
				
			} catch (JSONException e){
				Log.e("OnPost JSON", "JSON OBJECT EXCEPTION");
			}
		}
	}

	@Override
	public void onMessage(String message) {
		// TODO Auto-generated method stub
		
		// send message to second fragment
		SecondFragment sf = (SecondFragment) this.getFragmentManager().findFragmentById(R.id.fragment2);
		sf.updateMessage(message);
	}

	@Override
	public void updateResponse(String message) {
		// TODO Auto-generated method stub
		
		// send message to main fragment
		MainFragment mf = (MainFragment) this.getFragmentManager().findFragmentById(R.id.fragment1);
		mf.updateResponse(message);
	}

}
