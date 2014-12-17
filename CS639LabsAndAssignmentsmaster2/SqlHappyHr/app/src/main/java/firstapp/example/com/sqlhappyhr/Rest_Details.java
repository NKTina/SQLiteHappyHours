package firstapp.example.com.sqlhappyhr;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.URLEncoder;

/**
 * Created by murlidhar on 12/5/14.
 * Modified by Tina
 */
public class Rest_Details extends Activity
{
    private RestaurantDbAdapter dbHelper;
    private SQLiteDatabase mDb;

    // String containing extra values from previous activity
    String []value = null; // contains extra value from previous activity
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // To display restaurant details on second screen
        TextView name,address,phone;

        // for call and map button
        Button call,map;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.rest_details);

        // GET ID OF RESTAURANT THAT WAS CLICKED
        Bundle extra = getIntent().getExtras();
        if (extra != null)
        {
            value = getIntent().getStringArrayExtra("RestDetails");

            name = (TextView)findViewById(R.id.restName);
            address = (TextView)findViewById(R.id.restAddress);
            phone = (TextView)findViewById(R.id.restPhone);

            name.setText(value[0]);
            address.setText(value[1]);
            phone.setText(value[2]);

            // set restaurant image from URL we got from intent
            async imageDownload = new async(this);
            imageDownload.execute(value[3]);

        }
        call = (Button)findViewById(R.id.callButton);
        map = (Button)findViewById(R.id.mapButton);

        call.setOnClickListener(buttonListener);
        map.setOnClickListener(buttonListener);
    }

    // onClick listener for map and phone
    private View.OnClickListener buttonListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.callButton:
                    String uri = "tel:" + value[2];
                    final Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse(uri));
                    startActivity(callIntent);
                    break;
                case R.id.mapButton:
                    String addressUri = value[1];
                    Log.d("I am parsing",addressUri);
                    Intent map = viewOnMap(addressUri);
                    startActivity(map);
                    break;
            }
        }
    };
    public static Intent viewOnMap(String address)
    {
        try
        {
            return new Intent(Intent.ACTION_VIEW,Uri.parse(String.format("geo:0,0?q=%s", URLEncoder.encode(address,"UTF-8"))));
        }
        catch (Exception e)
        {
            Log.e("error", e.getMessage());
        }
        return null;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings)
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
