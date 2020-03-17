package com.example.gestiondenotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditModule extends AppCompatActivity {
    DatabaseReference ref;
    EditText nom_module_edit_text, coef_du_module_edit_text, note_eliminatoire_du_module_edit_text,
            test1_edi, test2_edi, abscence_edi, participation_edi;
    String get_nom, get_coeff, get_note_elim, id, test1, test2, participation, abscence;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_module);
        ref = FirebaseDatabase.getInstance().getReference();
        get_nom = getIntent().getExtras().getString("nom");
        get_coeff = getIntent().getExtras().getString("coeff");
        get_note_elim = getIntent().getExtras().getString("note eliminatoire");
        id = getIntent().getExtras().getString("id");
        test1 = getIntent().getExtras().getString("test1");
        test2 = getIntent().getExtras().getString("test2");
        participation = getIntent().getExtras().getString("absence");
        abscence = getIntent().getExtras().getString("participation");
        nom_module_edit_text = findViewById(R.id.nom_du_module_edit);
        coef_du_module_edit_text = findViewById(R.id.coefficient_edit);
        note_eliminatoire_du_module_edit_text = findViewById(R.id.note_eliminatoire_edit);
        test1_edi = findViewById(R.id.test1_edit);
        test2_edi = findViewById(R.id.test2_edit);
        participation_edi = findViewById(R.id.participation_edit);
        abscence_edi = findViewById(R.id.abscence_edit);
        nom_module_edit_text.setText(get_nom);
        note_eliminatoire_du_module_edit_text.setText(get_note_elim);
        coef_du_module_edit_text.setText(get_coeff);
        test1_edi.setText(test1);
        test2_edi.setText(test2);
        abscence_edi.setText(abscence);
        participation_edi.setText(participation);
    }

    public void Update(View view) {
        String new_nom, new_coef, new_note, new_test1, new_test2, new_absence, new_participation;
        new_nom = nom_module_edit_text.getText().toString();
        new_coef = coef_du_module_edit_text.getText().toString();
        new_note = note_eliminatoire_du_module_edit_text.getText().toString();
        new_test1 = test1_edi.getText().toString();
        new_test2 = test2_edi.getText().toString();
        new_absence = abscence_edi.getText().toString();
        new_participation = abscence_edi.getText().toString();


        if ((new_nom.isEmpty() || new_coef.isEmpty() || new_note.isEmpty() || new_test1.isEmpty() || new_test2.isEmpty() ||
                new_absence.isEmpty() || new_participation.isEmpty())) {
            final Snackbar s = Snackbar.make(findViewById(android.R.id.content),
                    "Vous devez remplir les champs", Snackbar.LENGTH_LONG);
            s.setDuration(10000);
            s.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
            s.setBackgroundTint(getResources().getColor(R.color.colorAccent));
            s.setTextColor(getResources().getColor(R.color.colorPrimary));
            s.setActionTextColor(getResources().getColor(R.color.colorPrimary));
            s.setAction("OK", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    s.dismiss();
                }
            });
            s.show();
            return;
        }
        if (Double.parseDouble(new_test1) + Double.parseDouble(new_test2) +
                Double.parseDouble(new_absence) + Double.parseDouble(new_participation) != 100) {
            final Snackbar s = Snackbar.make(findViewById(android.R.id.content),
                    "somme = 100", Snackbar.LENGTH_LONG);
            s.setDuration(10000);
            s.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
            s.setBackgroundTint(getResources().getColor(R.color.colorAccent));
            s.setTextColor(getResources().getColor(R.color.colorPrimary));
            s.setActionTextColor(getResources().getColor(R.color.colorPrimary));
            s.setAction("OK", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    s.dismiss();
                }
            });
            s.show();
            return;
        }

        Module_users module_users = new Module_users(new_nom, new_note, new_coef, new_test1, new_test2, new_participation, new_absence);
        module_users.setId(id);
        ref.child("Module_users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                child(id).setValue(module_users).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    onBackPressed();
                } else {
                    Toast.makeText(EditModule.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

        });

    }
}


