package com.example.social_media_app.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.social_media_app.R;
import com.example.social_media_app.model.NotificationtabModel;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.viewholder> {
    ArrayList<NotificationtabModel> lists;
    Context context;

    public NotificationAdapter(ArrayList<NotificationtabModel> lists, Context context) {
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
        NotificationtabModel model=lists.get(i);
        viewholder.profile.setImageResource(model.getProfile());
        viewholder.notification.setText(Html.fromHtml(model.getNotification()));
        viewholder.time.setText(model.getTime());
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{
        ImageView profile;
        TextView notification,time;
        public viewholder(@NonNull View itemView) {
            super(itemView);

            profile=itemView.findViewById(R.id.notificationId);
            notification=itemView.findViewById(R.id.notifications);
            time=itemView.findViewById(R.id.notificationTime);

        }
    }
}
