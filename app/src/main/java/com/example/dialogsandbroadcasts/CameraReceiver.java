package com.example.dialogsandbroadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class CameraReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_CAMERA_BUTTON)) {
            if (context instanceof MyListener) {
                ((MyListener) context).onCameraEvent();
            }
        }
    }
}
