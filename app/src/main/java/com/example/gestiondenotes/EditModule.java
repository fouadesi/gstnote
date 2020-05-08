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
    TextInputEditText nom_module_edit_text, coef_du_module_edit_text, note_eliminatoire_du_module_edit_text;
    String get_nom, get_coeff, get_note_elim, id ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_module);
        ref = FirebaseDatabase.getInstance().getReference();
        get_nom = getIntent().getExtras().getString("nom");
        get_coeff = getIntent().getExtras().getString("coeff");
        get_note_elim = getIntent().getExtras().getString("note eliminatoire");
        id = getIntent().getExtras().getString("id");
        nom_module_edit_text = findViewById(R.id.nom_du_module_edit);
        coef_du_module_edit_text = findViewById(R.id.coefficient_edit);
        note_eliminatoire_du_module_edit_text = findViewById(R.id.note_eliminatoire_edit);
        nom_module_edit_text.setText(get_nom);
        note_eliminatoire_du_module_edit_text.setText(get_note_elim);
        coef_du_module_edit_text.setText(get_coeff);
    }

    public void Update(View view) {
        String new_nom, new_coef, new_note ;
        new_nom = nom_module_edit_text.getText().toString();
        new_coef = coef_du_module_edit_text.getText().toString();
        new_note = note_eliminatoire_du_module_edit_text.getText().toString();


        if (new_nom.equals(get_nom) && new_coef.equals(get_coeff) && new_note.equals(get_note_elim)) {
            final Snackbar s = Snackbar.make(findViewById(android.R.id.content),
              "Vous devez effectuez un changement", Snackbar.LENGTH_LONG);
            s.setDuration(10000);
            s.setActionTextColor(getResources().getColor(R.color.colorAccent));
            s.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
            s.setBackgroundTint(getResources().getColor(R.color.colorPrimary));
            s.setTextColor(getResources().getColor(R.color.colorPrimary));
            s.setAction("OK", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    s.dismiss();

                }});
            s.show();
            return;
        }

        if (new_nom.isEmpty() || new_coef.isEmpty() || new_note.isEmpty()) {
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

                }
            });
            s.show();
            return;
        }
        Module_users module_users = new Module_users(new_nom,
                new_note, new_coef);
        module_users.setId(id);
        ref.child("Module_users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                child(id).setValue(module_users).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    final Snackbar s = Snackbar.make(findViewById(android.R.id.content),
                            "Modifier avec succees", Snackbar.LENGTH_LONG);
                    s.setDuration(10000);
                    s.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
                    s.setBackgroundTint(getResources().getColor(R.color.colorAccent));
                    s.setTextColor(getResources().getColor(R.color.colorPrimary));
                    s.setActionTextColor(getResources().getColor(R.color.colorPrimary));
                    s.setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onBackPressed();
                        }
                    });
                    s.show();
                    return;
                } else {
                    Toast.makeText(EditModule.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

        });

    }
}


