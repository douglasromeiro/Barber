package com.example.barber.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.barber.Fragments.AndroidFragment;
import com.example.barber.Fragments.HomeFragment;
import com.example.barber.Fragments.SearchFragment;
import com.example.barber.MainActivity;
import com.example.barber.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class TelaPrincipalActivity extends AppCompatActivity {

    private BottomNavigationView bottonNavigationView;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_principal);

        bottonNavigationView = findViewById(R.id.bottomNav);
        bottonNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();

    }


    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod = new
            BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    do {

                        Fragment fragment = null;

                        switch (menuItem.getItemId()) {
                            case R.id.android:
                                fragment = new AndroidFragment();
                                break;
                            case R.id.home:
                                fragment = new HomeFragment();
                                break;
                            case R.id.search:
                                fragment = new SearchFragment();
                                break;

                        }

                        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
                        return true;
                    } while (userConected() == true);

                }
            };


                public void desconnect() {
                    FirebaseAuth.getInstance().signOut();
                }

                public boolean userConected() {

                    FirebaseUser currentUser = mAuth.getCurrentUser();
                    if (currentUser == null) {
                        Intent manter = new Intent(this, MainActivity.class);
                        startActivity(manter);
                        return false;
                    } else {
                        return true;
                    }
                }
}

