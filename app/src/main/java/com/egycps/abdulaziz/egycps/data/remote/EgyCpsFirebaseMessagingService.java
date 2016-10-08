package com.egycps.abdulaziz.egycps.data.remote;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.egycps.abdulaziz.egycps.R;
import com.egycps.abdulaziz.egycps.data.local.PreferencesHelper;
import com.egycps.abdulaziz.egycps.ui.home.Home;
import com.egycps.abdulaziz.egycps.utils.GlobalEntities;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by abdulaziz on 10/6/16.
 */
public class EgyCpsFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String message = "";
        String type = "";
        int num_notifications = 0;
        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d("FirebaseMessaging", "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            message = remoteMessage.getData().get(GlobalEntities.NOTIFICATION_TEXT_TAG);
            type = remoteMessage.getData().get(GlobalEntities.NOTIFICATION_TYPE_TAG);
            Log.d("FirebaseMessaging", "Message body: " + message);
            Log.d("FirebaseMessaging", "Message Type: " + type);
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
//            message = remoteMessage.getNotification().getBody();
            Log.d("FirebaseMessaging", "Message Notification Body: " + message);
        }

        if(type.equals(GlobalEntities.NEWS_NOTIFICATION_TYPE_TAG)){
            num_notifications = Integer.parseInt(PreferencesHelper.getFromPrefs(getApplicationContext(), GlobalEntities.NEWS_NOTIFICATION_COUNT_TAG, "0"));
            PreferencesHelper.saveToPrefs(getApplicationContext(), GlobalEntities.NEWS_NOTIFICATION_COUNT_TAG, String.valueOf(num_notifications+1));
        }else if(type.equals(GlobalEntities.OFFERS_NOTIFICATION_TYPE_TAG)){
            num_notifications = Integer.parseInt(PreferencesHelper.getFromPrefs(getApplicationContext(), GlobalEntities.OFFERS_NOTIFICATION_COUNT_TAG, "0"));
            PreferencesHelper.saveToPrefs(getApplicationContext(), GlobalEntities.OFFERS_NOTIFICATION_COUNT_TAG, String.valueOf(num_notifications+1));
        }

        sendBroadcast(new Intent(GlobalEntities.BROADCAST_NOTIFICATION_HANDLER_TAG));
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
        sendNotification(message);

    }

    /**
     * Create and show a simple notification containing the received GCM message.
     *
     * @param message GCM message received.
     */
    private void sendNotification(String message) {
        Intent intent = new Intent(this, Home.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        intent.putExtra(GlobalEntities.NOTIFICATION_TYPE_TAG, notificationType);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle("EgyCps")
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

}
