package com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.fragments;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.preference.PreferenceManager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.BuildConfig;
import com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.R;
import com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.activity.About;
import com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.adapter.FragmentPagerAdapter;
import com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.data.ThemeModel;

public class WebViewFragment extends Fragment {

    private final String[] webUrls = new String[5];
    private final String[] titles = new String[5];
    private Toolbar toolbar;
    private ViewPager2 pager2;
    private FragmentPagerAdapter adapter;
    private SharedPreferences.OnSharedPreferenceChangeListener listener;
    private BottomNavigationView navigationView;

    public WebViewFragment() {

    }

    public static WebViewFragment newInstance() {
        return new WebViewFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        webUrls[0] = getString(R.string.Rapture_Ready_link);
        webUrls[1] = getString(R.string.Rapture_Ready_link);
        webUrls[2] = "https://eternityready.tv/live-tv";
        webUrls[3] = getString(R.string.Rapture_Radio_link);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        adapter = new FragmentPagerAdapter(getChildFragmentManager(), webUrls, getLifecycle());
        return inflater.inflate(R.layout.fragment_web_view, container, false);
        //webView.setMixedContentAllowed(true);
        //webView.setCookiesEnabled(true);
        //webView.setGeolocationEnabled(true);
        //webView.getSettings().setSupportZoom(true);
        //webView.getSettings().setBuiltInZoomControls(false);

        //webView.loadUrl(webUrl);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navigationView = view.findViewById(R.id.bottomNavigationView);
        pager2 = view.findViewById(R.id.viewpager2);
        toolbar = view.findViewById(R.id.toolbar);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
        int pos = preferences.getInt(ThemeModel.COLOR_KEY, 0);
        ThemeModel model = ThemeModel.get(pos);

        pager2.setNestedScrollingEnabled(true);
        pager2.setAdapter(adapter);
        pager2.setOffscreenPageLimit(5);
        pager2.setUserInputEnabled(false);

        pager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        navigationView.setSelectedItemId(R.id.home);
                        toolbar.setTitle("Home");
                        break;
                    case 1:
                        navigationView.setSelectedItemId(R.id.repture_ready);
                        toolbar.setTitle("Repture Ready");
                        break;
                    case 2:
                        navigationView.setSelectedItemId(R.id.watch_tv);
                        toolbar.setTitle("Watch TV");
                        break;
                    case 3:
                        navigationView.setSelectedItemId(R.id.radio);
                        toolbar.setTitle("Radio");
                        break;
                    case 4:
                        navigationView.setSelectedItemId(R.id.more);
                        toolbar.setTitle("More");
                        break;
                }
            }
        });

        navigationView.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.home) {
                pager2.setCurrentItem(0);
                Fragment fragment = getFragAt(0, getChildFragmentManager());
                if (fragment instanceof HomeFragment) ((HomeFragment) fragment).reload();
                toolbar.setTitle("Home");
            }

            if (item.getItemId() == R.id.repture_ready) {
                pager2.setCurrentItem(1);
                Fragment fragment = adapter.getFragAt(pager2.getCurrentItem(), getChildFragmentManager());
                if (fragment instanceof WebViewContainer)
                    ((WebViewContainer) fragment).Return(webUrls[1]);
                toolbar.setTitle("Repture Ready");
            }

            if (item.getItemId() == R.id.watch_tv) {
                pager2.setCurrentItem(2);
                Fragment fragment = adapter.getFragAt(pager2.getCurrentItem(), getChildFragmentManager());
                if (fragment instanceof WebViewContainer)
                    ((WebViewContainer) fragment).Return(webUrls[2]);
                toolbar.setTitle("Watch TV");
            }

            if (item.getItemId() == R.id.radio) {
                pager2.setCurrentItem(3);
                Fragment fragment = adapter.getFragAt(pager2.getCurrentItem(), getChildFragmentManager());
                if (fragment instanceof WebViewContainer)
                    ((WebViewContainer) fragment).Return(webUrls[3]);
                toolbar.setTitle("Radio");
            }

            if (item.getItemId() == R.id.more) {
                pager2.setCurrentItem(4);
                Fragment fragment = getFragAt(pager2.getCurrentItem(), getChildFragmentManager());
                if (fragment instanceof MoreFragment) ((MoreFragment) fragment).reload();
                toolbar.setTitle("More");
            }

            return true;
        });

        navigationView.setOnItemReselectedListener(item -> {
            if (item.getItemId() == R.id.home) {
                pager2.setCurrentItem(0);
                Fragment fragment = getFragAt(0, getChildFragmentManager());
                if (fragment instanceof HomeFragment) ((HomeFragment) fragment).reload();
                toolbar.setTitle("Home");
            }

            if (item.getItemId() == R.id.repture_ready) {
                pager2.setCurrentItem(1);
                Fragment fragment = adapter.getFragAt(pager2.getCurrentItem(), getChildFragmentManager());
                if (fragment instanceof WebViewContainer)
                    ((WebViewContainer) fragment).Return(webUrls[1]);
                toolbar.setTitle("Repture Ready");
            }

            if (item.getItemId() == R.id.watch_tv) {
                pager2.setCurrentItem(2);
                Fragment fragment = adapter.getFragAt(pager2.getCurrentItem(), getChildFragmentManager());
                if (fragment instanceof WebViewContainer)
                    ((WebViewContainer) fragment).Return(webUrls[2]);
                toolbar.setTitle("Tv");
            }

            if (item.getItemId() == R.id.radio) {
                pager2.setCurrentItem(3);
                Fragment fragment = adapter.getFragAt(pager2.getCurrentItem(), getChildFragmentManager());
                if (fragment instanceof WebViewContainer)
                    ((WebViewContainer) fragment).Return(webUrls[3]);
                toolbar.setTitle("Radio");
            }

            if (item.getItemId() == R.id.more) {
                pager2.setCurrentItem(4);
                Fragment fragment = getFragAt(pager2.getCurrentItem(), getChildFragmentManager());
                if (fragment instanceof MoreFragment) ((MoreFragment) fragment).reload();
                toolbar.setTitle("More");
            }
        });

        toolbar.setBackgroundColor(model.TOOLBAR_COLOR);

        listener = (sharedPreferences, key) -> {
            assert key != null;
            if (key.equals(ThemeModel.COLOR_KEY)) {
                int index = sharedPreferences.getInt(key, 0);
                ThemeModel themeModel = ThemeModel.get(index);
                toolbar.setBackgroundColor(themeModel.TOOLBAR_COLOR);
            }
        };

        preferences.registerOnSharedPreferenceChangeListener(listener);

