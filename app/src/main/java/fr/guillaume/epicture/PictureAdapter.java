package fr.guillaume.epicture;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Guillaume on 06/02/2018.
 */

public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.MyViewHolder> {
    private List<Picture> _PicturesList;
    private Context _Ctx;
    private ApiImgur _ApiImgur = null;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView _Title;
        public TextView _Date;
        public ImageView _Picture;
        public Button _AddFavorite;

        public MyViewHolder(View view) {
            super(view);

            _Title = view.findViewById(R.id.tv_title);
            _Date = view.findViewById(R.id.tv_date);
            _Picture = view.findViewById(R.id.iv_picture);
            _AddFavorite = view.findViewById(R.id.bt_add_favorite);
        }
    }


    public PictureAdapter(List<Picture> moviesList, Context ctx, ApiImgur api) {
        _PicturesList = moviesList;
        _Ctx = ctx;
        _ApiImgur = api;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.picture_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Picture picture = _PicturesList.get(position);
        holder._Title.setText(picture.getTitle());
        holder._Date.setText(picture.getDate());
        Picasso.with(_Ctx).load(picture.getLink()).into(holder._Picture);
        if (picture.getFavorite())
            holder._AddFavorite.setVisibility(View.INVISIBLE);
        holder._AddFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _ApiImgur.addToFavorite(picture.getHash());
            }
        });
    }

    @Override
    public int getItemCount() {
        return _PicturesList.size();
    }
}
