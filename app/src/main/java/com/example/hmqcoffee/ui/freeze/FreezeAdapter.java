package com.example.hmqcoffee.ui.freeze;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hmqcoffee.R;
import com.example.hmqcoffee.ui.product.product;
import com.example.hmqcoffee.ui.tea.TeaAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FreezeAdapter extends BaseAdapter {

    private Context context;
    private List<product> list;
    private int mresource;

    public FreezeAdapter() {
    }

    public FreezeAdapter(Context context, List<product> list, int mresource) {
        this.context = context;
        this.list = list;
        this.mresource = mresource;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
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
            holder.image = (ImageView)v.findViewById(R.id.item_image);
            holder.name = (TextView)v.findViewById(R.id.item_name);
            holder.price = (TextView)v.findViewById(R.id.item_price);
            v.setTag(holder);
        }
        else {
            holder = (FreezeAdapter.ViewHolder) v.getTag();
        }
        Picasso.get().load(list.get(position).linkimg).into(holder.image);
        holder.name.setText(list.get(position).name);
        holder.price.setText(list.get(position).price);

        return v;
    }

    public class ViewHolder{
        TextView name, price;
        ImageView image;

    }
}
