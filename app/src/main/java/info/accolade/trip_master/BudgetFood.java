package info.accolade.trip_master;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import info.accolade.trip_master.adapaters.CustomListAdapter2;
import info.accolade.trip_master.utils.Constants;
import info.accolade.trip_master.utils.ServiceHandler;

public class BudgetFood extends AppCompatActivity {
    String prc, num, id;
    private Toolbar toolbar;

    ListView listView;
    CustomListAdapter2 adapter;
    ArrayList<HashMap<String,String>> data=new ArrayList<HashMap<String,String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_food);

        listView = (ListView)findViewById(R.id.list);

        prc=getIntent().getExtras().getString("prc");
        num=getIntent().getExtras().getString("num");
        id=getIntent().getExtras().getString("id");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getIntent().getExtras().getString("title"));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        new HotelGetData().execute();

    }

    public class HotelGetData extends AsyncTask<Void, Void, Void> {

        String message;

        String result;
        ProgressDialog pDialog;
        ArrayList<NameValuePair>nameValuePair=new ArrayList<NameValuePair>();
        @Override
        protected void onPreExecute() {
            pDialog=new ProgressDialog(BudgetFood.this);
            pDialog.setCancelable(false);
            pDialog.setTitle("Loading");
            pDialog.show();
        }


        @Override
        protected Void doInBackground(Void... params) {
            nameValuePair.add(new BasicNameValuePair("prc", prc.toString()));
            nameValuePair.add(new BasicNameValuePair("num", num.toString()));
            nameValuePair.add(new BasicNameValuePair("id", id.toString()));
            Log.e("result:", ""+getIntent().getExtras().getString("id")+getIntent().getExtras().getString("prc")+prc.toString());

            ServiceHandler sh=new ServiceHandler();
            if(getIntent().getExtras().getString("type").equals("food"))
                result=sh.makeServiceCall(Constants.URL_FOOD, nameValuePair);
            if(getIntent().getExtras().getString("type").equals("mall"))
                result=sh.makeServiceCall(Constants.URL_ITEM, nameValuePair);
            Log.e("result:", ""+result);


            try{
                JSONObject jo=new JSONObject(result);
                JSONObject jdata=jo.getJSONObject("data");
                JSONArray ja=jdata.getJSONArray("info");
                for(int i=0;i<ja.length();i++){
                    JSONObject jv=ja.getJSONObject(i);
                    HashMap<String,String>map=new HashMap<String, String>();
                    map.put("name", jv.getString("name"));
                    map.put("type", jv.getString("type"));
                    map.put("price", jv.getString("price"));
                    data.add(map);

                }
            }catch(Exception e){

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            pDialog.dismiss();

            adapter=new CustomListAdapter2(BudgetFood.this,data);
            listView.setAdapter(adapter);


        }
    }
}
