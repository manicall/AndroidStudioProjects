package com.example.lab6;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

public class BackgroundFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_background, container, false);
    }

    // обновление текстового поля
    public void setSelectedItem(String selectedItem) {
        int numOfSeason = Integer.parseInt(selectedItem);
        ImageView imageView = getView().findViewById(R.id.imageView);

        if(numOfSeason == Seasons.SUMMER)  imageView.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.summer));
        if(numOfSeason == Seasons.AUTUMN)  imageView.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.autumn));
        if(numOfSeason == Seasons.WINTER)  imageView.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.winter2));
        if(numOfSeason == Seasons.SPRING)  imageView.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.spring));

    }

}