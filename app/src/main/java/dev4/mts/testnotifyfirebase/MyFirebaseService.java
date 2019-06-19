package dev4.mts.testnotifyfirebase;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseService extends FirebaseMessagingService {

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.i("MyFirebaseService","token"+s);

    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if(remoteMessage.getNotification() != null){
            Log.i("MyFirebaseService","title:"+remoteMessage.getNotification().getTitle());
            Log.i("MyFirebaseService","body:"+remoteMessage.getNotification().getBody());
        }

    }
}
