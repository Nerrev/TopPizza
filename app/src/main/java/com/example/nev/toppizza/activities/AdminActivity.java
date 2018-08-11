package com.example.nev.toppizza.activities;



import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;

import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.example.nev.toppizza.R;
import com.example.nev.toppizza.fragments.AddAdmin;
import com.example.nev.toppizza.fragments.AddAdvertFragment;
import com.example.nev.toppizza.fragments.DelUserFragment;
import com.example.nev.toppizza.fragments.ProfileFragment;

public class AdminActivity extends AppCompatActivity {

    private LinearLayout adminLayout;
    private DelUserFragment delUserFragment;
    private AddAdmin addAdmin;
    private AddAdvertFragment addAdvertFragment;
    private ProfileFragment profileFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragmentTransaction.replace(R.id.adminLyt,profileFragment,"PR");
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commitAllowingStateLoss();
                    return true;
                case R.id.navigation_AddAdmin:
                    fragmentTransaction.replace(R.id.adminLyt,addAdmin,"AA");
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commitAllowingStateLoss();
                    return true;

                case R.id.navigation_orders:
                    fragmentTransaction.replace(R.id.adminLyt,delUserFragment,"DU");
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commitAllowingStateLoss();
                    return true;
                case R.id.navigation_statistics:
                    fragmentTransaction.replace(R.id.adminLyt,delUserFragment,"DU");
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commitAllowingStateLoss();
                    return true;
                case R.id.navigation_adverts:
                    fragmentTransaction.replace(R.id.adminLyt,addAdvertFragment,"AD");
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commitAllowingStateLoss();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        delUserFragment= new DelUserFragment();
        addAdmin=new AddAdmin();
        addAdvertFragment= new AddAdvertFragment();
        profileFragment= new ProfileFragment();

    }

}
