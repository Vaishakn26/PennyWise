package info.accolade.trip_master;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import info.accolade.trip_master.adapaters.HotelReviewAdapter;
import info.accolade.trip_master.utils.Constants;
import info.accolade.trip_master.utils.ServiceHandler;

public class HotelReviews extends AppCompatActivity {

	Toolbar toolbar;
	SharedPreferences spr;
	public static final String PREF_FILE_NAME = "PrefFile";
	TextView no_result_hotel_review;
	
	public String hrrate_value;
	boolean flag = false;
	
	//edit mode
	Button yes,no;
	TextView u_text;
	EditText comm;
	RatingBar hrrate;
	
	ListView list_h_r;
	ArrayList<HashMap<String, String>>Hotel_Review_List=new ArrayList<HashMap<String, String>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hotel_reviews);

		toolbar = (Toolbar) findViewById(R.id.toolbar);
		toolbar.setTitle("Hotel Reviews");
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
		
		list_h_r = (ListView)findViewById(R.id.lv_hotel_reviews);
		no_result_hotel_review = (TextView)findViewById(R.id.txt_no_hotel_review);

		GetHotelReviewList();

	}
	
	private void GetHotelReviewList() {
		// TODO Auto-generated method stub
		new AsyncHelper().execute();
	}

	public class AsyncHelper extends AsyncTask<Void, Void, Void>
	{
		String message;
		ProgressDialog pd=new ProgressDialog(HotelReviews.this);
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
			nameValuePair.add(new BasicNameValuePair("hotelid", Constants.HOTEL_ID));

			ServiceHandler sh = new ServiceHandler();

			String result = sh.makeServiceCall(Constants.URL_HOTELREVIEWLIST, nameValuePair);
			Log.e("value:", "" +nameValuePair);
			Log.e("result:", "" +result);

			try{
				JSONObject jo=new JSONObject(result);
				JSONObject jdata=jo.getJSONObject("data");
				JSONArray ja=jdata.getJSONArray("hotelrevlist");
				for(int i=0;i<ja.length();i++){
					JSONObject jhotel_list=ja.getJSONObject(i);
					HashMap<String,String> map=new HashMap<String,String>();
					map.put("hr_user_name", jhotel_list.getString("hr_user_name"));
					map.put("hr_rate_value", jhotel_list.getString("hr_rate_value"));
					map.put("hr_comments", jhotel_list.getString("hr_comments"));
					Hotel_Review_List.add(map);			
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

			Toast.makeText(HotelReviews.this, message, Toast.LENGTH_LONG).show();
			if(status==1)
			{
				HotelReviewAdapter adapter=new HotelReviewAdapter(Hotel_Review_List, HotelReviews.this);
				list_h_r.setAdapter(adapter);
			}
			else
			{
				no_result_hotel_review.setVisibility(View.VISIBLE);
				no_result_hotel_review.setText(message);
			}
		}
	}
	
	private void AddHotelReview() {
		// TODO Auto-generated method stub
		new AsyncHelper1().execute();
	}

	public class AsyncHelper1 extends AsyncTask<Void, Void, Void>
	{
		String message;
		ProgressDialog pd=new ProgressDialog(HotelReviews.this);
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
			nameValuePair.add(new BasicNameValuePair("hotelid", Constants.HOTEL_ID));
			nameValuePair.add(new BasicNameValuePair("hr_un", u_text.getText().toString()));
			nameValuePair.add(new BasicNameValuePair("hr_comm", comm.getText().toString()));
			nameValuePair.add(new BasicNameValuePair("hr_rate", hrrate_value));

			ServiceHandler sh = new ServiceHandler();

			String result = sh.makeServiceCall(Constants.URL_ADDHOTELREVIEW, nameValuePair);
			Log.e("value:", "" +nameValuePair);
			Log.e("result:", "" +result);

			try{
				JSONObject jo=new JSONObject(result);
				JSONObject jdata=jo.getJSONObject("data");
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

			Toast.makeText(HotelReviews.this, message, Toast.LENGTH_LONG).show();
			if(status==1)
			{
				Log.e("status", "1");
//				GetHotelReviewList();
			}
		}
	}
	
	private boolean isFormValid() {	
		Boolean status=true;
		
		if(u_text.getText().toString().length()==0){
			u_text.setError("Enter your Subject");	
			status=false;
		}

		if(comm.getText().toString().length()==0){
			comm.setError("Enter your Comment");	
			status=false;
		}
		
		if(flag==false)
		{
			Toast.makeText(HotelReviews.this, "Rate Me", Toast.LENGTH_SHORT).show();
			status=false;
		}
		
		return status;
	}
	
	

	private void showAlert() {

		String chars = "Write Review";
		SpannableString str = new SpannableString(chars);
		str.setSpan(new ForegroundColorSpan(Color.WHITE), 0, chars.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); 

		final Dialog dialog = new Dialog(HotelReviews.this);
		dialog.setContentView(R.layout.custom_dialog_hotel_review);
		dialog.setTitle(str);

		u_text = (TextView) dialog.findViewById(R.id.txt_usr_name);
		u_text.setText(Constants.PARAM_USERNAME);
		comm = (EditText) dialog.findViewById(R.id.txt_hotel_comment);
		hrrate = (RatingBar) dialog.findViewById(R.id.add_hotel_ratingBar);
		
		hrrate.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
				// TODO Auto-generated method stub
				hrrate_value=String.valueOf(rating);
				flag = true;
				Log.e("rate", hrrate_value);
			}
		});
		
		yes = (Button) dialog.findViewById(R.id.btn_yes);
		no = (Button) dialog.findViewById(R.id.btn_no);
		dialog.show();
		dialog.getWindow().setBackgroundDrawableResource(R.color.trans_purple);

		yes.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(isFormValid()) 
				{
					AddHotelReview();
					dialog.dismiss();
				}
				
			}
		});
		no.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.hotel_reviews, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_write) {

			showAlert();

			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
