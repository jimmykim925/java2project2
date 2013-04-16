/*	Jimmy Kim
 *  Java 2 Project 2
 * 	Term: 1304
 */

package com.example.java2project1;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;
import com.example.lib.FileStuff;
import com.example.lib.WebStuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Debug;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class JavaProject1MainActivity extends Activity {
	// set up two buttons
	Button bSubmit;
	EditText _search;
	Boolean _connected = false;
	Context _context;
	String cityData;
	String elevationData;
	String latData;
	String longData;
	
	// create hashmap for history of search results
	HashMap<String, String>_history;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// set layout for main page
		setContentView(R.layout.activity_java_project1_main);
		
		_context = this;
		
		// detect network connection
		_connected = WebStuff.getConnectionStatus(_context);
		
		if(_connected){
			Log.i("NETWORK CONNECTION", WebStuff.getConnectionType(_context));
		}
		
		// set up button
		bSubmit = (Button)findViewById(R.id.bSubmit);
		
		_search = (EditText)findViewById(R.id.searchbox);
		
		bSubmit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				getQuote(_search.getText().toString());
				
			}
		});
	}
	
private void getQuote(String symbol){
		
		//Log.v("Symbol Passed", symbol);
		
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
	
	// READ saved file hashmap for search history
	@SuppressWarnings("unchecked")
	private HashMap<String, String> getHistory(){
		
		// read saved file object for search history
		Object stored = FileStuff.readObjectFile(_context, "history", false);
		
		// conditional statement to check for saved file 
		HashMap<String, String>history;
		if (stored == null){
			Log.i("HISTORY", "NO HISTORY FOUND");
			history = new HashMap<String, String>();
		} else {
			history = (HashMap<String, String>) stored;
		}
		return history;
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
				
				// Explicit Intent sending string to activity
				Intent intent = new Intent("com.example.java2project1.SECONDACTIVITY");
				intent.putExtra("City", cityData);
				intent.putExtra("Elevation", elevationData);
				intent.putExtra("Latitude", latData);
				intent.putExtra("Longitude", longData);
				startActivityForResult(intent, 0);
				
			} catch (JSONException e){
				Log.e("OnPost JSON", "JSON OBJECT EXCEPTION");
			}
		}
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if(resultCode == RESULT_OK){
			String resultMessage = data.getStringExtra("resultIntent");
			//resultText.setText(resultMessage);
			Log.v("result code", resultMessage);
		} else {
			
		}
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.java_project1_main, menu);
		return true;
	}

}
