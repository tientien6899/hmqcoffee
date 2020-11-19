package com.example.hmqcoffee.ui.receipt;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hmqcoffee.R;
import com.example.hmqcoffee.ui.cart.Cart;
import com.example.hmqcoffee.ui.cart.CartAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.List;

import static com.example.hmqcoffee.LoginActivity.gmail;
import static com.example.hmqcoffee.MenuActivity.mData;
import static com.example.hmqcoffee.MenuActivity.number;
import static com.example.hmqcoffee.ui.setting.SettingFragment.diachi;
import static com.example.hmqcoffee.ui.setting.SettingFragment.nguoidung;
import static com.example.hmqcoffee.ui.setting.SettingFragment.sdt;

public class ReceiptFragment extends Fragment {

    ListView listView;
    List<Receipt> arrayreceipt;
    RecriptAdapter adapter;
    List<Cart> arraycart;
    CartAdapter cartAdapter;

    private int temp_i = 0;
    public static String temp_stt = "", temp_ngaygio = "", temp_trangthai = "", temp_total = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        arrayreceipt = new ArrayList<>();

        View root = inflater.inflate(R.layout.fragment_receipt, container, false);

        listView = (ListView) root.findViewById(R.id.list_receipt);
        loaddata();

        new CountDownTimer(1000, 500) {
            @Override
            public void onTick(long millisUntilFinished) {
                arrayreceipt.clear();
                loaddata();
            }

            @Override
            public void onFinish() {
                start();
            }
        }.start();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Receipt r = (Receipt) adapter.getItem(position);
                temp_stt = String.valueOf(r.getStt());
                temp_ngaygio = r.getNgaygio();
                temp_total = r.getTongdon();
                recepitsdialog();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final Receipt r = (Receipt) adapter.getItem(position);
                AlertDialog.Builder message = new AlertDialog.Builder(getContext());
                message.setTitle("Notification");
                message.setIcon(R.drawable.noti);
                message.setMessage("Do you want to delete ?");
                message.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(r.getTrangthai().equals("Đang xử lý")){
                            mData.child("TaiKhoan").child(gmail).child("Cart").child("Cart" + (r.getStt())).removeValue();
                            mData.child("TaiKhoan").child(gmail).child("Receipts").child("Receipt" + (r.getStt())).removeValue();
                            arrayreceipt.clear();
                            loaddata();
                            Toast.makeText(getContext(), "Delete Success", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            AlertDialog.Builder message1 = new AlertDialog.Builder(getContext());
                            message1.setTitle("Notification");
                            message1.setIcon(R.drawable.noti);
                            message1.setMessage("Khong the xoa don hang nay!");
                            message1.setNeutralButton("Back", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    return;
                                }
                            }).show();
                        }
                        return;
                    }
                });
                message.setPositiveButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                }).show();
                return true;
            }
        });

        return root;
    }

    private void recepitsdialog() {
        Dialog d = new Dialog(getContext());
        d.setContentView(R.layout.dialog_receipts);

        ListView dialog_lv = (ListView) d.findViewById(R.id.list_detail_receipt);
        final TextView dialog_num = (TextView) d.findViewById(R.id.dialog_detailreceipt_num);
        final TextView dialog_date = (TextView) d.findViewById(R.id.dialog_detailreceipt_date);
        final TextView dialog_total = (TextView) d.findViewById(R.id.dialog_detailreceipt_total);
        TextView dialog_name = (TextView)d.findViewById(R.id.dialog_detailreceipt_name);
        TextView dialog_phone = (TextView)d.findViewById(R.id.dialog_detailreceipt_phone);
        TextView dialog_dc = (TextView)d.findViewById(R.id.dialog_detailreceipt_address);

        arraycart = new ArrayList<>();
        cartAdapter = new CartAdapter(getActivity(), arraycart, R.layout.dialog_cart_detailreceipt);
        dialog_lv.setAdapter(cartAdapter);

        dialog_num.setText("HD" + temp_stt);
        dialog_date.setText(temp_ngaygio);
        dialog_total.setText(temp_total);
        dialog_name.setText(nguoidung);
        dialog_phone.setText(sdt);
        dialog_dc.setText(diachi);

        mData.child("TaiKhoan").child(gmail).child("Cart").child("Cart" + temp_stt).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Cart c = snapshot.getValue(Cart.class);
                arraycart.add(new Cart(c.id, c.link, c.ten, c.gia, c.sl, c.total));
                cartAdapter.notifyDataSetChanged();
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
        temp_stt = "";
        temp_ngaygio = "";
        temp_total = "";
        d.show();
    }

    private void loaddata() {
        mData.child("TaiKhoan").child(gmail).child("Receipts").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Receipt r = snapshot.getValue(Receipt.class);
                arrayreceipt.add(new Receipt(r.stt, r.trangthai, r.ngaygio, r.tongdon));
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

    @Override
    public void onStart() {
        super.onStart();
        adapter = new RecriptAdapter(getActivity(), arrayreceipt, R.layout.receipt);
        listView.setAdapter(adapter);
    }
}