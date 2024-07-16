package info.accolade.trip_master;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

import info.accolade.trip_master.adapaters.Filters;
import info.accolade.trip_master.utils.Constants;
import info.accolade.trip_master.utils.ServiceHandler;
import info.accolade.trip_master.utils.Uploader;

public class Register extends AppCompatActivity {
    EditText usr;
    EditText psw;
    EditText cpsw;
    Button reg;
    EditText phn,fn;
    String Uname, cpass,password,fname,phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usr=(EditText)findViewById(R.id.et_usr);
        psw=(EditText)findViewById(R.id.et_psw);
        cpsw=(EditText)findViewById(R.id.et_cpsw);
        phn=(EditText)findViewById(R.id.et_phone);
        fn=(EditText)findViewById(R.id.et_fullname);

        reg=(Button)findViewById(R.id.btn_reg);

        reg.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View arg0)
            {
                Uname=usr.getText().toString();
                password=psw.getText().toString();
                cpass=cpsw.getText().toString();
                fname=fn.getText().toString();
                phone=phn.getText().toString();

                if(fname.length()==0){
                    fn.requestFocus();
                    fn.setError("Enter your fullname");
                }
                else if(phone.length()!=10){
                    phn.requestFocus();
                    phn.setError("Enter valid phone number");
                }
                else if(Uname.length()==0){
                    usr.requestFocus();
                    usr.setError("Enter your name");
                }
                else if(password.length()==0){
                    psw.requestFocus();
                    psw.setError("Enter your password");
                }
                else if(password.length()<6) {
                    psw.requestFocus();
                    psw.setError("Password should be more than 6");
                }
                else if(!password.equals(cpass)){
                    cpsw.requestFocus();
                    cpsw.setError("Password does not match");
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


    public class AsyncHelper extends AsyncTask<Void, Void, Void>{
        String message;
        ProgressDialog pd=new ProgressDialog(Register.this);
        int status;
        ArrayList<NameValuePair>nameValuePair=new ArrayList<NameValuePair>();

        protected void onPreExecute()
        {
            pd.setCancelable(false);
            pd.setTitle("Loading...");
            pd.show();
        }
        protected Void doInBackground(Void... params) {
            nameValuePair.add(new BasicNameValuePair("name", Uname));
            nameValuePair.add(new BasicNameValuePair("pass", password));
            nameValuePair.add(new BasicNameValuePair("fullname", fname));
            nameValuePair.add(new BasicNameValuePair("phone", phone));

            ServiceHandler sh=new ServiceHandler();

            String result=sh.makeServiceCall(Constants.URL_Reg, nameValuePair);
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
            Toast.makeText(Register.this, message, Toast.LENGTH_LONG).show();
            if(status==2)
                usr.setError("username should be unique");
            else if(status==1)
            {
                Intent i=new Intent(Register.this, Login.class);
                startActivity(i);
            }
        }

    }

    public void btnLogin(View view) {
        startActivity(new Intent(Register.this, Login.class));
    }
}
