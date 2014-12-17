package firstapp.example.com.sqlhappyhr;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by murlidhar on 12/5/14.
 */
public class RestaurantDbAdapter
{
        public static final String KEY_ROWID = "_id";
       // public static final String KEY_ID = "code";
        public static final String KEY_NAME = "name";       // restaurant name
        public static final String KEY_ADDRESS = "address"; // restaurant addres
        public static final String KEY_PHONE = "phone";     // phone number
        public static final String KEY_OFFERS = "offers";   // deals/offers
        public static final String KEY_TIME = "time";       // happy hour times
        public static final String KEY_IMAGE = "restimage"; // image for second activity

        private static final String TAG = "RestaurantDbAdapter";
        private DatabaseHelper mDbHelper;
        private SQLiteDatabase mDb;
        private static final String DATABASE_NAME = "Restaurants";
        private static final String SQLITE_TABLE = "HappyHours";
        private static final int DATABASE_VERSION = 1;
        private final Context mCtx;

        private static final String DATABASE_CREATE =
                "CREATE TABLE if not exists " + SQLITE_TABLE + " (" +
                        KEY_ROWID + " integer PRIMARY KEY autoincrement," +
                        KEY_NAME + "," +
                        KEY_ADDRESS + "," +
                        KEY_PHONE + "," +
                        KEY_OFFERS+ "," +
                        KEY_TIME+ "," +
                        KEY_IMAGE+ "," +
                        " UNIQUE (" + KEY_ROWID +"));";

        private static class DatabaseHelper extends SQLiteOpenHelper
        {
            DatabaseHelper(Context context)
            {
                super(context, DATABASE_NAME, null, DATABASE_VERSION);
            }


            @Override
            public void onCreate(SQLiteDatabase db)
            {
                Log.w(TAG, DATABASE_CREATE);
                db.execSQL(DATABASE_CREATE);
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
            {
                Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
                db.execSQL("DROP TABLE IF EXISTS " + SQLITE_TABLE);
                onCreate(db);
            }
        }

        public RestaurantDbAdapter(Context ctx)
        {
            this.mCtx = ctx;
        }
        public RestaurantDbAdapter open() throws SQLException
        {
            mDbHelper = new DatabaseHelper(mCtx);
            mDb = mDbHelper.getWritableDatabase();
            return this;
        }

        public void close()
        {
            if (mDbHelper != null)
            {
                mDbHelper.close();
            }
        }

        public long createRestaurant( String name,
                                  String address, String phone,String offer,String time,String imageUrl)
        {
            ContentValues initialValues = new ContentValues();
            initialValues.put(KEY_NAME, name);
            initialValues.put(KEY_ADDRESS, address);
            initialValues.put(KEY_PHONE, phone);
            initialValues.put(KEY_OFFERS, offer);
            initialValues.put(KEY_TIME,time);
            initialValues.put(KEY_IMAGE, imageUrl);

            return mDb.insert(SQLITE_TABLE, null, initialValues);
        }

        public boolean deleteAllRestaurants()
        {

            int doneDelete = 0;
            doneDelete = mDb.delete(SQLITE_TABLE, null , null);
            Log.w(TAG, Integer.toString(doneDelete));
            return doneDelete > 0;

        }

        public Cursor fetchRestaurantByAddress(String inputText) throws SQLException
        {
            Log.w(TAG, inputText);
            Cursor mCursor = null;
            if (inputText == null  ||  inputText.length () == 0)
            {
                mCursor = mDb.query(SQLITE_TABLE, new String[] {KEY_ROWID
                                , KEY_NAME, KEY_ADDRESS, KEY_PHONE,KEY_OFFERS,KEY_TIME,KEY_IMAGE},
                        null, null, null, null, null);

            }
            else
            {
                mCursor = mDb.query(true, SQLITE_TABLE, new String[] {KEY_ROWID,
                               KEY_NAME, KEY_ADDRESS, KEY_PHONE,KEY_OFFERS,KEY_TIME,KEY_IMAGE},
                        KEY_ADDRESS + " like '%" + inputText + "%'", null,
                        null, null, null, null);
            }
            if (mCursor != null)
            {
                mCursor.moveToFirst();
            }
            return mCursor;

        }

        public Cursor fetchAllRest()
        {
            Cursor mCursor = mDb.query(SQLITE_TABLE, new String[] {KEY_ROWID,
                           KEY_NAME, KEY_ADDRESS, KEY_PHONE,KEY_OFFERS,KEY_TIME,KEY_IMAGE},
                    null, null, null, null, null);

            if (mCursor != null)
            {
                mCursor.moveToFirst();
            }

            return mCursor;
        }

