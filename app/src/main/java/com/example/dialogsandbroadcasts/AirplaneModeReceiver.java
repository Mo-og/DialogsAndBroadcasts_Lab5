package com.example.dialogsandbroadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AirplaneModeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_AIRPLANE_MODE_CHANGED)) {
            if (context instanceof MyListener) {
                ((MyListener) context).onAirplaneChange(intent.getBooleanExtra("state", false));
            }
        }
    }
}
