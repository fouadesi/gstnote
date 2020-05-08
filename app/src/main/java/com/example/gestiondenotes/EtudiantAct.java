package com.example.gestiondenotes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.transition.Explode;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar ;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class EtudiantAct extends AppCompatActivity {
    private Fragment1 fragmentActivity1 ;
    private Fragment2 fragmentActivity2 ;
    private  Fragment3 fragmentActivity3 ;
    private Toolbar toolbar ;
    private ViewPager viewPager ;
    private TabLayout tabLayout ;
    public static String key_g ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etudiant);
        toolbar = findViewById(R.id.toolbar) ;
        viewPager = findViewById(R.id.view_pager) ;
        tabLayout = findViewById(R.id.tab_layout);
        key_g = getIntent().getExtras().getString("ID");
        fragmentActivity1 = new Fragment1();
        fragmentActivity2 = new Fragment2() ;
        fragmentActivity3 = new Fragment3() ;
        tabLayout.setupWithViewPager(viewPager);
        ViewPagerAdapter viewpageradapter = new ViewPagerAdapter(getSupportFragmentManager(),0) ;
        viewpageradapter.Addfragment(fragmentActivity2,"Liste des etudiants");
        viewpageradapter.Addfragment(fragmentActivity1,"Ajouter un etudiant");
        viewpageradapter.Addfragment(fragmentActivity3,"Marquer les Absence");

        viewPager.setAdapter(viewpageradapter);
    }


    public class ViewPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragment = new ArrayList<>();
        private List<String> FragmentTitle = new ArrayList<>();
        public ViewPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }
        public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @Override
        public int getCount() {
            return fragment.size();
        }
        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragment.get(position);
        }
        public void Addfragment(Fragment fra, String title) {
            fragment.add(fra);
            FragmentTitle.add(title);
        }
        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return FragmentTitle.get(position);
        }
    }
}
