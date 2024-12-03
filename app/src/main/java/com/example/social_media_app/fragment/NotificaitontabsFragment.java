package com.example.social_media_app.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.social_media_app.R;
import com.example.social_media_app.adapter.NotificationAdapter;
import com.example.social_media_app.model.NotificationtabModel;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;


public class NotificaitontabsFragment extends Fragment {
    ArrayList<NotificationtabModel> list;
    RecyclerView notificationRecyler;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_notificaitontabs, container, false);
        notificationRecyler=view.findViewById(R.id.NotificatinRecylervi);

        list=new ArrayList<>();
        list.add(new NotificationtabModel(R.drawable.anik,"<b>Anik Das</b> comment your photo","just now"));
        list.add(new NotificationtabModel(R.drawable.moshiur,"<b>Anik Das</b> mention you in a post you share","just now"));
        list.add(new NotificationtabModel(R.drawable.rifat,"<b>Anik Das</b> like your photo","1 minute ago"));
        list.add(new NotificationtabModel(R.drawable.anik,"<b>Rifat</b> comment your photo","just now"));
        list.add(new NotificationtabModel(R.drawable.moshiur,"<b>Anik Das</b> mention you in a post you share","just now"));
        list.add(new NotificationtabModel(R.drawable.rifat,"<b>Anik Das</b> like your photo","just now"));
        list.add(new NotificationtabModel(R.drawable.live,"he like you","just now"));
        list.add(new NotificationtabModel(R.drawable.live,"he like you","just now"));
        list.add(new NotificationtabModel(R.drawable.live,"he like you","just now"));

        NotificationAdapter adapter=new NotificationAdapter(list,getContext());
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        notificationRecyler.setLayoutManager(layoutManager);
        notificationRecyler.setAdapter(adapter);
        return view;
    }
}