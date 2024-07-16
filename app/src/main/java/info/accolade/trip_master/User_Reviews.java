package info.accolade.trip_master;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import info.accolade.trip_master.utils.Constants;
import info.accolade.trip_master.utils.ServiceHandler;

public class User_Reviews extends AppCompatActivity {

	Toolbar toolbar;
	RatingBar rate;
	public String rate_value;
	EditText subj,comment;
	Button submit;
	TextView view_ur;
	SharedPreferences spr;
	public static final String PREF_FILE_NAME = "PrefFile";
	boolean flag = false;
	Spinner category;
	ArrayList<String>ListCategory=new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user__reviews);

		toolbar = (Toolbar) findViewById(R.id.toolbar);
		toolbar.setTitle("User Reviews");
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
		Log.e("user_re", Constants.PARAM_USERNAME);

		rate = (RatingBar) findViewById(R.id.ratingBar);
		subj = (EditText)findViewById(R.id.ed_subject);
		comment = (EditText)findViewById(R.id.ed_feedback);
		submit = (Button)findViewById(R.id.btn_review_submit);
		view_ur = findViewById(R.id.view_user_review);
		category=(Spinner)findViewById(R.id.spn_category);
		
		getCategorylist();

		rate.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {

			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
				// TODO Auto-generated method stub
				rate_value=String.valueOf(rating);
				flag = true;
				Log.e("rate", rate_value);
			}
		});
		
/*		Filters fill = new Filters();
		 subj.setFilters(new InputFilter[]{fill.namefilter});
		 comment.setFilters(new InputFilter[]{fill.namefilter});*/

		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(isFormValid()) 
				{
					SendUserReview();
				}
			}
		});
		
		view_ur.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent view = new Intent(User_Reviews.this, View_user_reviews.class);
				startActivity(view);
			}
		});


	}	

	private boolean isFormValid() {	
		Boolean status=true;
		
		if(subj.getText().toString().length()==0){
			subj.setError("Enter your Subject");	
			status=false;
		}

		if(comment.getText().toString().length()==0){
			comment.setError("Enter your Comment");	
			status=false;
		}
		if(category.getSelectedItemId()==0){
			Toast.makeText(User_Reviews.this, "Select the category", Toast.LENGTH_LONG).show();
			status=false;
		}
		
		if(flag==false)
		{
			Toast.makeText(User_Reviews.this, "Rate Me", Toast.LENGTH_SHORT).show();
			status=false;
		}
		
		return status;
	}

	private void SendUserReview() {
		// TODO Auto-generated method stub
		new AsyncHelper().execute();
	}

	public class AsyncHelper extends AsyncTask<Void, Void, Void>
	{
		// yyyy-MM-dd HH:mm:ss
		String date_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
		String message;
		ProgressDialog pd=new ProgressDialog(User_Reviews.this);
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
			nameValuePair.add(new BasicNameValuePair("username", Constants.PARAM_USERNAME));
			nameValuePair.add(new BasicNameValuePair("subject", subj.getText().toString()));
			nameValuePair.add(new BasicNameValuePair("comment", comment.getText().toString()));
			nameValuePair.add(new BasicNameValuePair("category", category.getSelectedItem().toString()));
			nameValuePair.add(new BasicNameValuePair("rate", rate_value));
			nameValuePair.add(new BasicNameValuePair("d_t", date_time));

			ServiceHandler sh = new ServiceHandler();

			String result = sh.makeServiceCall(Constants.URL_USER_REVIEWS, nameValuePair);
			Log.e("value:", "" +nameValuePair);
			Log.e("result:", "" +result);

			try{
				JSONObject jo=new JSONObject(result);
				JSONObject jdata=jo.getJSONObject("data");
				message=jdata.getString("message");
				status=jdata.getInt("status");

			}catch(Exception e){

			}

			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			pd.dismiss();

			Toast.makeText(User_Reviews.this, message, Toast.LENGTH_LONG).show();
			if(status==1)
			{	
				subj.setText("");
				comment.setText("");
				category.setSelection(0);
				rate.setRating(0.0f);
			}
		}
	}
	
	private void getCategorylist() {
		// TODO Auto-generated method stub
		new AsyncHelper2().execute();
	}

	public class AsyncHelper2 extends AsyncTask<Void, Void, Void>
	{


		ProgressDialog pd=new ProgressDialog(User_Reviews.this);
		int status;

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

			String result = sh.makeServiceCall(Constants.URL_GET_CATEGORY, null);
			Log.e("result:", "" +result);

			try{
				JSONObject jo=new JSONObject(result);
				JSONObject jdata=jo.getJSONObject("data");
				status=jdata.getInt("status");
				if(status==1)
				{
					JSONArray ja=jdata.getJSONArray("cat");
					for(int i=0;i<ja.length();i++)
					{
						JSONObject jcategory=ja.getJSONObject(i);
						ListCategory.add(jcategory.getString("cat_name"));
						Log.e("6", ListCategory.toString());
					}
			
				}

			}catch(Exception e){

			}

			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			pd.dismiss();

			if(status==1)
			{	
				ArrayAdapter<String>adapter=new ArrayAdapter<String>(User_Reviews.this, android.R.layout.simple_spinner_item, ListCategory);
				adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
				adapter.notifyDataSetChanged();
				adapter.insert("Select Category", 0);
				category.setAdapter(adapter);	
			}
		}
	}
	
	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user__reviews, menu);
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
