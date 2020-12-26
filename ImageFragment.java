package org.foodbankmarket;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;
import java.util.List;

public class ImageFragment extends Fragment {

    View view;

    public static ImageFragment newInstance(String imageU) {

        Bundle args = new Bundle();
        args.putString("imageU", imageU);

        ImageFragment imageFragment = new ImageFragment();
        imageFragment.setArguments(args);
        return imageFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.image_fragment, container, false);
        PhotoView imageView = view.findViewById(R.id.imagefragment_imageview);
        GlideApp.with(container.getContext()).load(getArguments().getString("imageU")).into(imageView);
        return view;
    }
}
