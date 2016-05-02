package com.acme.catalogue.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.acme.catalogue.R;
import com.acme.catalogue.model.AlbumItem;

import java.util.ArrayList;

/**
 * Created by Djinodji on 3/7/2016.
 */
public class AlbumListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<AlbumItem> albumItems;


    public AlbumListAdapter(Context context, ArrayList<AlbumItem> productItems){
        this.context = context;
        this.albumItems = productItems;
    }
    @Override
    public int getCount() {
        return this.albumItems.size();
    }

    @Override
    public Object getItem(int position) {
        return this.albumItems.get(position);
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

            convertView = mInflater.inflate(R.layout.album_list_row, null);

        }

        TextView txtTitle = (TextView) convertView.findViewById(R.id.album_title);
        TextView description = (TextView) convertView.findViewById(R.id.album_description);

        TextView albumscount = (TextView) convertView.findViewById(R.id.photos_count);




        txtTitle.setText(albumItems.get(position).getTitle());
        description.setText(albumItems.get(position).getDescription());
        albumscount.setText(albumItems.get(position).getPhotoCount());


        return convertView;
    }
}
