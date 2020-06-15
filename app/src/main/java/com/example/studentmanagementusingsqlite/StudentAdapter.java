package com.example.studentmanagementusingsqlite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class StudentAdapter extends ArrayAdapter<Student> {
    Context context;
    int layout;
    ArrayList<Student> arrayList;

    public StudentAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<Student> objects) {
        super(context, resource, objects);
        this.context = context;
        this.layout = resource;
        this.arrayList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(layout, null);

        ImageView imageView = convertView.findViewById(R.id.image);
        TextView textName = convertView.findViewById(R.id.name);
        TextView textId = convertView.findViewById(R.id.id);

        textName.setText(arrayList.get(position).getName());
        textId.setText(arrayList.get(position).getId());

        return convertView;
    }
}
