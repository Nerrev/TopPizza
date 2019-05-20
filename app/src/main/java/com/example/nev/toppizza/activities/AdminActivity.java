package com.example.nev.toppizza.activities;



import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;

import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.nev.toppizza.R;
import com.example.nev.toppizza.fragments.AddAdmin;
import com.example.nev.toppizza.fragments.DelUserFragment;
import com.example.nev.toppizza.fragments.OrderFragment;
import com.example.nev.toppizza.fragments.ProfileFragment;
import com.example.nev.toppizza.models.Order;
import com.example.nev.toppizza.services.SQLhelper;

import java.util.ArrayList;
import java.util.List;

public class AdminActivity extends AppCompatActivity  implements OrderFragment.OnListFragmentInteractionListener {

    private LinearLayout adminLayout;
    private DelUserFragment delUserFragment;
    private AddAdmin addAdmin;
    private ProfileFragment profileFragment;
    private OrderFragment orderFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    hideFilter();
                    fragmentTransaction.replace(R.id.adminLyt,profileFragment,"PR");
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commitAllowingStateLoss();
                    return true;
                case R.id.navigation_AddAdmin:
                    hideFilter();
                    fragmentTransaction.replace(R.id.adminLyt,addAdmin,"AA");
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commitAllowingStateLoss();
                    return true;

                case R.id.navigation_orders:
                    showFilter();
                    Bundle bundl = new Bundle();
                    bundl.putInt("mode", 0);
                    OrderFragment of=new OrderFragment();
                    of.setArguments(bundl);
                    fragmentTransaction.replace(R.id.adminLyt,of,"UOR");
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_statistics:
                    hideFilter();
                    fragmentTransaction.replace(R.id.adminLyt,delUserFragment,"DU");
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commitAllowingStateLoss();
                    return true;
            }
            return false;
        }
    };
    public void onListFragmentInteraction(Order order){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        hideFilter();
        delUserFragment= new DelUserFragment();
        addAdmin=new AddAdmin();
        profileFragment= new ProfileFragment();
        orderFragment=new OrderFragment();

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.adminLyt,profileFragment,"PR");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();



        SQLhelper dbh=new SQLhelper(this);

        final Spinner mTypes = (Spinner) findViewById(R.id.adtypes);
        List<String> options = new ArrayList<>();
        options.add("Type");
        Cursor c = dbh.getTypes();

        while(c.moveToNext()) {
            if(!options.contains(c.getString(c.getColumnIndex("TYPE"))))
                options.add(c.getString(c.getColumnIndex("TYPE")));
        }


        ArrayAdapter objGenderArr = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, options);
        mTypes.setAdapter(objGenderArr);

        mTypes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(!mTypes.getSelectedItem().toString().equals("Type")) {

                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    Bundle bundl = new Bundle();
                    bundl.putInt("mode", 2);
                    bundl.putString("Type", mTypes.getSelectedItem().toString());
                    OrderFragment of = new OrderFragment();
                    of.setArguments(bundl);
                    fragmentTransaction.replace(R.id.adminLyt, of, "UPM");
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
                else{
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    Bundle bundl = new Bundle();
                    bundl.putInt("mode", 0);
                    bundl.putString("Type", mTypes.getSelectedItem().toString());
                    OrderFragment of = new OrderFragment();
                    of.setArguments(bundl);
                    fragmentTransaction.replace(R.id.adminLyt, of, "UPM");
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    public void hideFilter(){
        LinearLayout lyt = findViewById(R.id.filterLytad);
        lyt.setVisibility(View.GONE);
    }


    public void showFilter(){
        LinearLayout lyt = findViewById(R.id.filterLytad);
        lyt.setVisibility(View.VISIBLE);
    }
    public void setIncome(String inc){
        TextView income= (TextView) findViewById(R.id.OrderPayments);
        income.setText("Income: "+inc+"$");
    }
    public void setOrderCount(String count){
        TextView orderCount= (TextView) findViewById(R.id.OrderCount);
        orderCount.setText("Orders: "+count);

    }

}
