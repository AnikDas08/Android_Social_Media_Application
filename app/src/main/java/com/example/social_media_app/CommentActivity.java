package com.example.social_media_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.social_media_app.adapter.CommentAdapter;
import com.example.social_media_app.databinding.ActivityCommentBinding;
import com.example.social_media_app.model.Comment;
import com.example.social_media_app.model.Notification;
import com.example.social_media_app.model.Post;
import com.example.social_media_app.model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;

public class CommentActivity extends AppCompatActivity {
    ActivityCommentBinding binding;
    Intent intent;
    String postId;
    String postBy;
    FirebaseAuth mauth;
    FirebaseDatabase database;
    ArrayList<Comment> list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityCommentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        intent=getIntent();
        setSupportActionBar(binding.toolbar);
        CommentActivity.this.setTitle("Comment");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        postId=intent.getStringExtra("postId");
        postBy=intent.getStringExtra("postBy");
        mauth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        database.getReference()
                .child("posts")
                .child(postId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Post post=dataSnapshot.getValue(Post.class);
                        Picasso.get()
                                .load(post.getPostImage())
                                .placeholder(R.drawable.placeholders)
                                .into(binding.postImage);
                        binding.DescriptionId.setText(post.getPostDescription());
                        binding.like.setText(post.getPostLike()+"");
                        binding.comment.setText(post.getPostComment()+"");

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
        database.getReference()
                .child("Users")
                .child(postBy).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User user=dataSnapshot.getValue(User.class);
                        Picasso.get()
                                .load(user.getProfile())
                                .placeholder(R.drawable.placeholders)
                                .into(binding.ProfileImage);
                        binding.name.setText(user.getName());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
        binding.postButtonn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Comment comment=new Comment();
                comment.setCommentDescripti(binding.commentEdit.getText().toString());
                comment.setCommentAt(new Date().getTime());
                comment.setCommentBy(FirebaseAuth.getInstance().getUid());

                database.getReference()
                        .child("posts")
                        .child(postId)
                        .child("comments")
                        .push()
                        .setValue(comment).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                database.getReference()
                                        .child("posts")
                                        .child(postId)
                                        .child("postComment").addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                int countCom=0;
                                                if(dataSnapshot.exists()){
                                                    countCom=dataSnapshot.getValue(Integer.class);
                                                }
                                                database.getReference()
                                                        .child("posts")
                                                        .child(postId)
                                                        .child("postComment")
                                                        .setValue(countCom+1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void unused) {
                                                                binding.commentEdit.setText("");
                                                                Toast.makeText(CommentActivity.this, "Commented", Toast.LENGTH_SHORT).show();
                                                                Notification notification=new Notification();
                                                                notification.setNotificationBy(FirebaseAuth.getInstance().getUid());
                                                                notification.setPostId(postId);
                                                                notification.setPostBy(postBy);
                                                                notification.setType("comment");
                                                                notification.setNotificationAt(new Date().getTime());

                                                                FirebaseDatabase.getInstance().getReference()
                                                                        .child("notification")
                                                                        .child(postBy)
                                                                        .push()
                                                                        .setValue(notification);
                                                            }
                                                        });
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                            }
                        });
            }
        });
        CommentAdapter adapter=new CommentAdapter(list,this);
        LinearLayoutManager linear=new LinearLayoutManager(this);
        binding.commentRecyler.setLayoutManager(linear);
        binding.commentRecyler.setAdapter(adapter);

        database.getReference()
                .child("posts")
                .child(postId)
                .child("comments").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        list.clear();
                        for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                            Comment comment=snapshot.getValue(Comment.class);
                            list.add(comment);
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}