package com.example.dialogsandbroadcasts;

public interface MyListener {
    void onDialogResult(int result);
    void onAirplaneChange(boolean isOn);
    void onLowBattery();
    void onCameraEvent();
}
