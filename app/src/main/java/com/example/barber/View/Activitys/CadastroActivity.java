package com.example.barber.View.Activitys;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;

import com.example.barber.R;
import com.example.barber.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.Calendar;


public class CadastroActivity extends AppCompatActivity {

    private Button criarConta, cancelar;
    private EditText editNome, editEmail, editEndereco, editComplemento, editCep, editDataNascimento, editSenha, editConfSenha;

    private FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    private static final String USUARIO = "usuario";
    private static final String TAG = "CadastroActivity";

    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(USUARIO);
        mAuth = FirebaseAuth.getInstance();
        incializarComponentes();
        chamarCalendario();
        clicksButtons();
    }

    private void clicksButtons(){
        criarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editEmail.getText().toString() != null && editSenha.getText().toString() != null) {
                    String email = editEmail.getText().toString();
                    String nome = editNome.getText().toString();
                    String endereco = editEndereco.getText().toString();
                    String complemento = editComplemento.getText().toString();
                    String dataNascimento = editDataNascimento.getText().toString();
                    Integer cep = Integer.valueOf(editCep.getText().toString());
                    String senha = editSenha.getText().toString();
                    usuario = new Usuario(nome, email, endereco, complemento, senha, dataNascimento, cep);
                    registrarUsuario(email, senha);
                }
            }
        });
    }
    private void registrarUsuario(String email, String senha) {

        mAuth.createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:Sucesso");
                            Toast.makeText(CadastroActivity.this, "Usuário criado com sucesso!",
                                    Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:Falha", task.getException());
                            Toast.makeText(CadastroActivity.this, "Falha no cadastro!",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }
    private void incializarComponentes(){

        editNome = (EditText) findViewById(R.id.editNome);
        editEmail = (EditText) findViewById(R.id.editEmail);
        editEndereco = (EditText) findViewById(R.id.edtEndereco);
        editComplemento = (EditText) findViewById(R.id.edtComplemento);
        editCep = (EditText) findViewById(R.id.edtCep);
        editDataNascimento = (EditText) findViewById(R.id.edtDataNascimento);
        editSenha = (EditText) findViewById(R.id.editSenha);
        editConfSenha = (EditText) findViewById(R.id.edtConfSenha);
        cBd = (ImageButton) findViewById(R.id.calendarIb);
        criarConta = (Button) findViewById(R.id.btnCadastrar);
        cancelar = (Button) findViewById(R.id.btnCancelar);

    }

    private void chamarCalendario(){
        cBd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pegarData();
            }
        });
    }

    private ImageButton cBd;
    public void pegarData(){
        Calendar c = Calendar.getInstance();
        int dia = c.get(Calendar.DAY_OF_MONTH);
        int mes = c.get(Calendar.MONTH);
        int ano = c.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(CadastroActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                editDataNascimento.setText(dayOfMonth + "/" + (month+1) + "/" + year);
            }
        },ano,mes,dia);
        datePickerDialog.show();
    }

    public void updateUI(FirebaseUser currentUser){
        String keyId = databaseReference.push().getKey();
        databaseReference.child(keyId).setValue(usuario);
        Intent i = new Intent(this, TelaPrincipalActivity.class);
        startActivity(i);

    }


}
