package com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.fragments;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
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
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentTransaction;

import com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.BuildConfig;
import com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.R;
import com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.activity.About;
import com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.adapter.ListAdapter;
import com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.data.AdapterItems;
import com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.data.ButtonItem;
import com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.data.ItemsData;

import java.util.ArrayList;
import java.util.List;

public class MoreFragment extends Fragment {


    private ListView listView;
    private ListAdapter adapter;
    private ArrayList<AdapterItems> data;
    private FragmentContainerView fragmentContainerView;
    private boolean fragmentVisible = false;

    public MoreFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requireActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (fragmentVisible) {
                    getChildFragmentManager().popBackStack();
                    fragmentContainerView.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
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
        return inflater.inflate(R.layout.more_fragment, container, false);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = view.findViewById(R.id.list_more);
        fragmentContainerView = view.findViewById(R.id.more_frag_layout);

        fragmentContainerView.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);

        data = new ArrayList<>();

        adapter = new ListAdapter(data);
        listView.setAdapter(adapter);

        ItemsData.getItems().observe(getViewLifecycleOwner(), items -> {
            data.clear();
            for (Pair<String, List<ButtonItem>> item : items) {
                if (item.first.equalsIgnoreCase("more")) {
                    for (ButtonItem buttonItem : item.second) {
                        data.add(new AdapterItems(buttonItem.text, buttonItem.icon, buttonItem.link));
                    }
                    data.add(new AdapterItems("Share", R.drawable.share, ""));
                    data.add(new AdapterItems("Rate", R.drawable.rate, ""));
                    data.add(new AdapterItems("Themes", R.drawable.theme, ""));
                    data.add(new AdapterItems("About", R.drawable.about, ""));
                    data.add(new AdapterItems("Exit", R.drawable.exit, ""));

                    adapter.notifyDataSetChanged();
                }
            }
        });

        adapter.setOnClickListener(item -> {
            if (!item.url.isEmpty()) {
                fragmentContainerView.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);

                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.replace(R.id.more_frag_layout, WebViewContainer.newInstance(item.url));
                transaction.addToBackStack(null);
                transaction.commit();

                fragmentVisible = true;
            } else {
                if (item.title.equals("Themes")) {
                    DialogFragment dialogFragment = new ThemeDialogFragment();
                    dialogFragment.show(getParentFragmentManager(), "Theme Dialog");
                }

                if (item.title.equals("Rate")) {
                    Uri uri = Uri.parse("market://details?id=" + BuildConfig.APPLICATION_ID);
                    Intent rateIntent = new Intent(Intent.ACTION_VIEW, uri);
                    rateIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NEW_DOCUMENT | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    try {
                        startActivity(rateIntent);
                    } catch (ActivityNotFoundException e) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID)));
                    }
                }

                if (item.title.equals("Share")) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "Download Rapture Ready at: https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
                    sendIntent.setType("text/plain");
                    startActivity(sendIntent);
                }

                if (item.title.equals("About")) {
                    startActivity(new Intent(requireActivity(), About.class));
                }

                if (item.title.equals("Exit")) {
                    requireActivity().finish();
                }


            }

        });
    }

    public void reload(){
        fragmentContainerView.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);
    }

}
