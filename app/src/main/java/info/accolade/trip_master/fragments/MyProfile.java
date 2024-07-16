package info.accolade.trip_master.fragments;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.androidquery.AQuery;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

import info.accolade.trip_master.R;
import info.accolade.trip_master.utils.Constants;
import info.accolade.trip_master.utils.ServiceHandler;

public class MyProfile extends Fragment {
	
	ImageView my_profile;
	EditText fname,phno,email,city;
	TextView uname;
	RadioGroup prf_rgender;
	RadioButton prf_rbmale, prf_rbfemale;
	String prf_gen;
	SharedPreferences spr;
	private Menu menu;
	public static final String PREF_FILE_NAME = "PrefFile";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.frag_my_profile, container, false);
		setHasOptionsMenu(true);
		
		my_profile=(ImageView)rootView.findViewById(R.id.my_profile);
		
		fname = (EditText)rootView.findViewById(R.id.txt_fn);
		uname = (TextView)rootView.findViewById(R.id.txt_un);
		phno = (EditText)rootView.findViewById(R.id.txt_ph);
		email = (EditText)rootView.findViewById(R.id.txt_em);
		city = (EditText)rootView.findViewById(R.id.txt_city);
		
		prf_rgender = (RadioGroup)rootView.findViewById(R.id.prf_radio_group_gender);
		prf_rbmale = (RadioButton)rootView.findViewById(R.id.prf_rbtn_male);
		prf_rbfemale = (RadioButton)rootView.findViewById(R.id.prf_rbtn_female);
		
		prf_rgender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (checkedId == R.id.prf_rbtn_male) 
				{
					prf_gen="Male";
				} 
				else if (checkedId == R.id.prf_rbtn_female) 
				{
					prf_gen="Female";
				}
			}
		});
		
		
		spr = getActivity().getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
		Constants.PARAM_USERNAME=spr.getString("u_uname", null);
		
		MyProfileDetails();
		
		AQuery imgaq = new AQuery(getActivity());
		imgaq.id(my_profile).image(Constants.URL_UPLOADS+ Constants.PARAM_USERNAME+".jpg",true, true, 0, R.drawable.profile, null, com.androidquery.util.Constants.FADE_IN_NETWORK);
		Log.e("my_img", Constants.PARAM_USERNAME+".jpg");
		
		return rootView;
	}
	
	private void MyProfileDetails() {
		// TODO Auto-generated method stub
		new AsyncHelper().execute();
	}

	public class AsyncHelper extends AsyncTask<Void, Void, Void>
	{
		String message;
		ProgressDialog pd=new ProgressDialog(getActivity());
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

			ServiceHandler sh = new ServiceHandler();

			String result = sh.makeServiceCall(Constants.URL_MY_PROFILE, nameValuePair);
			Log.e("value:", "" +nameValuePair);
			Log.e("result:", "" +result);

			try{
				JSONObject jo=new JSONObject(result);
				JSONObject jdata=jo.getJSONObject("data");
				message=jdata.getString("message");
				status=jdata.getInt("status");
				
				Constants.FNAME=jdata.getString("firstname");
				Constants.USERNAME=jdata.getString("username");
				Constants.GENDER=jdata.getString("gender");
				Constants.PHONE=jdata.getString("phone");
				Constants.EMAIL=jdata.getString("email");
				Constants.CITY=jdata.getString("city");
								
				Log.e("my_prof_details", Constants.FNAME+"\n"+Constants.LNAME+"\n"+Constants.USERNAME+"\n"+Constants.DOB+"\n"
				+Constants.PHONE+"\n"+Constants.EMAIL+"\n"+Constants.ADDR+"\n"+Constants.CITY);

			}catch(Exception e){

			}

			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			pd.dismiss();

			Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
			if(status==1)
			{
				fname.setText(Constants.FNAME);
				uname.setText(Constants.USERNAME);
				phno.setText(Constants.PHONE);
				email.setText(Constants.EMAIL);
				city.setText(Constants.CITY);

//				Log.e("gender", Constants.GENDER);
				if(Constants.GENDER.equals("Male"))
					prf_rbmale.setChecked(true);
				else if(Constants.GENDER.equals("Female"))
					prf_rbfemale.setChecked(true);
			}
		}
	}
	
	private void MyUpdateprofile() {
		// TODO Auto-generated method stub
		new AsyncHelper2().execute();
	}

	public class AsyncHelper2 extends AsyncTask<Void, Void, Void>
	{
		String message;
		ProgressDialog pd=new ProgressDialog(getActivity());
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
			nameValuePair.add(new BasicNameValuePair("un", Constants.PARAM_USERNAME));
			nameValuePair.add(new BasicNameValuePair("firstname", fname.getText().toString()));
			nameValuePair.add(new BasicNameValuePair("gender", prf_gen));
			nameValuePair.add(new BasicNameValuePair("email", email.getText().toString()));
			nameValuePair.add(new BasicNameValuePair("phone", phno.getText().toString()));
			nameValuePair.add(new BasicNameValuePair("country", city.getText().toString()));

			ServiceHandler sh = new ServiceHandler();
			
			String result = sh.makeServiceCall(Constants.URL_UPDATEPROFILE, nameValuePair);
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
			Toast.makeText(getActivity(), "Updated Successfully", Toast.LENGTH_LONG).show();
			if(status==1){
				
			}
/*			else
				Toast.makeText(getActivity(), "Change to update", Toast.LENGTH_LONG).show();*/
		}
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// Inflate the menu; this adds items to the action bar if it is present.
		inflater.inflate(R.menu.fragment_my_profile, menu);
		Log.e("df", "gdfvdfdf");
		this.menu = menu;
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_done) {
//			editprofile();
			Boolean status=true;

			String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+"[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

			if(fname.getText().toString().length()==0){
				fname.setError("Enter your First Name");	
				status=false;
			}

			if(prf_gen.length()==0){
				Toast.makeText(getActivity(), "Select your Gender", Toast.LENGTH_SHORT).show();
				status=false;
			}

			if(email.length()!=0)
			{
				if (!email.getText().toString().matches(emailPattern))
				{
					email.setError("Enter your valid Email Address");
					status=false;
				}
			}

			if(phno.getText().toString().length()!=10){
				phno.setError("Enter your valid Mobile Number");	
				status=false;
			}
			if(city.getText().toString().length()==0){
				city.setError("Enter your Country");	
				status=false;
			}
			
			if (status == true) {

				MyUpdateprofile();
				
			}
			
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
