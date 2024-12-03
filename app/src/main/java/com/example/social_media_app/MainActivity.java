package com.example.social_media_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.social_media_app.databinding.ActivityMainBinding;
import com.example.social_media_app.fragment.AddFragment;
import com.example.social_media_app.fragment.HomeFragment;
import com.example.social_media_app.fragment.NotificationFragment;
import com.example.social_media_app.fragment.ProfileFragment;
import com.example.social_media_app.fragment.SearchFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.iammert.library.readablebottombar.ReadableBottomBar;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar2);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("My Profile");

        auth=FirebaseAuth.getInstance();

        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        binding.toolbar2.setVisibility(View.GONE);
        fragmentTransaction.replace(R.id.containers,new HomeFragment());
        fragmentTransaction.commit();

        binding.readableBottom.setOnItemSelectListener(new ReadableBottomBar.ItemSelectListener() {
            @Override
            public void onItemSelected(int i) {
                FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                switch (i){
                    case 0:
                        binding.toolbar2.setVisibility(View.GONE);
                        fragmentTransaction.replace(R.id.containers,new HomeFragment());
                        break;
                    case 1:
                        binding.toolbar2.setVisibility(View.GONE);
                        fragmentTransaction.replace(R.id.containers,new NotificationFragment());
                        break;
                    case 2:
                        binding.toolbar2.setVisibility(View.GONE);
                        fragmentTransaction.replace(R.id.containers,new AddFragment());
                        break;

                    case 3:
                        binding.toolbar2.setVisibility(View.GONE);
                        fragmentTransaction.replace(R.id.containers,new SearchFragment());
                        break;

                    case 4:
                        binding.toolbar2.setVisibility(View.VISIBLE);
                        fragmentTransaction.replace(R.id.containers,new ProfileFragment());
                        break;
                }
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.Setting){
            auth.signOut();
            Intent intent=new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);

    }
}