        public void insertRestList()
        {
            createRestaurant("123 Burger Shot Beer","738 10th Ave New York New York 10019","212-315-0123","$1 Burgers, $2 shots, $3 Beer","All day Everyday","http://places1.assets.gotime.com/2c20545f453b051c10047d07b4f889910a76d999_l.jpg");
            createRestaurant("Bar 13 ","35 E 13 st New York New York 10003","212-979-6677"," 2 for 1 drinks"," 5- 8 PM","http://images.nymag.com/listings/bar/mainbar13.jpg");
            createRestaurant("1849 ","183 Bleecker St New York New York 10012","212-505-3200"," 1/2 Priced beer & well drink, 20c wings","3PM - 1AM","http://newyork.cities2night.com/public/place_images/place_1867.jpg");
            createRestaurant("Bar none"," 98 Third Ave New York New York 10003","212-777-NONE","$4 drafts,$3Well","12PM-8PM","https://files.nyu.edu/ab3037/public/images/barNone.jpg");
            createRestaurant("Barrow St. Ale House","15 Barrow Street New York New York 10014","212-691-6127"," 40c Wings, $3 Sam Adams","7PM - 4AM","http://pipedreamsnyc.com/wp-content/uploads/2010/10/barrow-street-ale-house-dbl-pedestal.jpg");
            createRestaurant("The Big Easy","1768 2nd Ave New York New York 10128","212-348-0879","$2 BL Drafts, $3 Well"," 5PM - 8PM","");
            createRestaurant("Bliss Bar and Lounge","256 E 49th Sr New York New York 10017","212-644-8750","$5 Skyy Martinis"," 4PM - 7PM","http://www.newyorknetguide.com/Zim/Dir/BLISSBAR&LOUNGE.JPG");
            createRestaurant("Boss Tweeds","115 Essex St New York New York 10002","212-475-9997","$3 Bud/BL,$4 Natty Doubles, $5 Shot & Beer,$10 Pitchers","All day","http://www.pubcrawls.com/images/venues/nyc/Boss%20tweeds%20saloon/boss-tweeds-4.jpg");
            createRestaurant("Bulls Head Tavern","295 3rd Ave New York New York 10010","212-685-2589","$3 Drafts","1PM-7PM","http://52pretzels.files.wordpress.com/2010/10/bullshead.jpg");
            createRestaurant("The Central Bar"," 109 E. 9th Street New York New York 10003","212-529-5333","$4 Well/Draft/Shots"," 3PM-7PM","http://www.nyc-architecture.com/ARCH/040820_hannah_hmed_4p.hmedium.jpg");
            createRestaurant("Continental","25 3rd Ave","212-529-6924","Everything $2-4","4PM-8PM","https://files.nyu.edu/ab3037/public/images/continental.JPG");
            createRestaurant("Croxley Ales","28 Ave B","212-253-6140","10c Winds","5PM-1AM","http://www.localbozo.com/wp-content/flagallery/wing-down-croxley/wing-war-iii-001.jpg");
            createRestaurant("Keybar","432 E 13th Street","212-478-3021","2 for 1 drinks","5PM-10PM","http://pubcrawls.com/images/venues/keybar-3.jpg");
            createRestaurant("Mad River Bar & Grille","1442 3rd Ave","212-988-1832","7$ Pitchers 1/2 Priced Wings","8PM-2AM","http://2dineout.files.wordpress.com/2011/11/photo4.jpg");
            createRestaurant("Mc Fadden's","800 2nd Ave","212-986-3201","$3 Drafts, 1/2 priced burgers at the bar","6PM-9PM","http://www.mcfaddens42.com/site/ul/hpfeatures/lg/tWWr6.jpg");
            createRestaurant("Phebe's Tavern & Grill"," 359 Bowery New York New York 10003","212-358-1902","Order a pitcher, get 25c wings"," 8PM-12PM","http://media.timeout.com/images/resizeBestFit/100459521/660/370/image.jpg");
            createRestaurant("Off the Wagon","109 MacDougal Street","212-533-4487","Everything 1/2 price","4PM-8PM","https://catscitylife.files.wordpress.com/2011/09/offthewagon.jpg");
            createRestaurant("T.G. Whitney's","244 E 53rd Street","212-888-5772","25c wings, 3$ Yuengling Draft Miller Bottles, 5$-5 Sliders","","http://pubcrawls.com/images/venues/tg-whitneys-logo.jpg");
            createRestaurant("Three of Cups"," 83 1st Ave","212-388-0059"," Upstairs bar $4 all Beer, House Wine & Well Drinks","5PM-8PM","http://therestaurantfairy.com/wp-content/uploads/2012/06/DSC_6539-500x332.jpg");
            createRestaurant("Village Pour House","64 3rd Ave","212-979-BEER","$2 Bud/BL, 2 for 1 Margaritas & Absolute","5PM-7PM","http://s3-media3.fl.yelpcdn.com/bphoto/89Z37x0uOVfVtnNFhpd5WQ/l.jpg");
            createRestaurant("Ardesia","510 W 52nd St. New York NY 10019","212-247-9191","$2 off any Glass of Wine, $4Off Draft Beers","5PM-7PM","http://2.bp.blogspot.com/-fBnyfb1KOP4/TZZB0G1HIRI/AAAAAAAAAV4/BmgJERjMqC8/s200/Ardesia2.jpg");
            createRestaurant("Balkanika","691 9th Ave Manhattan NY 10036","212-974-0300","$5 Bottled Beers, $8 Cocktails,$7 Glasses of Wine","12PM-7PM","https://huntingfortheverybest.files.wordpress.com/2013/03/balkanika.jpg");
            createRestaurant("Blue Ruin","538 9th Ave New York NY 10018","917-945-3497"," 2 for 1, $3 PBR's","5PM-8PM","http://www.aguywalksinto365bars.com/storage/1.%20blueruintopandreview-158.jpg?__SQUARESPACE_CACHEVERSION=1276786155895");
        }

    }

