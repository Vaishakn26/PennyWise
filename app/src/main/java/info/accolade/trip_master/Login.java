package info.accolade.trip_master;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import info.accolade.trip_master.utils.Constants;
import info.accolade.trip_master.utils.ServiceHandler;

public class Login extends AppCompatActivity {

    String uname,psw;
    String res;
    EditText usr1, pas;
    Button lgn;
    TextView mreg;
    TextView fpsw;
    SharedPreferences sharedpreferences;
    public static final String PREF_FILE_NAME = "PrefFile";
    String pwd, forgot_uname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usr1=(EditText)findViewById(R.id.et_usr1);
        pas=(EditText)findViewById(R.id.et_psw1);
        lgn=(Button)findViewById(R.id.btn_lgn);
        mreg=(TextView)findViewById(R.id.txt_reg);
        fpsw=(TextView)findViewById(R.id.txt_forgot);

        sharedpreferences = getSharedPreferences(PREF_FILE_NAME, MODE_PRIVATE);

        mreg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent i=new Intent(Login.this, Register.class);
                startActivity(i);
            }
        });



        fpsw.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                showAlert();
            }
        });


        lgn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View arg0)
            {
                uname=usr1.getText().toString();
                psw=pas.getText().toString();
                if(uname.length()==0){
                    usr1.setError("Enter your name");
                }
                else if(psw.length()==0){
                    pas.setError("Enter your password");
                }

                else
                    register();


            }

        });

    }

    private void register()
    {
        new AsyncHelper().execute();
    }

    public class AsyncHelper extends AsyncTask<Void, Void, Void>
    {
        String message;
        ProgressDialog pd=new ProgressDialog(Login.this);
        int status;
        ArrayList<NameValuePair>nameValuePair=new ArrayList<NameValuePair>();

        protected void onPreExecute()
        {
            pd.setCancelable(false);
            pd.setTitle("Loading...");
            pd.show();
        }

        protected Void doInBackground(Void... params) {
            nameValuePair.add(new BasicNameValuePair("username", uname));
            nameValuePair.add(new BasicNameValuePair("password", psw));

            ServiceHandler sh=new ServiceHandler();

            String result=sh.makeServiceCall(Constants.URL_LOGIN, nameValuePair);
            Log.e("result1:", ""+nameValuePair.toString());
            Log.e("result:", ""+result);

            try{
                JSONObject jo=new JSONObject(result);
                JSONObject jdata=jo.getJSONObject("data");
                message=jdata.getString("message");
                status=jdata.getInt("status");

            }catch(Exception e){

            }

            return null;
        }

        protected void onPostExecute(Void result)
        {
            pd.dismiss();
            Toast.makeText(Login.this, message, Toast.LENGTH_LONG).show();
            if(status==1)
            {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putBoolean("logined", true);
                editor.putString("userid", uname);
                editor.commit();

                Intent i=new Intent(Login.this, MainActivity.class);
                startActivity(i);
            }
        }


    }



    private void showAlert()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Login.this);
        // Setting Dialog Title
        alertDialog.setTitle("Forgot Password");
        // Setting Dialog Message
        alertDialog.setMessage("Do you want to reset your password ?");
        // Setting OK Button
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                showInputDialog(1);
            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

            }
        });
        // Showing Alert Message
        alertDialog.show();
    }

    protected void showInputDialog(int which) {
        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(Login.this);
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Login.this);
        if(which==1)
        {
            View promptView = layoutInflater.inflate(R.layout.reset_password1, null);

            alertDialogBuilder.setView(promptView);
            final EditText frgt_uname = (EditText) promptView.findViewById(R.id.forgot_uname);

            // setup a dialog window
            alertDialogBuilder.setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
            // create an alert dialog
            final AlertDialog alert = alertDialogBuilder.create();
            alert.show();
            alert.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    forgot_uname=frgt_uname.getText().toString();
                    if(frgt_uname.length()==0)
                    {
                        frgt_uname.requestFocus();
                        frgt_uname.setError("Enter valid username");
                    }
                    else
                    {
                        alert.cancel();
                        resetpassword();
                    }
                }
            });
        }
        else if(which==2)
        {
            View promptView = layoutInflater.inflate(R.layout.reset_password2, null);
            alertDialogBuilder.setView(promptView);
            final EditText newpassword = (EditText) promptView.findViewById(R.id.pswd);
            final EditText newpassword2 = (EditText) promptView.findViewById(R.id.pswd2);

            // setup a dialog window
            alertDialogBuilder.setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
            // create an alert dialog
            final AlertDialog alert = alertDialogBuilder.create();
            alert.show();
            alert.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    pwd = newpassword.getText().toString();
                    Boolean failFlag = false;

                    if (pwd.length()<6) {
                        newpassword.requestFocus();
                        newpassword.setError("Password should be more than 6");
                        failFlag=true;
                    }

                    if(!pwd.matches(newpassword2.getText().toString()))
                    {
                        newpassword2.requestFocus();
                        newpassword2.setError("Password doesnt match");
                        failFlag=true;
                    }

                    if (failFlag == false) {
                        alert.cancel();
                        resetpassword2();
                    }

                }

            });
        }
    }

    private void resetpassword() {
        new AsyncHelper2().execute();
    }

    public class AsyncHelper2 extends AsyncTask<Void, Void, Void>{
        String message;
        int status;
        ProgressDialog pd=new ProgressDialog(Login.this);
        ArrayList<NameValuePair>nameValuePair=new ArrayList<NameValuePair>();

        @Override
        protected void onPreExecute() {
            pd.setCancelable(false);
            pd.setTitle("Loading...");
            pd.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            nameValuePair.add(new BasicNameValuePair("f_uname",forgot_uname.toString()));
            ServiceHandler sh=new ServiceHandler();

            String result=sh.makeServiceCall(Constants.url_reset_password, nameValuePair);

            Log.e("result:", ""+result);
            Log.e("nameValuePair:", ""+nameValuePair);

            try{
                JSONObject jo=new JSONObject(result);
                JSONObject jdata=jo.getJSONObject("data");
                message=jdata.getString("message");
                status=jdata.getInt("status");

            }catch(Exception e){

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            pd.dismiss();
            Toast.makeText(Login.this, message, Toast.LENGTH_LONG).show();
            if(status==1)
            {
                showInputDialog(2);
            }
            else
            {
                showInputDialog(1);
            }
        }
    }

    private void resetpassword2() {
        new AsyncHelper3().execute();
    }
    public class AsyncHelper3 extends AsyncTask<Void, Void, Void>{
        String message;
        int status;
        ProgressDialog pd=new ProgressDialog(Login.this);

        ArrayList<NameValuePair>nameValuePair=new ArrayList<NameValuePair>();

        @Override
        protected void onPreExecute() {
            pd.setCancelable(false);
            pd.setTitle("Loading...");
            pd.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            nameValuePair.add(new BasicNameValuePair("f_uname",forgot_uname.toString()));
            nameValuePair.add(new BasicNameValuePair("n_password", pwd.toString()));

            ServiceHandler sh=new ServiceHandler();


            String result=sh.makeServiceCall(Constants.url_reset_password2, nameValuePair);

            Log.e("result:", ""+result);
            Log.e("nameValuePair:", ""+nameValuePair);

            try{
                JSONObject jo=new JSONObject(result);
                JSONObject jdata=jo.getJSONObject("data");
                message=jdata.getString("message");
                status=jdata.getInt("status");

            }catch(Exception e){

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            pd.dismiss();
            Toast.makeText(Login.this, message, Toast.LENGTH_LONG).show();
        }

    }


}




