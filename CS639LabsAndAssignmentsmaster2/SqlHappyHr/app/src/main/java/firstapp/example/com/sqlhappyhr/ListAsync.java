package firstapp.example.com.sqlhappyhr;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by martine.nezerwa on 12/11/14.
 */
public class ListAsync extends AsyncTask<String,Integer,Bitmap>
{
    //private Activity activity;
    private final WeakReference imageViewReference;

   // public ListAsync(Activity myActivity)
   // {
   //     activity = myActivity;
   // }
    public ListAsync(ImageView imageView)
    {
        imageViewReference = new WeakReference<ImageView>(imageView);
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
            Log.e("Image", "Failed to load image", e);
            Log.e("error",e.getMessage());
        }
        return null;
    }
    @Override
    protected void onPostExecute(Bitmap img)
    {
        if (isCancelled())
        {
            img = null;
        }
        if (imageViewReference != null)
        {
            ImageView imageView = (ImageView) imageViewReference.get();

            if (imageView != null && img != null)
            {
                imageView.setImageBitmap(img);
            }
            else
            {
                imageView.setImageDrawable(imageView.getContext().getResources().getDrawable(R.drawable.happyhour));
            }
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
