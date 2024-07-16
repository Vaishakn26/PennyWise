package info.accolade.trip_master;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.androidquery.AQuery;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

import info.accolade.trip_master.utils.Constants;
import info.accolade.trip_master.utils.GetLocation;
import info.accolade.trip_master.utils.ServiceHandler;

public class HotelDetails extends AppCompatActivity {

	Toolbar toolbar;
	Button view_review;
	TextView hname, hdesc, hprc, hmap, haddr, hphone, hemail;
	ImageView himage;

	GetLocation getlocation;
	Double lat, lng;
	Location user_loc, hotel_loc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hotel_details);

		toolbar = (Toolbar) findViewById(R.id.toolbar);
		toolbar.setTitle("Hotel Details");
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

		hname = (TextView)findViewById(R.id.txt_h_d_name);
		hdesc = (TextView)findViewById(R.id.txt_h_d_desc);
		hprc = (TextView)findViewById(R.id.txt_h_d_price);
		hmap = (TextView)findViewById(R.id.txt_h_d_map);
		haddr = (TextView)findViewById(R.id.txt_h_d_addr);
		hphone = (TextView)findViewById(R.id.txt_h_d_phone);
		hemail = (TextView)findViewById(R.id.txt_h_d_email);

		himage = (ImageView)findViewById(R.id.h_d_img);

		GetHotelDetails();

		view_review = (Button)findViewById(R.id.btn_h_d_reviews);

		view_review.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent review = new Intent(HotelDetails.this, HotelReviews.class);
				startActivity(review);
			}
		});

		getlocation = new GetLocation(HotelDetails.this);

		LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		String provider = locationManager.getBestProvider(criteria, true);
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
		user_loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
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
		hotel_loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

		lat = getlocation.getLatitude();
		lng = getlocation.getLongitude();
//		loc = getlocation.getAddress(lat,lng);
		Log.e("ll", lat+lng+"");
		
		user_loc.setLatitude(lat);
		user_loc.setLongitude(lng);
        
		hotel_loc.setLatitude(Double.parseDouble(Constants.HOTEL_LATI));
		hotel_loc.setLongitude(Double.parseDouble(Constants.HOTEL_LONGI));

		

		
	}
	
	
	private void GetHotelDetails() {
		Log.e("hi", "ji");
		// TODO Auto-generated method stub
		new AsyncHelper1().execute();
	}

	public class AsyncHelper1 extends AsyncTask<Void, Void, Void>
	{
		String message,h_name,h_desc,h_dis,h_prc,h_map,h_addr,h_phone,h_email,h_image;
		ProgressDialog pd=new ProgressDialog(HotelDetails.this);
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
			nameValuePair.add(new BasicNameValuePair("hotel_id", Constants.HOTEL_ID));


			ServiceHandler sh = new ServiceHandler();

			String result = sh.makeServiceCall(Constants.URL_HOTELDETAILS, nameValuePair);
			Log.e("values:", "" +nameValuePair);
			Log.e("results:", "" +result);

			try{
				JSONObject jo=new JSONObject(result);
				JSONObject jdata=jo.getJSONObject("data");
				message=jdata.getString("message");
				status=jdata.getInt("status");
				
				h_name = jdata.getString("name");
				h_desc = jdata.getString("desc");
//				h_dis = jdata.getString("dist");
				h_prc = jdata.getString("price");
				h_map = jdata.getString("map");
				h_addr = jdata.getString("addr");
				h_phone = jdata.getString("phone");
				h_email = jdata.getString("email");
				
				h_image = jdata.getString("image");

			}
			catch(Exception e){

			}

			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			pd.dismiss();

			Toast.makeText(HotelDetails.this, message, Toast.LENGTH_LONG).show();
			if(status==1)
			{
				hname.setText(h_name);
				hdesc.setText(h_desc);
				hprc.setText(h_prc);
				hmap.setText("http://maps.google.com/?q="+Constants.HOTEL_LATI+","+Constants.HOTEL_LONGI);
				haddr.setText(h_addr);
				hphone.setText(h_phone);
				hemail.setText(h_email);
				
				AQuery imgaq = new AQuery(HotelDetails.this);
				imgaq.id(himage).image(Constants.URL_Hotel_image+h_image, true, true, 0, R.drawable.hotel_fade, null, com.androidquery.util.Constants.FADE_IN_NETWORK, 0.0f);

			}
		}
	}
	
	

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.hotel_details, menu);
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
