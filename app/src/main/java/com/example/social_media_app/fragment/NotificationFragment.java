package com.example.social_media_app.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.social_media_app.R;
import com.example.social_media_app.adapter.ViewpagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class NotificationFragment extends Fragment {

    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_notification, container, false);
        viewPager=view.findViewById(R.id.ViewPager);
        tabLayout=view.findViewById(R.id.tableLayout);

        viewPager.setAdapter(new ViewpagerAdapter(getFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }
}