package info.accolade.trip_master;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import info.accolade.trip_master.adapaters.CustomListAdapter;
import info.accolade.trip_master.utils.Constants;
import info.accolade.trip_master.utils.ServiceHandler;

public class Budget extends AppCompatActivity {
    String prc, num, type;
    private Toolbar toolbar;

    ListView listView;
    CustomListAdapter adapter;
    ArrayList<HashMap<String,String>> data=new ArrayList<HashMap<String,String>>();

    TextView t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);


        listView = (ListView)findViewById(R.id.list);
        t=(TextView)findViewById(R.id.t1);
        prc=getIntent().getExtras().getString("price");
        num=getIntent().getExtras().getString("num");
        type=getIntent().getExtras().getString("type");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getIntent().getExtras().getString("title"));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        if(type.equals("h"))
        {
            new HotelGetData().execute();
        }

        if(type.equals("m"))
        {
            new MallGetData().execute();
        }

        if(type.equals("c"))
        {
            new CinemaGetData().execute();
        }

        if(type.equals("p"))
        {
            new PlaceGetData().execute();
        }

    }


    public class HotelGetData extends AsyncTask<Void, Void, Void>
    {

        String message;

        String result;
        ProgressDialog pDialog;
        ArrayList<NameValuePair>nameValuePair=new ArrayList<NameValuePair>();
        @Override
        protected void onPreExecute()
        {
            pDialog=new ProgressDialog(Budget.this);
            pDialog.setCancelable(false);
            pDialog.setTitle("Loading");
            pDialog.show();
        }


        @Override
        protected Void doInBackground(Void... params) {
            nameValuePair.add(new BasicNameValuePair("prc", prc.toString()));
            nameValuePair.add(new BasicNameValuePair("num", num.toString()));

            ServiceHandler sh=new ServiceHandler();
            String result=sh.makeServiceCall(Constants.URL_HOTELS, nameValuePair);
            Log.e("result:", ""+result);


            try{
                JSONObject jo=new JSONObject(result);
                JSONObject jdata=jo.getJSONObject("data");
                JSONArray ja=jdata.getJSONArray("food_info");
                for(int i=0;i<ja.length();i++)
                {
                    JSONObject jv=ja.getJSONObject(i);
                    HashMap<String,String>map=new HashMap<String, String>();
                    map.put("id", jv.getString("id"));
                    map.put("name", jv.getString("name"));
                    map.put("loc", jv.getString("loc"));
                    map.put("lat", jv.getString("lat"));
                    map.put("long", jv.getString("long"));
                    map.put("image", jv.getString("image"));
                    map.put("t", "");

                    data.add(map);

                }
            }catch(Exception e)
            {

            }

            return null;
        }




        protected void onPostExecute(Void result)
        {

            // TODO Auto-generated method stub

            pDialog.dismiss();

            if(data.isEmpty())
            {
                listView.setVisibility(View.GONE);
                t.setVisibility(View.VISIBLE);
                t.setText("No hotel Found within your Budget");
            }
            else
            {
                adapter=new CustomListAdapter(Budget.this,data);
                listView.setAdapter(adapter);
            }

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1,
                                        int arg2, long arg3)
                {
                    // TODO Auto-generated method stub

                    Intent i = new Intent(Budget.this, BudgetFood.class);
                    i.putExtra("id", data.get(arg2).get("id"));
                    i.putExtra("prc",getIntent().getExtras().getString("price"));
                    i.putExtra("title", data.get(arg2).get("name"));
                    i.putExtra("num",getIntent().getExtras().getString("num"));
                    i.putExtra("type", "food");
                    startActivity(i);


                }
            });
        }

    }





    public class MallGetData extends AsyncTask<Void, Void, Void>
    {

        String message;
        String result;
        ProgressDialog pDialog;
        ArrayList<NameValuePair>nameValuePair=new ArrayList<NameValuePair>();
        @Override
        protected void onPreExecute()
        {
            pDialog=new ProgressDialog(Budget.this);
            pDialog.setCancelable(false);
            pDialog.setTitle("Loading");
            pDialog.show();
        }


        @Override
        protected Void doInBackground(Void... params)
        {
            nameValuePair.add(new BasicNameValuePair("prc", prc.toString()));
            nameValuePair.add(new BasicNameValuePair("num", num.toString()));

            ServiceHandler sh=new ServiceHandler();
            String result=sh.makeServiceCall(Constants.URL_MALLS, nameValuePair);
            Log.e("result:", ""+result);


            try{
                JSONObject jo=new JSONObject(result);
                JSONObject jdata=jo.getJSONObject("data");
                JSONArray ja=jdata.getJSONArray("mall_info");
                for(int i=0;i<ja.length();i++){
                    JSONObject jv=ja.getJSONObject(i);
                    HashMap<String,String>map=new HashMap<String, String>();
                    map.put("id", jv.getString("id"));
                    map.put("name", jv.getString("name"));
                    map.put("loc", jv.getString("loc"));
                    map.put("lat", jv.getString("lat"));
                    map.put("long", jv.getString("long"));
                    map.put("image", jv.getString("image"));
                    map.put("t", "");
                    data.add(map);

                }
            }catch(Exception e)
            {

            }

            return null;
        }


        @Override
        protected void onPostExecute(Void result)
        {

            // TODO Auto-generated method stub

            pDialog.dismiss();

            if(data.isEmpty())
            {
                listView.setVisibility(View.GONE);
                t.setVisibility(View.VISIBLE);
                t.setText("No Mall Found within your Budget");
            }
            else
            {
                adapter=new CustomListAdapter(Budget.this,data);
                listView.setAdapter(adapter);
            }

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1,int arg2, long arg3)
                {
                    // TODO Auto-generated method stub

                    Intent i = new Intent(Budget.this, BudgetFood.class);
                    i.putExtra("id", data.get(arg2).get("id"));
                    i.putExtra("prc",getIntent().getExtras().getString("price"));
                    i.putExtra("title", data.get(arg2).get("name"));
                    i.putExtra("num",getIntent().getExtras().getString("num"));

                    i.putExtra("type", "mall");
                    startActivity(i);

                }
            });

        }

    }



    public class CinemaGetData extends AsyncTask<Void, Void, Void>{

        String message;

        String result;
        ProgressDialog pDialog;
        ArrayList<NameValuePair>nameValuePair=new ArrayList<NameValuePair>();
        @Override
        protected void onPreExecute() {
            pDialog=new ProgressDialog(Budget.this);
            pDialog.setCancelable(false);
            pDialog.setTitle("Loading");
            pDialog.show();
        }


        @Override
        protected Void doInBackground(Void... params) {
            nameValuePair.add(new BasicNameValuePair("prc", prc.toString()));
            nameValuePair.add(new BasicNameValuePair("num", num.toString()));

            ServiceHandler sh=new ServiceHandler();
            String result=sh.makeServiceCall(Constants.URL_CINEMA, nameValuePair);
            Log.e("result:", ""+result);


            try{
                JSONObject jo=new JSONObject(result);
                JSONObject jdata=jo.getJSONObject("data");
                JSONArray ja=jdata.getJSONArray("info");
                for(int i=0;i<ja.length();i++){
                    JSONObject jv=ja.getJSONObject(i);
                    HashMap<String,String>map=new HashMap<String, String>();
                    map.put("name", jv.getString("name"));
                    map.put("loc", jv.getString("loc"));
                    map.put("desc", jv.getString("desc"));
                    map.put("his", jv.getString("his"));
                    map.put("image", jv.getString("image"));
                    map.put("price", jv.getString("price"));
                    map.put("lat", jv.getString("lat"));
                    map.put("long", jv.getString("long"));
                    map.put("t", "");
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

            if(data.isEmpty()){
                listView.setVisibility(View.GONE);
                t.setVisibility(View.VISIBLE);
                t.setText("No Movies Found within your Budget");
            }
            else
            {
                adapter=new CustomListAdapter(Budget.this,data);
                listView.setAdapter(adapter);
            }


            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1,
                                        int arg2, long arg3) {
                    // TODO Auto-generated method stub

                    Intent i = new Intent(Budget.this, BudgetCinema.class);
                    i.putExtra("name", data.get(arg2).get("name"));
                    i.putExtra("loc", data.get(arg2).get("loc"));
                    i.putExtra("desc", data.get(arg2).get("desc"));
                    i.putExtra("his", data.get(arg2).get("his"));
                    i.putExtra("price", data.get(arg2).get("price"));
                    i.putExtra("image", data.get(arg2).get("image"));
                    i.putExtra("type", "cinema");
                    startActivity(i);

                }
            });




        }
    }

    public class PlaceGetData extends AsyncTask<Void, Void, Void>{

        String message;

        String result;
        ProgressDialog pDialog;
        ArrayList<NameValuePair>nameValuePair=new ArrayList<NameValuePair>();
        @Override
        protected void onPreExecute() {
            pDialog=new ProgressDialog(Budget.this);
            pDialog.setCancelable(false);
            pDialog.setTitle("Loading");
            pDialog.show();
        }


        @Override
        protected Void doInBackground(Void... params) {
            nameValuePair.add(new BasicNameValuePair("prc", prc.toString()));
            nameValuePair.add(new BasicNameValuePair("num", num.toString()));

            ServiceHandler sh=new ServiceHandler();
            String result=sh.makeServiceCall(Constants.URL_PLACE, nameValuePair);
            Log.e("result:", ""+result);


            try{
                JSONObject jo=new JSONObject(result);
                JSONObject jdata=jo.getJSONObject("data");
                JSONArray ja=jdata.getJSONArray("plc_info");
                for(int i=0;i<ja.length();i++){
                    JSONObject jv=ja.getJSONObject(i);
                    HashMap<String,String>map=new HashMap<String, String>();
                    map.put("name", jv.getString("name"));
                    map.put("loc", jv.getString("loc"));
                    map.put("desc", jv.getString("desc"));
                    map.put("his", jv.getString("his"));
                    map.put("image", jv.getString("image"));
                    map.put("price", jv.getString("price"));
                    map.put("lat", jv.getString("lat"));
                    map.put("long", jv.getString("long"));
                    map.put("t", "");
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

            if(data.isEmpty()){
                listView.setVisibility(View.GONE);
                t.setVisibility(View.VISIBLE);
                t.setText("No places Found within your Budget");
            }
            else
            {
                adapter=new CustomListAdapter(Budget.this,data);
                listView.setAdapter(adapter);
            }


            listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1,
                                        int arg2, long arg3)
                {
                    // TODO Auto-generated method stub

                    Intent i = new Intent(Budget.this, BudgetPlace.class);
                    i.putExtra("name", data.get(arg2).get("name"));
                    i.putExtra("loc", data.get(arg2).get("loc"));
                    i.putExtra("desc", data.get(arg2).get("desc"));
                    i.putExtra("his", data.get(arg2).get("his"));
                    i.putExtra("price", data.get(arg2).get("price"));
                    i.putExtra("image", data.get(arg2).get("image"));
                    i.putExtra("type", "place");
                    startActivity(i);

                }
            });

        }
    }

}
