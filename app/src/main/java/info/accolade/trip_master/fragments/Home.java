package info.accolade.trip_master.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;


import androidx.fragment.app.Fragment;

import com.bumptech.glide.request.RequestOptions;
import com.glide.slider.library.SliderLayout;
import com.glide.slider.library.animations.DescriptionAnimation;
import com.glide.slider.library.slidertypes.BaseSliderView;
import com.glide.slider.library.slidertypes.DefaultSliderView;
import com.glide.slider.library.tricks.ViewPagerEx;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import info.accolade.trip_master.Destination;
import info.accolade.trip_master.Hotel;
import info.accolade.trip_master.R;
import info.accolade.trip_master.User_Reviews;
import info.accolade.trip_master.Weather;
import info.accolade.trip_master.adapaters.CustomGridView;
import info.accolade.trip_master.utils.Constants;

public class Home extends Fragment implements BaseSliderView.OnSliderClickListener,
		ViewPagerEx.OnPageChangeListener{
	private SliderLayout mDemoSlider;
	int currentPage=0;
	SharedPreferences spr;
	public static final String PREF_FILE_NAME = "PrefFile";

	private static final int[] IMAGES= {R.drawable.img_4,R.drawable.img_1,R.drawable.img_2,R.drawable.img_3};

	String[] gridViewString = {"Hotels" , "Weathers" , "Destinations" , "User Reviews"};			
	int[] gridViewImageId = {R.drawable.hotels, R.drawable.weather ,R.drawable.dest, R.drawable.user_reviews };
			
	GridView gridview;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.frag_home, container, false);
		mDemoSlider = rootView.findViewById(R.id.slider);
		
		spr = getActivity().getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
		Constants.PARAM_USERNAME=spr.getString("u_uname", null);
		
		CustomGridView adaptergrid = new CustomGridView(getActivity(), gridViewString, gridViewImageId);
		gridview = (GridView)rootView.findViewById(R.id.grid_view_image_text);
		gridview.setAdapter(adaptergrid);
		
		Log.e("home_udetails", Constants.PARAM_USERNAME);
		
		gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				switch (position) {
				case 0:
					Intent hotel = new Intent(getContext(), Hotel.class);
					startActivity(hotel);					
					break;
					
				case 1:
					Intent weather = new Intent(getActivity(), Weather.class);
					startActivity(weather);
					break;
					
				case 2:
					Intent destination = new Intent(getActivity(), Destination.class);
					startActivity(destination);
					break;
					
				case 3:
					Intent user_reviews = new Intent(getActivity(), User_Reviews.class);
					startActivity(user_reviews);

				default:
					break;
				}
			}
		});
		
		final Handler handler = new Handler();
		final Runnable Update = new Runnable() {
			@Override
			public void run() {
				if (currentPage == 4) {
					currentPage = 0;
				}
			}
		};

		Timer swipeTimer = new Timer();
		swipeTimer.schedule(new TimerTask() {

			@Override
			public void run() {
				handler.post(Update);
			}
		}, 4000,4000);

		final float density = getResources().getDisplayMetrics().density;


		//        slider start
		ArrayList<Integer> listUrl = new ArrayList<>();

		listUrl.add(R.drawable._1image);
		listUrl.add(R.drawable._2image);
		listUrl.add(R.drawable._3image);
		listUrl.add(R.drawable._4image);

		RequestOptions requestOptions = new RequestOptions();
		requestOptions.centerCrop();

		for (int i = 0; i < listUrl.size(); i++) {
			DefaultSliderView sliderView = new DefaultSliderView(getActivity());

			sliderView
					.image(listUrl.get(i))
					.setRequestOption(requestOptions)
					.setProgressBarVisible(true)
					.setOnSliderClickListener(this);

			mDemoSlider.addSlider(sliderView);
		}

		mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Background2Foreground);
		mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
		mDemoSlider.setCustomAnimation(new DescriptionAnimation());
		mDemoSlider.setDuration(4000);
		mDemoSlider.addOnPageChangeListener(this);
		mDemoSlider.stopCyclingWhenTouch(false);
		return rootView;
	}

	@Override
	public void onSliderClick(BaseSliderView slider) {

	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

	}

	@Override
	public void onPageSelected(int position) {

	}

	@Override
	public void onPageScrollStateChanged(int state) {

	}
}
