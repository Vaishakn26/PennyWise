package info.accolade.trip_master;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.androidquery.AQuery;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

import info.accolade.trip_master.utils.Constants;
import info.accolade.trip_master.utils.ServiceHandler;

public class DestinationDetails extends AppCompatActivity {
	
	Toolbar toolbar;
	ImageView dest_img;
	TextView dest_name, dest_descp, dest_map, dest_addr, dest_phone;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_destination_details);
		
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		toolbar.setTitle(Constants.TOOLBAR_TITLE);
		toolbar.setTitleTextColor(0xFFFFFFFF);
		
		dest_img = (ImageView)findViewById(R.id.destin_img);
		
		dest_name = (TextView)findViewById(R.id.txt_dest_name);
		dest_descp = (TextView)findViewById(R.id.txt_dest_descp);
		dest_map = (TextView)findViewById(R.id.txt_dest_map);
		dest_addr = (TextView)findViewById(R.id.txt_dest_addr);
//		dest_phone = (TextView)findViewById(R.id.txt_dest_phone);
		
		GetDestinationDetails();
		
	}
	
	private void GetDestinationDetails() {
		// TODO Auto-generated method stub
		new AsyncHelper().execute();
	}

	public class AsyncHelper extends AsyncTask<Void, Void, Void>
	{
		String message, place_name, place_desc, place_map, place_addr, place_image, place_phone;
		ProgressDialog pd=new ProgressDialog(DestinationDetails.this);
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
			nameValuePair.add(new BasicNameValuePair("des_id", Constants.DEST_ID));


			ServiceHandler sh = new ServiceHandler();

			String result = sh.makeServiceCall(Constants.URL_DESTINATIONDETAILS, nameValuePair);
			Log.e("value:", "" +nameValuePair);
			Log.e("result:", "" +result);

			try{
				JSONObject jo=new JSONObject(result);
				JSONObject jdata=jo.getJSONObject("data");
				message=jdata.getString("message");
				status=jdata.getInt("status");
				
				place_image = jdata.getString("des_image");
				place_name = jdata.getString("des_name");
				place_desc = jdata.getString("des_desc");
				place_map = jdata.getString("des_map");
				place_addr = jdata.getString("des_addr");
//				place_phone = jdata.getString("des_phone");

			}catch(Exception e){

			}

			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			pd.dismiss();

			Toast.makeText(DestinationDetails.this, message, Toast.LENGTH_LONG).show();
			if(status==1)
			{
				AQuery imgaq = new AQuery(DestinationDetails.this);
				imgaq.id(dest_img).image(Constants.URL_Destination_image+ place_image, true, true, 0, R.drawable.destination_fade, null, com.androidquery.util.Constants.FADE_IN_NETWORK);
				
				dest_name.setText(place_name);
				dest_descp.setText(place_desc);
				dest_map.setText("http://maps.google.com/?q="+Constants.DEST_LATI+","+Constants.DEST_LONGI);
				dest_addr.setText(place_addr);
//				dest_phone.setText(place_phone);
			}
		}
	}
	
	
	
	

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.destination_details, menu);
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
