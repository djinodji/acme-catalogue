package com.acme.catalogue.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.acme.catalogue.R;
import com.acme.catalogue.model.NavDrawerItem;
import com.acme.catalogue.model.ProductItem;

import java.util.ArrayList;

/**
 * Created by Djinodji on 3/1/2016.
 */
public class ProductListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<ProductItem> productItems;


    public ProductListAdapter(Context context, ArrayList<ProductItem> productItems){
        this.context = context;
        this.productItems = productItems;
    }
    @Override
    public int getCount() {
        return productItems.size();
    }

    @Override
    public Object getItem(int position) {
        return productItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

                convertView = mInflater.inflate(R.layout.product_list_row, null);

        }

        TextView txtTitle = (TextView) convertView.findViewById(R.id.title);
        TextView description = (TextView) convertView.findViewById(R.id.description);

        TextView albumscount = (TextView) convertView.findViewById(R.id.albums_count);




        txtTitle.setText(productItems.get(position).getTitle());
        description.setText(productItems.get(position).getDescription());
        albumscount.setText(productItems.get(position).getAlbumCount());


        return convertView;
    }

}
