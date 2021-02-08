package io.agora.openlive.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import io.agora.openlive.DoctorUI.ui.main.SectionsPagerAdapterMyPatientMF;
import io.agora.openlive.R;

public class MedicalFolderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_folder);
        SectionsPagerAdapterMyPatientMF sectionsPagerAdapter = new   SectionsPagerAdapterMyPatientMF(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        tabs.getTabAt(0).setIcon(R.drawable.ic_ambulance);
        tabs.getTabAt(1).setIcon(R.drawable.ic_medical_history);
    }
}