package io.agora.openlive.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import io.agora.openlive.R;
import io.agora.openlive.models.Consultation;

public class ConsultationFragment extends Fragment {
    ListView consultations;
    ConsultationAdapter adapter;
    ArrayList<Consultation> consultationList = new ArrayList<>();
    List<Consultation> myConsultations;
    FirebaseUser user;
    String emailPatient;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.consultation_fragment, container, false);
        consultations = view.findViewById(R.id.consultations);
        myConsultations = new ArrayList<>();
        user = FirebaseAuth.getInstance().getCurrentUser();
        emailPatient = user.getEmail();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Consultations");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myConsultations.clear();
                for(DataSnapshot data : dataSnapshot.getChildren())
                {
                    Consultation consultation = data.getValue(Consultation.class);
                    if(consultation.getPatientEmail()!=null){
                        if(consultation.getPatientEmail().equals(emailPatient)) {
                            myConsultations.add(consultation);
                            if (getActivity()!=null){
                                adapter = new ConsultationAdapter(getActivity(), myConsultations);
                                consultations.setAdapter(adapter);
                            }
                        }
                    }

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        consultations.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), ConsultationInfoActivity.class);
                Consultation consultation = myConsultations.get(position);
                intent.putExtra("doctorName", consultation.getDoctorName());
                intent.putExtra("doctorEmail", consultation.getDoctorEmail());
                intent.putExtra("date", consultation.getDate());
                intent.putExtra("price", consultation.getPrice());
                intent.putExtra("prescription", consultation.getPrescription());
                intent.putExtra("disease", consultation.getDisease());
                intent.putExtra("patientEmail",consultation.getPatientEmail());
                intent.putExtra("prescription_image_name",consultation.get_prescription_image_name());
                startActivity(intent);
            }
        });


        return view;
    }
}
