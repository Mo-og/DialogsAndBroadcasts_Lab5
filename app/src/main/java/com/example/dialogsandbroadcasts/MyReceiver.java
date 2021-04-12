package com.example.dialogsandbroadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                String msg = "From " + smsMessage.getOriginatingAddress() + " : " + smsMessage.getMessageBody();
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
