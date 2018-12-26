package fr.guillaume.epicture;

import android.text.format.DateFormat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Guillaume on 07/02/2018.
 */

public class PictureJsonParser {
    public Picture parse(JSONObject item, Boolean favorite) throws JSONException {
        Picture picture = new Picture();
        picture.setTitle(item.getString("title"));
        picture.setDescription(item.getString("description"));
        picture.setDate(getDate(item.getLong("datetime") * 1000));
        picture.setFavorite(item.getBoolean("favorite"));

        //Get image
        if (!favorite) {
            JSONArray itemImages = item.getJSONArray("images");
            JSONObject itemImage = itemImages.getJSONObject(0);
            picture.setLink(itemImage.getString("link"));
            picture.setHash(itemImage.getString("id"));
        }
        else
        {
            picture.setHash(item.getString("id"));
            picture.setLink(item.getString("link"));
        }
        return picture;
    }

    private String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.FRANCE);
        cal.setTimeInMillis(time);
        String date = DateFormat.format("dd-MM-yyyy", cal).toString();
        return date;
    }
}
