package com.example.gestiondenotes;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.IOException;




//Declrations




import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        View v =  inflater.inflate(R.layout.fragment_1, container, false);
        final TextInputEditText txt_nom = v.findViewById(R.id.nom_du_etudiant_fragment1);
        final TextInputEditText txt_prenom = v.findViewById(R.id.prenom_du_etudiant_fragment1);
        final TextInputEditText txt_email = v.findViewById(R.id.email_du_etudiant_email_fragment);
        final TextInputEditText txt_ID = v.findViewById(R.id.numero_du_etudiant_fragment1);
        imageView = v.findViewById(R.id.image);
        img_uri = Uri.parse("android.resource://com.example.gestiondenotes/drawable/e.jpg");
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
               StorageReference st = storageReference.child("IMAGES_ETUDIANT").child("img_"+ NI);
               st.putFile(img_uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                   @Override
                   public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                     String PICTURE = taskSnapshot.getUploadSessionUri().toString();
                     DatabaseReference db_ref  = FirebaseDatabase.getInstance().getReference();
                     Etudiant e = new Etudiant(nom,prenom,NI,mail,PICTURE);
                       db_ref.child("Module_users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                               child(Group_act.id_module).child("Groupes").child(EtudiantAct.key_g).setValue(e);
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
