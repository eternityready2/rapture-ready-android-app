package com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.R;
import com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.data.AdapterItems;
import com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.data.Constants;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {

    private final ArrayList<AdapterItems> data;
    private OnItemClickListener listener;

    // Constructor
    public ListAdapter(ArrayList<AdapterItems> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listview;
        if (convertView == null) {
            listview = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item, parent, false);

            TextView textView = listview.findViewById(R.id.txt);
            textView.setText(data.get(position).title);

            ImageView icon = listview.findViewById(R.id.icon);
            String iconUrl = data.get(position).icon;
            if (iconUrl == null) {
                icon.setImageResource(data.get(position).iconRes);
            } else {
                Glide.with(icon).load(Constants.BASE_URL + iconUrl).into(icon);
            }

            listview.setOnClickListener(v -> listener.onItemClick(data.get(position)));
        } else {
            listview = convertView;
        }

        return listview;
    }

    public void setOnClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(AdapterItems item);
    }

}
