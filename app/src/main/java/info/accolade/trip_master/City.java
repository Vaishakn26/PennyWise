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

import info.accolade.trip_master.adapaters.CityAdapter;
import info.accolade.trip_master.utils.Constants;
import info.accolade.trip_master.utils.ServiceHandler;

public class City extends AppCompatActivity {
	
	Toolbar toolbar;
	ListView list_city;
	ArrayList<HashMap<String, String>>City_List=new ArrayList<HashMap<String, String>>();
	TextView no_result_city;
	
	SearchView search_city;
	String query_city;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_city);
		
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		toolbar.setTitle("By City");
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
		
		no_result_city = (TextView)findViewById(R.id.txt_no_city);
		list_city = (ListView)findViewById(R.id.lv_city);

		GetCityList();

		search_city=(SearchView) findViewById(R.id.city_search);
		search_city.setQueryHint("Search City Name");

		search_city.setOnQueryTextFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View arg0, boolean arg1) {
				// TODO Auto-generated method stub
//				Toast.makeText(getBaseContext(), "Enter hotel name", Toast.LENGTH_SHORT).show();
			}
		});

		search_city.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

			@Override
			public boolean onQueryTextSubmit(String query) {
				// TODO Auto-generated method stub				
				query_city = query;
				Log.e("srch", query_city);
				GetSearchCity();
				
				return false;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				// TODO Auto-generated method stub
				
				return false;
			}
		});
				
	}
	
	private void GetCityList() {
		// TODO Auto-generated method stub
		new AsyncHelper().execute();
	}

	public class AsyncHelper extends AsyncTask<Void, Void, Void>
	{
		String message;
		ProgressDialog pd=new ProgressDialog(City.this);
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

			ServiceHandler sh = new ServiceHandler();

			String result = sh.makeServiceCall(Constants.URL_CITYLIST, nameValuePair);
			Log.e("value:", "" +nameValuePair);
			Log.e("result:", "" +result);

			try{
				JSONObject jo=new JSONObject(result);
				JSONObject jdata=jo.getJSONObject("data");
				JSONArray ja=jdata.getJSONArray("citylist");
				for(int i=0;i<ja.length();i++){
					JSONObject jhotel_list=ja.getJSONObject(i);
					HashMap<String,String> map=new HashMap<String,String>();
					map.put("city_id", jhotel_list.getString("city_id"));
					map.put("city_name", jhotel_list.getString("city_name"));
					map.put("city_image", jhotel_list.getString("city_image"));
					map.put("city_lat", jhotel_list.getString("city_lat"));
					map.put("city_lng", jhotel_list.getString("city_lng"));
					City_List.add(map);		
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

			Toast.makeText(City.this, message, Toast.LENGTH_LONG).show();
			if(status==1)
			{
				CityAdapter adapter=new CityAdapter(City_List, City.this);
				list_city.setAdapter(adapter);
			}
			else
			{
				no_result_city.setVisibility(View.VISIBLE);
				no_result_city.setText(message);
			}
		}
	}
	
	private void GetSearchCity() {
		// TODO Auto-generated method stub
		new AsyncHelper1().execute();
	}

	public class AsyncHelper1 extends AsyncTask<Void, Void, Void>
	{
		String message;
		ProgressDialog pd=new ProgressDialog(City.this);
		int status;
		ArrayList<NameValuePair>nameValuePair=new ArrayList<NameValuePair>();

		@Override
		protected void onPreExecute() {
			pd.setCancelable(false);
			pd.setTitle("Loading...");
			pd.show();
			
			City_List.clear();

		}

		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			nameValuePair.add(new BasicNameValuePair("q_city", query_city));


			ServiceHandler sh = new ServiceHandler();

			String result = sh.makeServiceCall(Constants.URL_SEARCH_CITY, nameValuePair);
			Log.e("value:", "" +nameValuePair);
			Log.e("result:", "" +result);

			try{
				JSONObject jo=new JSONObject(result);
				JSONObject jdata=jo.getJSONObject("data");
				JSONArray ja=jdata.getJSONArray("citylist");
				for(int i=0;i<ja.length();i++){
					JSONObject jhotel_list=ja.getJSONObject(i);
					HashMap<String,String> map=new HashMap<String,String>();
					map.put("city_id", jhotel_list.getString("city_id"));
					map.put("city_name", jhotel_list.getString("city_name"));
					map.put("city_image", jhotel_list.getString("city_image"));
					map.put("city_lat", jhotel_list.getString("city_lat"));
					map.put("city_lng", jhotel_list.getString("city_lng"));
					City_List.add(map);
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

			Toast.makeText(City.this, message, Toast.LENGTH_LONG).show();
			if(status==1)
			{
				CityAdapter adapter=new CityAdapter(City_List, City.this);
				list_city.setAdapter(adapter);
			}
			else
			{
				no_result_city.setVisibility(View.VISIBLE);
				no_result_city.setText(message);
			}
		}
	}
	
	

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.city, menu);
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
