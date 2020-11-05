package com.example.plasmadonator.ui.send;

import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.plasmadonator.R;
import com.squareup.picasso.Picasso;

public class SendFragment extends Fragment {
    String data="";


    ImageView image,image1,image2,image3;
    public static TextView confirmed,active,deceased,recovered;
    Button button,button1;
    ScrollView scroll;
    private LocationManager locationManager;

    private SendViewModel sendViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sendViewModel =
                ViewModelProviders.of(this).get(SendViewModel.class);
        View root = inflater.inflate(R.layout.fragment_send, container, false);
        fetchData a=new fetchData();

        scroll=root.findViewById(R.id.scroll);

        button=root.findViewById(R.id.button);
        button1=root.findViewById(R.id.button1);
        image=root.findViewById(R.id.image);
        image1=root.findViewById(R.id.image1);
        image2=root.findViewById(R.id.image2);
        image3=root.findViewById(R.id.image3);
        confirmed=root.findViewById(R.id.confirmed);
        deceased=root.findViewById(R.id.deceased);
        active=root.findViewById(R.id.active);
        recovered=root.findViewById(R.id.recovered);


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), web.class);
                intent.putExtra("url","https://www.who.int/news-room/q-a-detail/q-a-coronaviruses#:~:text=symptoms");
                startActivity(intent);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), web.class);
                intent.putExtra("url","https://www.mohfw.gov.in/");
                startActivity(intent);
            }
        });

        Picasso.get().load("https://www.mohfw.gov.in/assets/images/icon-infected.png").into(image);
        Picasso.get().load("https://www.mohfw.gov.in/assets/images/icon-infected.png").into(image1);
        Picasso.get().load("https://www.mohfw.gov.in/assets/images/icon-active.png").into(image2);
        Picasso.get().load("https://www.mohfw.gov.in/assets/images/icon-inactive.png").into(image3);
        a.execute();



        return root;
    }
}