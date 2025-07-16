package com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.activity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Pair;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.core.app.NotificationCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.suddenh4x.ratingdialog.AppRating;
import com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.R;
import com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.data.ButtonItem;
import com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.data.ItemsData;
import com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.fragments.WebViewFragment;
import com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.network.NetworkAndDataConversionClass;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends BaseActivity {

    private ExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SystemClock.sleep(500); 

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //  Edge-to-edge setup
        setupEdgeToEdge();

        //  Start background thread
        executorService = Executors.newSingleThreadExecutor();

        //  Initial fragment load
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, WebViewFragment.newInstance(), "FRAGMENT")
                    .commitNow();
        }

        //  Show rating prompt if eligible
        new AppRating.Builder(this)
                .setMinimumDays(3)
                .setMinimumDaysToShowAgain(3)
                .setMinimumLaunchTimes(6)
                .showIfMeetsConditions();

        //  Start data loading
        View loadingOverlay = findViewById(R.id.loading_dialog);
        executorService.execute(() -> {
            List<Pair<String, List<ButtonItem>>> data =
                    NetworkAndDataConversionClass.fetchDataFromNetwork(this);

            runOnUiThread(() -> {
                ItemsData.setItems(data);
                if (loadingOverlay != null) loadingOverlay.setVisibility(View.GONE);
            });
        });
    }

    private void setupEdgeToEdge() {
        EdgeToEdge.enable(this);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().setNavigationBarColor(Color.TRANSPARENT);

        View rootView = findViewById(R.id.fragment_container);
        ViewCompat.setOnApplyWindowInsetsListener(rootView, (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, bars.top, 0, 0);
            return insets;
        });

        WindowInsetsControllerCompat insetsController =
                WindowCompat.getInsetsController(getWindow(), rootView);
        insetsController.setAppearanceLightStatusBars(true); // Dark icons on light background
    }

    @Override
    protected void onDestroy() {
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdownNow();
        }
        super.onDestroy();
    }

    //  Optional: Call this for debug/demo push
    private void sendNotification() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                0,
                intent,
                PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE
        );

        String channelId = "Default_Channel";
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_stat_product_logo_trans)
                .setContentTitle("Hi")
                .setContentText("remoteMessage.getNotification().getBody()")
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "General Notifications",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0, notificationBuilder.build());
    }
}
