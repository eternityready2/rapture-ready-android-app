package com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.R;
import com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.data.ItemsData;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {

    private Context context;
    private final ArrayList<ItemsData> data;
    private OnItemClickListener listener;

    // Constructor
    public ListAdapter(Context context, ArrayList<ItemsData> data) {
        this.context = context;
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
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View listview;

        if (convertView == null) {
            listview = new View(context);
            listview = inflater.inflate(R.layout.list_item, null);

            TextView textView = listview.findViewById(R.id.txt);
            textView.setText(data.get(position).title);

            ImageView icon = listview.findViewById(R.id.icon);
            icon.setImageResource(data.get(position).icon);

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
        void onItemClick(ItemsData item);
    }

}
