package com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.fragments;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.preference.PreferenceManager;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.R;
import com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.adapter.FragmentPagerAdapter;
import com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.data.ButtonItem;
import com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.data.Constants;
import com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.data.ItemsData;
import com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.data.ThemeModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class WebViewFragment extends Fragment {

    private final String[] webUrls = new String[5];
    private Toolbar toolbar;
    private ViewPager2 pager2;
    private FragmentPagerAdapter adapter;
    private SharedPreferences.OnSharedPreferenceChangeListener listener;
    private BottomNavigationView navigationView;

    private final Map<Integer, Runnable> runnableMap = new HashMap<>();

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

        /*pager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
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
        });*/

        navigationView.setOnItemSelectedListener(item -> {
            Runnable runnable = runnableMap.get(item.getItemId());
            if (runnable != null) {
                runnable.run();
                return true;
            } else {
                Toast.makeText(requireContext(), "Item not found", Toast.LENGTH_SHORT).show();
                return false;
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

        ItemsData.getItems().observe(getViewLifecycleOwner(), items -> {
            for (Pair<String, List<ButtonItem>> item: items) {
                if (item.first.equalsIgnoreCase("BottomNav")) {
                    List<ButtonItem> buttons = item.second;
                    navigationView.getMenu().clear();

                    for (int i = 0; i < buttons.size(); i++) {
                        ButtonItem button = buttons.get(i);
                        MenuItem menuItem = navigationView.getMenu().add(
                                Menu.NONE, i, Menu.NONE, button.text);
                        Glide.with(this)
                                .asDrawable()
                                .load(Constants.BASE_URL + button.icon)
                                .override(48, 48)
                                .into(new CustomTarget<Drawable>() {
                                    @Override
                                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                        menuItem.setIcon(resource);
                                    }

                                    @Override
                                    public void onLoadCleared(@Nullable Drawable placeholder) {
                                        // Handle cleanup if needed
                                    }
                                });

                        runnableMap.put(menuItem.getItemId(), () -> {
                            if (Objects.requireNonNull(menuItem.getTitle()).toString()
                                    .equalsIgnoreCase("Home")) {
                                pager2.setCurrentItem(0);
                                Fragment fragment = getFragAt(0, getChildFragmentManager());
                                if (fragment instanceof HomeFragment) ((HomeFragment) fragment).reload();
                                toolbar.setTitle("Home");
                            } else {
                                pager2.setCurrentItem(menuItem.getItemId());
                                Fragment fragment = adapter.getFragAt(pager2.getCurrentItem(), getChildFragmentManager());
                                if (fragment instanceof WebViewContainer)
                                    ((WebViewContainer) fragment).Return(button.link);
                                toolbar.setTitle(button.text);
                            }
                        } );
                    }

                    MenuItem menuItem = navigationView.getMenu().add(
                            Menu.NONE, View.generateViewId(), Menu.NONE, "More");
                    menuItem.setIcon(R.drawable.more);
                    runnableMap.put(menuItem.getItemId(), () -> {
                        pager2.setCurrentItem(4);
                        Fragment fragment = getFragAt(pager2.getCurrentItem(), getChildFragmentManager());
                        if (fragment instanceof MoreFragment) ((MoreFragment) fragment).reload();
                        toolbar.setTitle("More");
                    });

                    toolbar.setTitle("Home");
                }
            }
        });
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

    @Override
    public void onDestroyView() {
        runnableMap.clear();
        super.onDestroyView();
    }

    public Fragment getFragAt(int currentItem, FragmentManager manager) {
        return manager.findFragmentByTag("f" + currentItem);
    }
}