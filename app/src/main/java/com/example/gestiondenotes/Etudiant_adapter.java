package com.example.gestiondenotes;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import android.telephony.CellSignalStrength;
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
    View v ;
    public Etudiant_adapter(@NonNull Context context, ArrayList<Etudiant> etudiant) {
        super(context, 0, etudiant);
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        final Etudiant etudiant = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_layout, parent, false);
        }
        final TextView nom_etudiant , email_etudiant , prenom_etudiant,NI,moy_etu;
        moy_etu = convertView.findViewById(R.id.moy_text);
        double _test1P = Double.valueOf(Group_act._test1);
        double _test2P = Double.valueOf(Group_act._test2);
        double _absencePoi = Double.valueOf(Group_act._absence);
        double _test1 = Double.valueOf(etudiant.getNote1()) ;
        double _test2 = Double.valueOf(etudiant.getNote2()) ;
        double _absence = Double.valueOf(etudiant.getAbscence()) ;
        double moyenne = ( (_test1P / 100)  * _test1) +( (_test2P / 100) * _test2 ) - (_absence * _absencePoi);
        if ( moyenne < 0 ) {
            moy_etu.setText(" Moyenne : " + 0);
            moy_etu.setTextColor(Color.parseColor("#ff1744"));
        } else {
            moy_etu.setText(" Moyenne : " + moyenne);
        }





        ImageButton supprimer_etudiant ;
         CircleImageView photo_etudiant ;
          photo_etudiant=  convertView.findViewById(R.id.profil_image);

          /// 100 % fonctionement  ;
        ImageButton etudiant_ajouter = convertView.findViewById(R.id.etudiant_ajouter);

          supprimer_etudiant = convertView.findViewById(R.id.supprimer_etudiant);
          /// 100 % fonctionnenement ;
          ImageButton img_btn = convertView.findViewById(R.id.mail_ajouter);
        nom_etudiant = convertView.findViewById(R.id.name_text);
        email_etudiant = convertView.findViewById(R.id.email_text);
        prenom_etudiant = convertView.findViewById(R.id.prenom_text);
        NI = convertView.findViewById(R.id.ni_text);
        final View finalConvertView = convertView;
        v = convertView ;
        img_btn.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      Intent intent = new Intent(Intent.ACTION_SEND);
                      intent.setType("text/html");
                      intent.putExtra(Intent.EXTRA_EMAIL  , new String[] { etudiant.getEmail() });                      intent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                      intent.putExtra(Intent.EXTRA_TEXT, "I'm email body.");
                      finalConvertView.getContext().startActivity(Intent.createChooser(intent, "Email via..."));
                  }
              });
        nom_etudiant.setText("Nom : " + etudiant.getNom());
        email_etudiant.setText("Email : " +etudiant.getEmail());
        prenom_etudiant.setText("Prenom : " + etudiant.getPrenom());
        NI.setText("NI : " + etudiant.getNI());
        Glide.with(getContext()).load(etudiant.getPicture()).into(photo_etudiant);
        ImageButton etu =  convertView.findViewById(R.id.etudiant_update);
         etu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i  = new Intent(getContext(),profil_etudiant.class);
                i.putExtra("nom",etudiant.getNom());
                i.putExtra("prenom",etudiant.getPrenom());
                i.putExtra("email",etudiant.getEmail());
                i.putExtra("ni",etudiant.getNI());
                i.putExtra("test1",etudiant.getNote1());
                i.putExtra("test2",etudiant.getNote2());
                i.putExtra("photo",etudiant.getPicture());
                i.putExtra("absence",etudiant.getAbscence()) ;
                getContext().startActivity(i);
            }
        });
        final View finalConvertView1 = convertView;
        supprimer_etudiant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                MaterialAlertDialogBuilder m  = new MaterialAlertDialogBuilder(getContext()) ;
                m.setTitle("Attention");
                m.setMessage("Êtes-vous sûr de vouloir supprimer?");
                m.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DatabaseReference db_ref = FirebaseDatabase.getInstance().getReference();
                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                                ref.child("Module_users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                                        child(Group_act.id_module).child("Groupes").child(EtudiantAct.key_g).
                                        child("Etudiants").child(etudiant.getNI()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                     if (task.isSuccessful()) {


                                                Toast.makeText(getContext(),"supprimer avec succès",Toast.LENGTH_LONG).show();

                                        }
                                        else {
                                         Toast.makeText(getContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();

                                     }
                                    }
                                });
                            }
                        }
                );
                m.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                m.show();
            }
        });

        photo_etudiant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInfoEtudiant(etudiant);
            }
        });
        v = convertView ;
        return convertView;
    }

   void  getInfoEtudiant(Etudiant etudiant) {
       MaterialAlertDialogBuilder Dialog = new MaterialAlertDialogBuilder(getContext());
       Dialog.setTitle("Information Sur" + etudiant.getNom() + " " + etudiant.getPrenom());
      Dialog.setIcon(R.drawable.info_etudiant);
    }
}
