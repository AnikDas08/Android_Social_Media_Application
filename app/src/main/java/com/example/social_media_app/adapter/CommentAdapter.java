package com.example.social_media_app.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.social_media_app.R;
import com.example.social_media_app.databinding.CommentSimpleBinding;
import com.example.social_media_app.model.Comment;
import com.example.social_media_app.model.User;
import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.viewHolder> {
    ArrayList<Comment> lists;
    Context context;

    public CommentAdapter(ArrayList<Comment> lists, Context context) {
        this.lists = lists;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.comment_simple,viewGroup,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder viewHolder, int i) {
        Comment comment=lists.get(i);
        String time= TimeAgo.using(comment.getCommentAt());
        viewHolder.binding.TimeId.setText(time);
        FirebaseDatabase.getInstance().getReference().child("Users").child(comment.getCommentBy()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user=dataSnapshot.getValue(User.class);
                viewHolder.binding.CommentId.setText(Html.fromHtml("<b>"+user.getName()+"</b>"+" "+comment.getCommentDescripti()));
                Picasso.get()
                        .load(user.getProfile())
                        .placeholder(R.drawable.placeholders)
                        .into(viewHolder.binding.ProfileImage);
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
        CommentSimpleBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding=CommentSimpleBinding.bind(itemView);
        }
    }
}
