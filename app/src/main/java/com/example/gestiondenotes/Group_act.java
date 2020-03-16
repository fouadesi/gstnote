package com.example.gestiondenotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.github.clans.fab.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Group_act extends AppCompatActivity {
  static String id_module ;
    FloatingActionButton btn ;
    DatabaseReference mDatabase ;
    String key ;
    ListView listView ;
    ArrayList<Groupes> groupes_users ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_act);
        id_module = getIntent().getExtras().getString("id");
        listView = findViewById(R.id.list_view_groupes );

        DatabaseReference db_ref = FirebaseDatabase.getInstance().
                getReference().child("Module_users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                child(id_module).child("Groupes");
        db_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                groupes_users = new ArrayList<>();
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {
                    Groupes m = dataSnapshot1.getValue(Groupes.class);
                    groupes_users.add(m);
                }
                groupesadapter adapter =new groupesadapter(Group_act.this,groupes_users);
                listView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }});
        groupesadapter adapter= new groupesadapter(Group_act.this,groupes_users);
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
        final AlertDialog.Builder dialog = new AlertDialog.Builder(Group_act.this);
        dialog.setTitle("Ajouter un Groupe");
       final View mview = getLayoutInflater().inflate(R.layout.dialog_add_groupe,null);
        final EditText nom_edite = mview.findViewById(R.id.nom_groupe_dialog_bar);
        final EditText niveau_edit = mview.findViewById(R.id.niveau_groupe_dialog_bar);
        dialog.setPositiveButton("Ajouter", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               String nom =  nom_edite.getText().toString();
               String niveau = niveau_edit.getText().toString();
               if (nom.isEmpty()) {
                   nom_edite.setError("Vous devez remplir ce champs");
                   nom_edite.requestFocus();
                   return;
               }
                if (niveau.isEmpty()) {
                    nom_edite.setError("Vous devez remplir ce champs");
                    nom_edite.requestFocus();
                    return;
                }
                insertGroupe(nom,niveau);

            }
        });
        dialog.setNegativeButton("Quitter", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
             dialog.dismiss();
            }
        });
        dialog.setView(mview);
        AlertDialog d = dialog.create();
        d.show();
    }
    void insertGroupe(String nom ,String niveau) {
        Groupes g = new Groupes(nom,niveau);
         key = mDatabase.child("Module_users").push().getKey();
        g.setId(key);
        mDatabase.child("Module_users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(id_module)
                .child("Groupes").child(key).setValue(g);
    }
}
