package info.accolade.trip_master.adapaters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.androidquery.AQuery;

import java.util.ArrayList;
import java.util.HashMap;

import info.accolade.trip_master.HotelDetails;
import info.accolade.trip_master.R;
import info.accolade.trip_master.utils.Constants;

public class HotelAdapter extends BaseAdapter{
	
	ArrayList<HashMap<String,String>> items;
    Context ctxt;
    private static LayoutInflater inflater=null;
	
	public HotelAdapter(ArrayList<HashMap<String, String>> itemList, Context c) {
		// TODO Auto-generated constructor stub
		
		items = itemList;
        ctxt = c;
        inflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public class Holder
	{
	TextView tv_hotel_name, tv_readmore;
	ImageView img_hotel;
	CardView cv_hotel;
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Holder holder;
		View view = convertView;
		if(convertView==null)
		{
		view = inflater.inflate(R.layout.list_hotel, null);
		holder=new Holder();
		holder.tv_hotel_name=(TextView) view.findViewById(R.id.hotel_name);
		holder.tv_readmore=(TextView) view.findViewById(R.id.read_more);
		holder.img_hotel=(ImageView) view.findViewById(R.id.hotel_image);
		holder.cv_hotel=(CardView) view.findViewById(R.id.hotel_card_view);
		view.setTag(holder); 
		}
		else
		
		holder=(Holder)view.getTag();
		
		holder.tv_hotel_name.setText(items.get(position).get("hotel_name"));
		AQuery imgaq = new AQuery(ctxt);
		imgaq.id(holder.img_hotel).image(Constants.URL_Hotel_image+items.get(position).get("hotel_image"), true, true, 0, R.drawable.hotel_fade, null, com.androidquery.util.Constants.FADE_IN_NETWORK, 0.0f);

		
		holder.tv_readmore.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Constants.HOTEL_ID=items.get(position).get("hotel_id");
				Constants.HOTEL_LATI=items.get(position).get("hotel_lati");
				Constants.HOTEL_LONGI=items.get(position).get("hotel_longi");
				Log.e("hid", Constants.HOTEL_ID+","+Constants.HOTEL_LATI+","+Constants.HOTEL_LONGI);

				Intent h = new Intent(ctxt, HotelDetails.class);
				ctxt.startActivity(h);
			}
		});
		
		return view;
	}

}
