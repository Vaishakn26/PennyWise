package info.accolade.trip_master;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import info.accolade.trip_master.adapaters.HotelAdapter;
import info.accolade.trip_master.utils.Constants;
import info.accolade.trip_master.utils.ServiceHandler;

public class Hotel extends AppCompatActivity {

	Toolbar toolbar;
	ListView list_hotel;
	ArrayList<HashMap<String, String>>Hotel_List=new ArrayList<HashMap<String, String>>();
	TextView no_result_hotel;

	SearchView search_hotel;
	String query_hotel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hotel);

		toolbar = (Toolbar) findViewById(R.id.toolbar);
		toolbar.setTitle("Hotels");
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

		no_result_hotel = (TextView)findViewById(R.id.txt_no_hotel);
		list_hotel = (ListView)findViewById(R.id.lv_hotel);

		GetHotelList();

		search_hotel=(SearchView) findViewById(R.id.hotel_search);
		search_hotel.setQueryHint("Search Hotel Name");

		search_hotel.setOnQueryTextFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View arg0, boolean arg1) {
				// TODO Auto-generated method stub
//				Toast.makeText(getBaseContext(), "Enter hotel name", Toast.LENGTH_SHORT).show();
			}
		});

		search_hotel.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

			@Override
			public boolean onQueryTextSubmit(String query) {
				// TODO Auto-generated method stub
//				list.setFilterText(query.toString());
				
				query_hotel = query;
				Log.e("srch", query_hotel);
				GetSearchHotel();
				
				return false;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				// TODO Auto-generated method stub
				
				return false;
			}
		});

	}

	private void GetHotelList() {
		// TODO Auto-generated method stub
		new AsyncHelper().execute();
	}

	public class AsyncHelper extends AsyncTask<Void, Void, Void>
	{
		String message;
		ProgressDialog pd=new ProgressDialog(Hotel.this);
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
			//			nameValuePair.add(new BasicNameValuePair("q_city", query_city));

			ServiceHandler sh = new ServiceHandler();

			String result = sh.makeServiceCall(Constants.URL_HOTELLIST, nameValuePair);
			Log.e("value:", "" +nameValuePair);
			Log.e("result:", "" +result);

			try{
				JSONObject jo=new JSONObject(result);
				JSONObject jdata=jo.getJSONObject("data");
				JSONArray ja=jdata.getJSONArray("hotellist");
				for(int i=0;i<ja.length();i++){
					JSONObject jhotel_list=ja.getJSONObject(i);
					HashMap<String,String> map=new HashMap<String,String>();
					map.put("hotel_id", jhotel_list.getString("hotel_id"));
					map.put("hotel_name", jhotel_list.getString("hotel_name"));
					map.put("hotel_image", jhotel_list.getString("hotel_image"));
					map.put("hotel_lati", jhotel_list.getString("hotel_lati"));
					map.put("hotel_longi", jhotel_list.getString("hotel_longi"));
					Hotel_List.add(map);
			
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

			Toast.makeText(Hotel.this, message, Toast.LENGTH_LONG).show();
			if(status==1)
			{
				HotelAdapter adapter=new HotelAdapter(Hotel_List, Hotel.this);
				list_hotel.setAdapter(adapter);
			}
			else
			{
				no_result_hotel.setVisibility(View.VISIBLE);
				no_result_hotel.setText(message);
			}
		}
	}

	private void GetSearchHotel() {
		// TODO Auto-generated method stub
		new AsyncHelper1().execute();
	}

	public class AsyncHelper1 extends AsyncTask<Void, Void, Void>
	{
		String message;
		ProgressDialog pd=new ProgressDialog(Hotel.this);
		int status;
		ArrayList<NameValuePair>nameValuePair=new ArrayList<NameValuePair>();

		@Override
		protected void onPreExecute() {
			pd.setCancelable(false);
			pd.setTitle("Loading...");
			pd.show();
			
			Hotel_List.clear();

		}

		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			nameValuePair.add(new BasicNameValuePair("q_hotel", query_hotel));


			ServiceHandler sh = new ServiceHandler();

			String result = sh.makeServiceCall(Constants.URL_SEARCH_HOTEL, nameValuePair);
			Log.e("value:", "" +nameValuePair);
			Log.e("result:", "" +result);

			try{
				JSONObject jo=new JSONObject(result);
				JSONObject jdata=jo.getJSONObject("data");
				JSONArray ja=jdata.getJSONArray("hotellist");
				for(int i=0;i<ja.length();i++){
					JSONObject jhotel_list=ja.getJSONObject(i);
					HashMap<String,String> map=new HashMap<String,String>();
					map.put("hotel_id", jhotel_list.getString("hotel_id"));
					map.put("hotel_name", jhotel_list.getString("hotel_name"));
					map.put("hotel_image", jhotel_list.getString("hotel_image"));
					map.put("hotel_lati", jhotel_list.getString("hotel_lati"));
					map.put("hotel_longi", jhotel_list.getString("hotel_longi"));
					Hotel_List.add(map);

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

			Toast.makeText(Hotel.this, message, Toast.LENGTH_LONG).show();
			if(status==1)
			{
				HotelAdapter adapter=new HotelAdapter(Hotel_List, Hotel.this);
				list_hotel.setAdapter(adapter);
			}
			else
			{
				no_result_hotel.setVisibility(View.VISIBLE);
				no_result_hotel.setText(message);
			}
		}
	}
	
	

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.hotel, menu);
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
