package info.accolade.trip_master;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import info.accolade.trip_master.utils.ConnectionDetector;

public class Splash extends AppCompatActivity {
    SharedPreferences sharedpreferences;
    public static final String PREF_FILE_NAME = "PrefFile";
    boolean isloginuser = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sharedpreferences = getSharedPreferences(PREF_FILE_NAME, MODE_PRIVATE);

        isloginuser = sharedpreferences.getBoolean("logined", false);

        Thread thread =new Thread()
        {
            @Override
            public void  run()
            {
                try {
                    sleep(6000);
                }
                catch (Exception e)
                {

                }
                finally {
                    if(isloginuser==true){
                        Intent intent=new Intent(Splash.this,MainActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Intent intent=new Intent(Splash.this,Login.class);
                        startActivity(intent);
                    }
                }
            }

        };
        thread.start();

    }

}
