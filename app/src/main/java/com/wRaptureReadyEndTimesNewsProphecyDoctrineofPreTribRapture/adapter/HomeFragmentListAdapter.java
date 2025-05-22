package com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.adapter;

import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.R;
import com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.data.ButtonItem;

import java.util.ArrayList;
import java.util.List;

public class HomeFragmentListAdapter extends BaseAdapter {

    private final ArrayList<Pair<String, List<ButtonItem>>> data;
    private GridHomeAdapter.OnItemClickListener listener;

    // Constructor
    public HomeFragmentListAdapter(ArrayList<Pair<String, List<ButtonItem>>> data) {
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
        View display;
        if (convertView == null) {
            display = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.home_view_items, parent, false);

            TextView textView = display.findViewById(R.id.grid_title);
            textView.setText(data.get(position).first);

            GridView gridView = display.findViewById(R.id.home_grid);
            List<ButtonItem> buttonItems = data.get(position).second;
            Log.e("buttn size", buttonItems.size() + " " + data.get(position).first);
            GridHomeAdapter adapter = new GridHomeAdapter(new ArrayList<>(buttonItems));
            gridView.setAdapter(adapter);
            expandGridView(gridView, 3);
            adapter.setOnClickListener(listener);
        } else {
            display = convertView;
        }

        return display;
    }

    public void setOnClickListener(GridHomeAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    public static void expandGridView(GridView gridView, int numColumns) {
        GridHomeAdapter adapter = (GridHomeAdapter) gridView.getAdapter();
        if (adapter == null) return;

        int totalHeight = 0;
        int items = adapter.getCount();
        int rows = (int) Math.ceil((double) items / numColumns);

        for (int i = 0; i < rows; i++) {
            View item = adapter.getView(i, null, gridView);
            item.measure(
                    View.MeasureSpec.makeMeasureSpec(gridView.getWidth(), View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            );
            totalHeight += item.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = totalHeight + (gridView.getVerticalSpacing() * (rows - 1));
        gridView.setLayoutParams(params);
        gridView.requestLayout();
    }

}
