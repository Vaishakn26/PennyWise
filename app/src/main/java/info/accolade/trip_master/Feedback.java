package info.accolade.trip_master;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

import info.accolade.trip_master.utils.Constants;
import info.accolade.trip_master.utils.ServiceHandler;

public class Feedback extends AppCompatActivity {
    String username;
    String desp;
    EditText desc;
    Button sub;

    SharedPreferences sharedpreferences;
    public static final String PREF_FILE_NAME = "PrefFile";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        desc=(EditText)findViewById(R.id.textView1);
        sub=(Button)findViewById(R.id.btn_des);
        sharedpreferences = getSharedPreferences(PREF_FILE_NAME, MODE_PRIVATE);

        username = sharedpreferences.getString("userid", "");


        sub.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View arg0)
            {
                desp=desc.getText().toString();
                if(desp.length()==0){
                    desc.setError("Enter your description");
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
        ProgressDialog pd=new ProgressDialog(Feedback.this);
        int status;
        ArrayList<NameValuePair> nameValuePair=new ArrayList<NameValuePair>();

        protected void onPreExecute()
        {
            pd.setCancelable(false);
            pd.setTitle("Loading...");
            pd.show();
        }

        protected Void doInBackground(Void... params) {
            nameValuePair.add(new BasicNameValuePair("description", desp));
            nameValuePair.add(new BasicNameValuePair("username", username));

            ServiceHandler sh=new ServiceHandler();

            String result=sh.makeServiceCall(Constants.URL_feedback, nameValuePair);
            //String result=sh.makeServiceCall("http://10.0.3.2/budgetphp/feedback.php", nameValuePair);

            Log.e("result1:", ""+nameValuePair.toString());
            Log.e("result:", ""+result);

            try{
                JSONObject jo=new JSONObject(result);
                JSONObject jdata=jo.getJSONObject("data");
                message=jdata.getString("message");
                status=jdata.getInt("status");

            }catch(Exception e)
            {
            }

            return null;
        }

        protected void onPostExecute(Void result)
        {
            pd.dismiss();
            Toast.makeText(Feedback.this, message, Toast.LENGTH_LONG).show();
            if(status==1)
            {
                Feedback.this.finish();

            }
        }


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.feedback, menu);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
