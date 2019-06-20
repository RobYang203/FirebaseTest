package dev4.mts.testnotifyfirebase;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String g = getAccount();

        FirebaseApp.initializeApp(MainActivity.this);
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
                    @Override
                    public void onSuccess(InstanceIdResult instanceIdResult) {

                        final String token = instanceIdResult.getToken();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                addToGroup(token);
                            }
                        }).start();

                        Log.i("MainActivity","token:" + token);
                    }
                });
    }

    private String getAccount(){
        Account[] accounts = AccountManager.get(MainActivity.this)
                .getAccounts();
        if(accounts.length <=0)
            return null;

        return accounts[0].name;

    }

    private String addToGroup(String registrationId)
    {
        String ret ="";

        String senderId = "197237028549";
        String notification_key_name="appAllUser";
        String notification_key = getResources().getString(R.string.Firebase_Notification_Key);
        String API_Key =getResources().getString(R.string.Firebase_API_Key);

        try{
            URL url = new URL("https://fcm.googleapis.com/fcm/notification");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoOutput(true);

            //http request header
            con.setRequestProperty("project_id",senderId);
            con.setRequestProperty("Content-Type","application/json");
            con.setRequestProperty("Authorization","key="+API_Key);
            con.setRequestMethod("POST");
            con.connect();

            //http request

            JSONObject data = new JSONObject();
            data.put("operation","add");
            data.put("notification_key_name",notification_key_name);
            data.put("notification_key",notification_key);
            data.put("registration_ids",new JSONArray(Arrays.asList(registrationId)));

            OutputStream os = con.getOutputStream();
            os.write(data.toString().getBytes("UTF-8"));
            os.close();

            InputStream in = con.getInputStream();
            String response = new Scanner(in,"UTF-8").useDelimiter("\\A").next();
            in.close();
            ret = response;
        }catch (Exception e){
            Log.e("Err",e.toString());
        }


        return ret;
    }

}
