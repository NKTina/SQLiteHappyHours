package firstapp.example.com.sqlhappyhr;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by martine.nezerwa on 12/8/14.
 */
public class async extends AsyncTask<String,Integer,Bitmap>
{
    private Activity activity;

    public async(Activity myActivity)
    {
        activity = myActivity;
    }
    @Override
    protected Bitmap doInBackground(String... urls)
    {
        publishProgress(1);

        // get picture from internet
        try
        {
            URL url = new URL(urls[0]);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK)
            {
                throw new Exception("Failed to connect");
            }
            InputStream input = connection.getInputStream();
            publishProgress(0);
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            input.close();
            return bitmap;
        }
        catch (Exception e)
        {
            Log.e("Image","Failed to load image",e);
            Log.e("error",e.getMessage());
        }
        return null;
    }
    @Override
    protected void onPostExecute(Bitmap img)
    {
        ImageView iv = (ImageView) activity.findViewById(R.id.restImage); // for rest_details activity and the image there
        if (iv != null && img!= null)
        {
            iv.setImageBitmap(img);
        }
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(Integer... values)
    {
        super.onProgressUpdate(values);
    }

}
