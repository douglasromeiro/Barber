package com.example.barber.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.barber.R;
import com.google.firebase.auth.FirebaseAuth;

public class TelaPrincipalActivity extends AppCompatActivity {

    private Button btnDeslogar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_principal);

        btnDeslogar = (Button) findViewById(R.id.btnLogout);

        btnDeslogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disconect();
                closeProject();
            }
        });
    }

    private void disconect(){
        FirebaseAuth.getInstance().signOut();
    }

    private void closeProject(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }
}
