package com.example.gestiondenotes;

import android.provider.ContactsContract;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDatabaseHelper {
    private FirebaseDatabase mDatabase ;
    private DatabaseReference databaseReference;
    private List<groupeModel> groups = new ArrayList<groupeModel> ();
    public FirebaseDatabaseHelper() {
        mDatabase = FirebaseDatabase.getInstance();
        databaseReference = mDatabase.getReference("Groupes_Users");
    }
    public interface Datastatut {
        void Dataloaded(List<groupeModel> groups,List<String> keys );
        void DataInserted();
        void DataDeleted();
        void DataUpdated();

    }
    public void readgroupes(final Datastatut datastatut) {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                groups.clear();
                List<String> keys = new ArrayList<>() ;
              for( DataSnapshot keynode : dataSnapshot.getChildren()) {
                  keys.add(keynode.getKey()) ;
                 groupeModel group = keynode.getValue(groupeModel.class);
                 groups.add(group) ;
              }
              datastatut.Dataloaded(groups,keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
