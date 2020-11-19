package com.example.hmqcoffee.ui.cart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
;import com.example.hmqcoffee.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartAdapter extends BaseAdapter {

    private Context context;
    private List<Cart> listcart;
    private int mresource;

    public CartAdapter() {
    }

    public CartAdapter(Context context, List<Cart> listcart, int mresource) {
        this.context = context;
        this.listcart = listcart;
        this.mresource = mresource;
    }

    @Override
    public int getCount() {
        return listcart.size();
    }

    @Override
    public Object getItem(int position) {
        return listcart.get(position);
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

            holder.image = (ImageView)v.findViewById(R.id.cart_item_image);
            holder.name = (TextView)v.findViewById(R.id.cart_item_name);
            holder.price = (TextView)v.findViewById(R.id.cart_item_price);
            holder.sl = (TextView)v.findViewById(R.id.cart_item_sl);
            holder.total = (TextView)v.findViewById(R.id.cart_item_total);
            v.setTag(holder);
        }
        else {
            holder = (CartAdapter.ViewHolder) v.getTag();
        }

        Picasso.get().load(listcart.get(position).link).into(holder.image);
        holder.name.setText(listcart.get(position).ten);
        holder.price.setText(listcart.get(position).gia);
        holder.sl.setText(listcart.get(position).sl);
        holder.total.setText(listcart.get(position).total);

        return v;
    }
    public class ViewHolder{
        TextView name, price, sl, total;
        ImageView image;

    }

}
