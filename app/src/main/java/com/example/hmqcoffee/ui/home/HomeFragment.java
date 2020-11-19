package com.example.hmqcoffee.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.hmqcoffee.MenuActivity;
import com.example.hmqcoffee.R;
import com.example.hmqcoffee.ui.cake.FoodFragment;
import com.example.hmqcoffee.ui.freeze.FreezeFragment;
import com.example.hmqcoffee.ui.product.ProductFragment;
import com.example.hmqcoffee.ui.tea.TeaFragment;

public class HomeFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        ImageButton coffee = (ImageButton)root.findViewById(R.id.home_coffee);
        ImageButton tea = (ImageButton)root.findViewById(R.id.home_tea1);
        ImageButton freeze = (ImageButton)root.findViewById(R.id.home_freeze);
        ImageButton food = (ImageButton)root.findViewById(R.id.home_food);
        ImageButton trungthu  = (ImageButton)root.findViewById(R.id.home_trungthu);
        ImageButton coldbrew = (ImageButton) root.findViewById(R.id.home_coldbrew2);
        ImageButton freeze1 = (ImageButton) root.findViewById(R.id.home_freeze1);
        ImageButton tea1 = (ImageButton)root.findViewById(R.id.home_tea1);
        ImageButton phucbontu = (ImageButton)root.findViewById(R.id.home_phucbontu);
        ImageButton thommoi = (ImageButton)root.findViewById(R.id.home_thomoi);


        ((MenuActivity) getActivity()).getSupportActionBar().setTitle(R.string.menu_home);
        coffee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductFragment p = new ProductFragment();
                changefragment(p);
                ((MenuActivity) getActivity()).getSupportActionBar().setTitle(R.string.menu_coffee);
            }
        });

        tea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TeaFragment t = new TeaFragment();
                changefragment(t);
                ((MenuActivity) getActivity()).getSupportActionBar().setTitle(R.string.menu_Tea);
            }
        });

        freeze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FreezeFragment f = new FreezeFragment();
                changefragment(f);
                ((MenuActivity) getActivity()).getSupportActionBar().setTitle(R.string.menu_Freeze);
            }
        });

        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FoodFragment fo = new FoodFragment();
                changefragment(fo);
                ((MenuActivity) getActivity()).getSupportActionBar().setTitle(R.string.menu_Food);
            }
        });

        trungthu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FoodFragment fo = new FoodFragment();
                changefragment(fo);
                ((MenuActivity) getActivity()).getSupportActionBar().setTitle(R.string.menu_Food);
            }
        });

        coldbrew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductFragment p = new ProductFragment();
                changefragment(p);
                ((MenuActivity) getActivity()).getSupportActionBar().setTitle(R.string.menu_coffee);
            }
        });
        freeze1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FreezeFragment f = new FreezeFragment();
                changefragment(f);
                ((MenuActivity) getActivity()).getSupportActionBar().setTitle(R.string.menu_Freeze);
            }
        });

        tea1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TeaFragment t = new TeaFragment();
                changefragment(t);
                ((MenuActivity) getActivity()).getSupportActionBar().setTitle(R.string.menu_Tea);
            }
        });

        phucbontu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TeaFragment t = new TeaFragment();
                changefragment(t);
                ((MenuActivity) getActivity()).getSupportActionBar().setTitle(R.string.menu_Tea);
            }
        });

        thommoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TeaFragment t = new TeaFragment();
                changefragment(t);
                ((MenuActivity) getActivity()).getSupportActionBar().setTitle(R.string.menu_Tea);
            }
        });
        return root;
    }

    public void changefragment(Fragment fragment){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.nav_host_fragment, fragment);
        ft.commit();
    }

}