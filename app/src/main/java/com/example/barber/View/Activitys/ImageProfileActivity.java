package com.example.barber.View.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.barber.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ImageProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_profile);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        inserindoImagemPerfil();
    }

    private void inserindoImagemPerfil() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        //StorageReference storageRef = storage;
    }
}
