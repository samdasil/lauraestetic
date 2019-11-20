package com.example.lauraestetic;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.lauraestetic.adapter.ItemAdapter;
import com.example.lauraestetic.classes.Servico;
import com.example.lauraestetic.dao.ServicoDAO;
import com.example.lauraestetic.fragment.ClientesFragment;
import com.example.lauraestetic.fragment.RelatorioFragment;
import com.example.lauraestetic.fragment.ServicosFragment;
import com.example.lauraestetic.helper.RecyclerItemClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {



    private TextView textNomeUser;
    private static final String FILE_USER_DATA = "DataUser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        NavigationView header = findViewById( R.id.nav_view );
        View headerView = header.getHeaderView(0);

        textNomeUser    = findViewById( R.id.textNomeUser );
        Toolbar toolbar = findViewById( R.id.toolbar );

        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("Laura Estetic");

        // mode = 0 (private) para que somente o app possa mudar o arquivo
        SharedPreferences preferences = getSharedPreferences(FILE_USER_DATA, 0);
        String nome  = preferences.getString("nome", "Usuário não definido");



        // float action button
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Cadastro.class);
                startActivity(intent);
            }
        });

        // Menu lateral
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        // Menu lateral
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            startActivity(new Intent(this,Home.class));
        } else if (id == R.id.nav_clientes) {
            ClientesFragment clientesFragment = new ClientesFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frameContainer, clientesFragment);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_servicos) {
            ServicosFragment servicosFragment = new ServicosFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frameContainer, servicosFragment);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_relatorio) {
            RelatorioFragment relatorioFrag = new RelatorioFragment();
            FragmentTransaction fragTransaction = getSupportFragmentManager().beginTransaction();
            fragTransaction.replace(R.id.frameContainer, relatorioFrag);
            fragTransaction.commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
