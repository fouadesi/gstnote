
package com.example.gestiondenotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static android.util.Patterns.EMAIL_ADDRESS;

public class Login extends AppCompatActivity {
    EditText email_ed;
    EditText pass_ed;
    TextView  singup_btn;
    FloatingActionButton login_btn ;
    FirebaseAuth mAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email_ed = findViewById(R.id.email_edit_text);
        pass_ed = findViewById(R.id.pwd_edit_text);
        login_btn = findViewById(R.id.login_btn);
        singup_btn = findViewById(R.id.signUp_btn);
        mAuth = FirebaseAuth.getInstance();
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, mot_de_passe;
                email = email_ed.getText().toString();
                mot_de_passe = pass_ed.getText().toString();
                if (EMAIL_ADDRESS.matcher(email).matches()) {
                    if (mot_de_passe.length() >= 8) {
                        signin(email, mot_de_passe);
                    } else {
                        pass_ed.setError("entrer un password valide ");
                    }
                } else {
                    email_ed.setError("entrer un mail valide ");
                }
            }
        });
        singup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, SignUpActivity.class);
                startActivity(i);
            }
        });
    }
    public void signin(String email, String password) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Attendez s'il vous plait");
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                                                    @Override
                                                                                    public void onComplete(@NonNull Task<AuthResult> task) {
        progressDialog.dismiss();
        if (task.isSuccessful()) {
          Intent i = new Intent(Login.this, MainActivity.class);
          startActivity(i);
         finish();
      } else {
            Toast.makeText(Login.this, task.getException().getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
                                                                                    }
                                                                                });
    }

}
