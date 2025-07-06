package com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.activity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Pair;
import android.view.View;

import androidx.core.app.NotificationCompat;

import com.suddenh4x.ratingdialog.AppRating;
import com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.R;
import com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.data.ButtonItem;
import com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.data.ItemsData;
import com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.fragments.WebViewFragment;
import com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.network.NetworkAndDataConversionClass;
import com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.utility.AppExecutors;

import java.util.List;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SystemClock.sleep(500);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, WebViewFragment.newInstance(), "FRAGMENT").commitNow();
            //getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new HomeFragment(), "FRAGMENT").commitNow();
        }

        // sendNotification();

        AppRating.Builder builder = new AppRating.Builder(this).setMinimumDays(3).setMinimumDaysToShowAgain(3).setMinimumLaunchTimes(6);
        builder.showIfMeetsConditions();

        AppExecutors.getSingleThreadExecutor().execute(() -> {
            List<Pair<String, List<ButtonItem>>> data =
                    NetworkAndDataConversionClass.fetchDataFromNetwork(this);
            if (data != null) {
                runOnUiThread(() -> {
                    ItemsData.setItems(data);
                    findViewById(R.id.loading_dialog).setVisibility(View.GONE);
                });
            }
        });

    }

    private void sendNotification() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);

        String channelId = "Default_Channel";
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_stat_product_logo_trans)// Add your app's notification icon here
                .setContentTitle("Hi")
                .setContentText("remoteMessage.getNotification().getBody()")
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // For Android Oreo and above, you need to create a notification channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Channel human readable title", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0, notificationBuilder.build());
    }

    @Override
    protected void onDestroy() {
        AppExecutors.shutdown();
        super.onDestroy();
    }

    //    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if(keyCode == KeyEvent.KEYCODE_BACK){
//            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
//            builder.setMessage("Are you sure you want to exit?").setTitle("Exit")
//                    .setPositiveButton("Yes", (dialog, which) -> finish())
//                    .setNegativeButton("cancel", (dialog, which) -> dialog.dismiss()).setCancelable(false);
//            builder.create().show();
//            return true;
//        }
//        return false;
//    }
}