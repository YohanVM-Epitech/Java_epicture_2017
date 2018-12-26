package fr.guillaume.epicture;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment implements PictureGetter{

    ApiImgur _ApiImgur = null;

    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        _ApiImgur = new ApiImgur(this);
        _ApiImgur.getAllFavorite();
    }

    @Override
    public void updatePicture(final List<Picture> picturesList) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                RecyclerView recyclerView = getActivity().findViewById(R.id.recycler_picture);
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setAdapter(new PictureAdapter(picturesList, getActivity(), _ApiImgur));
            }
        });
    }

}
