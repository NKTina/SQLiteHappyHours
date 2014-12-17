package firstapp.example.com.sqlhappyhr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by martine.nezerwa on 12/15/14.
 */
public class CustomListAdapter extends BaseAdapter
{
    Context context;
    ArrayList<Restaurant> restaurantArrayList;

    public CustomListAdapter (Context context,ArrayList<Restaurant> list)
    {
        this.context = context;
        restaurantArrayList = list;
    }

    @Override
    public int getCount()
    {
        return restaurantArrayList.size();
    }
    @Override
    public Object getItem(int position)
    {
        return restaurantArrayList.get(position);
    }
    @Override
    public long getItemId (int position)
    {
        return position;
    }
    @Override
    public View getView (int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder;
        Restaurant restaurants = restaurantArrayList.get(position);
        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.rest_list,null);
            holder = new ViewHolder();
            holder.nameView = (TextView)convertView.findViewById(R.id.name);
            holder.offerView = (TextView)convertView.findViewById(R.id.offers);
            holder.timeView = (TextView)convertView.findViewById(R.id.time);
            holder.imageView = (ImageView)convertView.findViewById(R.id.imageView2);

            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.nameView.setText(restaurants.getName());
        holder.offerView.setText(restaurants.getOffers());
        holder.timeView.setText(restaurants.getTime());

        if (holder.imageView != null)
        {
            new ListAsync(holder.imageView).execute(restaurants.getImage());
        }

        return convertView;
    }
    static class ViewHolder
    {
        TextView nameView,offerView,timeView;
        ImageView imageView;
    }

}
