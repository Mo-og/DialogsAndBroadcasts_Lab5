package com.example.dialogsandbroadcasts;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class MyDialog extends DialogFragment {
    public MyDialog() {
    }

    private MyListener mListener;

    private String mTitle;
    private String mMessage;

    private static final String ARG_TITLE = "title";
    private static final String ARG_MESSAGE = "message";

    public static MyDialog newInstance(String title, String message) {

        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_MESSAGE, message);

        MyDialog fragment = new MyDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mTitle = getArguments().getString(ARG_TITLE);
            mMessage = getArguments().getString(ARG_MESSAGE);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            mListener = (MyListener) context;
        } catch (ClassCastException ex){
            throw new ClassCastException(context.toString() + "must implement MyDialog.MyListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener=null;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_my,null,false);
        Button button = view.findViewById(R.id.button2);
        button.setOnClickListener(view1->{
            Toast.makeText(getContext(),"HELLO!",Toast.LENGTH_SHORT).show();
        });
        return new AlertDialog.Builder(getContext())
                /*.setView(view)
                .create();*/
                .setIcon(android.R.drawable.stat_sys_warning)
                .setTitle(mTitle)
                .setMessage(mMessage)
                /*.setPositiveButton("OK", (dialogInterface, i) -> {
                    mListener.onDialogResult(1);
                })*/
                .setNeutralButton("Закрыть", (dialogInterface, i) -> {
                    mListener.onDialogResult(0);
                })
                /*.setNegativeButton("NO", (dialogInterface, i) -> {
                    mListener.onDialogResult(-1);

                })*/
                .create();
    }
}
