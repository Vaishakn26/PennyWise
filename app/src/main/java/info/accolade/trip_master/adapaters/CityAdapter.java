package info.accolade.trip_master.adapaters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.androidquery.AQuery;

import java.util.ArrayList;
import java.util.HashMap;

import info.accolade.trip_master.Category;
import info.accolade.trip_master.R;
import info.accolade.trip_master.utils.Constants;

public class CityAdapter extends BaseAdapter {

    ArrayList<HashMap<String,String>> items;
    Context ctxt;
    private static LayoutInflater inflater=null;

    public CityAdapter(ArrayList<HashMap<String, String>> itemList, Context c) {
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
        TextView tv_city_name;
        CardView cv_city;
        ImageView img_city;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        Holder holder;
        View view = convertView;
        if(convertView==null)
        {
            view = inflater.inflate(R.layout.list_city, null);
            holder=new Holder();
            holder.tv_city_name=(TextView) view.findViewById(R.id.city_name);
            holder.cv_city=(CardView) view.findViewById(R.id.city_card_view);
            holder.img_city=(ImageView) view.findViewById(R.id.city_image);
            view.setTag(holder);
        }
        else

            holder=(Holder)view.getTag();

        holder.tv_city_name.setText(items.get(position).get("city_name"));
        AQuery imgaq = new AQuery(ctxt);
        imgaq.id(holder.img_city).image(Constants.URL_City_image+items.get(position).get("city_image"), true, true, 0, R.drawable.city_fade, null, com.androidquery.util.Constants.FADE_IN_NETWORK, 0.0f);


        holder.cv_city.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent h = new Intent(ctxt, Category.class);
                ctxt.startActivity(h);
                Constants.CITY_ID=items.get(position).get("city_id");
                Constants.CITY_LATI=items.get(position).get("city_lat");
                Constants.CITY_LONGI=items.get(position).get("city_lng");
                Log.e("c_info", Constants.CITY_ID+" "+Constants.CITY_LATI+" "+Constants.CITY_LONGI);

            }
        });

        return view;
    }

}
