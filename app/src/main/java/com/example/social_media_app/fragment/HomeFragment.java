package com.example.social_media_app.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.social_media_app.R;
import com.example.social_media_app.adapter.DashboardAdapter;
import com.example.social_media_app.adapter.StoryAdapter;
import com.example.social_media_app.model.Post;
import com.example.social_media_app.model.StoryModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    RecyclerView storyrecycler,dashboardrecyl;
    ArrayList<StoryModel> list;
    ArrayList<Post> dashboardModel=new ArrayList<>();
    FirebaseDatabase database;
    FirebaseAuth auth;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        storyrecycler=view.findViewById(R.id.Storyrecler);

        database=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();

        list=new ArrayList<>();
        list.add(new StoryModel(R.drawable.anik,R.drawable.live,R.drawable.anik,"Anik Das"));
        list.add(new StoryModel(R.drawable.rifat,R.drawable.rifat,R.drawable.live,"Rifat"));
        list.add(new StoryModel(R.drawable.moshiur,R.drawable.anik,R.drawable.pluss,"Moshiur Rahman"));
        list.add(new StoryModel(R.drawable.anik,R.drawable.anik,R.drawable.live,"Anik Das"));
        list.add(new StoryModel(R.drawable.rifat,R.drawable.anik,R.drawable.live,"Anik Das"));
        list.add(new StoryModel(R.drawable.moshiur,R.drawable.anik,R.drawable.live,"Anik Das"));

        StoryAdapter storyAdapter=new StoryAdapter(list,getContext());
        LinearLayoutManager linear=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        storyrecycler.setLayoutManager(linear);
        storyrecycler.setNestedScrollingEnabled(false);
        storyrecycler.setAdapter(storyAdapter);

        dashboardrecyl=view.findViewById(R.id.Dashboardrecle);


        DashboardAdapter dashboardAdapter=new DashboardAdapter(dashboardModel,getContext());
        LinearLayoutManager linears=new LinearLayoutManager(getContext());
        dashboardrecyl.setLayoutManager(linears);
        dashboardrecyl.setNestedScrollingEnabled(false);
        dashboardrecyl.setAdapter(dashboardAdapter);

        database.getReference().child("posts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dashboardModel.clear();
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Post post=snapshot.getValue(Post.class);
                    post.setPostId(snapshot.getKey());
                    dashboardModel.add(post);
                }
                dashboardAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }
}