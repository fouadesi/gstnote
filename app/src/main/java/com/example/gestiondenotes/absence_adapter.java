package com.example.gestiondenotes;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class absence_adapter  extends ArrayAdapter<Etudiant> {
    public absence_adapter(@NonNull Context context, ArrayList<Etudiant> etudiant) {
        super(context, 0, etudiant);
    }
    View v ;

    public View getView(int position, View convertView, ViewGroup parent) {
        final Etudiant etudiant = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.absence_layout, parent, false);
        }
        CircleImageView profil_image_absence = convertView.findViewById(R.id.profil_image_absence);
        TextView name_absence = convertView.findViewById(R.id.name_absence);
        TextView prenom_absence = convertView. findViewById(R.id.prenom_absence) ;
        TextView email_absence = convertView.findViewById(R.id.email_absence);
        TextView ni_text_absence = convertView.findViewById(R.id.ni_text_absence);
        TextView absence_textView = convertView.findViewById(R.id.absence_textView);



       ImageButton Ajouter_absence  = convertView.findViewById(R.id.Ajouter_absence);
       Ajouter_absence.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Ajouter_abs(etudiant);
           }
       });
       ImageButton supprimer_absence = convertView.findViewById(R.id.supprimer_absence);
       supprimer_absence.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Supprimer_abs(etudiant) ;
           }
       });
        Glide.with(getContext()).load(etudiant.getPicture()).into(profil_image_absence);
           if (Integer.parseInt(etudiant.getAbscence())> 2) {
               profil_image_absence.setBorderColor(Color.parseColor("#ff1744"));
           }
        name_absence.setText("Nom : " + etudiant.getNom());
        prenom_absence.setText("Prenom :" + etudiant.getPrenom());
        email_absence.setText("Email :" + etudiant.getEmail());
        ni_text_absence.setText("NI :" + etudiant.getNI());
        absence_textView.setText("Absence " + etudiant.getAbscence());
        v = convertView ;
        return convertView ;

    }
   void Ajouter_abs(final Etudiant e) {
       MaterialAlertDialogBuilder m = new MaterialAlertDialogBuilder(getContext());
       m.setTitle("Ajouter une absence");
       m.setMessage("êtes vous sûr de vouloir ajouter une absence ");
       m.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {
               int i = Integer.parseInt(e.getAbscence()) +1 ;
               e.setAbscence(String.valueOf(i));
               DatabaseReference db_ref = FirebaseDatabase.getInstance().getReference();
               DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
               ref.child("Module_users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                       child(Group_act.id_module).child("Groupes").child(EtudiantAct.key_g).
                       child("Etudiants").child(e.getNI()).setValue(e);

           }});
       m.setNegativeButton("Non", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {

           }
       });
       m.show();

   }
 void Supprimer_abs(final Etudiant e) {
     MaterialAlertDialogBuilder m = new MaterialAlertDialogBuilder(getContext());
     m.setTitle("Ajouter une absence");
     m.setMessage("êtes vous sûr de vouloir ajouter une absence ");
     m.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
         @Override
         public void onClick(DialogInterface dialog, int which) {
             if (e.getAbscence().equals("0")) {
               Toast.makeText(getContext(),"Impossible",Toast.LENGTH_LONG).show();
                 return;
             }
             if (Integer.parseInt(e.getAbscence()) != 0 ) {
                 int i = Integer.parseInt(e.getAbscence()) - 1;
                 e.setAbscence(String.valueOf(i));
                 DatabaseReference db_ref = FirebaseDatabase.getInstance().getReference();
                 DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                 ref.child("Module_users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                         child(Group_act.id_module).child("Groupes").child(EtudiantAct.key_g).
                         child("Etudiants").child(e.getNI()).setValue(e);
             } else {
                 final Snackbar s = Snackbar.make(v,
                         "Erreur.", Snackbar.LENGTH_LONG);
                 s.setDuration(10000);
                 s.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
                 s.setBackgroundTint(v.getResources().getColor(R.color.colorAccent));
                 s.setTextColor(v.getResources().getColor(R.color.colorPrimary));
                 s.setActionTextColor(v.getResources().getColor(R.color.colorPrimary));
                 s.setAction("OK", new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         s.dismiss();
                     }
                 });
                 s.show();
             }

         }});
     m.setNegativeButton("Non", new DialogInterface.OnClickListener() {
         @Override
         public void onClick(DialogInterface dialog, int which) {

         }
     });
     m.show();
 }


}
