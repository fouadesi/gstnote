package com.example.gestiondenotes;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import com.bumptech.glide.Glide;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class Etudiant_adapter extends ArrayAdapter<Etudiant> {
    public Etudiant_adapter(@NonNull Context context, ArrayList<Etudiant> etudiant) {
        super(context, 0, etudiant);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        final Etudiant etudiant = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_layout, parent, false);
        }
        TextView nom_etudiant , email_etudiant , prenom_etudiant,NI;
              CircleImageView photo_etudiant;
              ImageButton supprimer_etudiant ;
              supprimer_etudiant = convertView.findViewById(R.id.supprimer_etudiant);
        nom_etudiant = convertView.findViewById(R.id.name_text);
        email_etudiant = convertView.findViewById(R.id.email_text);
        prenom_etudiant = convertView.findViewById(R.id.prenom_text);
        photo_etudiant = convertView.findViewById(R.id.profile_image);
        NI = convertView.findViewById(R.id.ni_text);
        nom_etudiant.setText("Nom : " + etudiant.getNom());
        email_etudiant.setText("Email : " +etudiant.getEmail());
        prenom_etudiant.setText("Prenom : " + etudiant.getPrenom());
        NI.setText("NI : " + etudiant.getNI());
        Glide.with(getContext()).load(etudiant.getPicture()).into(photo_etudiant);
        supprimer_etudiant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Supprimer_etudiant(etudiant);
            }
        });

        photo_etudiant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInfoEtudiant(etudiant);
            }
        });
        return convertView;
    }
    void Supprimer_etudiant(final Etudiant etudiant) {
        MaterialAlertDialogBuilder m  = new MaterialAlertDialogBuilder(getContext()) ;
        m.setTitle("Attention");
        m.setMessage("Vous voulez Supprimece etudiant");
        m.show();
        m.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DatabaseReference db_ref = FirebaseDatabase.getInstance().getReference();
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                ref.child("Module_users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                        child(Group_act.id_module).child("Groupes").child(EtudiantAct.key_g).
                        child("Etudiants").child(etudiant.getNI()).removeValue();

            }
        });
        m.setNegativeButton("Non", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
    }
   void  getInfoEtudiant(Etudiant etudiant) {
       MaterialAlertDialogBuilder Dialog = new MaterialAlertDialogBuilder(getContext());
       Dialog.setTitle("Information Sur" + etudiant.getNom() + " " + etudiant.getPrenom());
      Dialog.setIcon(R.drawable.info_etudiant);
    }
}
