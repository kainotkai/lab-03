package com.example.listycitylab3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

public class AddCityFragment extends DialogFragment {
    interface AddCityDialogListener {
        void addCity(City city);
        void updateCity(String name, String newName, String newProvince);
    }
    Bundle b;
    public AddCityFragment(Bundle b) {
        this.b = b;
    }

    private AddCityDialogListener listener;

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AddCityDialogListener) {
            listener = (AddCityDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement AddCityDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);
        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);
        Boolean editing = b.size() == 2;
        if(editing) {
            editCityName.setText(b.get("change_city").toString());
            editProvinceName.setText(b.get("change_province").toString());
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder.setView(view).setTitle("Add/Edit a city").setNegativeButton("Cancel", null).setPositiveButton("Add", (dialog, which) -> {
            String cityName = editCityName.getText().toString();
            String provinceName = editProvinceName.getText().toString();
            if(editing) listener.updateCity(b.get("change_city").toString(), cityName, provinceName);
            else listener.addCity(new City(cityName, provinceName));
        }).create();
    }
}
