package com.example.dialogsandbroadcasts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements MyListener {
    private MyReceiver receiver;
    private AirplaneModeReceiver airplaneModeReceiver;
    private CameraReceiver cameraReceiver;
    private LowBatteryReceiver lowBatteryReceiver;
    private RecyclerView recyclerView;
    private ElementAdapter adapter;
    private List<Element> list = new ArrayList<>();

    @Override
    protected void onStart() {
        super.onStart();
        receiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        intentFilter.addAction("android.intent.action.AIRPLANE_MODE");
        intentFilter.addAction("android.intent.action.BATTERY_LOW");
        intentFilter.addAction("android.intent.action.CAMERA_BUTTON");
        registerReceiver(receiver, intentFilter);
        registerReceiver(airplaneModeReceiver, intentFilter);
        registerReceiver(cameraReceiver, intentFilter);
        registerReceiver(lowBatteryReceiver, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (receiver != null)
            unregisterReceiver(receiver);
        if (airplaneModeReceiver != null)
            unregisterReceiver(airplaneModeReceiver);
        if (cameraReceiver != null)
            unregisterReceiver(cameraReceiver);
        if (lowBatteryReceiver != null)
            unregisterReceiver(lowBatteryReceiver);
    }

    @Override
    public void onAirplaneChange(boolean isOn) {
        String message = "Режим полёта ";
        message += isOn ? "включён!" : "отключён!";
        MyDialog.newInstance("Режим полёта", message).show(getSupportFragmentManager(), "flyMode");
    }

    @Override
    public void onLowBattery() {
        MyDialog.newInstance("Батарея разряжена!", "Внимание! Низкий уровень заряда аккумулятора!").show(getSupportFragmentManager(), "lowPower");
    }

    @Override
    public void onCameraEvent() {
        MyDialog.newInstance("Камера", "Камера активирована!").show(getSupportFragmentManager(), "camera");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(view -> {
            MyDialog dialog = MyDialog.newInstance("О программе", "Программка реагирует (выводит диалоговые окна или уведомления) при получении SMS сообщений, смены режима полёта, низком уровне заряда аккумулятора, нажатии кнопки камеры.");
            dialog.show(getSupportFragmentManager(), "dlg");
        });
        airplaneModeReceiver = new AirplaneModeReceiver();
        cameraReceiver = new CameraReceiver();
        lowBatteryReceiver = new LowBatteryReceiver();

        requestPermissions();

        Random random = new Random();
        for (int j = 0; j < 24; j++) {
            list.add(new Element(random.nextInt(200)-100, Color.argb(200, random.nextInt(200)+56, random.nextInt(200)+56, random.nextInt(200)+56)));
        }
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        adapter = new ElementAdapter(getApplicationContext(), list, element -> {
            int index = recyclerView.getChildLayoutPosition(element);
            MyDialog dialog = MyDialog.newInstance("Выбор","Выбрано значение: "+list.get(index).getNumber());
            dialog.show(getSupportFragmentManager(), "bubble");
        });
        recyclerView.setAdapter(adapter);
    }

    private void requestPermissions() {
        if (checkSelfPermission(Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.RECEIVE_SMS}, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            String msg = "Permission to receive SMS";
            msg += (grantResults[0] == PackageManager.PERMISSION_GRANTED) ? " granted" : " not granted";
            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDialogResult(int result) {
        switch (result) {
            case 1:
                Toast.makeText(this, "Clicked OK!", Toast.LENGTH_SHORT).show();
                break;
            case -1:
                Toast.makeText(this, "Clicked NO!", Toast.LENGTH_SHORT).show();
                break;
            case 0:
                Toast.makeText(this, "Окно скрыто!", Toast.LENGTH_SHORT).show();
                break;

        }
    }
}