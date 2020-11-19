package com.example.hmqcoffee.ui.tea;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hmqcoffee.R;
import com.example.hmqcoffee.ui.cart.Cart;
import com.example.hmqcoffee.ui.product.product;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.squareup.picasso.Picasso;

import static com.example.hmqcoffee.LoginActivity.gmail;
import static com.example.hmqcoffee.MenuActivity.number;
import static com.example.hmqcoffee.MenuActivity.tamtinh;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.example.hmqcoffee.MenuActivity.mData;

public class TeaFragment extends Fragment {

    ListView listView;
    List<product> list;
    private TeaAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        list = new ArrayList<>();

        View root = inflater.inflate(R.layout.fragment_tea, container, false);

        loaddata();

        listView = (ListView) root.findViewById(R.id.list_tea);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                product p = (product) adapter.getItem(position);
                itemDialog(p.linkimg, p.name, p.price);
            }
        });
        return root;
    }

    private void loaddata() {
        mData.child("Tea").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                product p = snapshot.getValue(product.class);
                list.add(new product(p.image, p.linkimg, p.name, p.price));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void itemDialog(final String link, final String ten, final String gia) {
        final Dialog d = new Dialog(getContext());
        d.setContentView(R.layout.dialog_item);

        ImageView img = (ImageView) d.findViewById(R.id.dialog_item_image);
        TextView name = (TextView) d.findViewById(R.id.dialog_item_name);
        final TextView price = (TextView) d.findViewById(R.id.dialog_item_price);
        final TextView sl = (TextView) d.findViewById(R.id.dialog_item_sl);
        Button cong = (Button) d.findViewById(R.id.dialog_item_cong);
        final Button tru = (Button) d.findViewById(R.id.dialog_item_tru);
        final TextView temp_sl = (TextView) d.findViewById(R.id.dialog_item_sl_change);
        final TextView temp_price = (TextView) d.findViewById(R.id.dialog_item_price_change);
        final TextView temp_total = (TextView) d.findViewById(R.id.dialog_item_total);

        Picasso.get().load(link).into(img);
        name.setText(ten);
        price.setText(gia);

        tru.setVisibility(View.INVISIBLE);

        String temp = (String) price.getText();

        final String lines = temp.replace(".", " ");
        String[] temps = lines.split(" ");
        final String temp_gia = temps[0];

        int tong = Integer.parseInt(sl.getText().toString()) * Integer.parseInt(temp_gia);
        temp_sl.setText(sl.getText());
        temp_price.setText(price.getText());
        temp_total.setText(tong + ".000 đ");

        final int[] a = {Integer.parseInt(sl.getText().toString())};
        cong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a[0] = a[0] + 1;
                if (a[0] != 1) {
                    tru.setVisibility(View.VISIBLE);
                }
                sl.setText(String.valueOf(a[0]));
                int tong = Integer.parseInt(sl.getText().toString()) * Integer.parseInt(temp_gia);
                temp_sl.setText(sl.getText());
                temp_price.setText(price.getText());
                temp_total.setText(tong + ".000 đ");
            }
        });
        tru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //int a = Integer.parseInt(sl.getText().toString());
                a[0] = a[0] - 1;
                if (a[0] == 1) {
                    tru.setVisibility(View.INVISIBLE);
                }
                sl.setText(String.valueOf(a[0]));
                int tong = Integer.parseInt(sl.getText().toString()) * Integer.parseInt(temp_gia);
                temp_sl.setText(sl.getText());
                temp_price.setText(price.getText());
                temp_total.setText(tong + ".000 đ");
            }
        });

        Button additem = (Button) d.findViewById(R.id.dialog_item_add);
        additem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(number == 0){
                    Calendar calendar = Calendar.getInstance();
                    number = calendar.getTimeInMillis();
                }
                int tong = Integer.parseInt(sl.getText().toString()) * Integer.parseInt(temp_gia);
                Cart c = new Cart(list.size(), link, ten, gia, sl.getText().toString(), String.valueOf(tong)+".000 đ");
                mData.child("TaiKhoan").child(gmail).child("Cart").child("Cart" + number).child(c.ten).setValue(c);
                Toast.makeText(getActivity(), "Đã thêm vào giỏ hàng!", Toast.LENGTH_SHORT).show();
                tamtinh += tong* 1000;
                d.dismiss();
            }
        });
        d.show();
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter = new TeaAdapter(getActivity(), list, R.layout.item);
        listView.setAdapter(adapter);
    }
}