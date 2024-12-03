package com.example.social_media_app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.social_media_app.R;
import com.example.social_media_app.databinding.DashboardSimpleBinding;
import com.example.social_media_app.model.Post;
import com.example.social_media_app.model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.viewHolder> {
    ArrayList<Post> lists;
    Context context;

    public DashboardAdapter(ArrayList<Post> lists, Context context) {
        this.lists = lists;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new viewHolder(LayoutInflater.from(context).inflate(R.layout.dashboard_simple,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder viewHolder, int i) {
        Post model=lists.get(i);
        Picasso.get()
                .load(model.getPostImage())
                .placeholder(R.drawable.placeholders)
                .into(viewHolder.binding.postImg);
        String descript=model.getPostDescription();
        if(!descript.isEmpty()){
            viewHolder.binding.PostDescriptionId.setText(model.getPostDescription());
            viewHolder.binding.PostDescriptionId.setVisibility(View.VISIBLE);
        }
        else{
            viewHolder.binding.PostDescriptionId.setVisibility(View.GONE);
        }
        viewHolder.binding.like.setText(model.getPostLike()+"");
        FirebaseDatabase.getInstance().getReference().child("Users")
                .child(model.getPostBy())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User user=dataSnapshot.getValue(User.class);
                        Picasso.get()
                                .load(user.getProfile())
                                .placeholder(R.drawable.placeholders)
                                .into(viewHolder.binding.Profiles);
                        viewHolder.binding.userName.setText(user.getName());
                        viewHolder.binding.about.setText(user.getProfession());

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
        FirebaseDatabase.getInstance().getReference().child("posts").child(model.getPostId()).child("likes").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    viewHolder.binding.like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.likeimg,0,0,0);
                    viewHolder.binding.like.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            FirebaseDatabase.getInstance().getReference().child("posts")
                                    .child(model.getPostId())
                                    .child("likes")
                                    .child(FirebaseAuth.getInstance().getUid())
                                    .removeValue()
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            FirebaseDatabase.getInstance().getReference().child("posts")
                                                    .child(model.getPostId())
                                                    .child("postLike")
                                                    .setValue(model.getPostLike() - 1)
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void unused) {
                                                            // Update the UI to show the "unliked" state
                                                            viewHolder.binding.like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.like, 0, 0, 0);
                                                        }
                                                    });
                                        }
                                    });
                        }
                    });
                }
                else{
                    viewHolder.binding.like.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            FirebaseDatabase.getInstance().getReference().child("posts")
                                    .child(model.getPostId())
                                    .child("likes")
                                    .child(FirebaseAuth.getInstance().getUid())
                                    .setValue(true).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            FirebaseDatabase.getInstance().getReference().child("posts").child(model.getPostId()).child("postLike").setValue(model.getPostLike()+1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    viewHolder.binding.like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.likeimg,0,0,0);
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
        DashboardSimpleBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding=DashboardSimpleBinding.bind(itemView);
        }
    }
}
