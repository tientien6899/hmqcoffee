package com.example.hmqcoffee.ui.cart;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.example.hmqcoffee.ui.receipt.Receipt;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.annotations.Nullable;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;

import static com.example.hmqcoffee.MenuActivity.database;
import static com.example.hmqcoffee.LoginActivity.gmail;
import static com.example.hmqcoffee.MenuActivity.mData;
import static com.example.hmqcoffee.MenuActivity.number;
import static com.example.hmqcoffee.MenuActivity.tamtinh;

public class CartFragment extends Fragment {

    ListView lscart;
    ArrayList<Cart> arraycart;
    CartAdapter adapter;
    TextView tamtinhcart;
    Button btnorder;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_cart, container, false);

        String sql = "CREATE TABLE IF NOT EXISTS TBCART(ID INTEGER PRIMARY KEY AUTOINCREMENT, LINK varchar(200), NAME varchar(50), PRICE varchar(20), SL varchar(3), TOTAL varchar(10))";
        database.querydata(sql);

        tamtinhcart = (TextView) v.findViewById(R.id.cart_tamtinh);
        if (tamtinh > 999) {
            int a = tamtinh / 1000;
            tamtinhcart.setText(a + ".000 đ");
        }

        lscart = (ListView) v.findViewById(R.id.list_cart);
        arraycart = new ArrayList<>();
        adapter = new CartAdapter(getContext(), arraycart, R.layout.cart);
        lscart.setAdapter(adapter);

        lscart.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cart c = (Cart) adapter.getItem(position);
                itemDialog(c.link, c.ten, c.gia, c.sl);
            }
        });

        LoadList();

        btnorder = (Button) v.findViewById(R.id.btn_order);
        btnorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                String a = String.valueOf(calendar.getTime());
                String b = a.substring(0, a.length() - 16);
                final String c = b + a.substring(b.length() + 10, a.length());
                final AlertDialog.Builder message = new AlertDialog.Builder(getContext());
                message.setTitle("Notification");
                message.setIcon(R.drawable.noti);
                message.setMessage("Confirm Orders ?");
                message.setNegativeButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Receipt r = new Receipt(number, "Đang xử lý", c, tamtinhcart.getText().toString());
                        mData.child("TaiKhoan").child(gmail).child("Receipts").child("Receipt" + r.getStt()).setValue(r);
                        Toast.makeText(getActivity(), "Đơn hàng đã được chuyển tới của hàng!", Toast.LENGTH_SHORT).show();
                        number = 0;
                        lscart.removeAllViewsInLayout();
                        arraycart.clear();
                        tamtinh = 0;
                        tamtinhcart.setText("0 đ");
                        return;
                    }
                });
                message.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                }).show();
            }
        });
        return v;
    }

    public void itemDialog(final String link, final String ten, final String gia, final String sol) {
        final Dialog d = new Dialog(getContext());
        d.setContentView(R.layout.dialog_cart);

        ImageView img = (ImageView) d.findViewById(R.id.dialog_cart_image);
        TextView name = (TextView) d.findViewById(R.id.dialog_cart_name);
        final TextView price = (TextView) d.findViewById(R.id.dialog_cart_price);
        final TextView sl = (TextView) d.findViewById(R.id.dialog_cart_sl);
        Button cong = (Button) d.findViewById(R.id.dialog_cart_cong);
        final Button tru = (Button) d.findViewById(R.id.dialog_cart_tru);
        final TextView temp_sl = (TextView) d.findViewById(R.id.dialog_item_sl_change);
        final TextView temp_price = (TextView) d.findViewById(R.id.dialog_item_price_change);
        final TextView temp_total = (TextView) d.findViewById(R.id.dialog_item_total);
        Button delete = (Button) d.findViewById(R.id.dialog_cart_delete);

        Picasso.get().load(link).into(img);
        name.setText(ten);
        price.setText(gia);
        sl.setText(sol);

        String temp = (String) price.getText();
        final String lines = temp.replace(".", " ");
        String[] temps = lines.split(" ");
        final String temp_gia = temps[0];
        final int[] a = {Integer.parseInt(sl.getText().toString())};

        temp_sl.setText(sl.getText());
        temp_price.setText(price.getText());
        temp_total.setText((a[0] * Integer.parseInt(temp_gia)) + ".000 đ");

        cong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp_tong = Integer.parseInt(temp_gia) * 1000;
                a[0] = a[0] + 1;
                if (a[0] != 1) {
                    tru.setVisibility(View.VISIBLE);
                }
                sl.setText(String.valueOf(a[0]));
                tamtinh += temp_tong;
                int tong = Integer.parseInt(sl.getText().toString()) * Integer.parseInt(temp_gia);
                temp_sl.setText(sl.getText());
                temp_price.setText(price.getText());
                temp_total.setText(tong + ".000 đ");
            }
        });
        tru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp_tru = Integer.parseInt(temp_gia) * 1000;
                a[0] = a[0] - 1;
                if (a[0] == 1) {
                    tru.setVisibility(View.INVISIBLE);
                }
                sl.setText(String.valueOf(a[0]));
                tamtinh -= temp_tru;
                int tong = Integer.parseInt(sl.getText().toString()) * Integer.parseInt(temp_gia);
                temp_sl.setText(sl.getText());
                temp_price.setText(price.getText());
                temp_total.setText(tong + ".000 đ");
            }
        });

        Button savecart = (Button) d.findViewById(R.id.dialog_cart_save);
        savecart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tong = Integer.parseInt(sl.getText().toString()) * Integer.parseInt(temp_gia);
                Cart c = new Cart(arraycart.size(), link, ten, gia, sl.getText().toString(), String.valueOf(tong)+".000 đ");
                mData.child("TaiKhoan").child(gmail).child("Cart").child("Cart" + number).child(c.ten).setValue(c, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        arraycart.clear();
                        LoadList();
                        if (tamtinh > 999) {
                            int a = tamtinh / 1000;
                            tamtinhcart.setText(a + ".000 đ");
                        }
                        if(tamtinh > 999999){
                            int a = tamtinh / 1000000;
                            tamtinhcart.setText(a +".000 đ");
                        }
                    }
                });
                Toast.makeText(getActivity(), "Save success!", Toast.LENGTH_SHORT).show();
                d.dismiss();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mData.child("TaiKhoan").child(gmail).child("Cart").child("Cart" + number).child(ten).removeValue();
                arraycart.clear();
                LoadList();
                Toast.makeText(getContext(), "Delete Success", Toast.LENGTH_SHORT).show();
                d.dismiss();
            }
        });

        d.show();
    }


    public void LoadList() {
        mData.child("TaiKhoan").child(gmail).child("Cart").child("Cart" + number).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Cart c = snapshot.getValue(Cart.class);
                arraycart.add(new Cart(c.id, c.link, c.ten, c.gia, c.sl, c.total));
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
}