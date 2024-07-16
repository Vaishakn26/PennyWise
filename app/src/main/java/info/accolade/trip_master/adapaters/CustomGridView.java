package info.accolade.trip_master.adapaters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import info.accolade.trip_master.R;

public class CustomGridView extends BaseAdapter{
	
	private Context mContext;
    private final String[] gridViewString;
    private final int[] gridViewImageId;
    private static LayoutInflater inflater=null;

    public CustomGridView(Context context, String[] gridViewString, int[] gridViewImageId) {
    	Log.e("custom", "gridview");
        mContext = context;
        this.gridViewImageId = gridViewImageId;
        this.gridViewString = gridViewString;
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return gridViewString.length;
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
		TextView tv;
		ImageView img;
	}

	@Override
	public View getView(int i, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Holder holder=new Holder();
		View rowView;
//        LayoutInflater inflater = (LayoutInflater) mContext
//                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

      //  if (convertView == null) {
    //        gridViewAndroid = new View(mContext);
        	rowView = inflater.inflate(R.layout.gridview_layout, null);
            holder.tv = (TextView) rowView.findViewById(R.id.gridview_text);
            holder.img = (ImageView) rowView.findViewById(R.id.gridview_image);
            holder.tv.setText(gridViewString[i]);
            holder.img.setImageResource(gridViewImageId[i]);
//      } 
//        else 
//        {
//            gridViewAndroid = (View) convertView;
//        }

        return rowView;	
	}
}
