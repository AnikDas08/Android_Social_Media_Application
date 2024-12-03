package com.example.social_media_app.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.social_media_app.fragment.NotificaitontabsFragment;
import com.example.social_media_app.fragment.RequestFragment;

public class ViewpagerAdapter extends FragmentPagerAdapter {
    public ViewpagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:return new NotificaitontabsFragment();
            case 1:return new RequestFragment();
            default:return new NotificaitontabsFragment();
        }

    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title=null;
        if (position==0){
            title="notification";
        }
        if(position==1){
            title="requ";
        }
        return title;
    }
}
