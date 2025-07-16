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
 * Optimized WebView Fragment with better performance and memory management
 */
public class WebViewContainer extends Fragment {

    private static final String URL_KEY = "URL_KEY";
    private static final String IS_LOADED_KEY = "IS_LOADED_KEY";

    private String url;
    private ExtendedWebView webView;
    private ProgressBar progressBar;
    private boolean isLoaded = false;
    private LoadingDialog loadingDialog;

    public WebViewContainer() {
        // Default constructor
    }

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
        if (args != null) {
            url = args.getString(URL_KEY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.adapter_layout, container, false);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeViews(view);
        setupWebView();

        // Only load URL if not already loaded or if this is first time
        if (!isLoaded && url != null) {
            loadUrl(url);
        }
    }

    private void initializeViews(View view) {
        webView = view.findViewById(R.id.webview);
        progressBar = view.findViewById(R.id.progress);

        // Initialize loading dialog only when needed
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(requireContext());
        }
    }

    private void setupWebView() {
        // WebView settings - set once
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setDatabaseEnabled(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT); // Use default caching
        settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

        webView.setFocusableInTouchMode(true);
        webView.setSaveEnabled(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                showLoading();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                hideLoading();
                isLoaded = true;
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                hideLoading();
                // Handle error appropriately
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                updateProgress(newProgress);
            }
        });
    }

    private void loadUrl(String url) {
        if (webView != null && url != null) {
            webView.loadUrl(url);
        }
    }

    private void showLoading() {
        if (loadingDialog != null && !loadingDialog.isShowing()) {
            loadingDialog.show();
        }
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(0);
        }
    }

    private void hideLoading() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void updateProgress(int progress) {
        if (progressBar != null) {
            progressBar.setProgress(progress);
            if (progress >= 100) {
                progressBar.setVisibility(View.GONE);
            } else {
                progressBar.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (webView != null) {
            webView.onResume();
            // Don't reload URL automatically - let user decide
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (webView != null) {
            webView.onPause();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (webView != null) {
            webView.saveState(outState);
        }
        outState.putBoolean(IS_LOADED_KEY, isLoaded);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null && webView != null) {
            webView.restoreState(savedInstanceState);
            isLoaded = savedInstanceState.getBoolean(IS_LOADED_KEY, false);
        }
    }

    // Public methods for external control
    public void tryReload() {
        if (webView != null) {
            webView.reload();
        }
    }

    public void loadNewUrl(String newUrl) {
        if (webView != null && newUrl != null) {
            this.url = newUrl;
            webView.loadUrl(newUrl);
        }
    }

    public void clearCache() {
        if (webView != null) {
            webView.clearCache(true);
        }
    }

    @Override
    public void onDestroyView() {
        // Clean up WebView first
        if (webView != null) {
            webView.stopLoading();
            webView.destroy();
            webView = null;
        }

        // Clean up loading dialog
        if (loadingDialog != null) {
            if (loadingDialog.isShowing()) {
                loadingDialog.dismiss();
            }
            loadingDialog = null;
        }

        progressBar = null;
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Additional cleanup if needed
    }
}