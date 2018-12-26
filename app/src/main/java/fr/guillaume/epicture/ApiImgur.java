package fr.guillaume.epicture;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Guillaume on 07/02/2018.
 */

public class ApiImgur {

    PictureGetter _PictureGetter = null;

    public ApiImgur(PictureGetter pictureGetter) {
        _PictureGetter = pictureGetter;
    }

    public void getImageByName(String name)
    {
        final List<Picture> picturesList = new ArrayList<>();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.imgur.com/3/gallery/search/time?q=" + name)
                .header("Authorization","Client-ID 7b0f1c199ebd900")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("OKHTTP", "An error has occurred " + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                JSONObject data = null;
                int nbItems = 0;
                try {
                    data = new JSONObject(response.body().string());
                    JSONArray items = data.getJSONArray("data");
                    if (items.length() > 10)
                        nbItems = 10;
                    else
                        nbItems = items.length();
                    for (int i = 0; i < nbItems; i++) {
                        JSONObject item = items.getJSONObject(i);
                        picturesList.add(new PictureJsonParser().parse(item, false));
                    }
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
                _PictureGetter.updatePicture(picturesList);
            }
        });
    }

    public void addToFavorite(String hash)
    {
        OkHttpClient client = new OkHttpClient();
        RequestBody reqbody = RequestBody.create(null, new byte[0]);
        Request.Builder formBody = new Request.Builder()
                .url("https://api.imgur.com/3/image/"+hash+"/favorite")
                .header("Authorization","Bearer 78468a62541a842b01f30ed130f3173bf67c7cd0")
                .method("POST", reqbody).header("Content-Length", "0");
        client.newCall(formBody.build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }

    public void getAllFavorite()
    {
        final List<Picture> picturesList = new ArrayList<>();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.imgur.com/3/account/JavaEpicture1/favorites")
                .header("Authorization","Bearer 78468a62541a842b01f30ed130f3173bf67c7cd0")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("OKHTTP", "An error has occurred " + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                JSONObject data = null;
                int nbItems = 0;
                try {
                    data = new JSONObject(response.body().string());
                    JSONArray items = data.getJSONArray("data");
                    if (items.length() > 10)
                        nbItems = 10;
                    else
                        nbItems = items.length();
                    for (int i = 0; i < nbItems; i++) {
                        JSONObject item = items.getJSONObject(i);
                        picturesList.add(new PictureJsonParser().parse(item, true));
                    }
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
                _PictureGetter.updatePicture(picturesList);
            }
        });
    }
}