//        toolbar.inflateMenu(R.menu.toolbar_menu);
//
//        toolbar.setOnMenuItemClickListener((menuItem) -> {
//            int itemId = menuItem.getItemId();
//            if (itemId == R.id.menu_settings) {
//                DialogFragment dialogFragment = new ThemeDialogFragment();
//                dialogFragment.show(getParentFragmentManager(), "Theme Dialog");
//            } else if (itemId == R.id.menu_share) {
//                Intent sendIntent = new Intent();
//                sendIntent.setAction(Intent.ACTION_SEND);
//                sendIntent.putExtra(Intent.EXTRA_TEXT, "Download Rapture Ready at: https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
//                sendIntent.setType("text/plain");
//                startActivity(sendIntent);
//            } else if (itemId == R.id.menu_exit) {
//                requireActivity().finish();
//            } else if (itemId == R.id.menu_about) {
//                startActivity(new Intent(requireActivity(), About.class));
//            } else if (itemId == R.id.menu_refresh) {
//                Fragment fragment = adapter.getFragAt(pager2.getCurrentItem(), getChildFragmentManager());
//                if (fragment instanceof WebViewContainer)
//                    ((WebViewContainer) fragment).tryReload();
//            } else if (itemId == R.id.menu_rate) {
//                Uri uri = Uri.parse("market://details?id=" + BuildConfig.APPLICATION_ID);
//                Intent rateIntent = new Intent(Intent.ACTION_VIEW, uri);
//                rateIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NEW_DOCUMENT | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
//                try {
//                    startActivity(rateIntent);
//                } catch (ActivityNotFoundException e) {
//                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID)));
//                }
//            }
//            return true;
//        });
    }

    public Fragment getFragAt(int currentItem, FragmentManager manager) {
        return manager.findFragmentByTag("f" + currentItem);
    }
}