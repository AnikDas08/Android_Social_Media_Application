package com.example.social_media_app.fragment;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.example.social_media_app.R;
import com.example.social_media_app.adapter.DashboardAdapter;
import com.example.social_media_app.adapter.StoryAdapter;
import com.example.social_media_app.model.Post;
import com.example.social_media_app.model.StoryModel;
import com.example.social_media_app.model.UserStory;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.makeramen.roundedimageview.RoundedImageView;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Date;

public class HomeFragment extends Fragment {
    ShimmerRecyclerView dashboardrecyl,storyrecycler;
    ArrayList<StoryModel> list;
    ArrayList<Post> dashboardModel=new ArrayList<>();
    FirebaseDatabase database;
    FirebaseAuth auth;
    RoundedImageView addstory;
    ActivityResultLauncher<String> gelary;
    FirebaseStorage storage;
    ProgressDialog dialog;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialog=new ProgressDialog(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        dashboardrecyl=view.findViewById(R.id.Dashboardrecle);
        dashboardrecyl.showShimmerAdapter();
        storyrecycler=view.findViewById(R.id.Storyrecler);
        storyrecycler.showShimmerAdapter();

        database=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();
        storage=FirebaseStorage.getInstance();
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setTitle("Stroy");
        dialog.setMessage("wait uploading");
        dialog.setCancelable(false);
        list=new ArrayList<>();

        StoryAdapter storyAdapter=new StoryAdapter(list,getContext());
        LinearLayoutManager linear=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        storyrecycler.setLayoutManager(linear);
        storyrecycler.setNestedScrollingEnabled(false);

        database.getReference()
                .child("stories").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                                StoryModel story=new StoryModel();
                                story.setStoryBy(snapshot.getKey());
                                story.setStoryAt(snapshot.child("postedBy").getValue(Long.class));

                                ArrayList<UserStory> stories=new ArrayList<>();
                                for(DataSnapshot snapshot1:snapshot.child("userStories").getChildren()){
                                    UserStory userStory=snapshot1.getValue(UserStory.class);
                                    stories.add(userStory);
                                }
                                story.setStories(stories);
                                list.add(story);
                            }
                            storyrecycler.setAdapter(storyAdapter);
                            storyrecycler.hideShimmerAdapter();
                            storyAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



        DashboardAdapter dashboardAdapter=new DashboardAdapter(dashboardModel,getContext());
        LinearLayoutManager linears=new LinearLayoutManager(getContext());
        dashboardrecyl.setLayoutManager(linears);
        dashboardrecyl.setNestedScrollingEnabled(false);

        database.getReference().child("posts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dashboardModel.clear();
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Post post=snapshot.getValue(Post.class);
                    post.setPostId(snapshot.getKey());
                    dashboardModel.add(post);
                }
                dashboardrecyl.setAdapter(dashboardAdapter);
                dashboardrecyl.hideShimmerAdapter();
                dashboardAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        addstory=view.findViewById(R.id.imageView1);
        addstory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gelary.launch("image/*");
            }
        });
        gelary=registerForActivityResult(new ActivityResultContracts.GetContent()
                , new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri ur) {
                        dialog.show();
                        addstory.setImageURI(ur);
                        final StorageReference storageReference=storage.getReference()
                                .child("stories")
                                .child(auth.getUid())
                                .child(new Date().getTime()+"");
                        storageReference.putFile(ur).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        StoryModel story=new StoryModel();
                                        story.setStoryAt(new Date().getTime());
                                        database.getReference()
                                                .child("stories")
                                                .child(auth.getUid())
                                                .child("postedBy")
                                                .setValue(story.getStoryAt()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        UserStory userStories=new UserStory(uri.toString(),story.getStoryAt());
                                                        database.getReference()
                                                                .child("stories")
                                                                .child(FirebaseAuth.getInstance().getUid())
                                                                .child("userStories")
                                                                .push()
                                                                .setValue(userStories).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                    @Override
                                                                    public void onSuccess(Void unused) {
                                                                        dialog.dismiss();
                                                                    }
                                                                });
                                                    }
                                                });
                                    }
                                });
                            }
                        });
                    }
                });
        return view;
    }
}