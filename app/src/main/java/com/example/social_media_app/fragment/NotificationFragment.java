package com.example.social_media_app.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.social_media_app.R;
import com.example.social_media_app.adapter.NotificationAdapter;
import com.example.social_media_app.adapter.ViewpagerAdapter;
import com.example.social_media_app.model.Notification;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NotificationFragment extends Fragment {

    ViewPager viewPager;
    TabLayout tabLayout;
    RecyclerView recyclerView;
    ArrayList<Notification> list;
    FirebaseDatabase database;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database=FirebaseDatabase.getInstance();

    }

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_notification, container, false);
        /*viewPager=view.findViewById(R.id.ViewPager);
        tabLayout=view.findViewById(R.id.tableLayout);

        viewPager.setAdapter(new ViewpagerAdapter(getFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);*/

        recyclerView=view.findViewById(R.id.NotificationRec);
        list=new ArrayList<>();

        NotificationAdapter adapter=new NotificationAdapter(list,getContext());
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        database.getReference()
                .child("notification")
                .child(FirebaseAuth.getInstance().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        list.clear();
                        for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                            Notification notification=snapshot.getValue(Notification.class);
                            notification.setNotificationId(snapshot.getKey());
                            list.add(notification);
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

        return view;
    }
}