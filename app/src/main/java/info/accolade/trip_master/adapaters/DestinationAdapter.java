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

import info.accolade.trip_master.DestinationDetails;
import info.accolade.trip_master.R;
import info.accolade.trip_master.utils.Constants;

public class DestinationAdapter extends BaseAdapter{
	
	ArrayList<HashMap<String,String>> items;
    Context ctxt;
    private static LayoutInflater inflater=null;
    
    public DestinationAdapter(ArrayList<HashMap<String, String>> itemList, Context c) {
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
	TextView tv_dest_name;
	ImageView img_dest;
	CardView cv_dest;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Holder holder;
		View view = convertView;
		if(convertView==null)
		{
		view = inflater.inflate(R.layout.list_destination, null);
		holder=new Holder();
		holder.tv_dest_name=(TextView) view.findViewById(R.id.dest_name);
		holder.img_dest=(ImageView) view.findViewById(R.id.dest_image);
		holder.cv_dest=(CardView) view.findViewById(R.id.dest_card_view);
		view.setTag(holder); 
		}
		else
		
		holder=(Holder)view.getTag();
		
		holder.tv_dest_name.setText(items.get(position).get("dest_name"));
		AQuery imgaq = new AQuery(ctxt);
		imgaq.id(holder.img_dest).image(Constants.URL_Destination_image+items.get(position).get("dest_image"), true, true, 0, R.drawable.destination_fade, null, com.androidquery.util.Constants.FADE_IN_NETWORK, 0.0f);
		
		holder.cv_dest.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent h = new Intent(ctxt, DestinationDetails.class);
				ctxt.startActivity(h);
				Constants.DEST_ID=items.get(position).get("dest_id");
				Constants.DEST_LATI=items.get(position).get("dest_lati");
				Constants.DEST_LONGI=items.get(position).get("dest_longi");
				Log.e("d_id", Constants.DEST_ID+","+Constants.DEST_LATI+","+Constants.DEST_LONGI);
			}
		});
		
		return view;
	}

}
