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
import com.example.social_media_app.model.StoryModel;

import java.util.ArrayList;


public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.viewHolder> {

    ArrayList<StoryModel> lists;
    Context context;

    public StoryAdapter(ArrayList<StoryModel> lists, Context context) {
        this.lists = lists;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new viewHolder(LayoutInflater.from(context).inflate(R.layout.story_design,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder viewHolder, int i) {
        StoryModel model=lists.get(i);
        viewHolder.story.setImageResource(model.getStory());
        viewHolder.storytype.setImageResource(model.getStoryType());
        viewHolder.profile.setImageResource(model.getProfile());
        viewHolder.name.setText(model.getName());
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        ImageView story,storytype,profile;
        TextView name;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            story=itemView.findViewById(R.id.StoryImage);
            storytype=itemView.findViewById(R.id.Storytype);
            profile=itemView.findViewById(R.id.ProfileImage);
            name=itemView.findViewById(R.id.Name);
        }
    }
}
