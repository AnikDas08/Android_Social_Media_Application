package com.example.social_media_app.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.social_media_app.R;
import com.example.social_media_app.adapter.NotificationAdapter;
import com.example.social_media_app.model.Notification;

import java.util.ArrayList;


public class NotificaitontabsFragment extends Fragment {
    ArrayList<Notification> list;
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

        /*NotificationAdapter adapter=new NotificationAdapter(list,getContext());
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        notificationRecyler.setLayoutManager(layoutManager);
        notificationRecyler.setAdapter(adapter);*/
        return view;
    }
}