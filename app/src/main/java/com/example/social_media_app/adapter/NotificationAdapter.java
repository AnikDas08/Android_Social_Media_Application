package com.example.social_media_app.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.social_media_app.CommentActivity;
import com.example.social_media_app.R;
import com.example.social_media_app.databinding.NotificationtabSampleBinding;
import com.example.social_media_app.model.Notification;
import com.example.social_media_app.model.User;
import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.viewholder> {
    ArrayList<Notification> lists;
    Context context;

    public NotificationAdapter(ArrayList<Notification> lists, Context context) {
        this.lists = lists;
        this.context = context;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new viewholder(LayoutInflater.from(context).inflate(R.layout.notificationtab_sample,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder viewholder, int i) {
        Notification model=lists.get(i);
        String type=model.getType();
        String time= TimeAgo.using(model.getNotificationAt());
        viewholder.binding.notificationTime.setText(time);
        FirebaseDatabase.getInstance().getReference()
                .child("Users")
                .child(model.getNotificationBy())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User user=dataSnapshot.getValue(User.class);
                        Picasso.get()
                                .load(user.getProfile())
                                .placeholder(R.drawable.placeholders)
                                .into(viewholder.binding.notificationId);
                        if(model.getNotificationBy().equals(FirebaseAuth.getInstance().getUid())){
                            if(type.equals("like")){
                                viewholder.binding.notifications.setText(Html.fromHtml("<b>You</b>"+" like your post"));
                            }
                            else if(type.equals("comment")){
                                viewholder.binding.notifications.setText(Html.fromHtml("<b>You</b>"+" commented your post"));
                            }
                        }
                        else{
                            if(type.equals("like")){
                                viewholder.binding.notifications.setText(Html.fromHtml("<b>"+user.getName()+"</b>"+" like your post"));
                            }
                            else if(type.equals("comment")){
                                viewholder.binding.notifications.setText(Html.fromHtml("<b>"+user.getName()+"</b>"+" commented your post"));
                            }
                            else{
                                viewholder.binding.notifications.setText(Html.fromHtml("<b>"+user.getName()+"</b>"+" start to follow you"));
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
        viewholder.binding.OpenNotificationViewId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!type.equals("follow")) {
                    FirebaseDatabase.getInstance().getReference().child("notification").child(model.getNotificationBy()).child(model.getNotificationId()).child("check").setValue(true);
                    viewholder.binding.OpenNotificationViewId.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    Intent intent = new Intent(context, CommentActivity.class);
                    intent.putExtra("postId", model.getPostId());
                    intent.putExtra("postBy", model.getPostBy());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });

        Boolean check=model.isCheck();
        if(check==true){
            viewholder.binding.OpenNotificationViewId.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }
    public class viewholder extends RecyclerView.ViewHolder{
        NotificationtabSampleBinding binding;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            binding=NotificationtabSampleBinding.bind(itemView);
        }
    }
}
