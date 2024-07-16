package info.accolade.trip_master;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
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

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import info.accolade.trip_master.adapaters.UserReviewAdapter;
import info.accolade.trip_master.utils.Constants;
import info.accolade.trip_master.utils.ServiceHandler;

public class View_user_reviews extends AppCompatActivity {
	
	Toolbar toolbar;
	SharedPreferences spr;
	public static final String PREF_FILE_NAME = "PrefFile";
	TextView no_result_user_review;
	
	ListView list_u_r;
	ArrayList<HashMap<String, String>>User_Review_List=new ArrayList<HashMap<String, String>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_user_reviews);
		
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		toolbar.setTitle("View User Reviews");
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

		spr = getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
		Constants.PARAM_USERNAME=spr.getString("u_uname", null);

		Log.e("hotel_reviews_udetails", Constants.PARAM_USERNAME);
		
		list_u_r = (ListView)findViewById(R.id.lv_user_reviews);
		no_result_user_review =(TextView)findViewById(R.id.txt_no_user_review);
		
		GetUserReviewList();
		
	}
	
	private void GetUserReviewList() {
		// TODO Auto-generated method stub
		new AsyncHelper().execute();
	}

	public class AsyncHelper extends AsyncTask<Void, Void, Void>
	{
		String message;
		ProgressDialog pd=new ProgressDialog(View_user_reviews.this);
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
//			nameValuePair.add(new BasicNameValuePair("hotelid", Constants.HOTEL_ID));

			ServiceHandler sh = new ServiceHandler();

			String result = sh.makeServiceCall(Constants.URL_USERREVIEWLIST, nameValuePair);
			Log.e("value:", "" +nameValuePair);
			Log.e("result:", "" +result);

			try{
				JSONObject jo=new JSONObject(result);
				JSONObject jdata=jo.getJSONObject("data");
				JSONArray ja=jdata.getJSONArray("userrevlist");
				for(int i=0;i<ja.length();i++){
					JSONObject jhotel_list=ja.getJSONObject(i);
					HashMap<String,String> map=new HashMap<String,String>();
					map.put("urev_user_name", jhotel_list.getString("urev_user_name"));
					map.put("urev_user_cat", jhotel_list.getString("urev_user_cat"));
					map.put("urev_rate_value", jhotel_list.getString("urev_rate_value"));
					map.put("urev_user_sub", jhotel_list.getString("urev_user_sub"));
					map.put("urev_user_comments", jhotel_list.getString("urev_user_comments"));
					map.put("urev_user_date", jhotel_list.getString("urev_user_date"));
					User_Review_List.add(map);			
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

			Toast.makeText(View_user_reviews.this, message, Toast.LENGTH_LONG).show();
			if(status==1)
			{
				UserReviewAdapter adapter=new UserReviewAdapter(User_Review_List, View_user_reviews.this);
				list_u_r.setAdapter(adapter);
			}
			else
			{
				no_result_user_review.setVisibility(View.VISIBLE);
				no_result_user_review.setText(message);
			}
		}
	}

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_user_reviews, menu);
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
