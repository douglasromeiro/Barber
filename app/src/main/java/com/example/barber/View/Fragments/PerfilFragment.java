package com.example.barber.View.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.barber.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link androidx.fragment.app.Fragment} subclass.
 */
public class PerfilFragment extends Fragment {

    private ImageView photoPerfil;
    private CircleImageView photoProfile;
    private TextView tNome, tEmail, tDataNasciment, tCep, tEndereco, tComplemento;
    private DatabaseReference database;
    private DatabaseReference userRef;
    private final String TAG = this.getClass().getName().toUpperCase();
    private final String USUARIO="usuario";
    View view;
    private Uri uri_image;


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
        imagemPerfil();
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

    private void imagemPerfil(){
        photoProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pegarPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(Intent.createChooser(pegarPhoto, "Selciona uma foto de perfil"), 0);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode ==  RESULT_OK && requestCode == 0 && data != null){
                    uri_image = data.getData();
                    Glide.with(getContext()).asBitmap().load(uri_image).listener(new RequestListener<Bitmap>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                            Toast.makeText(getContext(), "Falha ao selecionar Imagem", Toast.LENGTH_LONG).show();
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                            return false;
                        }
                    }).into(photoProfile);
        }else {
            Toast.makeText(getContext(), "Falha ao selecionar Imagem", Toast.LENGTH_LONG).show();
        }
    }

    private void inicializarCampos() {

        photoProfile = (CircleImageView) view.findViewById(R.id.profile_image);
        tNome = (TextView) view.findViewById(R.id.nome);
        tEmail = (TextView) view.findViewById(R.id.email);
        tDataNasciment = (TextView) view.findViewById(R.id.dtNascimento);
        tCep = (TextView) view.findViewById(R.id.cep);
        tEndereco = (TextView) view.findViewById(R.id.endereco);
        tComplemento = (TextView) view.findViewById(R.id.complemento);
    }


}


