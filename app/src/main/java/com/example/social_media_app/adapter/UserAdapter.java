package com.example.social_media_app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.social_media_app.R;
import com.example.social_media_app.databinding.UsersSampleBinding;
import com.example.social_media_app.model.FollowModel;
import com.example.social_media_app.model.User;
import com.google.android.gms.common.internal.ConnectionTelemetryConfiguration;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.viewHolder>{
    ArrayList<User> lists;
    Context context;

    public UserAdapter(ArrayList<User> lists, Context context) {
        this.lists = lists;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new viewHolder(LayoutInflater.from(context).inflate(R.layout.users_sample,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder viewHolder, int i) {
        User user=lists.get(i);
        Picasso.get()
                .load(user.getProfile())
                .into(viewHolder.binding.ProfileImage);
        viewHolder.binding.name.setText(user.getName());
        viewHolder.binding.profession.setText(user.getProfession());



        FirebaseDatabase.getInstance().getReference()
                        .child("Users")
                                .child(user.getUserId())
                                        .child("followers")
                                                .child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            viewHolder.binding.followButton.setText("following");
                            viewHolder.binding.followButton.setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.active_button));
                            viewHolder.binding.followButton.setTextColor(context.getResources().getColor(R.color.black));
                            viewHolder.binding.followButton.setEnabled(false);
                        }
                        else{
                            viewHolder.binding.followButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    FollowModel model=new FollowModel();
                                    model.setFollowedBy(FirebaseAuth.getInstance().getUid());
                                    model.setFollowedAt(new Date().getTime());
                                    FirebaseDatabase.getInstance().getReference()
                                            .child("Users")
                                            .child(user.getUserId())
                                            .child("followers")
                                            .child(FirebaseAuth.getInstance().getUid())
                                            .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    FirebaseDatabase.getInstance().getReference()
                                                            .child("Users")
                                                            .child(user.getUserId())
                                                            .child("followerCount")
                                                            .setValue(user.getFollowerCount()+1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void unused) {
                                                                    Toast.makeText(context, "you followed"+user.getName(), Toast.LENGTH_SHORT).show();
                                                                }
                                                            });
                                                }
                                            });
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        UsersSampleBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding=UsersSampleBinding.bind(itemView);
        }
    }
}
