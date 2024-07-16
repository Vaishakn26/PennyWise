package info.accolade.trip_master.adapaters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.androidquery.AQuery;

import java.util.ArrayList;
import java.util.HashMap;

import info.accolade.trip_master.R;
import info.accolade.trip_master.utils.Constants;

public class UserReviewAdapter extends BaseAdapter{
	
	ArrayList<HashMap<String,String>> items;
    Context ctxt;
    private static LayoutInflater inflater=null;
	
	public UserReviewAdapter(ArrayList<HashMap<String, String>> itemList, Context c) {
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
	TextView tv_user_review_name, tv_user_review_sub, tv_user_review_com, tv_user_review_date,tv_user_review_cat;
	RatingBar rb_user_rate;
	ImageView img_users;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Holder holder;
		View view = convertView;
		if(convertView==null)
		{
		view = inflater.inflate(R.layout.list_user_reviews, null);
		holder=new Holder();
		holder.tv_user_review_name=(TextView) view.findViewById(R.id.user_review_name);
		holder.tv_user_review_cat=(TextView) view.findViewById(R.id.user_review_category);
		holder.tv_user_review_sub=(TextView) view.findViewById(R.id.user_review_subject);
		holder.tv_user_review_com=(TextView) view.findViewById(R.id.user_review_comment);
		holder.tv_user_review_date=(TextView) view.findViewById(R.id.user_review_date);
		holder.rb_user_rate=(RatingBar) view.findViewById(R.id.user_review_ratingBar);
		holder.img_users=(ImageView) view.findViewById(R.id.user_review_image);
		view.setTag(holder); 
		}
		else
		
		holder=(Holder)view.getTag();
		holder.tv_user_review_name.setText(items.get(position).get("urev_user_name"));
		holder.tv_user_review_cat.setText(items.get(position).get("urev_user_cat"));
		holder.tv_user_review_sub.setText(items.get(position).get("urev_user_sub"));
		holder.tv_user_review_com.setText(items.get(position).get("urev_user_comments"));
		holder.tv_user_review_date.setText(items.get(position).get("urev_user_date"));
		holder.rb_user_rate.setRating(Float.parseFloat(items.get(position).get("urev_rate_value")));

		AQuery imgaq = new AQuery(ctxt);
		imgaq.id(holder.img_users).image(Constants.URL_UPLOADS+ items.get(position).get("urev_user_name")+".jpg",true, true, 0, R.drawable.profile, null, com.androidquery.util.Constants.FADE_IN_NETWORK);
		
		return view;
	}

}
