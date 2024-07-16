package info.accolade.trip_master.fragments;

import android.app.AlertDialog;
import androidx.fragment.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import info.accolade.trip_master.R;
import info.accolade.trip_master.utils.Constants;
import info.accolade.trip_master.utils.ServiceHandler;

public class Settings extends Fragment {
	
	Button change;
	String old_pass, new_pass, new_cnpass;
	AlertDialog alert;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.frag_settings, container, false);
		
		change = (Button)rootView.findViewById(R.id.btn_change_pass);
		
		Log.e("sett_unmae", Constants.PARAM_USERNAME);
		
		change.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showAlert();
			}
		});
		
		return rootView;
	}
	
	protected void showAlert() {
		
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
		alertDialog.setTitle("Change Password");
		alertDialog.setMessage("Do you want to change your password ?");
		alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {				
				showInputdialog();				
			}
		});
		
		alertDialog.setNegativeButton("No",new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});		
		alertDialog.show();
	}
	
	protected void showInputdialog(){
		LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
		final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
		View promptView = layoutInflater.inflate(R.layout.change_password, null);
		alertDialogBuilder.setView(promptView);
		final EditText oldpassword = (EditText) promptView.findViewById(R.id.old_pswd);
		final EditText newpassword = (EditText) promptView.findViewById(R.id.pswd);
		final EditText new_cnpassword = (EditText) promptView.findViewById(R.id.cn_pswd);
		
		alertDialogBuilder.setCancelable(false)
		.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {

			}
		})
		.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});
		// create an alert dialog
		alert = alertDialogBuilder.create();
		alert.show();
		alert.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				old_pass = oldpassword.getText().toString();
				new_pass = newpassword.getText().toString();
				new_cnpass = new_cnpassword.getText().toString();
				Boolean failFlag = false;
				if(old_pass.length()<6)
				{
					oldpassword.requestFocus();
					oldpassword.setError("Password should be more than 6");
					failFlag=true;
					Log.e("op", "error");
				}
				
				if(new_pass.length()<6) {
					newpassword.requestFocus();
					newpassword.setError("Password should be more than 6");
					failFlag=true;
					Log.e("np", "error");
				}
				
				if(!new_pass.matches(new_cnpass))
				{
					new_cnpassword.requestFocus();
					new_cnpassword.setError("Password doesnt match");
					failFlag=true;
					Log.e("cnp", "error");
				}
				
				if (failFlag == false) {
					ChangePassword();
					
				}
			}
		});
	}

	private void ChangePassword() {
		// TODO Auto-generated method stub
		new AsyncHelper().execute();
	}
	
	public class AsyncHelper extends AsyncTask<Void, Void, Void>
	{
	
		String Result,password,message;
		ProgressDialog pd=new ProgressDialog(getActivity());
		int status;
		ArrayList<NameValuePair>nameValuePair=new ArrayList<NameValuePair>();

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			//show progress dialog
			pd.setCancelable(false);
			pd.setTitle("Loading...");
			pd.show();

		}

		@Override
		protected Void doInBackground(Void... params) {

			Log.e("settng_uname", Constants.PARAM_USERNAME);
			nameValuePair.add(new BasicNameValuePair("username",Constants.PARAM_USERNAME ));
			nameValuePair.add(new BasicNameValuePair("oldpasswrd",old_pass));
			nameValuePair.add(new BasicNameValuePair("newpasswrd",new_pass));
			ServiceHandler sh = new ServiceHandler();

			String result = sh.makeServiceCall(Constants.URL_CHANGEPASSWORD, nameValuePair);
			Log.e("value:", "" +nameValuePair);
			Log.e("result:", "" +result);			

			try {

				JSONObject jo=new JSONObject(result);
				JSONObject jdata=jo.getJSONObject("data");
				message=jdata.getString("message");
				status=jdata.getInt("status");
				
			} 
			catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			pd.dismiss();
			Toast.makeText(getActivity(), message , Toast.LENGTH_LONG).show();
			if(status==1)
			{
				alert.cancel();
			}
		}
	}

}
