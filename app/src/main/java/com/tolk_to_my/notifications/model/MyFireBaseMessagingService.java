package com.tolk_to_my.notifications.model;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.tolk_to_my.R;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicInteger;

public class MyFireBaseMessagingService extends FirebaseMessagingService {

    private final static AtomicInteger c = new AtomicInteger(0);

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        String title = remoteMessage.getData().get("title");
        String message = remoteMessage.getData().get("body");
        Log.e("response", "title = " + title);
        Log.e("response", "message = " + message);
        sendNotification(title, message);
    }

    @Override
    public void onNewToken(@NotNull String token) {
        super.onNewToken(token);
        Log.e("response", "onNewToken = " + token);
    }

    private void sendNotification(String messageBody, String title) {
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        CharSequence name = getString(R.string.app_name);// The user-visible name of the channel.
        int importance = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            importance = NotificationManager.IMPORTANCE_HIGH;
        }
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, "notify_001")
                        .setSmallIcon(R.drawable.logo)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri);
        notificationBuilder.setContentTitle(getString(R.string.app_name));
        notificationBuilder.setContentText(messageBody);
        notificationBuilder.setContentTitle(title);
        notificationBuilder.setStyle(new NotificationCompat.BigTextStyle()
                .bigText(messageBody));
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel("notify_001", name, importance);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(mChannel);
        }
        assert notificationManager != null;
        notificationManager.notify(c.incrementAndGet(), notificationBuilder.build());
    }

}
