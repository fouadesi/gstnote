package com.example.gestiondenotes;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
public class moduleadapter extends ArrayAdapter<Module_users> {
    DatabaseReference db_ref ;

    public moduleadapter(@NonNull Context context, ArrayList<Module_users> module_users) {
        super(context, 0, module_users);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Module_users module_users = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_module, parent, false);
        }

        TextView nom, note_eli, coeff;
        ImageButton edit, delete,groupe;
        nom = (TextView) convertView.findViewById(R.id.nom_du_module_listview);
        note_eli = (TextView) convertView.findViewById(R.id.note_eliminatoire_du_module_listview);
        coeff = (TextView) convertView.findViewById(R.id.coefficient_module_listview);
        nom.setText("Nom : " +module_users.getNom());
        note_eli.setText("Note Eliminatoire : " + module_users.getNote_eliminatoire());
        coeff.setText("Coefficient : " + module_users.getCoef());
        edit = (ImageButton) convertView.findViewById(R.id.update_icon_listview);
        delete = (ImageButton) convertView.findViewById(R.id.delete_icon_listview);
        groupe = (ImageButton) convertView.findViewById(R.id.groupes_listview) ;
        groupe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),Group_act.class);
                i.putExtra("id",module_users.getId());
                getContext().startActivity(i); }});
        // update module
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent (getContext(), EditModule.class) ;
                i.putExtra("nom",module_users.getNom());
                i.putExtra("coeff",module_users.getCoef());
                i.putExtra("note eliminatoire",module_users.getNote_eliminatoire());
                i.putExtra("id",module_users.getId());
                i.putExtra("test1",module_users.formule.getTest1());
                i.putExtra("test2",module_users.formule.getTest2());
                i.putExtra("absence",module_users.formule.getAbscence());
                i.putExtra("participation",module_users.formule.getParticipation());
                getContext().startActivity(i); }});
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // supression
                db_ref = FirebaseDatabase.getInstance().getReference();
                db_ref.child("Module_users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(
                        module_users.getId()).removeValue();
            }});
        return convertView;
    }
}
