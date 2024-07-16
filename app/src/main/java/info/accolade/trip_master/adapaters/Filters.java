package info.accolade.trip_master.adapaters;

import android.text.InputFilter;
import android.text.Spanned;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Filters {

	public InputFilter namefilter = new InputFilter(){

		@Override
		public CharSequence filter(CharSequence source, int start, int end,
				Spanned dest, int dstart, int dend) {
			// TODO Auto-generated method stub
			for(int i=start;i<end;i++){
				String checkname = String.valueOf(source.charAt(i));
				Pattern pname = Pattern.compile("[a-zA-Z]+");
				Matcher mname = pname.matcher(checkname);
				boolean valid = mname.matches();
				if(!valid){
//					Log.d("", "Invalid");
					return "";
				}
			}
			return null;
		}
		
	};
	
	
	public InputFilter numfilter = new InputFilter(){

		@Override
		public CharSequence filter(CharSequence source, int start, int end,
				Spanned dest, int dstart, int dend) {
			// TODO Auto-generated method stub
			for(int i=start;i<end;i++){
				String checkid = String.valueOf(source.charAt(i));
				Pattern pid = Pattern.compile("[+0-9]+");
				Matcher mid = pid.matcher(checkid);
				boolean valid = mid.matches();
				if(!valid){
//					Log.d("", "Invalid");
					return "";
				}
			}
			return null;
		}
		
	};
	
}
