package info.accolade.trip_master;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import info.accolade.trip_master.adapaters.SliderAdapter;
import info.accolade.trip_master.utils.Constants;
import info.accolade.trip_master.utils.ServiceHandler;

public class MainActivity extends AppCompatActivity {
    EditText num1, num2;
    Button htl,tplc,cnm,mal;
    //String ht1,tp1,cnm1,mal1;

    SliderView sliderView;
    int[] images = {R.drawable.ban_1,
            R.drawable.ban_2,
            R.drawable.ban_3,
            R.drawable.ban_4};
    String n1,n2, username;
    private Toolbar toolbar;
    SharedPreferences sharedpreferences;
    public static final String PREF_FILE_NAME = "PrefFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        num1=(EditText)findViewById(R.id.et_n1);
        num2=(EditText)findViewById(R.id.et_n2);
        htl=(Button)findViewById(R.id.btn_htl);
        tplc=(Button)findViewById(R.id.btn_plc);
        cnm=(Button)findViewById(R.id.btn_cnm);
        mal=(Button)findViewById(R.id.btn_mal);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SliderView sliderView = findViewById(R.id.imageSlider);

        SliderAdapter adapter = new SliderAdapter(images);

        sliderView.setSliderAdapter(adapter);

        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.startAutoCycle();

        sharedpreferences = getSharedPreferences(PREF_FILE_NAME, MODE_PRIVATE);
        username = sharedpreferences.getString("userid", "");

        htl.setOnClickListener(new View.OnClickListener()
                               {
                                   public void onClick(View arg0)
                                   {
                                       n1=num1.getText().toString();
                                       n2=num2.getText().toString();

                                       if(n1.length()==0)
                                       {
                                           num1.setError("Enter your budget");
                                       }
                                       else if(n2.length()==0)
                                       {
                                           num2.setError("Enter your number of people");
                                       }
                                       else

                                       {
                                           Intent intent=new Intent(MainActivity.this,Budget.class);
                                           intent.putExtra("price", n1);
                                           intent.putExtra("num", n2);
                                           intent.putExtra("title", "Budget hotels");
                                           intent.putExtra("type", "h");
                                           startActivity(intent);
                                       }


                                   }
                               }
        );

        tplc.setOnClickListener(new View.OnClickListener()
                                {
                                    public void onClick(View arg0)
                                    {
                                        n1=num1.getText().toString();
                                        n2=num2.getText().toString();

                                        if(n1.length()==0)
                                        {
                                            num1.setError("Enter your budget");
                                        }
                                        else if(n2.length()==0)
                                        {
                                            num2.setError("Enter your number of people");
                                        }
                                        else

                                        {
                                            Intent intent=new Intent(MainActivity.this,Budget.class);
                                            intent.putExtra("price", n1);
                                            intent.putExtra("num", n2);
                                            intent.putExtra("title", "Budget Places");
                                            intent.putExtra("type", "p");
                                            startActivity(intent);
                                        }

                                    }
                                }
        );

        cnm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0)
            {
                n1=num1.getText().toString();
                n2=num2.getText().toString();

                if(n1.length()==0)
                {
                    num1.setError("Enter your budget");
                }
                else if(n2.length()==0)
                {
                    num2.setError("Enter your number of people");
                }
                else

                {
                    Intent intent=new Intent(MainActivity.this,Budget.class);
                    intent.putExtra("price", n1);
                    intent.putExtra("num", n2);
                    intent.putExtra("title", "Budget Movie Theatres");
                    intent.putExtra("type", "c");
                    startActivity(intent);
                }


            }
        });

        mal.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0)
            {
                n1=num1.getText().toString();
                n2=num2.getText().toString();

                if(n1.length()==0)
                {
                    num1.setError("Enter your budget");
                }
                else if(n2.length()==0)
                {
                    num2.setError("Enter your number of people");
                }
                else

                {
                    Intent intent=new Intent(MainActivity.this,Budget.class);
                    intent.putExtra("price", n1);
                    intent.putExtra("num", n2);
                    intent.putExtra("title", "Budget Shopping Malls");
                    intent.putExtra("type", "m");
                    startActivity(intent);
                }

            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id==R.id.action_mall)
        {
//            Intent i=new Intent(MainActivity.this, Route.class);
//            i.putExtra("value", "shopping_mall");
//            startActivity(i);

            findNearby("shopping_mall");
            return true;
        }

        else if(id==R.id.action_rest)
        {
//            Intent i=new Intent(MainActivity.this, Route.class);
//            i.putExtra("value", "restaurant");
//            startActivity(i);
            findNearby("restaurant");
            return true;
        }
        else if(id==R.id.action_masjd)
        {
//            Intent i=new Intent(MainActivity.this, Route.class);
//            i.putExtra("value", "mosque");
//            startActivity(i);

            findNearby("mosque");
            return true;
        }
        else if(id==R.id.action_templ)
        {
//            Intent i=new Intent(MainActivity.this, Route.class);
//            i.putExtra("value", "hindu_temple");
//            startActivity(i);

            findNearby("hindu_temple");
            return true;
        }
        else if(id==R.id.action_chrch)
        {
//            Intent i=new Intent(MainActivity.this, Route.class);
//            i.putExtra("value", "church");
//
//            startActivity(i);

            findNearby("church");
            return true;
        }
        else if(id == R.id.action_change){
            showAlert();
            return true;
        }
        else if(id==R.id.action_fb)
        {
            Intent i=new Intent(MainActivity.this, Feedback.class);
            startActivity(i);
            return true;
        }

        else if(id==R.id.action_logout)
        {

            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.clear();
            editor.commit();
            Toast.makeText(this, "You have logged out successfully", Toast.LENGTH_SHORT).show();
            Intent i=new Intent(MainActivity.this, Login.class);
            startActivity(i);
            MainActivity.this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    String old_pass, new_pass, new_cnpass;
    AlertDialog alert;

    protected void showAlert() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
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
        LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        View promptView = layoutInflater.inflate(R.layout.change_password, null);
        alertDialogBuilder.setView(promptView);
        final EditText oldpassword = (EditText) promptView.findViewById(R.id.old_pswd);
        final EditText newpassword = (EditText) promptView.findViewById(R.id.pswd);
        final EditText new_cnpassword = (EditText) promptView.findViewById(R.id.cn_pswd);

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
        alert = alertDialogBuilder.create();
        alert.show();
        alert.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
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
        ProgressDialog pd=new ProgressDialog(MainActivity.this);
        int status;
        ArrayList<NameValuePair> nameValuePair=new ArrayList<NameValuePair>();

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
            nameValuePair.add(new BasicNameValuePair("username",username));
            nameValuePair.add(new BasicNameValuePair("oldpasswrd",old_pass));
            nameValuePair.add(new BasicNameValuePair("newpasswrd",new_pass));
            ServiceHandler sh = new ServiceHandler();

            String result = sh.makeServiceCall(Constants.url_change_password, nameValuePair);
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
            Toast.makeText(MainActivity.this, message , Toast.LENGTH_LONG).show();
            if(status==1)
            {
                alert.cancel();
            }
        }
    }

    @Override
    public void onBackPressed() {
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setIcon(R.drawable.alert_icon)
                .setTitle("Alert..!")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                })
                .show();
    }


    private void findNearby(String val) {
        Uri gmmIntentUri = Uri.parse("geo:0,0?q="+val);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        }
    }
}
