package info.accolade.trip_master;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import info.accolade.trip_master.adapaters.CustomGridView;
import info.accolade.trip_master.utils.Constants;

public class Category extends AppCompatActivity {
	
	Toolbar toolbar;
	
	String[] gridViewString = {"Tourist Place","Hospitals","Shopping Malls","Police Station",
			"ATM","Clubs","Library","Amusement Parks","Beaches","Adventure","Airport","Railway Station","Trekking","Scuba Diving"};
	int[] gridViewImageId = {R.drawable.tourr, R.drawable.hospital ,R.drawable.shop,
			R.drawable.police, R.drawable.atmicon,
			R.drawable.club, R.drawable.library,
			R.drawable.amusementpark, R.drawable.beach, 
			R.drawable.adventure,R.drawable.airplane,R.drawable.railway,
			R.drawable.trekking,R.drawable.scubadive };
	GridView GridView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_category);
		
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		toolbar.setTitle("By Category");
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
		
		Log.e("category", "grid");
		
		CustomGridView adaptergrid = new CustomGridView(Category.this, gridViewString, gridViewImageId);
		GridView = (GridView)findViewById(R.id.grid_view_image_text);
		GridView.setAdapter(adaptergrid);
		
		GridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				switch (position) {
				case 0:
					Intent tour = new Intent(Category.this,Tourist.class);
					Constants.CATEGORY_ID = 1;
					Constants.TOOLBAR_TITLE="Tourist Place Details";
					Log.e("cat_id", String.valueOf(Constants.CATEGORY_ID));
					startActivity(tour);					
					break;
					
				case 1:
					Intent hospi = new Intent(Category.this, Route.class);
					hospi.putExtra("value", "hospital");                      
					Constants.TOOLBAR_TITLE="Hospitals";
					startActivity(hospi);					
					break;
					
				case 2:
					Intent shop = new Intent(Category.this, Route.class);
					shop.putExtra("value", "shopping_mall");               //
					Constants.TOOLBAR_TITLE="Shopping Mall";
					startActivity(shop);					
					break;
					
				case 3:
					Intent police = new Intent(Category.this, Route.class);
					police.putExtra("value", "police");                        
					Constants.TOOLBAR_TITLE="Police Stations";
					startActivity(police);					
					break;
					
				case 4:
					Intent atm = new Intent(Category.this, Route.class);
					atm.putExtra("value", "atm");                           //
					Constants.TOOLBAR_TITLE="ATM";
					startActivity(atm);					
					break;
					
				case 5:
					Intent club = new Intent(Category.this, Route.class);
					club.putExtra("value", "club");                            
					Constants.TOOLBAR_TITLE="Clubs";
					startActivity(club);					
					break;
					
				case 6:
					Intent libr = new Intent(Category.this, Route.class);
					libr.putExtra("value", "library");                            
					Constants.TOOLBAR_TITLE="Library";
					startActivity(libr);					
					break;
					
				case 7:
					Intent park = new Intent(Category.this, Park.class);
					Constants.CATEGORY_ID = 2;
					Constants.TOOLBAR_TITLE="Amusement Parks Details";
					startActivity(park);					
					break;
					
				case 8:
					Intent beach = new Intent(Category.this, Beach.class);
					Constants.CATEGORY_ID = 4;
					Constants.TOOLBAR_TITLE="Beach Details";
					startActivity(beach);					
					break;
					
				case 9:
					Intent adven = new Intent(Category.this, Adventure.class);
					Constants.CATEGORY_ID = 3;
					Constants.TOOLBAR_TITLE="Adventure Details";
					startActivity(adven);					
					break;

				case 10:
					Intent airport = new Intent(Category.this, Route.class);
					airport.putExtra("value", "airport");                      
					Constants.TOOLBAR_TITLE="Airport";
					startActivity(airport);					
					break;
				case 11:
					Intent railway = new Intent(Category.this, Route.class);
					railway.putExtra("value", "train_station");                      
					Constants.TOOLBAR_TITLE="Railway Station";
					startActivity(railway);					
					break;
					
				case 12:
					Intent trekking = new Intent(Category.this,Trekking.class);
					Constants.CATEGORY_ID = 5;
					Constants.TOOLBAR_TITLE="Trekking";
					startActivity(trekking);					
					break;
				case 13:
					Intent diving = new Intent(Category.this,ScubaDiving.class);
					Constants.CATEGORY_ID = 6;
					Constants.TOOLBAR_TITLE="Scuba Diving";
					startActivity(diving);			
					break;

				default:
					break;
				}
			}
		});
		
	}
}
