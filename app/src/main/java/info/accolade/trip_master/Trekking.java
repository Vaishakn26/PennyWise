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
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import info.accolade.trip_master.adapaters.DestinationAdapter;
import info.accolade.trip_master.utils.Constants;
import info.accolade.trip_master.utils.ServiceHandler;

public class Trekking extends AppCompatActivity {

	Toolbar toolbar;
	ListView list_trekking;
	ArrayList<HashMap<String, String>> Trekking_List = new ArrayList<HashMap<String, String>>();
	TextView no_result_adven;

	String current_a_cityname;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trekking);

		toolbar = (Toolbar) findViewById(R.id.toolbar);
		toolbar.setTitle("Trekking");
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

		list_trekking = (ListView) findViewById(R.id.lv_trekking);
		no_result_adven = (TextView) findViewById(R.id.txt_no_trek);


		if (Constants.is_city == 1) {
			GetCityTrekkingList();
		} else {
			LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
			Criteria criteria = new Criteria();
			locationManager.getBestProvider(criteria, true);
//			Location location=locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria,true)); 
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
						current_a_cityname = addresses.get(0).getLocality().toString();
						Toast.makeText(Trekking.this, "Your Current City is " + current_a_cityname, Toast.LENGTH_SHORT).show();
						Log.e("c_name", current_a_cityname);
					}
				 	catch (Exception e){
				 		current_a_cityname = "";
					}
				 }
				 
			 }
			 catch (IOException e) 
			 {
				 e.printStackTrace();
			 }
			GetCurrentCityTrekkingList();
		}
		
	}
	
	private void GetCurrentCityTrekkingList() {
		// TODO Auto-generated method stub
		new AsyncHelper().execute();
	}

	public class AsyncHelper extends AsyncTask<Void, Void, Void>
	{
		String message;
		ProgressDialog pd=new ProgressDialog(Trekking.this);
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
			nameValuePair.add(new BasicNameValuePair("q_t_city", current_a_cityname));
			nameValuePair.add(new BasicNameValuePair("q_t_categoryid", String.valueOf(Constants.CATEGORY_ID)));

			ServiceHandler sh = new ServiceHandler();

			String result = sh.makeServiceCall(Constants.URL_CurCity_DESTLIST, nameValuePair);
			Log.e("value:", "" +nameValuePair);
			Log.e("result:", "" +result);

			try{
				JSONObject jo=new JSONObject(result);
				JSONObject jdata=jo.getJSONObject("data");
				JSONArray ja=jdata.getJSONArray("destlist");
				for(int i=0;i<ja.length();i++){
					JSONObject jhotel_list=ja.getJSONObject(i);
					HashMap<String,String> map=new HashMap<String,String>();
					map.put("dest_id", jhotel_list.getString("dest_id"));
					map.put("dest_name", jhotel_list.getString("dest_name"));
					map.put("dest_image", jhotel_list.getString("dest_image"));
					map.put("dest_lati", jhotel_list.getString("dest_lati"));
					map.put("dest_longi", jhotel_list.getString("dest_longi"));
					Trekking_List.add(map);

				}
				
				message=jdata.getString("message");
				status=jdata.getInt("status");	
				Log.e("msg", message);
				
			}
			catch(Exception e){

			}

			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			pd.dismiss();

			
			if(status==1)
			{
				Toast.makeText(Trekking.this, message, Toast.LENGTH_LONG).show();
				DestinationAdapter adapter=new DestinationAdapter(Trekking_List, Trekking.this);
				list_trekking.setAdapter(adapter);
			}
			else
			{
				no_result_adven.setVisibility(View.VISIBLE);
				no_result_adven.setText(message);
			}
		}
	}
	
	
	private void GetCityTrekkingList() {
		// TODO Auto-generated method stub
		new AsyncHelper1().execute();
	}

	public class AsyncHelper1 extends AsyncTask<Void, Void, Void>
	{
		String message;
		ProgressDialog pd=new ProgressDialog(Trekking.this);
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
			nameValuePair.add(new BasicNameValuePair("q_t_cityid", Constants.CITY_ID));
			nameValuePair.add(new BasicNameValuePair("q_t_categoryid", String.valueOf(Constants.CATEGORY_ID)));

			ServiceHandler sh = new ServiceHandler();

			String result = sh.makeServiceCall(Constants.URL_City_DESTLIST, nameValuePair);
			Log.e("value:", "" +nameValuePair);
			Log.e("result:", "" +result);

			try{
				JSONObject jo=new JSONObject(result);
				JSONObject jdata=jo.getJSONObject("data");
				JSONArray ja=jdata.getJSONArray("destlist");
				for(int i=0;i<ja.length();i++){
					JSONObject jhotel_list=ja.getJSONObject(i);
					HashMap<String,String> map=new HashMap<String,String>();
					map.put("dest_id", jhotel_list.getString("dest_id"));
					map.put("dest_name", jhotel_list.getString("dest_name"));
					map.put("dest_image", jhotel_list.getString("dest_image"));
					map.put("dest_lati", jhotel_list.getString("dest_lati"));
					map.put("dest_longi", jhotel_list.getString("dest_longi"));
					Trekking_List.add(map);
				
				}
				
				message=jdata.getString("message");
				status=jdata.getInt("status");
				
			}
			catch(Exception e){

			}

			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			pd.dismiss();

			Toast.makeText(Trekking.this, message, Toast.LENGTH_LONG).show();
			if(status==1)
			{
				DestinationAdapter adapter=new DestinationAdapter(Trekking_List, Trekking.this);
				list_trekking.setAdapter(adapter);
			}
			else
			{
				no_result_adven.setVisibility(View.VISIBLE);
				no_result_adven.setText(message);
			}
		}
	}
	
	

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.trekking, menu);
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
