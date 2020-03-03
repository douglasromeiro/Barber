package com.example.barber.View.Activitys;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.nfc.Tag;
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

import com.example.barber.Control.Banco;
import com.example.barber.R;
import com.example.barber.model.Cliente;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.Calendar;
import java.util.UUID;

public class CadastroActivity extends AppCompatActivity {

    private Button criarConta, cancelar;
    private EditText editNome, editEmail, editEndereco, editComplemento, editCep, editDataNascimento, editSenha, editConfSenha;

    private FirebaseUser user;
    private FirebaseAuth auth;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    Cliente c = new Cliente();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        auth = FirebaseAuth.getInstance();
        inicializarFirebase();
        incializarComponentes();
        chamarCalendario();
        clckCadastro();
        clickCancelar();
    }

    private void clckCadastro() {
        criarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               creatAccount();
               userLogin();
            }
        });
    }

    private void clickCancelar(){
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(CadastroActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }


       private void userLogin() {
                   c.setId(UUID.randomUUID().toString());
                   c.setNome(editNome.getText().toString());
                   c.setEndereco(editEndereco.getText().toString());
                   c.setComplemento(editComplemento.getText().toString());
                   c.setCep(Integer.valueOf(editCep.getText().toString()));
                   c.setDataNascimento(editDataNascimento.getText().toString());
                   databaseReference.child("Cliente").child(c.getId()).setValue(c);

                }

    private void creatAccount(){
        String email = editEmail.getText().toString();
        String senha = editSenha.getText().toString();

        auth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    alert("Usuário cadastrado com sucesso!");
                    FirebaseUser user = auth.getCurrentUser();
                    limparCampos();
                    Intent i = new Intent(CadastroActivity.this, TelaPrincipalActivity.class);
                    startActivity(i);
                    finish();
                }else{
                    alert("Erro ao cadastrar!" +email + senha);
                }
            }
        });
    }

    private void limparCampos() {
        editNome.setText("");
        editEmail.setText("");
        editEndereco.setText("");
        editComplemento.setText("");
        editCep.setText("");
        editDataNascimento.setText("");
        editSenha.setText("");
        editConfSenha.setText("");
    }


    private void alert(String msg){
        Toast.makeText(CadastroActivity.this, msg ,Toast.LENGTH_LONG).show();
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

    @Override
    protected void onStart() {
        super.onStart();
        auth = Banco.getFirebaseAuth();
        user = Banco.getFirebaseUser();
        //FirebaseUser currentUser = auth.getCurrentUser();
    }

}
