package com.example.social_media_app.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.social_media_app.R;
import com.example.social_media_app.adapter.FollowersAdapter;
import com.example.social_media_app.databinding.FragmentProfileBinding;
import com.example.social_media_app.model.FollowModel;
import com.example.social_media_app.model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ProfileFragment extends Fragment {
    ArrayList<FollowModel> list;
    FragmentProfileBinding binding;
    FirebaseStorage storage;
    FirebaseAuth Auth;
    FirebaseDatabase database;
    //User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Auth=FirebaseAuth.getInstance();
        storage=FirebaseStorage.getInstance();
        database=FirebaseDatabase.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding= FragmentProfileBinding.inflate(inflater, container, false);


            database.getReference().child("Users").child(Auth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        User users=dataSnapshot.getValue(User.class);
                        Picasso.get()
                                .load(users.getCoverPhoto())
                                .placeholder(R.drawable.placeholders)
                                .into(binding.Image);
                        binding.UserName.setText(users.getName());
                        binding.Profession.setText(users.getProfession());
                        Picasso.get().load(users.getProfile()).placeholder(R.drawable.placeholders).into(binding.ProfileImage);
                        Toast.makeText(getContext(), "ImageShow", Toast.LENGTH_SHORT).show();
                        binding.folowers.setText(users.getFollowerCount()+"");

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        list=new ArrayList<>();

        FollowersAdapter adapter=new FollowersAdapter(list,getContext());
        LinearLayoutManager linear=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        binding.FriendRecyler.setLayoutManager(linear);
        binding.FriendRecyler.setNestedScrollingEnabled(false);
        binding.FriendRecyler.setAdapter(adapter);

        database.getReference().child("Users").child(Auth.getUid())
                        .child("followers").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                            FollowModel model=snapshot.getValue(FollowModel.class);
                            list.add(model);

                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

        binding.chaCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,11);
            }
        });

        binding.Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,22);
            }
        });


        return binding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==11){
            if(data.getData()!=null){
                Uri uri=data.getData();
                //binding.Image.setImageURI(uri);
                final StorageReference reference=storage.getReference().child("cover_photo")
                        .child(FirebaseAuth.getInstance().getUid());
                reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getContext(), "Cover photo add okay", Toast.LENGTH_LONG).show();
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                database.getReference().child("Users").child(Auth.getUid()).child("CoverPhoto").setValue(uri.toString());
                            }
                        });
                    }
                });
            }
        }
        else{
            if(data.getData()!=null){
                Uri uri=data.getData();
                final StorageReference reference=storage.getReference().child("profile_image")
                        .child(FirebaseAuth.getInstance().getUid());
                reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getContext(), "Profile photo add okay", Toast.LENGTH_LONG).show();
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                database.getReference().child("Users").child(Auth.getUid()).child("profile").setValue(uri.toString());
                            }
                        });
                    }
                });
            }
        }

    }
}