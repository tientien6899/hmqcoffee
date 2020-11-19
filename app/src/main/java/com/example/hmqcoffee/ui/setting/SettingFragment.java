package com.example.hmqcoffee.ui.setting;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.hmqcoffee.LoginActivity;
import com.example.hmqcoffee.R;
import com.squareup.picasso.Picasso;

import java.io.File;

import static android.app.Activity.RESULT_OK;
import static com.example.hmqcoffee.LoginActivity.gmail;

public class SettingFragment extends Fragment {
    EditText name, phone, address;
    ImageButton avatar;
    Button select, save;
    int CODE_CHOOSE_PHOTO = 2, CODE_TAKE_PHOTO = 1;
    public static String diachi = "", sdt = "", nguoidung = "", anhdaidien = "";
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_setting, container, false);
        name = (EditText) root.findViewById(R.id.profile_name);
        phone = (EditText) root.findViewById(R.id.profile_phone);
        address = (EditText) root.findViewById(R.id.profile_address);
        avatar = (ImageButton) root.findViewById(R.id.btn_selectprofile);
        save = (Button) root.findViewById(R.id.profile_save);
        name.setText(gmail);

        if (!sdt.equals("") && !diachi.equals("") && !anhdaidien.equals("")) {
            name.setText(nguoidung);
            phone.setText(sdt);
            address.setText(diachi);
            Picasso.get().load(anhdaidien).into(avatar);
        }

        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                dialog.setTitle("Notification");
                dialog.setIcon(R.drawable.noti);
                dialog.setMessage("Choose your action?");
                dialog.setNeutralButton("Choose Photo", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ChoosePhoto();
                    }
                });
                dialog.setNegativeButton("Take Photo", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TakePhoto();
                    }
                });
                dialog.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                }).show();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nguoidung = String.valueOf(name.getText());
                sdt = String.valueOf(phone.getText());
                diachi = String.valueOf(address.getText());
            }
        });

        return root;
    }

    private void TakePhoto() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, CODE_TAKE_PHOTO);
    }

    private void ChoosePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
        startActivityForResult(intent, CODE_CHOOSE_PHOTO);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == CODE_TAKE_PHOTO && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent,CODE_TAKE_PHOTO);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_TAKE_PHOTO && resultCode == RESULT_OK && data != null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            avatar.setImageBitmap(bitmap);
        } else if( requestCode == CODE_CHOOSE_PHOTO && resultCode == RESULT_OK){
            Uri imageUri = data.getData();
            anhdaidien = imageUri.toString();
            avatar.setImageURI(imageUri);
        }
    }
}