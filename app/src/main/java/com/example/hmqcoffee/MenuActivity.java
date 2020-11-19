package com.example.hmqcoffee;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.hmqcoffee.ui.cart.Database;
import com.facebook.login.LoginManager;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import static com.example.hmqcoffee.LoginActivity.gmail;
import static com.example.hmqcoffee.LoginActivity.image;
import static com.example.hmqcoffee.ui.setting.SettingFragment.anhdaidien;
import static com.example.hmqcoffee.ui.setting.SettingFragment.diachi;
import static com.example.hmqcoffee.ui.setting.SettingFragment.nguoidung;
import static com.example.hmqcoffee.ui.setting.SettingFragment.sdt;

public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    public static DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
    public static Database database;
    public static AppBarConfiguration mAppBarConfiguration;
    public static int tamtinh = 0;
    public static long number = 0;
    public static int trang = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        //tao database CART.sqlite
        database = new Database(this, "CART.sqlite", null, 1);

        navigationView.setNavigationItemSelectedListener(this);
        View v = navigationView.getHeaderView(0);
        ImageView img = (ImageView) v.findViewById(R.id.imgAvatar);

//        if(!image.equals("")){
//            Picasso.get().load(image).into(img);
//        } else if(!anhdaidien.equals("")) {
//            Picasso.get().load(anhdaidien).into(img);
//        }

        TextView txtgmail = (TextView) v.findViewById(R.id.txtEmail);
        txtgmail.setText("Hi, " + gmail);
        Button btnlogout = (Button) v.findViewById(R.id.btnLogout);
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(v.getContext());
                dialog.setTitle("Notification");
                dialog.setIcon(R.drawable.noti);
                dialog.setMessage("Do you want to Logout?");
                dialog.setNeutralButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LoginManager.getInstance().logOut();
                        gmail = "";
                        sdt = "";
                        diachi = "";
                        nguoidung = "";
                        Intent i = new Intent(MenuActivity.this, LoginActivity.class);
                        startActivity(i);
                    }
                });
                dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                }).show();
            }
        });

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_coffee, R.id.nav_home, R.id.nav_cart, R.id.nav_setting, R.id.nav_tea, R.id.nav_Freeze, R.id.nav_Food, R.id.nav_receipt)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }
}