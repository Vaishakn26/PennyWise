package info.accolade.trip_master;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import info.accolade.trip_master.utils.Constants;


public class Destination extends AppCompatActivity {

	Button category, city;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_destination);

		category = (Button)findViewById(R.id.btn_category);
		city = (Button)findViewById(R.id.btn_city);

/*		category.setOnClickListener(this);
		city.setOnClickListener(this);*/

		category.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Constants.is_city = 0;
				Intent cat = new Intent(Destination.this, Category.class);
				startActivity(cat);
				Log.e("destin", "cat click "+ Constants.is_city);
			}
		});

		city.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Constants.is_city = 1;
				Intent city = new Intent(Destination.this, City.class);
				startActivity(city);
				Log.e("destin", "city click "+ Constants.is_city);
			}
		});


	}

/*	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_category:
			Intent cat = new Intent(Destination.this,Category.class);
			startActivity(cat);
			Log.e("destin", "cat click");

		case R.id.btn_city:
			Intent city = new Intent(Destination.this,City.class);
			startActivity(city);
			Log.e("destin", "city click");

		}
	}*/

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.destination, menu);
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
