package com.egycps.abdulaziz.egycps.utils;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

//import com.google.android.gms.gcm.GoogleCloudMessaging;
//import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

public class RegistrationIntentService{}
//public class RegistrationIntentService extends IntentService {
//
//    /**
//     * Creates an IntentService.  Invoked by your subclass's constructor.
//     *
//     * @param name Used to name the worker thread, important only for debugging.
//     */
//    public RegistrationIntentService(String name) {
//        super(name);
//        Log.i(GlobalEntities.REGISTRATION_SERVICE_TAG, "onCreate");
//
//    }
//
//    public RegistrationIntentService() {
//        super(GlobalEntities.REGISTRATION_SERVICE_TAG);
//        Log.i(GlobalEntities.REGISTRATION_SERVICE_TAG, "onCreate");
//
//    }
//
//    @Override
//    public IBinder onBind(Intent intent) {
//        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
//    }
//
//    @Override
//    protected void onHandleIntent(Intent intent) {
//        Log.i(GlobalEntities.REGISTRATION_SERVICE_TAG, "Handle Intent");
//
//        InstanceID instanceID = InstanceID.getInstance(this);
//        String token = null;
//        try {
//            token = instanceID.getToken(getString(R.string.gcm_defaultSenderId),
//                    GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
//
//            Intent i = new Intent(GlobalEntities.BROADCAST_CHANGE_DEVICE_TOKEN_EVENT);
//            i.putExtra(GlobalEntities.PLAYER_ID_TAG, PrefUtils.getFromPrefs(getApplicationContext(), GlobalEntities.PLAYER_ID_TAG, ""));
//            i.putExtra(GlobalEntities.NOTIFICATION_TOKEN_TAG, token);
//            sendBroadcast(i);
//
//            Log.i(GlobalEntities.REGISTRATION_SERVICE_TAG, "Registration Token: "+token);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
//}
