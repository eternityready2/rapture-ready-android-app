package com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.fragments;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.R;
import com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.adapter.FragmentPagerAdapter;
import com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.ui.LoadingDialog;
import com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.view.ExtendedWebView;

/**
 * This class inherits from fragment, it's the class that keeps the web view and operate on it
 */
public class WebViewContainer extends Fragment {

    private static final String URL_KEY = "URL_KEY";
    private String url;

    private ExtendedWebView webView;
    private ProgressBar progressBar;
    private boolean isLoaded;
    private LoadingDialog loadingDialog;
    private View v;

    //default constructor
    public WebViewContainer() {
    }

    //creates an instance of the fragment and passes an argument to the fragment
    public static WebViewContainer newInstance(String url) {
        Bundle args = new Bundle();
        args.putString(URL_KEY, url);
        WebViewContainer fragment = new WebViewContainer();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        assert args != null;
        url = args.getString(URL_KEY);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.adapter_layout, container, false);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadingDialog = new LoadingDialog(requireContext());

        webView = view.findViewById(R.id.webview);
        progressBar = view.findViewById(R.id.progress);
        v = view;
        progressBar.setVisibility(View.GONE);

        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);


        //webView.getSettings().setMediaPlaybackRequiresUserGesture(false);

        //webView.getSettings().setAllowUniversalAccessFromFileURLs(true);

        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.setSaveEnabled(true);

        disableCache(webView);

        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.setFocusableInTouchMode(true);
        //webView.clearCache(true);

        webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }
        });
        webView.setWebViewClient(new WebViewClient() {

            boolean aBoolean = false;

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
                if (!aBoolean){
                    loadingDialog.show();
                    progressBar.setVisibility(View.VISIBLE);
                    aBoolean = true;
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

               // WebViewContainer.this.url = url;
                progressBar.setVisibility(View.GONE);
                aBoolean = true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                loadingDialog.cancel();
            }

        });

        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                // Update progress bar
                progressBar.setProgress(newProgress);
                if (newProgress == 100) {
                    // Hide progress bar when page is fully loaded
                    progressBar.setVisibility(ProgressBar.GONE);
                } else {
                    // Show progress bar in progress
                    progressBar.setVisibility(ProgressBar.VISIBLE);
                }
            }
        });
    }


    /*
    overrides the onResume method, and calls the web view onResume to stop every html5, javascript processing
     */
    @Override
    public void onResume() {
        super.onResume();
//        if (isLoaded)
        webView.onResume();
        disableCache(webView);
//        if (!isLoaded) {
        webView.loadUrl(url);
        isLoaded = true;
//        }
    }

    //opposite of onResume
    @Override
    public void onPause() {
        super.onPause();
        if (isLoaded)
            webView.onPause();
        disableCache(webView);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        webView.saveState(outState);
        outState.putBoolean(URL_KEY, isLoaded);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            webView.restoreState(savedInstanceState);
            isLoaded = savedInstanceState.getBoolean(URL_KEY, false);
        }
    }

    private void disableCache(WebView wv) {
        // Clear cache
        wv.clearCache(true);
        wv.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
    }

    public void tryReload() {
        webView.reload();
    }

    public void Return(String _url) {
        webView.loadUrl(_url);
    }


}
