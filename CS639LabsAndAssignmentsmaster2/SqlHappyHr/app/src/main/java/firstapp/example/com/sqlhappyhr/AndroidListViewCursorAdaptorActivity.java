package firstapp.example.com.sqlhappyhr;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import android.widget.AdapterView.OnItemClickListener;

import java.util.ArrayList;

public class AndroidListViewCursorAdaptorActivity extends Activity
{
    private RestaurantDbAdapter dbHelper;
    private SimpleCursorAdapter dataAdapter;
    private CustomListAdapter dataLoaderAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        dbHelper = new RestaurantDbAdapter(this);
        dbHelper.open();

        //Clean all data
        dbHelper.deleteAllRestaurants();
        //Add some data
        dbHelper.insertRestList();

        //Generate ListView from SQLite Database
       // displayListView();

        // generate listview from sqlite
        showList();
    }

/*    private void displayListView()
    {
        Cursor cursor = dbHelper.fetchAllRest();

        // The desired columns to be bound
        final String[] columns = new String[]
                {
                RestaurantDbAdapter.KEY_NAME,
                RestaurantDbAdapter.KEY_OFFERS,
                RestaurantDbAdapter.KEY_TIME,
                RestaurantDbAdapter.KEY_IMAGE
        };

        // the XML defined views which the data will be bound to
        int[] to = new int[]
                {

                R.id.name,
                R.id.offers,
                R.id.time,
                R.id.imageView2

        };

        // create the adapter using the cursor pointing to the desired data
        //as well as the layout information
        dataAdapter = new SimpleCursorAdapter(
                this, R.layout.rest_list,
                cursor,
                columns,
                to,
                0);

        ListView listView = (ListView) findViewById(R.id.listView1);

        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);

        listView.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> listView, View view,
                                    int position, long id)
            {
                // Get the cursor, positioned to the corresponding row in the result set
                Cursor cursor = (Cursor) listView.getItemAtPosition(position);

                Intent intent = new Intent(view.getContext(),Rest_Details.class);

                // get restaurant info for next activity
                String restName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String restAddress = cursor.getString(cursor.getColumnIndexOrThrow("address"));
                String restPhone = cursor.getString(cursor.getColumnIndexOrThrow("phone"));
                String restImage = cursor.getString(cursor.getColumnIndexOrThrow("restimage"));

                String []restDetails = {restName,restAddress,restPhone,restImage};

                intent.putExtra("RestDetails",restDetails);
                startActivity(intent);
            }
        });

        EditText myFilter = (EditText) findViewById(R.id.myFilter);
        myFilter.addTextChangedListener(new TextWatcher()
        {

            public void afterTextChanged(Editable s)
            {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after)
            {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count)
            {
                dataAdapter.getFilter().filter(s.toString());
            }
        });

        dataAdapter.setFilterQueryProvider(new FilterQueryProvider()
        {
            public Cursor runQuery(CharSequence constraint)
            {
                return dbHelper.fetchRestaurantByAddress(constraint.toString());
            }
        });

    }*/

   private void showList()
   {
       ArrayList<Restaurant> restaurantArrayList = new ArrayList<Restaurant>();
       restaurantArrayList.clear();
       Cursor cursor = dbHelper.fetchAllRest();

       if (cursor != null && cursor.getCount() != 0)
       {
           if (cursor.moveToFirst())
           {
               do {
                   Restaurant restaurants = new Restaurant();
                   restaurants.setName(cursor.getString(cursor.getColumnIndexOrThrow("name")));
                   restaurants.setOffers(cursor.getString(cursor.getColumnIndexOrThrow("offers")));
                   restaurants.setTime(cursor.getString(cursor.getColumnIndexOrThrow("time")));
                   restaurants.setImage(cursor.getString(cursor.getColumnIndexOrThrow("restimage")));
                   restaurants.setPhone(cursor.getString(cursor.getColumnIndexOrThrow("phone")));
                   restaurants.setAddress(cursor.getString(cursor.getColumnIndexOrThrow("address")));

                   restaurantArrayList.add(restaurants);
               } while (cursor.moveToNext());
           }
       }
       cursor.close();
       dataLoaderAdapter = new CustomListAdapter(this,restaurantArrayList);
       ListView list = (ListView) findViewById(R.id.listView1);
       list.setAdapter(dataLoaderAdapter);


       list.setOnItemClickListener(new OnItemClickListener()
       {
           @Override
           public void onItemClick(AdapterView<?> listView, View view,
                                   int position, long id)
           {
               Intent intent = new Intent(view.getContext(),Rest_Details.class);

               Restaurant rDetails = (Restaurant)dataLoaderAdapter.getItem(position);

               String name = rDetails.getName();
               String address = rDetails.getAddress();
               String phone = rDetails.getPhone();
               String image = rDetails.getImage();

               String [] restDetails = {name,address,phone,image};

               intent.putExtra("RestDetails",restDetails);
               startActivity(intent);
           }
       });
/*       EditText myFilter = (EditText) findViewById(R.id.myFilter);
       Log.d("test","I'm here");
       myFilter.addTextChangedListener(new TextWatcher()
       {

           public void afterTextChanged(Editable s)
           {
           }

           public void beforeTextChanged(CharSequence s, int start,
                                         int count, int after)
           {
           }

           public void onTextChanged(CharSequence s, int start,
                                     int before, int count)
           {
               dataAdapter.getFilter().filter(s.toString());
           }
       });

       dataAdapter.setFilterQueryProvider(new FilterQueryProvider()
       {
           public Cursor runQuery(CharSequence constraint)
           {
               return dbHelper.fetchRestaurantByAddress(constraint.toString());
           }
       });*/
   }
}
