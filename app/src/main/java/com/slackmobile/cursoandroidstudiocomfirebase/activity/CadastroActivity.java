package com.slackmobile.cursoandroidstudiocomfirebase.activity;

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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.slackmobile.cursoandroidstudiocomfirebase.R;
import com.slackmobile.cursoandroidstudiocomfirebase.activity.Util.configuraBd;
//Anderson Clayton Ribas RA 223326

public class CadastroActivity extends AppCompatActivity {
    Usuario usuario;
    FirebaseAuth autenticacao;
    EditText campoNome,campoEmail,campoSenha;
    Button botaoCadstrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.LinearLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;


        }
        );

        incializar();
        //validar campo
    }

    private void incializar() {
        campoNome=findViewById(R.id.editTextNome);
        campoEmail=findViewById(R.id.editTextEmail);
        campoSenha=findViewById(R.id.editTextSenha);
        botaoCadstrar=findViewById(R.id.buttonCadastrar);
    }
    public void validarCampo(View v){
        String nome = campoNome.getText().toString();
        String email = campoEmail.getText().toString();
        String senha = campoSenha.getText().toString();
        //validar
        if(!nome.isEmpty()){
            if(!email.isEmpty()){
                if(!senha.isEmpty()){

                     usuario = new Usuario();


                    usuario.setNome(nome);
                    usuario.setEmail(email);
                    usuario.setSenha(senha);







                    //cadastro
                    cadastrarUsuario();

                }else{
                    Toast.makeText(this, "Preencha a senha", Toast.LENGTH_SHORT).show();
                }

        }else {
                Toast.makeText(this, "Preencha o email", Toast.LENGTH_SHORT).show();
            }

        }else {

            Toast.makeText(this, "Preencha o nome", Toast.LENGTH_SHORT).show();
        }

    }

    private void cadastrarUsuario() {
        autenticacao = configuraBd.FirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
                usuario.getEmail(), usuario.getSenha()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(CadastroActivity.this, "Sucesso ao cadastrar usuario", Toast.LENGTH_SHORT).show();

                }else{
                    String excecao = "";

                    try {
                        throw task.getException();
                    }catch (FirebaseAuthWeakPasswordException e){
                        excecao = "Digite uma senha mais forte";
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        excecao = "Digite um Email válido";
                    }catch (FirebaseAuthUserCollisionException e) {
                        excecao = "Essa conta já existe";
                    }catch(Exception e) {
                        excecao = "Erro ao cadastrar usuário" + e.getMessage();
                        e.printStackTrace();
                    }
                    Toast.makeText(CadastroActivity.this, excecao, Toast.LENGTH_SHORT).show();


                }

            }
        });
    }


}
