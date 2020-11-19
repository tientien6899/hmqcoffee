package com.example.hmqcoffee.ui.receipt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.hmqcoffee.R;

import java.util.List;

public class RecriptAdapter extends BaseAdapter {
    private Context context;
    private List<Receipt> listreceipt;
    private int mresource;

    public RecriptAdapter() {
    }

    public RecriptAdapter(Context context, List<Receipt> listreceipt, int mresource) {
        this.context = context;
        this.listreceipt = listreceipt;
        this.mresource = mresource;
    }

    @Override
    public int getCount() {
        return listreceipt.size();
    }

    @Override
    public Object getItem(int position) {
        return listreceipt.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View v = convertView;
        ViewHolder holder = new ViewHolder();

        if(v == null){
            v = inflater.inflate(mresource,null);
            holder.stt = (TextView)v.findViewById(R.id.receipt_detail);
            holder.tt = (TextView)v.findViewById(R.id.receipt_status);
            holder.ngay = (TextView)v.findViewById(R.id.receipt_date);
            holder.tong = (TextView)v.findViewById(R.id.recript_total);
            v.setTag(holder);
        }
        else {
            holder = (RecriptAdapter.ViewHolder) v.getTag();
        }

        holder.stt.setText("HD"+String.valueOf(listreceipt.get(position).stt));
        holder.tt.setText(listreceipt.get(position).trangthai);
        holder.ngay.setText(listreceipt.get(position).ngaygio);
        holder.tong.setText(listreceipt.get(position).tongdon);
        return v;
    }
    public class ViewHolder{
        TextView stt, tt, ngay, tong;
    }
}

