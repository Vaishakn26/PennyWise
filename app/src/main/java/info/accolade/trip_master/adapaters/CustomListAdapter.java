package info.accolade.trip_master.adapaters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;

import java.util.ArrayList;
import java.util.HashMap;

import info.accolade.trip_master.Map;
import info.accolade.trip_master.MapsActivity;
import info.accolade.trip_master.R;
import info.accolade.trip_master.utils.Constants;

public class CustomListAdapter extends BaseAdapter {
    ArrayList<HashMap<String, String>> data=new ArrayList<HashMap<String,String>>();
    Activity activity;
    LayoutInflater inflater;


    public CustomListAdapter(Activity activity, ArrayList<HashMap<String, String>> data) {
        // TODO Auto-generated constructor stub
        this.activity=activity;
        this.data=data;

        inflater=(LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
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

    @Override
    public View getView(int position, View view, ViewGroup container) {
        TextView t1,t2;
        ImageView i, i2, i3;
        String imgurl;
        String type;
        final String lat;
        final String lng;
        final String name;
        if(view==null){
            view=inflater.inflate(R.layout.bud_list, container, false);
        }


        t1=(TextView)view.findViewById(R.id.name);
        t2=(TextView)view.findViewById(R.id.loc);


        i=(ImageView)view.findViewById(R.id.pic);
        i2=(ImageView)view.findViewById(R.id.pic2);
        i3=(ImageView)view.findViewById(R.id.pic3);

        imgurl=data.get(position).get("image");
        type=data.get(position).get("type");
        lat= data.get(position).get("lat");
        lng= data.get(position).get("long");

        Log.e("result:", ""+lat+lng);
        Log.e("image:", ""+Constants.URL_UPLOADS+imgurl);

        AQuery imgaq = new AQuery(activity);
        imgaq.id(i).image(Constants.URL_UPLOADS+imgurl,false, false, 0, R.drawable.ic_launcher1,  null,com.androidquery.util.Constants.FADE_IN_NETWORK);
        Log.e("id:",""+imgurl);


        name=data.get(position).get("name");

        t1.setText( data.get(position).get("name"));


        t2.setText( data.get(position).get("loc"));


        i2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent i=new Intent((Activity)activity, MapsActivity.class);
                i.putExtra("lat", lat);
                i.putExtra("long", lng);
                i.putExtra("name", name);
                activity.startActivity(i);
            }
        });

        i3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                Uri gmmIntentUri = Uri.parse("google.navigation:q=" + lat + "," + lng);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");

                if (mapIntent.resolveActivity(activity.getPackageManager()) != null) {
                    activity.startActivity(mapIntent);
                }
            }
        });



        return view;
    }



}
