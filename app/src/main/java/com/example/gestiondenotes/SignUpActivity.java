package com.example.gestiondenotes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
public class SignUpActivity extends AppCompatActivity {

    EditText ed_nom , ed_specialite , ed_mail , ed_password , ed_conf_password ;
    FloatingActionButton btn ;
            TextView btn2;
    FirebaseAuth mAuth ;
    FirebaseDatabase fRef;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        btn2 = findViewById(R.id.sign_in);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent (SignUpActivity.this,Login.class));
                finish();
            }
        });
        ed_mail = findViewById(R.id.email_signUp);
        ed_nom= findViewById(R.id.nom_signUp);
        ed_password = findViewById(R.id.mot_de_passe_signUp);
        ed_conf_password = findViewById(R.id.confirmer_mot_de_passe_signUp);
        btn = findViewById(R.id.signUp_btn);
        mAuth = FirebaseAuth.getInstance();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterUser();
            }
        });

    }
    void RegisterUser() {
        final String name = ed_nom.getText().toString().trim();  ;
        final String email = ed_mail.getText().toString().trim();
        String password = ed_password.getText().toString().trim();
        String confpassword = ed_conf_password.getText().toString().trim();
        if (name.isEmpty()) {
            ed_nom.setError("Vous devez remplir le champ");
            ed_nom.requestFocus();
            return; }
        if (email.isEmpty()) {
            ed_mail.setError("Vous devez remplir le champ");
            ed_nom.requestFocus();
            return; }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            ed_mail.setError("Vous devez entrer un mail valid");
            ed_mail.requestFocus();
            return; }
        if (confpassword.isEmpty()) {
            ed_conf_password.setError("Vous devez remplir le champ");
            ed_conf_password.requestFocus();
            return; }
        if (password.length() < 6 ) {
            ed_password.setError("> 6");
            ed_password.requestFocus();
            return; }
        if (!password.equals(confpassword)) {
            ed_conf_password.setError("Les mot de passe doivent etre identique");
            ed_conf_password.requestFocus();
            return; }
        final  ProgressDialog pr = new ProgressDialog(SignUpActivity.this) ;
        pr.setMessage("Attendez s'il vous plait");
        pr.show();
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    User user = new User(name,email);
                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            pr.dismiss();
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(SignUpActivity.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(SignUpActivity.this,task.getException().getMessage().toString(),
                                        Toast.LENGTH_SHORT).show(); } }});
                } else {
                    pr.dismiss();
                    Toast.makeText(SignUpActivity.this,task.getException().getMessage().toString(),Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}