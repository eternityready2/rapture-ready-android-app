package com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.services;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.R;
import com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.activity.MainActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import androidx.core.app.NotificationCompat;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        // Check if the message contains data
        if (!remoteMessage.getData().isEmpty()) {
            // Handle the data message here
        }

        // Check if the message contains a notification
        if (remoteMessage.getNotification() != null) {
            // Show the notification
            sendNotification(remoteMessage);
        }
    }

    private void sendNotification(RemoteMessage remoteMessage) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);

        // Convert drawable resource to Bitmap
        Bitmap largeIconBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.notification_logo);


        String channelId = "Default_Channel";
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.notification_logo) // Add your app's notification icon here
                .setLargeIcon(largeIconBitmap) // Add your app's notification icon here
                .setContentTitle(remoteMessage.getNotification().getTitle())
                .setContentText(remoteMessage.getNotification().getBody())
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
}
