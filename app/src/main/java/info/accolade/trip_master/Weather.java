package info.accolade.trip_master;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import info.accolade.trip_master.utils.Constants;
import info.accolade.trip_master.utils.RemoteFetch;
import info.accolade.trip_master.utils.ServiceHandler;

public class Weather extends AppCompatActivity {

	Toolbar toolbar;
	SearchView search_wcity;
	TextView degree, month, desc;

	String current_w_cityname;
	String query_wcity;

	TextView weatherIcon;
	TextView currentTemperatureField;
	TextView detailsField;

	Handler handler;

	public Weather() {
		handler = new Handler();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weather);

		toolbar = (Toolbar) findViewById(R.id.toolbar);
		toolbar.setTitle("Weathers");
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_backspace);
		getSupportActionBar().setHomeButtonEnabled(true);

		toolbar.setNavigationOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				handleOnBackPress();
			}

			private void handleOnBackPress() {
				// TODO Auto-generated method stub
				finish();
			}
		});

//		degree = (TextView)findViewById(R.id.txt_temp);
		month = (TextView) findViewById(R.id.txt_month);
		desc = (TextView) findViewById(R.id.txt_desc);

		currentTemperatureField = (TextView) findViewById(R.id.current_temperature_field);
		weatherIcon = (TextView) findViewById(R.id.weather_icon);
		detailsField = (TextView) findViewById(R.id.details);

		LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		locationManager.getBestProvider(criteria, true);
//		Location location=locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria,true)); 

		if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			// TODO: Consider calling
			//    ActivityCompat#requestPermissions
			// here to request the missing permissions, and then overriding
			//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
			//                                          int[] grantResults)
			// to handle the case where the user grants the permission. See the documentation
			// for ActivityCompat#requestPermissions for more details.
			return;
		}
		Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		Geocoder gcd=new Geocoder(getBaseContext(), Locale.getDefault());
		List<Address> addresses;
		 try 
		 {
			 addresses=gcd.getFromLocation(location.getLatitude(),location.getLongitude(),1);
			 if(addresses.size()>0)
			 {
			 	try {
					current_w_cityname = addresses.get(0).getLocality().toString();
					Log.e("c_name", current_w_cityname);
					Toast.makeText(Weather.this, "Your Current City is "+current_w_cityname, Toast.LENGTH_SHORT).show();
					GetCurrentWeatherDetails();
				}
			 	catch (Exception e){
					current_w_cityname = "";
				}

			 }
			 
		 }
		 catch (IOException e) 
		 {
			 e.printStackTrace();
		 }
		 
		updateWeatherData(current_w_cityname);
//		updateWeatherData("Vamanjoor");
		 
		search_wcity=(SearchView) findViewById(R.id.weather_search);
		search_wcity.setQueryHint("Search by City Name");
		
		search_wcity.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
					
