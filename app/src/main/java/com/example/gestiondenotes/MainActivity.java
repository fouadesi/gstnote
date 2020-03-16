package com.example.gestiondenotes;
import android.content.DialogInterface;
import android.content.Intent;

import com.google.firebase.database.FirebaseDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth ;
    private FloatingActionButton add_groupe_btn;
    private ListView listView;
    private Button btn ;
    DatabaseReference mDatabase ;
    ArrayList<Module_users> module_users ;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.ex_menu,menu);
        return true ; }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.Disconnect: {

                mAuth.signOut();
                Intent i = new  Intent (MainActivity.this,Login.class);
                startActivity(i);
                finish();
                break;
            }
        }
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        // tester l'utilisateur si il est connecte
        if (mAuth.getCurrentUser() == null) {
            Intent i = new Intent(MainActivity.this, Login.class);
            startActivity(i);
            finish();
        }
        listView = findViewById(R.id.listview);
 /// Affichage des modules
        DatabaseReference db_ref = FirebaseDatabase.getInstance().
                getReference().child("Module_users").child(mAuth.getCurrentUser().getUid());
        db_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                module_users= new ArrayList<>();
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {
                    Module_users m = dataSnapshot1.getValue(Module_users.class);
                   module_users.add(m);
               }
               moduleadapter adapter =new moduleadapter(MainActivity.this,module_users);
                listView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }});
        // relier l'adapter avec les donnes
        moduleadapter adapter= new moduleadapter(this,module_users);
        adapter.notifyDataSetChanged();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        add_groupe_btn = findViewById(R.id.floating_action_button_add_groupe);
        add_groupe_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // dialog affichage
                final AlertDialog.Builder Dialog = new AlertDialog.Builder(MainActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_add_module, null);
                Dialog.setTitle("Ajouter un module");
                final EditText Nom_du_module = mView.findViewById(R.id.nom_du_module_dialog_bar);
                final EditText Note_eliminatoire_du_module = mView.findViewById(R.id.note_eliminatoire_du_module);
                final EditText Coefficient_du_module = mView.findViewById(R.id.coefficient_du_module);
                Dialog.setPositiveButton("Ajouter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {
                        final String Nom = Nom_du_module.getText().toString().trim();
                        final String Noteelim = Note_eliminatoire_du_module.getText().toString().trim();
                        final String coeff = Coefficient_du_module.getText().toString().trim();

                        if (Nom.isEmpty()) {
                            Nom_du_module.setError("vous devez remplir ce champ");
                            Nom_du_module.requestFocus();
                            return;
                        } if (Noteelim.isEmpty()) {
                            Note_eliminatoire_du_module.setError("Vous devez remplir ce champ");
                            Note_eliminatoire_du_module.requestFocus();
                            return;
                        } if (coeff.isEmpty()) {
                            Coefficient_du_module.setError("Vous devez remplir ce champ");
                            Coefficient_du_module.requestFocus();
                            return; }

                            add_groupe(Nom,Noteelim,coeff); }
                });
                Dialog.setNegativeButton("Quitter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss(); }
                });
                Dialog.setView(mView);
                AlertDialog dialog = Dialog.create();
                dialog.show();
            }
        }); }
        // insertion
    void add_groupe(String nom,String Note_eliminatoire, String Coeff) {
         Module_users module_users = new Module_users(nom,Note_eliminatoire,Coeff);
         String key = mDatabase.child("Module_users").push().getKey();
         module_users.setId(key);
        mDatabase.child("Module_users").child(mAuth.getCurrentUser().getUid()).child(key).
                setValue(module_users);
    }

}