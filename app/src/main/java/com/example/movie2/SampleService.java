package com.example.movie2;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class SampleService extends Service {
    private static final String TAG = "SampleService";

    private IBinder binder = new LocalBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }


    public class LocalBinder extends Binder {
        SampleService getBinder() {
            return SampleService.this;
        }

    }
}
