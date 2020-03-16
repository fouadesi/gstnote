package com.example.gestiondenotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
public class Editgroupe extends AppCompatActivity {
    EditText nom_groupe , niveau_groupe ;
    String nom , niveau, id_groupe ;
    Button edit_groupe ;
    DatabaseReference db_ref ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editgroupe);
        nom_groupe = findViewById(R.id.nom_du_groupe_edit);
        niveau_groupe =findViewById(R.id.niveau_du_groupe_edit);
        nom =getIntent().getExtras().getString("nom");
        niveau = getIntent().getExtras().getString("niveau") ;
        id_groupe = getIntent().getExtras().getString("id");
        nom_groupe.setText(nom);
        niveau_groupe.setText(niveau);
        edit_groupe = findViewById(R.id.edit_groupe);
        edit_groupe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nom = nom_groupe.getText().toString();
                niveau = niveau_groupe.getText().toString();
                if (nom.isEmpty() || niveau.isEmpty()) {
                    Toast.makeText(Editgroupe.this,"Vous devez remplir ce champ",Toast.LENGTH_SHORT).show();
                } else {
                    Groupes gr = new Groupes(nom,niveau);
                    gr.setId(id_groupe);
                    db_ref = FirebaseDatabase.getInstance().getReference();
                    db_ref.child("Module_users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child
                            (Group_act.id_module).child("Groupes").child(id_groupe).setValue(gr).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            onBackPressed();
                        }else {
                            Toast.makeText(Editgroupe.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show(); } }
                    });
                }

            }
        });


    }
}
