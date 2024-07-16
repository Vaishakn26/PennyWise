package info.accolade.trip_master;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;

import info.accolade.trip_master.utils.Constants;

public class BudgetCinema extends AppCompatActivity {
    TextView t1,t2,t3,t4,t5;
    ImageView i;
    String imgurl;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_cinema);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getIntent().getExtras().getString("name"));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        i=(ImageView)findViewById(R.id.i1);
        t1=(TextView)findViewById(R.id.t1);
        t2=(TextView)findViewById(R.id.t2);
        t3=(TextView)findViewById(R.id.t3);
        t4=(TextView)findViewById(R.id.t4);
        t5=(TextView)findViewById(R.id.t5);

        imgurl=getIntent().getExtras().getString("image");
        AQuery imgaq = new AQuery(BudgetCinema.this);
        imgaq.id(i).image(Constants.URL_UPLOADS+imgurl,false, false, 0, R.drawable.movie2,  null,com.androidquery.util.Constants.FADE_IN_NETWORK);
        Log.e("id:",""+imgurl);

        if(getIntent().getExtras().get("type").equals("cinema"))
        {
            t1.setText(getIntent().getExtras().getString("name"));
            t5.setText("Location: "+getIntent().getExtras().getString("loc"));
            t3.setText("Price: "+getIntent().getExtras().getString("price")+" /-");
            t2.setText("Description:\n"+getIntent().getExtras().getString("desc"));
            t4.setText("Theatre:\n"+getIntent().getExtras().getString("his"));
        }

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.budget_cinema, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
