package com.example.social_media_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.social_media_app.databinding.ActivityRegintation2Binding;
import com.example.social_media_app.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegintationActivity2 extends AppCompatActivity {
    ActivityRegintation2Binding binding;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    String name,professional;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegintation2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        binding.signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegintationActivity2.this, LoginActivity.class));
            }
        });



            binding.signupButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String email = binding.EmailEditRegistation.getText().toString();
                    String password = binding.PasswordEditRegistation.getText().toString();
                    name=binding.NameEditRegistation.getText().toString();
                    professional=binding.ProfessionEditRegistation.getText().toString();
                    if (validateInputs(email, password)) {
                        signUpUser(email, password);
                    }



                }
            });

    }
    private boolean validateInputs(String email, String password) {
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Email is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(password) || password.length() < 6) {
            Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private void signUpUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        User user=new User(name,professional,email,password);
                        String ids=task.getResult().getUser().getUid();
                        database.getReference().child("Users").child(ids).setValue(user);
                        Intent intent=new Intent(RegintationActivity2.this,MainActivity.class);
                        startActivity(intent);
                        Toast.makeText(RegintationActivity2.this, "Sign-up Successful!", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(RegintationActivity2.this, "Sign-up Failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}