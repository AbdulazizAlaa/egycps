package com.egycps.abdulaziz.egycps.utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

//import com.google.android.gms.gcm.GcmListenerService;

public class MyGcmListenerService{}
//public class MyGcmListenerService extends GcmListenerService {
//    public MyGcmListenerService() {
//    }
//
//    @Override
//    public void onMessageReceived(String from, Bundle msg) {
//        super.onMessageReceived(from, msg);
//        Bundle notification = msg.getBundle("notification");
//        String message = "";
//        if(notification!=null){
//            message = notification.getString("body");
//        }
//        Log.i(GlobalEntities.MY_GCM_LISTENER_SERVICE_TAG, "From: "+from);
//        Log.i(GlobalEntities.MY_GCM_LISTENER_SERVICE_TAG, "bundleReceived: "+msg.toString());
//        Log.i(GlobalEntities.MY_GCM_LISTENER_SERVICE_TAG, "MessageReceived: "+message);
//
//        int notif_counter = Integer.parseInt(PrefUtils.getFromPrefs(getApplicationContext(), GlobalEntities.NOTIFICATION_COUNTER_TAG, "0"));
//        PrefUtils.saveToPrefs(getApplicationContext(), GlobalEntities.NOTIFICATION_COUNTER_TAG, String.valueOf(notif_counter+1));
//
//        Intent i = new Intent(GlobalEntities.BROADCAST_NOTIFICATION_HANDLE_EVENT);
//        sendBroadcast(i);
//
//        sendNotification(message, GlobalEntities.NOTIFICATION_TYPE_NEWS_TAG);
//    }
//
//    @Override
//    public void onMessageSent(String s) {
//        super.onMessageSent(s);
//
//        Log.i(GlobalEntities.MY_GCM_LISTENER_SERVICE_TAG, "onMessageSent: "+s);
//    }
//
//
//    /**
//     * Create and show a simple notification containing the received GCM message.
//     *
//     * @param message GCM message received.
//     */
//    private void sendNotification(String message, String notificationType) {
//        Intent intent = new Intent(this, Home.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        intent.putExtra(GlobalEntities.NOTIFICATION_TYPE_TAG, notificationType);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
//                PendingIntent.FLAG_ONE_SHOT);
//
//        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
//                .setSmallIcon(R.drawable.match_football_icon)
//                .setContentTitle("TheWinner")
//                .setContentText(message)
//                .setAutoCancel(true)
//                .setSound(defaultSoundUri)
//                .setContentIntent(pendingIntent);
//
//        NotificationManager notificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
//    }
//}
