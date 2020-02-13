package com.example.barber.View.Activitys;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText edtEmail, edtPassword;
    private Button btnLogar, btnCancelar;
    private TextView cConta;

    //Conexão com Firebase automaticamente
    private FirebaseAuth mAuth;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Instacia o metodo de conexão com Firebase
        mAuth = FirebaseAuth.getInstance();
        //Chama os componentes criados na activity
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        btnLogar = (Button) findViewById(R.id.btnLogin);
        btnCancelar = (Button) findViewById(R.id.btnCancelar);
        cConta = (TextView) findViewById(R.id.criarConta);
        //Botão para logar
        btnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUsuario(edtEmail.getText().toString(), edtPassword.getText().toString());
            }
        });
        //Botão para cancelar
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtEmail.setText("");
                edtPassword.setText("");
            }
        });
        cConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), CadastroActivity.class);
                startActivity(i);
                finish();
            }
        });
    }



    @Override
    protected void onStart() {
        super.onStart();
        //Checa se o usuário está logado e atualiza
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }

    //Método para validar login
    private void loginUsuario(String email, String password) {

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithEmail:success");
                            //FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(getApplicationContext(), "Login realizado com sucesso", Toast.LENGTH_SHORT).show();
                            openTelaPrincipal();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    //Verifica se o usuário está conectado
    private boolean userConected() {

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            return false;
        } else {
            return true;
        }
    }

    //Interação entre telas
    public void openTelaPrincipal(){

        Intent telaPrincipal = new Intent(getApplicationContext(), TelaPrincipalActivity.class);
        telaPrincipal.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        telaPrincipal.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(telaPrincipal);
        finish();
    }
}