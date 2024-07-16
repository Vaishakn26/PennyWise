package info.accolade.trip_master.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import info.accolade.trip_master.R;

public class ImagesDisplay extends Fragment {
	
	private static final String KEY_CONTENT = "TestFragment:Content";
	int position;
	public static String[] desc;
	public static int[] image;

	public ImagesDisplay(int position, int[] image, String[]desc) {
		this.position=position;
		ImagesDisplay.image=image;
		ImagesDisplay.desc=desc;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if ((savedInstanceState != null) && savedInstanceState.containsKey(KEY_CONTENT)) {
			position = savedInstanceState.getInt(KEY_CONTENT);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		ViewGroup root = (ViewGroup) inflater.inflate(R.layout.image_display, null);

		ImageView mNewsImage=(ImageView)root.findViewById(R.id.img);
		mNewsImage.setBackgroundResource(image[position]);

/*		WebView mNewsDesc=(WebView)root.findViewById(R.id.web);
		WebSettings webset0 = mNewsDesc.getSettings();
		webset0.setJavaScriptEnabled(true);
		webset0.setDefaultFontSize(16);
		mNewsDesc.setBackgroundColor(Color.parseColor("#ffab91"));
		mNewsDesc.loadData(desc[position], "text/html", "utf-8");*/

		return root;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(KEY_CONTENT, position);
	}

}
