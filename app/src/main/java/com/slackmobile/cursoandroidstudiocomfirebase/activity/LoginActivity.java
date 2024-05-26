package com.slackmobile.cursoandroidstudiocomfirebase.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.slackmobile.cursoandroidstudiocomfirebase.R;
import com.slackmobile.cursoandroidstudiocomfirebase.activity.Util.configuraBd;

public class LoginActivity extends AppCompatActivity {

    EditText campoEmail, campoSenha;
    Button botaoAcessar;
    private FirebaseAuth auth;
    private configuraBd ConfiguraBd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.LinearLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;



        });
        auth = ConfiguraBd.FirebaseAutenticacao();

        inicializarComponentes();



        }



        public void validarAutenticacao(){
        String email = campoEmail.getText().toString();
        String senha = campoSenha.getText().toString();

        if(!email.isEmpty()){
            if(!senha.isEmpty()){

                Usuario usuario = new Usuario();

                usuario.setEmail(email);
                usuario.setSenha(senha);

                logar(usuario);



                
            }else{
                Toast.makeText(this, "Preencher a senha", Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(this, "Preencha o email", Toast.LENGTH_SHORT).show();
        }


        }

    private void logar(Usuario usuario) {
        auth.signInWithEmailAndPassword(
                usuario.getEmail(), usuario.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    abrirHome();

                }else{
                    String excecao="";
                    try{
                        throw task.getException();

                    }catch (FirebaseAuthInvalidUserException e) {
                        excecao="Usuário nao cadastrado";
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        excecao="Email e senha incorreto";
                    }catch (Exception e){
                        excecao="Erro ao logar"+e.getMessage();
                        e.printStackTrace();
                    }
                    Toast.makeText(LoginActivity.this, excecao, Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    private void abrirHome() {
        //quando e logado mudara tela de Home
        Intent i =new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(i);
    }

    public void cadastrar(View view){
        Intent i = new Intent (this,CadastroActivity.class) ;
        startActivity(i);



    }
    private void inicializarComponentes(){
        campoEmail= findViewById(R.id.editTextEmailLogin);
        campoSenha= findViewById(R.id.editTextSenhaLogin);
        botaoAcessar= findViewById(R.id.buttonAcessar);
    }
}