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

import java.util.ArrayList;

/**
 * Created by Djinodji on 2/19/2016.
 */


public class NavDrawerListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<NavDrawerItem> navDrawerItems;
    private boolean isTop=false;
    public NavDrawerListAdapter(Context context, ArrayList<NavDrawerItem> navDrawerItems){
        this.context = context;
        this.navDrawerItems = navDrawerItems;
    }

    public NavDrawerListAdapter(Context context, ArrayList<NavDrawerItem> navDrawerItems, boolean istop){
        this.context = context;
        this.navDrawerItems = navDrawerItems;

            this.isTop=true;

    }

    @Override
    public int getCount() {
        return navDrawerItems.size();
    }

    @Override
    public Object getItem(int position) {
        return navDrawerItems.get(position);
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
            if (this.isTop && position==0)
            {
                convertView = mInflater.inflate(R.layout.drower_list_item_top, null);
            }
            else {
                convertView = mInflater.inflate(R.layout.drawer_list_item, null);
            }
        }

        TextView txtTitle = (TextView) convertView.findViewById(R.id.title);
        TextView txtCount = (TextView) convertView.findViewById(R.id.counter);
        if (this.isTop && position==0)
        {
            ImageView imgIcon = (ImageView) convertView.findViewById(R.id.icon);
            imgIcon.setImageBitmap(BitmapFactory.decodeFile(navDrawerItems.get(position).getIconUrl()));
        }
        else
        {
            ImageView imgIcon = (ImageView) convertView.findViewById(R.id.icon);


            imgIcon.setImageResource(navDrawerItems.get(position).getIcon());
        }


        txtTitle.setText(navDrawerItems.get(position).getTitle());

        // displaying count
        // check whether it set visible or not
        if(navDrawerItems.get(position).getCounterVisibility()){
           // txtCount.setText(navDrawerItems.get(position).getCount());
            //txtCount.setVisibility(View.VISIBLE);
        }else{
            // hide the counter view
            if (!isTop && position!=0)
            txtCount.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }

}