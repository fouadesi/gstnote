package com.example.gestiondenotes;
import android.content.DialogInterface;
import android.content.Intent;
import com.google.firebase.database.FirebaseDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth ;
    private  FirebaseDatabase database;
    private  DatabaseReference reference ;

    private FloatingActionButton add_groupe_btn;
    private DatabaseReference fAuth;
    private ListView listView;
    private RecyclerView recyclerView ;
    String uid ;
    ArrayList<String> arrayList= new ArrayList<>() ;
    protected void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() == null) {
            Intent i = new Intent(MainActivity.this,Login.class);
            startActivity(i);
            finish();
        } else {
            uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listview);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_list_item_1,arrayList) ;
        FirebaseDatabase database = FirebaseDatabase.getInstance();

            DatabaseReference reference = database.getReference("Groupes_Users");


        reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String Value = "";
                String module = dataSnapshot.child("Nom_du_Module").getValue(String.class);
                String groupe = dataSnapshot.child("Nom_du_groupe").getValue(String.class);
                Value += "Groupe   " + groupe + " Module  " + module;
                arrayList.add(Value);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                arrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                arrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                arrayAdapter.notifyDataSetChanged();

            }
        });
        add_groupe_btn = findViewById(R.id.floating_action_button_add_groupe);
        add_groupe_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder Dialog = new AlertDialog.Builder(MainActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_add_groupe,null);
                Dialog.setTitle("Ajouter un groupe") ;
                final EditText nom_du_groupe = mView.findViewById(R.id.nom_du_groupe_dialog_bar);
                final EditText nom_du_module = mView.findViewById(R.id.module_du_groupe_dialog_bar);
                final EditText annee_du_module= mView.findViewById(R.id.annee_du_groupe_dialog_bar);
                Dialog.setPositiveButton("Ajouter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {
                        final String nom = nom_du_groupe.getText().toString().trim();
                        final String nommodule = nom_du_module.getText().toString().trim();
                        final String anneemodule = annee_du_module.getText().toString().trim();
                        if (nom.isEmpty() || nommodule.isEmpty() || anneemodule.isEmpty()) {
                            Toast.makeText(MainActivity.this, "Vous devez Remplir tout les champs",
                                    Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        } else {
                            add_groupe(nom,nommodule,anneemodule);
                        }
                    }
                });
                Dialog.setNegativeButton("Quitter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                Dialog.setView(mView);
                AlertDialog dialog = Dialog.create();
                dialog.show();
            }
        });
        listView.setAdapter(arrayAdapter);
    }





    void add_groupe(String nom, String nom_module, String annee) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        DatabaseReference fReff = FirebaseDatabase.getInstance().getReference().child("Groupes_Users").child(mAuth.getCurrentUser().getUid());
        DatabaseReference newGroupe = fReff.push();
        final Map groupe = new HashMap();
        groupe.put("Nom_du_groupe",nom);
        groupe.put("Nom_du_Module",nom_module);
        groupe.put("Annee",annee);
        newGroupe.setValue(groupe);
    }





}
