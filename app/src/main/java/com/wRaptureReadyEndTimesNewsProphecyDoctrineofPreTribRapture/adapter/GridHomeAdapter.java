package com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.R;
import com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.data.ButtonItem;
import com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.data.Constants;

import java.util.ArrayList;

public class GridHomeAdapter extends BaseAdapter {
    private final ArrayList<ButtonItem> data;
    private OnItemClickListener listener;

    // Constructor
    public GridHomeAdapter(ArrayList<ButtonItem> data) {
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
        View gridView;
        if (convertView == null) {
            gridView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.home_grid_item, parent, false);

            TextView textView = gridView.findViewById(R.id.txt);
            textView.setText(data.get(position).text);

            ImageView icon = gridView.findViewById(R.id.icon);
            Glide.with(icon).load(Constants.BASE_URL + data.get(position).icon).into(icon);

            gridView.setOnClickListener(v -> listener.onItemClick(data.get(position)));
        } else {
            gridView = convertView;
        }

        return gridView;
    }

    public void setOnClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(ButtonItem item);
    }

}
