package com.home.atm6;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class HelloService extends IntentService {  // 原本為Service
    private static final String TAG = HelloService.class.getSimpleName ();
    public static final String Action_HELLO_DONE="action_hello_done";
    /**
     *
     * @deprecated
     */
    public HelloService() {
        super ("HelloService");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d (TAG, "onBind");
        return null;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d (TAG, "onHandleIntent: "+intent.getStringExtra ("NAME"));
//        可執行耗時工作
        Intent done=new Intent (  );
        done.setAction (Action_HELLO_DONE);
        sendBroadcast (done);
    }

    @Override
    public void onCreate() {
        super.onCreate ( );
        Log.d (TAG, "onCreate");
    }

    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
////        on UI Thread:
//        Log.d (TAG, "onStartCommand");
//        return START_STICKY;
//    }

    public void onDestroy() {
        super.onDestroy ( );
        Log.d (TAG, "onDestroy");
    }
}
