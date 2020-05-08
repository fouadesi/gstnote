package com.example.gestiondenotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
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
    ListView listView,listView2;
    ArrayList<Groupes> groupes_users;
    ArrayList<Formule> formules ;
    TextView formule_teste1,formule_teste2 ,formule_participation,formule_absence ;
    static String _test1 ;
   static  String _test2 ;
  static   String _participation ;
  static  String _absence;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_group_act);
         formule_absence = findViewById(R.id.absence_list);
         formule_teste1 = findViewById(R.id.test1_list) ;
         formule_teste2 = findViewById(R.id.test2_list);
         formule_participation = findViewById(R.id.participation_list);
        _test1 = getIntent().getExtras().getString("test1");
        _test2 = getIntent().getExtras().getString("test2");
        _participation = getIntent().getExtras().getString("participation");
        _absence = getIntent().getExtras().getString("absence");


     final   String test1 = getIntent().getExtras().getString("test1");
      final  String test2 = getIntent().getExtras().getString("test2");
      final  String participation = getIntent().getExtras().getString("participation");
       final String absence = getIntent().getExtras().getString("absence");

        formule_teste1.setText("TEST1 : " + test1 + " %");
        formule_teste2.setText("TEST2 : " + test2 + " %");
        formule_absence.setText("ABSENCE : " + absence + " %");
        formule_participation.setText("PARTICIPATION : " + participation + " %");


         id_module   =  getIntent().getExtras().getString("id");
         listView    =  findViewById(R.id.list_view_groupes);
         add_formule = findViewById(R.id.item_ajouterformule);

         add_formule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(Group_act.this);
                builder.setTitle("Ajouter une Formule");
                builder.setIcon(R.drawable.groupe_blackicon);
                builder.setBackground(getResources().getDrawable(R.drawable.design_alert_dialog));
                final View mview = getLayoutInflater().inflate(R.layout.add_formule, null);
                final TextInputEditText Test1 = mview.findViewById(R.id.formule_test1);
                final TextInputEditText Test2 = mview.findViewById(R.id.formule_test2);
                final TextInputEditText Absence = mview.findViewById(R.id.formule_absence);
                final TextInputEditText Participation = mview.findViewById(R.id.formule_participation);
                Test1.setText(test1);
                Test2.setText(test2);
                Absence.setText(absence);
                Participation.setText(participation);
                builder.setView(mview);
                builder.setPositiveButton("Ajouter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String Test1_edi = Test1.getText().toString().trim();
                        final String Test2_edi = Test2.getText().toString().trim();
                        final String Absence_edi = Absence.getText().toString().trim();
                        String Participation_edi = Participation.getText().toString();

                        if (Test1_edi.isEmpty() || Test2_edi.isEmpty() || Absence_edi.isEmpty() || Participation_edi.isEmpty()) {
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
                        if (Double.valueOf(Test1_edi) + Double.valueOf(Test2_edi) != 100) {
                            final Snackbar s = Snackbar.make(findViewById(android.R.id.content),
                                    "La somme Entre le test1 et le test2 != 100 % ", Snackbar.LENGTH_LONG);
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
                        final Formule f = new Formule(Test1_edi,Test2_edi,Absence_edi,Participation_edi) ;
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                        mDatabase = FirebaseDatabase.getInstance().getReference() ;
                        mDatabase.child("Module_users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                                child(id_module)
                                .child("formule").setValue(f).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                             if (task.isSuccessful()) {
                                 _test1= Test1_edi ;
                                 _test2 = Test2_edi ;
                                 _absence = Absence_edi ;
                                 formule_teste1.setText("TEST1 : " + f.getTest1()+ " %");
                                 formule_teste2.setText("TEST2 : " + f.getTest2()+ " %");
                                 formule_absence.setText("ABSENCE : " + " - " +f.getAbscence()+ " / abs");
                                 formule_participation.setText("PARTICIPATION : " + "+ " + f.getParticipation() +" / Par");
                                 final Snackbar s = Snackbar.make(findViewById(android.R.id.content),
                                         "Bien inserer", Snackbar.LENGTH_LONG);
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
                             }  else {
                                 final Snackbar s = Snackbar.make(findViewById(android.R.id.content),
                                         task.getException().getMessage().toString(), Snackbar.LENGTH_LONG);
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
                             }
                            }
                        });
                     }
                });
                builder.show();
            }});




        DatabaseReference db_ref2 = FirebaseDatabase.getInstance().
                getReference().child("Module_users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                child(id_module).child("Groupes");
        db_ref2.addValueEventListener(new ValueEventListener() {
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
                .child("Groupes").child(key).
                setValue(g).addOnCompleteListener(new OnCompleteListener<Void>() {
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
                        }});
                    s.show();
                } else {
                    final Snackbar s = Snackbar.make(findViewById(android.R.id.content),
                            task.getException().getMessage(), Snackbar.LENGTH_LONG);
                    s.setDuration(10000);
                    s.setActionTextColor(getResources().getColor(R.color.colorAccent));
                    s.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
                    s.setBackgroundTint(getResources().getColor(R.color.colorAccent));
                    s.setTextColor(getResources().getColor(R.color.colorPrimary));
                    s.setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            s.dismiss();

                        }});
                    s.show();
                }
            }
        });
    }


}

