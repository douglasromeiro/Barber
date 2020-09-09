package com.example.barber.View.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.barber.Control.Banco;
import com.example.barber.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private EditText edtEmail, edtSenha;
    private Button btnLogar, btnCancelar;
    private TextView cConta;

    //Conexão com Firebase automaticamente
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Instacia o metodo de conexão com Firebase
        inicializarCampos();
        btnClicks();
    }



    private void btnClicks() {
        cConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, CadastroActivity.class);
                startActivity(i);
                finish();
            }
        });

        btnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString().trim();
                String senha = edtSenha.getText().toString().trim();
                login(email, senha);
            }
        });
    }

    private void login(String email, String senha) {
        auth.signInWithEmailAndPassword(email,senha).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    alert("Login realizado com sucesso!");
                    Intent i = new Intent(MainActivity.this, TelaPrincipalActivity.class);
                    startActivity(i);
                    finish();
                }else{
                    alert("E-mail ou senha errado!");
                }
            }
        });
    }


    private void inicializarCampos(){
        //Chama os componentes criados na activity
        edtEmail = (EditText) findViewById(R.id.loginEmail);
        edtSenha = (EditText) findViewById(R.id.loginSenha);
        btnLogar = (Button) findViewById(R.id.btnLogin);
        btnCancelar = (Button) findViewById(R.id.btnCancelar);
        cConta = (TextView) findViewById(R.id.cadastrarConta);
    }

    @Override
    public void onStart() {
        super.onStart();
        auth = Banco.getFirebaseAuth();
    }

    private void alert(String msg){
        Toast.makeText(MainActivity.this, msg ,Toast.LENGTH_LONG).show();
    }
}
