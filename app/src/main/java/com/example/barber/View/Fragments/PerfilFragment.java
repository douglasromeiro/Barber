package com.example.barber.View.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.barber.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link androidx.fragment.app.Fragment} subclass.
 */
public class PerfilFragment extends Fragment {

    private TextView tNome, tEmail, tDataNasciment, tCep, tEndereco, tComplemento;
    private DatabaseReference database;
    private DatabaseReference userRef;
    private final String TAG = this.getClass().getName().toUpperCase();
    private final String USUARIO="usuario";
    //String email;
    View view;


    public PerfilFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_perfil, container,false);
        database = FirebaseDatabase.getInstance().getReference();
        userRef =database.child("usuario");
        inicializarCampos();
        consultarUsuario();
        return view;
    }

    private void consultarUsuario() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String email = user.getEmail();
            userRef.addValueEventListener(new ValueEventListener() {
                String lNome = null, lEmail = null, lDataNascimento = null , lEndereco = null, lComplemento = null;
                String lCep;
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot keyId : dataSnapshot.getChildren()) {
                        if (keyId.child("email").getValue().equals(email)){
                            lNome = keyId.child("nome").getValue(String.class);
                            lEmail = keyId.child("email").getValue(String.class);
                            lDataNascimento = keyId.child("dataNascimento").getValue(String.class);
                            lCep = String.valueOf(keyId.child("cep").getValue(Integer.class));
                            lEndereco = keyId.child("endereco").getValue(String.class);
                            lComplemento = keyId.child("complemento").getValue(String.class);
                            break;
                        }
                    }

                    tNome.setText(lNome);
                    tEmail.setText(lEmail);
                    tDataNasciment.setText(lDataNascimento);
                    tCep.setText(lCep);
                    tEndereco.setText(lEndereco);
                    tComplemento.setText(lComplemento);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.w(TAG, "Falha ao ler valores", databaseError.toException());
                }
            });
        }
    }


    private void inicializarCampos(){

        tNome = (TextView) view.findViewById(R.id.nome);
        tEmail = (TextView) view.findViewById(R.id.email);
        tDataNasciment = (TextView) view.findViewById(R.id.dtNascimento);
        tCep = (TextView) view.findViewById(R.id.cep);
        tEndereco = (TextView) view.findViewById(R.id.endereco);
        tComplemento = (TextView) view.findViewById(R.id.complemento);
    }


}


