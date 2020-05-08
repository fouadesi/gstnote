package com.example.gestiondenotes;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class groupesadapter extends ArrayAdapter<Groupes> {
    DatabaseReference db_ref;


    public groupesadapter(@NonNull Context context, ArrayList<Groupes> groupes) {
        super(context, 0, groupes);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        final Groupes groupe_users = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_groupes, parent, false);
        }
        TextView nom, niveau;
        ImageButton edit, delete, etudiant;
        nom = (TextView) convertView.findViewById(R.id.nom_du_groupe_listview);
        niveau = (TextView) convertView.findViewById(R.id.niveau_du_groupe_listview);
        nom.setText("Nom : " + groupe_users.getNom());
        niveau.setText("Niveau : " + groupe_users.getNiveau());
        edit = convertView.findViewById(R.id.update_groupe_listview);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), Editgroupe.class);
                 i.putExtra("id",groupe_users.getId());
                 i.putExtra("nom",groupe_users.getNom());
                 i.putExtra("niveau",groupe_users.getNiveau()) ;
                 getContext().startActivity(i);
            }
        });

        delete = convertView.findViewById(R.id.delete_groupe_icon_listview);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialAlertDialogBuilder m = new MaterialAlertDialogBuilder(getContext());
                m.setTitle("Attention");
                m.setMessage("Êtes-vous sûr de vouloir supprimer?");
                m.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db_ref = FirebaseDatabase.getInstance().getReference();
                        db_ref.child("Module_users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(
                                Group_act.id_module).child("Groupes").child(groupe_users.getId()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                           if (task.isSuccessful()) {
                               Toast.makeText(getContext(),"supprimer avec succès",Toast.LENGTH_LONG).show();
                           } else  {
                               Toast.makeText(getContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();
                           }
                            }
                        });
                    }
                });
                m.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                m.show();
            }
        });
        etudiant = convertView.findViewById(R.id.etudiant_listview);
        etudiant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), EtudiantAct.class);
                i.putExtra("ID",groupe_users.getId());
                getContext().startActivity(i);

            }
        });
        return convertView;
    }
}
