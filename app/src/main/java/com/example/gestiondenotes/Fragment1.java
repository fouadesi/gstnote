package com.example.gestiondenotes;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.provider.MediaStore;

import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


//Declrations




import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment1#newInstance} factory method to
 * create an instance of this fragment.
 */

public class Fragment1 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment1() {

        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment1.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment1 newInstance(String param1, String param2) {
        Fragment1 fragment = new Fragment1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);


        }


    }

    @Override
    public void onStart() {
        super.onStart();

    }
    @Nullable
    CircleImageView imageView ;
    private StorageReference storageReference ;
       Uri img_uri ;
    private ProgressDialog progressDialog;
    private ImageButton img_button ;
    String img_uri_download ;
    TextInputEditText txt_nom  ;
    TextInputEditText txt_prenom ;
  TextInputEditText txt_email ;
    TextInputEditText txt_ID ;
    String PHOTO_DEFAULT = "https://firebasestorage.googleapis.com/" +
            "v0/b/gestion-des-notes-57987.appspot.com/o/IMAGES_ETUDIANT%2Fe.j" +
            "pg?alt=media&token=0d25d198-f42d-42b7-8b59-1c61fa95ef8d" ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_1, container, false);
         txt_nom = v.findViewById(R.id.nom_du_etudiant_fragment1);
         txt_prenom = v.findViewById(R.id.prenom_du_etudiant_fragment1);
         txt_email = v.findViewById(R.id.email_du_etudiant_email_fragment);
         txt_ID = v.findViewById(R.id.numero_du_etudiant_fragment1);
         imageView = v.findViewById(R.id.image);
         progressDialog = new ProgressDialog(getContext());
         storageReference = FirebaseStorage.getInstance().getReference();
         Button btn = v.findViewById(R.id.Button_ajouter_un_eleve);
         btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               final String nom = txt_nom.getText().toString() ;
               final String prenom = txt_prenom.getText().toString();
               final String  mail = txt_email.getText().toString();
               final String NI = txt_ID.getText().toString() ;
               String ERROR = "Vous devez remplir ce champ" ;
               if (nom.isEmpty()) {
                   txt_nom.setError(ERROR);
                   return;
               }
               if (prenom.isEmpty()) {
                   txt_prenom.setError(ERROR);
                   return; }

               if (mail.isEmpty()) {
                   txt_email.setError(ERROR);
                   return; }

               if (NI.isEmpty()) {
                   txt_ID.setError(ERROR);
                   return; }

               if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
                   txt_email.setError("Enter a valid mail");
                   return; }

              if (img_uri == null) {
                    Etudiant e  = new Etudiant(nom,prenom,NI,mail,PHOTO_DEFAULT );
                    saveEtudiant(e);
                  return;
              }
             final  StorageReference st = storageReference.child("IMAGES_ETUDIANT").child("img_"+ NI);
             final UploadTask uploadTask =   st.putFile(img_uri);
             uploadTask.addOnFailureListener(new OnFailureListener() {
                 @Override
                 public void onFailure(@NonNull Exception e) {
                      Toast.makeText(getContext(), e.getMessage().toString(),Toast.LENGTH_LONG).show();
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
                             img_uri_download = st.getDownloadUrl().toString();
                             return st.getDownloadUrl();
                         }
                     }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                         @Override
                         public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                img_uri_download = task.getResult().toString();
                       Etudiant e = new Etudiant(nom,prenom,NI,mail,img_uri_download);
                       saveEtudiant(e);
                            } else {

                            }
                         }
                     });
                 }
             });



            }
        });
        img_button = v.findViewById(R.id.upload_image) ;
        img_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // lancer un intent pour y'aller a la galerie
     Intent gallery = new Intent (Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
     // on lance l'activite pour ramener la photo avec un request code
     startActivityForResult(gallery,200);
            }
        });
      return v ;
    }
    void saveEtudiant(Etudiant e) {
        CheckIfNiexists(e);


    }
   void  CheckIfNiexists(final Etudiant e) {

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference().child("Module_users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                child(Group_act.id_module).child("Groupes").child(EtudiantAct.key_g).
                child("Etudiants");
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.hasChild(e.getNI())) {
                    final Snackbar s = Snackbar.make(getActivity().findViewById(android.R.id.content),
                            "Ce Étudiant existe déjà.", Snackbar.LENGTH_LONG);
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
                } else {
                    e.setAbscence("0");
                    e.setNote1("0");
                    e.setNote2("0");
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                    ref.child("Module_users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                            child(Group_act.id_module).child("Groupes").child(EtudiantAct.key_g).
                            child("Etudiants").child(e.getNI()).setValue(e).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                txt_nom.setText("");
                                txt_prenom.setText("");
                                txt_email.setText("");
                                txt_ID.setText("");
                                final Snackbar s = Snackbar.make(getActivity().findViewById(android.R.id.content),
                                        "Ajout effectué avec succés .", Snackbar.LENGTH_LONG);
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

                            } else {

                                final Snackbar s = Snackbar.make(getActivity().findViewById(android.R.id.content),
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
                                s.show();
                            } }}); } }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // on teste si le resulta de notre demande et validee et data != null
        if (resultCode == RESULT_OK && requestCode == 200 && data != null) {
            Uri image_url = data.getData();
            imageView.setImageURI(image_url);
            img_uri = image_url ;
        }
    }

    void  ImageViewOnCLick(View v ) {

    }



    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

                // Faire un truc, ce menu item appartient à l'activité, c'est à elle de le traiter

            default:
                //Appelez vos fragments pour qu'ils vérifient si ce menu item n'est pas sous leur responsabilité
                return super.onOptionsItemSelected(item);
        }}
}