//				Toast.makeText(getBaseContext(), String.valueOf(hasFocus),Toast.LENGTH_SHORT).show();
//				Toast.makeText(getBaseContext(), "Enter city name", Toast.LENGTH_SHORT).show();
			}
		});
		
		
		search_wcity.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			
			@Override
			public boolean onQueryTextSubmit(String query) {
				// TODO Auto-generated method stub
					
				Toast.makeText(getBaseContext(), "hiii "+query,Toast.LENGTH_SHORT).show();
				
				query_wcity = query.trim();
				Log.e("srch", query_wcity);
				GetWeatherDetails();
				
				return false;
			}
			
			@Override
			public boolean onQueryTextChange(String newText) {
				// TODO Auto-generated method stub
					
//				Toast.makeText(getBaseContext(), "haaa "+newText,Toast.LENGTH_SHORT).show();
				return false;
			}
		});
	}
	
	private void GetWeatherDetails() {
		// TODO Auto-generated method stub
		new AsyncHelper().execute();
	}

	public class AsyncHelper extends AsyncTask<Void, Void, Void>
	{
		String message,w_tem,w_mon,w_des;
		ProgressDialog pd=new ProgressDialog(Weather.this);
		int status;
		ArrayList<NameValuePair>nameValuePair=new ArrayList<NameValuePair>();

		@Override
		protected void onPreExecute() {
			pd.setCancelable(false);
			pd.setTitle("Loading...");
			pd.show();

		}

		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			nameValuePair.add(new BasicNameValuePair("q_wcity", query_wcity));


			ServiceHandler sh = new ServiceHandler();

			String result = sh.makeServiceCall(Constants.URL_WEATHERDETAILS, nameValuePair);
			Log.e("value:", "" +nameValuePair);
			Log.e("result:", "" +result);

			try{
				JSONObject jo=new JSONObject(result);
				JSONObject jdata=jo.getJSONObject("data");
				message=jdata.getString("message");
				status=jdata.getInt("status");
				
//				w_tem = jdata.getString("temp");
				w_mon = jdata.getString("month");
				w_des = jdata.getString("descrp");

			}catch(Exception e){

			}

			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			pd.dismiss();

			Toast.makeText(Weather.this, message, Toast.LENGTH_SHORT).show();
			if(status==1)
			{
//				degree.setText(w_tem);
				month.setText(w_mon);
				desc.setText(w_des);
			}
		}
	}
	
	
	private void GetCurrentWeatherDetails() {
		// TODO Auto-generated method stub
		new AsyncHelper1().execute();
	}

	public class AsyncHelper1 extends AsyncTask<Void, Void, Void>
	{
		String message,w_tem,w_mon,w_des;
		ProgressDialog pd=new ProgressDialog(Weather.this);
		int status;
		ArrayList<NameValuePair>nameValuePair=new ArrayList<NameValuePair>();

		@Override
		protected void onPreExecute() {
			pd.setCancelable(false);
			pd.setTitle("Loading...");
			pd.show();

		}

		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			nameValuePair.add(new BasicNameValuePair("q_wcity", current_w_cityname));


			ServiceHandler sh = new ServiceHandler();

			String result = sh.makeServiceCall(Constants.URL_WEATHERDETAILS, nameValuePair);
			Log.e("value:", "" +nameValuePair);
			Log.e("result:", "" +result);

			try{
				JSONObject jo=new JSONObject(result);
				JSONObject jdata=jo.getJSONObject("data");
				message=jdata.getString("message");
				status=jdata.getInt("status");
				
//				w_tem = jdata.getString("temp");
				w_mon = jdata.getString("month");
				w_des = jdata.getString("descrp");

			}catch(Exception e){

			}

			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			pd.dismiss();

			Toast.makeText(Weather.this, message, Toast.LENGTH_LONG).show();
			if(status==1)
			{
//				degree.setText(w_tem);
				month.setText(w_mon);
				desc.setText(w_des);
			}
		}
	}
	
	
	
	private void updateWeatherData(final String city){
		new Thread(){
			@Override
			public void run(){
				final JSONObject json = RemoteFetch.getJSON(Weather.this, city);
				Log.e("city", ":"+city);
				Log.e("json", ":"+json);
				if(json == null){
					handler.post(new Runnable(){
						@Override
						public void run()
						{
						Toast.makeText(Weather.this, Weather.this.getString(R.string.place_not_found), Toast.LENGTH_LONG).show(); 
						}
					});
				} else {
					handler.post(new Runnable(){
						@Override
						public void run(){
							renderWeather(json);
						}
					});
				}               
			}
		}.start();
	}
	
	private void renderWeather(JSONObject json){
		try {
			Log.e("json",":"+json);

			JSONObject details = json.getJSONArray("weather").getJSONObject(0);
			JSONObject main = json.getJSONObject("main");
			JSONObject wind = json.getJSONObject("wind");
			//JSONObject rain = json.getJSONObject("rain");
			Log.e("details",":"+details);
			
			detailsField.setText(details.getString("description").toUpperCase(Locale.US));			
			
			currentTemperatureField.setText(
					String.format("%.2f", main.getDouble("temp"))+ " \u00B0C");

			DateFormat df = DateFormat.getDateTimeInstance();
			String updatedOn = df.format(new Date(json.getLong("dt")*1000));

			setWeatherIcon(details.getInt("id"),
					json.getJSONObject("sys").getLong("sunrise") * 1000,
					json.getJSONObject("sys").getLong("sunset") * 1000);

		}catch(Exception e){
			Log.e("SimpleWeather", "One or more fields not found in the JSON data");
		}
	}
	
	private void setWeatherIcon(int actualId, long sunrise, long sunset){
		int id = actualId / 100;
		String icon = "";
		if(actualId == 800){
			long currentTime = new Date().getTime();
			if(currentTime>=sunrise && currentTime<sunset) {
				icon = Weather.this.getString(R.string.weather_sunny);
			} else {
				icon = Weather.this.getString(R.string.weather_clear_night);
			}
		} else {
			switch(id) {
			case 2 : icon = Weather.this.getString(R.string.weather_thunder);
			break;         
			case 3 : icon = Weather.this.getString(R.string.weather_drizzle);
			break;     
			case 7 : icon = Weather.this.getString(R.string.weather_foggy);
			break;
			case 8 : icon = Weather.this.getString(R.string.weather_cloudy);
			break;
			case 6 : icon = Weather.this.getString(R.string.weather_snowy);
			break;
			case 5 : icon = Weather.this.getString(R.string.weather_rainy);
			break;
			}
		}
		weatherIcon.setText(icon);
		Log.e("hghh", icon);
	}
	
	

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.weather, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}*/
}
