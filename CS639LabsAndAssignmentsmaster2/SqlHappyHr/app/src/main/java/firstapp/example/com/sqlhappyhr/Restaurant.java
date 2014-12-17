package firstapp.example.com.sqlhappyhr;

import android.app.Activity;
import android.graphics.Bitmap;

import java.security.SecureRandom;

/**
 * Created by murlidhar on 12/5/14.
 */
public class Restaurant extends Activity
{
    Integer id = null;
    String name = null;
    String address = null;
    String phone = null;
    String offers = null;
    String time = null;
    String image = null;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getAddress()
    {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOffers() {
        return offers;
    }
    public void setOffers(String offer) {
        this.offers = offer;
    }

    public void setTime(String time) {
        this.time = time;
    }
    public String getTime() {
        return time;
    }

    public void setImage(String image)
    {
        this.image = image;
    }
    public String getImage()
    {
        return image;
    }
}
