package info.accolade.trip_master.adapaters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import info.accolade.trip_master.R;

public class CustomListAdapter2 extends BaseAdapter {
    ArrayList<HashMap<String, String>> data=new ArrayList<HashMap<String,String>>();
    Activity activity;
    LayoutInflater inflater;


    public CustomListAdapter2(Activity activity, ArrayList<HashMap<String, String>> data) {
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
        TextView t1,t2,t3;

        if(view==null){
            view=inflater.inflate(R.layout.foodlist, container, false);
        }


        t1=(TextView)view.findViewById(R.id.item);
        t2=(TextView)view.findViewById(R.id.type);
        t3=(TextView)view.findViewById(R.id.price);

        if(data.get(position).get("type").isEmpty()){
            t2.setVisibility(View.GONE);
        }

        t1.setText( "Item Name: "+data.get(position).get("name"));
        t2.setText( "Type: "+data.get(position).get("type"));
        t3.setText( "Price: "+data.get(position).get("price")+" /-");


        return view;
    }

}
