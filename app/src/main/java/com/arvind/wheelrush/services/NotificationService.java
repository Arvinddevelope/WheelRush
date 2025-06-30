package com.arvind.wheelrush.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.arvind.wheelrush.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Handles incoming Firebase Cloud Messaging notifications.
 */
public class NotificationService extends FirebaseMessagingService {

    private static final String CHANNEL_ID = "spin_earn_channel";
    private static final String CHANNEL_NAME = "Spin & Earn Notifications";

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        // You can upload this token to Firebase Database if needed
        // Example: FirebaseUtil.getUserRef(FirebaseAuth.getInstance().getUid()).child("fcmToken").setValue(token);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        // Extract notification data
        String title = "Spin & Earn";
        String message = "You've received a reward!";

        if (remoteMessage.getNotification() != null) {
            title = remoteMessage.getNotification().getTitle() != null ? remoteMessage.getNotification().getTitle() : title;
            message = remoteMessage.getNotification().getBody() != null ? remoteMessage.getNotification().getBody() : message;
        }

        showNotification(title, message);
    }

    /**
     * Displays the notification using NotificationManager.
     */
    private void showNotification(String title, String message) {
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Create channel for Android O+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification) // Replace with your notification icon
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        // Show the notification
        manager.notify((int) System.currentTimeMillis(), builder.build());
    }
}
