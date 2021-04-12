package com.example.dialogsandbroadcasts;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ElementAdapter extends RecyclerView.Adapter<ElementAdapter.ElementWrapper> {

    private final LayoutInflater inflater;
    private List<Element> list;
    private View.OnClickListener listener;


    public ElementAdapter(Context context, List<Element> list, View.OnClickListener listener) {
        this.inflater = LayoutInflater.from(context);
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ElementWrapper onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = inflater.inflate(R.layout.element, viewGroup, false);
        v.setOnClickListener(listener);
        return new ElementWrapper(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ElementWrapper holder, int i) {
        holder.image.setImageTintList(ColorStateList.valueOf(list.get(i).getColor()));
        holder.text.setText(Integer.toString(list.get(i).getNumber()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ElementWrapper extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView text;

        public ElementWrapper(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.background);
            text = itemView.findViewById(R.id.number_value);
        }
    }
}
