package fr.guillaume.epicture;


import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;

import java.io.File;

import fr.guillaume.epicture.imgurmodel.Upload;

/**
 * A simple {@link Fragment} subclass.
 */
public class UploadFragment extends Fragment {

    private FloatingActionButton buttonUpload;
    private Button buttonSearch;
    private Upload upload;
    private Intent intent;
    private Uri image = null;
    private ImageView imageView;
    public static final int RESULT_LOAD_IMAGE = 1;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        buttonSearch = getActivity().findViewById(R.id.buttonSearch);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, RESULT_LOAD_IMAGE);
                new Thread(new Runnable() {
                    public void run() {
                        while (image == null) {
                            image = ((MainActivity) getActivity()).getSelectedImage();
                            if (image != null) {
                                ((MainActivity) getActivity()).removeSelectedImage();
                                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                                Cursor cursor = getActivity().getContentResolver().query(image, filePathColumn, null, null, null);
                                cursor.moveToFirst();
                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                String picturePath = cursor.getString(columnIndex);
                                cursor.close();
                                imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                            }
                        }
                    }
                }).start();
            }
        });
        buttonUpload = getActivity().findViewById(R.id.fab);
        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (image != null) {
                    upload = new Upload();

                    upload.image = new File(image.getPath());
                    upload.title = "test";
                    upload.description = "description";
                    imageView.setImageURI(null);
                }
            }
        });
    }

    public UploadFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_upload, container, false);
    }

}
