package com.example.social_media_app.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.social_media_app.R;
import com.example.social_media_app.adapter.UserAdapter;
import com.example.social_media_app.databinding.FragmentSearchBinding;
import com.example.social_media_app.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class SearchFragment extends Fragment {
    ArrayList<User> list=new ArrayList<>();
    FragmentSearchBinding binding;
    FirebaseAuth mauths;
    FirebaseDatabase database;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mauths=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentSearchBinding.inflate(inflater, container, false);

        //list=new ArrayList<>();

        UserAdapter adapter=new UserAdapter(list,getContext());
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        binding.userRecyler.setLayoutManager(layoutManager);
        binding.userRecyler.setAdapter(adapter);

        database.getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    User user=snapshot.getValue(User.class);
                    user.setUserId(snapshot.getKey());
                    if(!snapshot.getKey().equals(FirebaseAuth.getInstance().getUid())){
                        list.add(user);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return binding.getRoot();
    }
}