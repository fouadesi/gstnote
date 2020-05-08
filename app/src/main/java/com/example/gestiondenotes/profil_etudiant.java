package com.example.gestiondenotes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class profil_etudiant extends AppCompatActivity {
 String nom ;
 String prenom ;
 String email ;
 String photo ;
 String Test1 ;
 String Test2 ;
 String absence ;
 String num ;
 String ni ;
 TextInputEditText nom_edi , prenom_edi , email_edi, Test1_edi,Test2_edi ,absence_edi,num_edi;
 Button btn ;
 CircleImageView im ;
 ImageButton upload_image ;
 Uri img_uri ;
StorageReference firebaseStorage ;
    String img_uri_download ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_etudiant);
        // Recuperation des Donnees a partir ;;;;
        btn = findViewById(R.id.Button_profile_etu);
        num =  getIntent().getExtras().getString("num");
        nom = getIntent().getExtras().getString("nom");
        prenom = getIntent().getExtras().getString("prenom");
        email = getIntent().getExtras().getString("email");
        photo = getIntent().getExtras().getString("photo");
        Test1 = getIntent().getExtras().getString("test1");
        img_uri_download = photo  ;
        Test2 = getIntent().getExtras().getString("test2");
        absence = getIntent().getExtras().getString("absence");
        ni = getIntent().getExtras().getString("ni");
        nom_edi = findViewById(R.id.nom_du_etudiant_profile_etu);
        prenom_edi = findViewById(R.id.prenom_du_etudiant_profile_etu);
        email_edi = findViewById(R.id.email_du_etudiant_email_profile_etu);
        Test1_edi = findViewById(R.id.Test1_pro_etu);
        Test2_edi = findViewById(R.id.Test2_pro_etu);
        absence_edi = findViewById(R.id.absence_etu);
        num_edi = findViewById(R.id.numero_du_etudiant_profile_etu);
        im = findViewById(R.id.image_profile_etu) ;
        upload_image = findViewById(R.id.upload_image_profile_etu);
        Glide.with(profil_etudiant.this).load(photo).into(im);
        nom_edi.setText(nom);
        prenom_edi.setText(prenom);
        email_edi.setText(email);
        Test1_edi.setText(Test1);
        Test2_edi.setText(Test2);
        absence_edi.setText(absence);
        num_edi.setText(ni);
        upload_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // lancer un intent pour y'aller a la galerie
                Intent gallery = new Intent (Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery,200);
            }});
       btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               final String new_nom = nom_edi.getText().toString();
               final String new_prenom = prenom_edi.getText().toString();
               final String new_email = email_edi.getText().toString() ;
               final String new_test1 = Test1_edi.getText().toString();
               final String new_test2 = Test2_edi.getText().toString();
               final String new_absence = absence_edi.getText().toString();
               if (new_nom.equals(nom) && new_prenom.equals(prenom) && new_test1.equals(Test1)
                       && new_test2.equals(Test2) && new_email.equals(email) && new_absence.equals(absence) && img_uri == null) {
                   final Snackbar s = Snackbar.make(findViewById(android.R.id.content),
                           "Aucun changement !", Snackbar.LENGTH_LONG);
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
               } else {
                   String ERROR = "Vous devez remplir ce champ" ;
                     if (new_nom.isEmpty() || new_prenom.isEmpty() || new_absence.isEmpty() ||
                             new_email.isEmpty() || new_test1.isEmpty() || new_test2.isEmpty() ) {
                         final Snackbar s = Snackbar.make(findViewById(android.R.id.content),
                                 "Vous devez remplir tous les champs", Snackbar.LENGTH_LONG);
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


                     } else {
                             if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                                 email_edi.setText("Entrer un email valid");
                                 return;
                             }
                             if (img_uri ==null) {
                                 Etudiant e = new Etudiant(new_nom, new_prenom, ni,new_absence, new_email,new_test1,new_test2 ,img_uri_download);
                                 edit_etu(e);
                                 return;
                             }else
                              {
                                 firebaseStorage = FirebaseStorage.getInstance().getReference();
                                 final StorageReference st = firebaseStorage.child("IMAGES_ETUDIANT").child("img_"+ ni);

                                     final UploadTask uploadTask = st.putFile(img_uri);
                                 uploadTask.addOnFailureListener(new OnFailureListener() {
                                     @Override
                                     public void onFailure(@NonNull Exception e) {

                                     }
                                 }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                     @Override
                                     public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                         Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                             @Override
                                             public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                                 if (!task.isSuccessful()) {
                                                     throw  task.getException() ;
                                                 }
                                                 String img_uri_download = st.getDownloadUrl().toString();
                                                 return st.getDownloadUrl();
                                             }
                                         }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                             @Override
                                             public void onComplete(@NonNull Task<Uri> task) {
                                                 if (task.isSuccessful()) {
                                                     img_uri_download = task.getResult().toString();
                                                     Etudiant e = new Etudiant(new_nom, new_prenom, ni,new_absence, new_email,new_test1,new_test2 ,img_uri_download);
                                                     edit_etu(e);
                                                 }
                                             }
                                         });

                                     }
                                 });
                             }
                     }

               }
           }
       });
        nom_edi.addTextChangedListener(watcher);
     prenom_edi.addTextChangedListener(watcher);
     email_edi.addTextChangedListener(watcher);
     Test1_edi.addTextChangedListener(watcher);
     Test2_edi.addTextChangedListener(watcher);
     absence_edi.addTextChangedListener(watcher);
     num_edi.addTextChangedListener(watcher);



    }
public void edit_etu(Etudiant e) {
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    ref.child("Module_users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
            child(Group_act.id_module).child("Groupes").child(EtudiantAct.key_g).
            child("Etudiants").child(e.getNI()).setValue(e).addOnCompleteListener(new OnCompleteListener<Void>() {
        @Override
        public void onComplete(@NonNull Task<Void> task) {
            if (task.isSuccessful()) {

                final Snackbar s = Snackbar.make(findViewById(android.R.id.content),
                        "Modification  a été effectuée avec succès", Snackbar.LENGTH_LONG);
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
                btn.setEnabled(false);
            } else {
                final Snackbar s = Snackbar.make(findViewById(android.R.id.content),
                        task.getException().getMessage(), Snackbar.LENGTH_LONG);
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
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // on teste si le resulta de notre demande et validee et data != null
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 200 && data != null) {
            Uri image_url = data.getData();
            im.setImageURI(image_url);
            img_uri = image_url;
            btn.setEnabled(true);
        }
    }


    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            btn.setEnabled(!nom_edi.getText().toString().trim().equals(nom) || !prenom_edi.getText().toString().trim().equals(prenom)
                    || !email_edi.getText().toString().trim().equals(email) || !Test1_edi.getText().toString().trim().
                    equals(Test1) || !Test2_edi.getText().toString().trim().equals(Test2) || !num_edi.getText().toString().trim().equals(num)  );
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

}
