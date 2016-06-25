package com.intern.aqumulate.aqumulate;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

       // NavigationDrawerFragment drawerFragment= (NavigationDrawerFragment)
               // getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);

     //   drawerFragment.setUp((DrawerLayout) findViewById(R.id.drawer_layout),toolbar);
    }
}
