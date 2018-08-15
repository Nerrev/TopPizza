package com.example.nev.toppizza.activities;

import android.app.FragmentManager;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import com.example.nev.toppizza.R;
import com.example.nev.toppizza.fragments.ContactUsFragment;
import com.example.nev.toppizza.fragments.OrderFragment;
import com.example.nev.toppizza.fragments.PizzaDialogFragment;
import com.example.nev.toppizza.fragments.PizzaFragment;
import com.example.nev.toppizza.fragments.ProfileFragment;
import com.example.nev.toppizza.fragments.UserHomeFragment;
import com.example.nev.toppizza.models.Order;
import com.example.nev.toppizza.models.Pizza;
import com.example.nev.toppizza.services.Functions;
import com.example.nev.toppizza.services.Login;
import com.example.nev.toppizza.services.SQLhelper;

import java.util.ArrayList;
import java.util.List;


public class UserActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, PizzaFragment.OnListFragmentInteractionListener
,OrderFragment.OnListFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View view, float v) {
            }

            @Override
            public void onDrawerOpened(@NonNull View view) {

                ImageView profilePic= (ImageView)  findViewById(R.id.uProfilePicture);
                TextView userName= (TextView)  findViewById(R.id.uuserName);
                TextView userEmail= (TextView)  findViewById(R.id.uuserEmail);



                byte[] profilePicture=null;
                int index=-1;
                index=Login.user.getColumnIndex("IMAGE");
                if(index!=-1) {
                    profilePicture = Login.user.getBlob(index);
                    if (profilePicture != null && profilePicture.length > 0)
                        profilePic.setImageBitmap(Functions.getImage(profilePicture));
                }

                userName.setText(Login.user.getString(Login.user.getColumnIndex("FNAME"))+" "+Login.user.getString(Login.user.getColumnIndex("LNAME")));
                userEmail.setText(Login.user.getString(Login.user.getColumnIndex("EMAIL")));
                LinearLayout profileHeader= findViewById(R.id.profHeader);
                profileHeader.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        drawer.closeDrawers();
                        final FragmentManager fragmentManager = getFragmentManager();
                        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.userActivityHolder,new ProfileFragment(),"UPR");
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        hideFilter();
                    }
                });
            }

            @Override
            public void onDrawerClosed(@NonNull View view) {

            }

            @Override
            public void onDrawerStateChanged(int i) {

            }
        });
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.userActivityHolder,new UserHomeFragment(),"HM");
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        ///////////////////////////////////////////////////////////////
        ImageView ordersBtn= (ImageView) findViewById(R.id.appBarOrders);
        ordersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Bundle bundl = new Bundle();
                bundl.putInt("mode", 1);
                OrderFragment of=new OrderFragment();
                of.setArguments(bundl);
                fragmentTransaction.replace(R.id.userActivityHolder,of,"UOR");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                hideFilter();
            }
        });


        SQLhelper dbh=new SQLhelper(this);

        final Spinner mTypes = (Spinner) findViewById(R.id.Mtypes);
        List<String> options = new ArrayList<>();
        options.add("Type");
        Cursor c = dbh.getTypes();

        while(c.moveToNext()) {
            if(!options.contains(c.getString(c.getColumnIndex("TYPE"))))
            options.add(c.getString(c.getColumnIndex("TYPE")));
        }


        ArrayAdapter objGenderArr = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, options);
        mTypes.setAdapter(objGenderArr);



        final  Spinner mSizes = (Spinner) findViewById(R.id.Msizes);
        String[] soptions = {"Small", "Medium", "Large"};
        ArrayAdapter sobjGenderArr = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, soptions);
        mSizes.setAdapter(sobjGenderArr);

        final EditText mPrice = (EditText) findViewById(R.id.Mprice);


        hideFilter();




        mTypes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(!mTypes.getSelectedItem().toString().equals("Type")) {

                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    Bundle bundl = new Bundle();
                    bundl.putInt("mode", 3);
                    bundl.putString("Type", mTypes.getSelectedItem().toString());
                    PizzaFragment of = new PizzaFragment();
                    of.setArguments(bundl);
                    fragmentTransaction.replace(R.id.userActivityHolder, of, "UPM");
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
                else{
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    Bundle bundl = new Bundle();
                    bundl.putInt("mode", 0);
                    bundl.putString("Type", mTypes.getSelectedItem().toString());
                    PizzaFragment of = new PizzaFragment();
                    of.setArguments(bundl);
                    fragmentTransaction.replace(R.id.userActivityHolder, of, "UPM");
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ImageView priceSearch = findViewById(R.id.sizeSearch);


        priceSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String price =mPrice.getText().toString();
                if(!price.equals("")){
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    Bundle bundl = new Bundle();
                    bundl.putInt("mode", 4);
                    bundl.putInt("size", mSizes.getSelectedItemPosition());
                    bundl.putString("Price", mPrice.getText().toString());
                    PizzaFragment of = new PizzaFragment();
                    of.setArguments(bundl);
                    fragmentTransaction.replace(R.id.userActivityHolder, of, "UPM");
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }

            }
        });


    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (id == R.id.nav_yHome) {
            hideFilter();
            fragmentTransaction.replace(R.id.userActivityHolder,new UserHomeFragment(),"HM");
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }else if (id == R.id.nav_Pizza) {
            showFilter();
            Bundle bundl = new Bundle();
            bundl.putInt("mode",0);
            PizzaFragment of=new PizzaFragment();
            of.setArguments(bundl);
            fragmentTransaction.replace(R.id.userActivityHolder,of,"PZ");
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_yFavorites) {
            hideFilter();
            Bundle bundl = new Bundle();
            bundl.putInt("mode", 1);
           PizzaFragment of=new PizzaFragment();
            of.setArguments(bundl);
            fragmentTransaction.replace(R.id.userActivityHolder,of,"UFV");
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_yOffers) {
            hideFilter();
            Bundle bundl = new Bundle();
            bundl.putInt("mode", 2);
            PizzaFragment of=new PizzaFragment();
            of.setArguments(bundl);
            fragmentTransaction.replace(R.id.userActivityHolder,of,"UOF");
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_yorders) {
            hideFilter();
            Bundle bundl = new Bundle();
            bundl.putInt("mode", 1);
            OrderFragment of=new OrderFragment();
            of.setArguments(bundl);
            fragmentTransaction.replace(R.id.userActivityHolder,of,"UOR");
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_CallUs) {
            hideFilter();
            fragmentTransaction.replace(R.id.userActivityHolder,new ContactUsFragment(),"UOR");
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_yLogout) {
            hideFilter();
            Login.Logout();
            Intent i = new Intent(UserActivity.this, LoginActivity.class);
            UserActivity.this.startActivity(i);
            finish();

        }else if (id == R.id.nav_yProfile) {
            hideFilter();
            fragmentTransaction.replace(R.id.userActivityHolder,new ProfileFragment(),"UPR");
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void onListFragmentInteraction(Pizza pizza){
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();

        PizzaDialogFragment pizzaDialog = PizzaDialogFragment.newInstance(pizza);

        pizzaDialog.show(fm,"PZD");
    }
    public void onListFragmentInteraction(Order order){
    }

    public void hideFilter(){
        LinearLayout lyt = findViewById(R.id.filterLyt);
        lyt.setVisibility(View.GONE);
    }


    public void showFilter(){
       LinearLayout lyt = findViewById(R.id.filterLyt);
       lyt.setVisibility(View.VISIBLE);
    }

}
