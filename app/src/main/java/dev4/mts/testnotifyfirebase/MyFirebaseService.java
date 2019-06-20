package dev4.mts.testnotifyfirebase;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

public class MyFirebaseService extends FirebaseMessagingService {
    int g =0;
    public MyFirebaseService(){
        g=100;

    }
    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.i("MyFirebaseService","token:"+s);

    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if(remoteMessage.getNotification() != null){
            Log.i("MyFirebaseService","g:"+g);
            Log.i("MyFirebaseService","title:"+remoteMessage.getNotification().getTitle());
            Log.i("MyFirebaseService","body:"+remoteMessage.getNotification().getBody());
        }

    }


}
