package com.example.social_media_app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.pm.PackageInfoCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.social_media_app.R;
import com.example.social_media_app.databinding.StoryDesignBinding;
import com.example.social_media_app.model.StoryModel;
import com.example.social_media_app.model.User;
import com.example.social_media_app.model.UserStory;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import omari.hamza.storyview.StoryView;
import omari.hamza.storyview.callback.StoryClickListeners;
import omari.hamza.storyview.model.MyStory;


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
        return new viewHolder(LayoutInflater.from(context).inflate(R.layout.story_design, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder viewHolder, int i) {
        StoryModel story = lists.get(i);
        if (story.getStories().size() > 0) {
            UserStory lastSt = story.getStories().get(story.getStories().size() - 1);
            Picasso.get()
                    .load(lastSt.getImage())
                    .into(viewHolder.binding.StoryImage);
            viewHolder.binding.statuscircle.setPortionsCount(story.getStories().size());
            FirebaseDatabase.getInstance().getReference()
                    .child("Users")
                    .child(story.getStoryBy()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            User user = dataSnapshot.getValue(User.class);
                            Picasso.get()
                                    .load(user.getProfile())
                                    .into(viewHolder.binding.ProfileImage);
                            viewHolder.binding.Name.setText(user.getName());
                            viewHolder.binding.StoryImage.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    ArrayList<MyStory> myStories = new ArrayList<>();

                                    for (UserStory stories : story.getStories()) {
                                        myStories.add(new MyStory(
                                                stories.getImage()
                                        ));
                                    }
                                    new StoryView.Builder(((AppCompatActivity) context).getSupportFragmentManager())
                                            .setStoriesList(myStories) // Required
                                            .setStoryDuration(5000) // Default is 2000 Millis (2 Seconds)
                                            .setTitleText(user.getName()) // Default is Hidden
                                            .setTitleLogoUrl(user.getProfile()) // Default is Hidden
                                            .setStoryClickListeners(new StoryClickListeners() {
                                                @Override
                                                public void onDescriptionClickListener(int position) {
                                                    //your action
                                                }

                                                @Override
                                                public void onTitleIconClickListener(int position) {
                                                    //your action
                                                }
                                            }) // Optional Listeners
                                            .build() // Must be called before calling show method
                                            .show();
                                }
                            });
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
        }
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        StoryDesignBinding binding;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding = StoryDesignBinding.bind(itemView);
        }
    }
}
