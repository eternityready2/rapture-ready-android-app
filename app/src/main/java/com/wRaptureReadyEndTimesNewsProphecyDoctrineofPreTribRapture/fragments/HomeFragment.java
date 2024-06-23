package com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentTransaction;

import com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.R;
import com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.adapter.GridHomeAdapter;
import com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.data.ItemsData;

import java.util.ArrayList;

public class HomeFragment extends Fragment {


    private ArrayList<ItemsData> browseData, eternityData, connectData;
    private GridHomeAdapter browseDataAdapter, eternityDataAdapter, connectDataAdapter;
    private GridView browseGridView, eternityGridView, connectGridView;
    private FragmentContainerView fragmentContainerView;
    private LinearLayout home_grids_bg;

    private boolean fragmentVisible = false;

    public HomeFragment(){

    }

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
        browseGridView = view.findViewById(R.id.rapture_ready_grid);
        eternityGridView = view.findViewById(R.id.eternity_ready_media_grid);
        connectGridView = view.findViewById(R.id.connect_grid);
        home_grids_bg = view.findViewById(R.id.home_grids_bg);

        fragmentContainerView.setVisibility(View.GONE);
        home_grids_bg.setVisibility(View.VISIBLE);

        browseGrid();
        etrinityGrid();
        connectGrid();
    }


    public void browseGrid(){
        browseData = new ArrayList<>();

        browseData.add(new ItemsData("Rapture Ready", R.drawable.repture_ready, "https://www.raptureready.com/"));
        browseData.add(new ItemsData("Rapture Index", R.drawable.rapture_index, "https://www.raptureready.com/rapture-ready-index/"));
        browseData.add(new ItemsData("Rapture Ready News", R.drawable.rr_news, "https://www.raptureready.com/category/rapture-ready-news"));

        browseData.add(new ItemsData("Rapture Ready TV", R.drawable.repture_ready_tv, "https://www.raptureready.tv"));
        browseData.add(new ItemsData("Read Bible", R.drawable.repture_bible, "https://www.biblegateway.com/"));
        browseData.add(new ItemsData("Nearing Midnight", R.drawable.nearing_midnight, "https://www.raptureready.com/category/nearing-midnight/"));

        browseData.add(new ItemsData("Israel Watch", R.drawable.israel_watch, "https://www.raptureready.com/category/israel-watch/"));
        browseData.add(new ItemsData("Prayer Center", R.drawable.prayer_center, "https://www.raptureready.com/prayercenter/"));
        browseData.add(new ItemsData("RR Forms", R.drawable.rr_forms, "https://www.raptureforums.com/forums/"));

        browseData.add(new ItemsData("VertiZontal Media", R.drawable.vertizonatal_media, "https://www.vertizontalmedia.com"));
        browseData.add(new ItemsData("End Time Writers", R.drawable.end_time_writers, "https://www.raptureready.com/featured-end-time-writers/"));
        browseData.add(new ItemsData("Christian Classics", R.drawable.christian_classics, "https://www.raptureready.com/best-christian-classics/"));

        browseDataAdapter = new GridHomeAdapter(getContext(), browseData);

        browseGridView.setAdapter(browseDataAdapter);

        browseDataAdapter.setOnClickListener(item -> {
            fragmentContainerView.setVisibility(View.VISIBLE);
            home_grids_bg.setVisibility(View.GONE);

            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.replace(R.id.home_frag_layout, WebViewContainer.newInstance(item.url));
            transaction.addToBackStack(null);
            transaction.commit();

            fragmentVisible = true;
        });
    }

    public void etrinityGrid(){
        eternityData = new ArrayList<>();

        eternityData.add(new ItemsData("Watch TV", R.drawable.watch_tv, "https://eternityready.tv/live-tv"));
        eternityData.add(new ItemsData("Podcasts", R.drawable.podcasts, "https://www.eternityreadyradio.com/series.php"));
        eternityData.add(new ItemsData("Radio", R.drawable.radio, "https://listen.eternityready.com/"));

        eternityData.add(new ItemsData("On Demand", R.drawable.on_demand, "https://www.eternityready.com"));
        eternityData.add(new ItemsData("TV Channels", R.drawable.tv_channels, "https://www.eternityready.com/category.php?cat=tvnetworks"));
        eternityData.add(new ItemsData("Music Videos", R.drawable.music_videos, "https://www.eternityreadyradio.com"));

        eternityDataAdapter = new GridHomeAdapter(getContext(), eternityData);

        eternityGridView.setAdapter(eternityDataAdapter);

        eternityDataAdapter.setOnClickListener(item -> {
            fragmentContainerView.setVisibility(View.VISIBLE);
            home_grids_bg.setVisibility(View.GONE);

            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            transaction.replace(R.id.home_frag_layout, WebViewContainer.newInstance(item.url));
            transaction.addToBackStack(null);
            transaction.commit();

            fragmentVisible = true;
        });
    }

    public void connectGrid(){
        connectData = new ArrayList<>();

        connectData.add(new ItemsData("Contact", R.drawable.contact, "https://help.eternityready.com/"));
        connectData.add(new ItemsData("Donate", R.drawable.donate, "https://donorbox.org/eternity-ready-radio/"));
        connectData.add(new ItemsData("Call US", R.drawable.call, ""));


        connectDataAdapter = new GridHomeAdapter(getContext(), connectData);

        connectGridView.setAdapter(connectDataAdapter);

        connectDataAdapter.setOnClickListener(item -> {
            if (item.title.equals("Call US")){
                if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(requireActivity(), new String[]{android.Manifest.permission.CALL_PHONE}, 1);
                }else {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + "14172335389"));
                    startActivity(callIntent);
                }

            }else {
                fragmentContainerView.setVisibility(View.VISIBLE);
                home_grids_bg.setVisibility(View.GONE);

                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.replace(R.id.home_frag_layout, WebViewContainer.newInstance(item.url));
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
