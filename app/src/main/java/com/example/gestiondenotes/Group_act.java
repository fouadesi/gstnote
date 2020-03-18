package com.example.gestiondenotes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Group_act extends AppCompatActivity {
    static String id_module;
    FloatingActionButton btn;
    DatabaseReference mDatabase;
    FloatingActionButton add_formule;
   static String key;
    ListView listView;
    ArrayList<Groupes> groupes_users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_group_act);
         id_module   =  getIntent().getExtras().getString("id");
         listView    =  findViewById(R.id.list_view_groupes);
         add_formule = findViewById(R.id.item_ajouterformule);
         add_formule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ajouter_formule();
            }
        });
        DatabaseReference db_ref = FirebaseDatabase.getInstance().
                getReference().child("Module_users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                child(id_module).child("Groupes");
        db_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                groupes_users = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Groupes m = dataSnapshot1.getValue(Groupes.class);
                    groupes_users.add(m);
                }
                groupesadapter adapter = new groupesadapter(Group_act.this, groupes_users);
                listView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        groupesadapter adapter = new groupesadapter(Group_act.this, groupes_users);
        adapter.notifyDataSetChanged();
        btn = findViewById(R.id.floating_action_button_add_groupe2);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addGroupe();
            }
        });
    }

    void addGroupe() {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(Group_act.this);
        builder.setTitle("Ajouter un Groupe");
        builder.setIcon(R.drawable.groupe_blackicon);
        builder.setBackground(getResources().getDrawable(R.drawable.design_alert_dialog));
        final View mview = getLayoutInflater().inflate(R.layout.dialog_add_groupe, null);
        final TextInputEditText nom_edite = mview.findViewById(R.id.nom_groupe_dialog_bar);
        final TextInputEditText niveau_edit = mview.findViewById(R.id.niveau_groupe_dialog_bar);
        builder.setPositiveButton("Ajouter", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String nom = nom_edite.getText().toString();
                String niveau = niveau_edit.getText().toString();
                if (nom.isEmpty() || niveau.isEmpty()) {
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
                insertGroupe(nom, niveau);

            }
        });
        builder.setNegativeButton("Quitter", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setView(mview);
        builder.show();
    }

    void insertGroupe(String nom, String niveau) {
        Groupes g = new Groupes(nom, niveau);
        key = mDatabase.child("Module_users").push().getKey();
        g.setId(key);
        mDatabase.child("Module_users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(id_module)
                .child("Groupes").child(key).setValue(g).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    final Snackbar s = Snackbar.make(findViewById(android.R.id.content), "Module est inserer", Snackbar.LENGTH_LONG);
                    s.setDuration(10000);
                    s.setActionTextColor(getResources().getColor(R.color.colorAccent));
                    s.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
                    s.setBackgroundTint(getResources().getColor(R.color.colorAccent));
                    s.setTextColor(getResources().getColor(R.color.colorPrimary));
                    s.setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            s.dismiss();
                        }
                    });
                    s.show();
                } else {
                    Snackbar s = Snackbar.make(findViewById(android.R.id.content), task.getException().getMessage(), Snackbar.LENGTH_LONG);
                    s.show();
                }
            }
        });
    }

    void Ajouter_formule() {
        DatabaseReference db_ref;
        db_ref = FirebaseDatabase.getInstance().getReference();
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(Group_act.this);
        builder.setBackground(getResources().getDrawable(R.drawable.design_alert_dialog));
        builder.setIcon(getResources().getDrawable(R.drawable.add_formule_icon));
        View mView = getLayoutInflater().inflate(R.layout.item_module, null);
        final TextInputEditText Test1_for = mView.findViewById(R.id.test1_dialog_bar);
        TextInputEditText Test2_for = mView.findViewById(R.id.test2_dialog_bar);
        TextInputEditText Participation_for = mView.findViewById(R.id.participation_dialog_bar);
         TextInputEditText abscence_for = findViewById(R.id.abscence_dialog_bar);
        final Formule f = new Formule("","","","");
        db_ref.child("Module_users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(id_module).child("000").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        f.setTest1(dataSnapshot.getValue(Formule.class).getTest1());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Test1_for.setText("f.getTest1()");
        Test2_for.setText(f.getTest2());
        abscence_for.setText(f.getAbscence());
        Participation_for.setText(f.getParticipation());
        builder.setView(mView);

        builder.setPositiveButton("Ajouter", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

    }
}

