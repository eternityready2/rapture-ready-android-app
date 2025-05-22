package com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentTransaction;

import com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.R;
import com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.adapter.HomeFragmentListAdapter;
import com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.data.ButtonItem;
import com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.data.ItemsData;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {


    private ArrayList<Pair<String, List<ButtonItem>>> data;
    private HomeFragmentListAdapter listAdapter;
    private FragmentContainerView fragmentContainerView;
    private ListView home_grids_bg;

    private boolean fragmentVisible = false;

    public HomeFragment(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requireActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (fragmentVisible) {
                    getChildFragmentManager().popBackStack();
                    fragmentContainerView.setVisibility(View.GONE);
                    home_grids_bg.setVisibility(View.VISIBLE);
                    fragmentVisible = false;
                } else {
                    setEnabled(false); // Disable this callback and pass the back press to activity
                    requireActivity().onBackPressed();
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.home_fragment, container, false);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fragmentContainerView = view.findViewById(R.id.home_frag_layout);
        home_grids_bg = view.findViewById(R.id.home_grids_list);

        data = new ArrayList<>();
        listAdapter = new HomeFragmentListAdapter(data);
        home_grids_bg.setAdapter(listAdapter);

        fragmentContainerView.setVisibility(View.GONE);
        home_grids_bg.setVisibility(View.VISIBLE);

        connectGrid();

        ItemsData.getItems().observe(getViewLifecycleOwner(), items -> {
            data.clear();
            for (Pair<String, List<ButtonItem>> item : items) {
                if (!item.first.equalsIgnoreCase("more")
                        && !item.first.equalsIgnoreCase("bottomNav")) {
                    data.add(item);
                }
            }
            listAdapter.notifyDataSetChanged();
        });
    }

    public void connectGrid(){
        listAdapter.setOnClickListener(item -> {
            if (item.text.equalsIgnoreCase("Call US")){
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + "+14172335389"));
                startActivity(callIntent);
            } else {
                fragmentContainerView.setVisibility(View.VISIBLE);
                home_grids_bg.setVisibility(View.GONE);

                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.replace(R.id.home_frag_layout, WebViewContainer.newInstance(item.link));
                transaction.addToBackStack(null);
                transaction.commit();

                fragmentVisible = true;
            }

        });
    }

    public void reload(){
        fragmentContainerView.setVisibility(View.GONE);
        home_grids_bg.setVisibility(View.VISIBLE);
    }

}
