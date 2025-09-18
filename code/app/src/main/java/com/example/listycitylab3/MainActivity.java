package com.example.listycitylab3;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements AddCityFragment.AddCityDialogListener {
    private ArrayList<City> dataList;
    private ListView cityList;
    private ArrayAdapter<City> cityAdapter;

    @Override
    public void addCity(City city) {
        cityAdapter.add(city);
        cityAdapter.notifyDataSetChanged();
    }
    @Override
    public void updateCity(String name, String newName, String newProvince) {
        for(int i = 0; i < dataList.size(); i++) {
            if(dataList.get(i).getName().equals(name)) {
                City updated = new City(newName, newProvince);
                dataList.set(i, updated);
            }
        }
        cityAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] cities = {"Edmonton", "Vancouver", "Calgary",};

        String[] provinces = {"AB", "BC", "AB"};

        dataList = new ArrayList<City>();
        for (int i = 0; i < cities.length; i++) {
            dataList.add(new City(cities[i], provinces[i]));
        }
        cityList = findViewById(R.id.city_list);
        cityAdapter = new CityArrayAdapter(this, dataList);
        cityList.setAdapter(cityAdapter);
        FloatingActionButton fab = findViewById(R.id.button_add_city);
        fab.setOnClickListener(v -> {
            new AddCityFragment(new Bundle()).show(getSupportFragmentManager(), "Add/Edit City");
        });

        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?>adapter,View v, int position, long id) {
                cityAdapter.getItem(position);
                Bundle b = new Bundle();
                b.putCharSequence("change_city", cityAdapter.getItem(position).getName());
                b.putCharSequence("change_province", cityAdapter.getItem(position).getProvince());
                new AddCityFragment(b).show(getSupportFragmentManager(), "Add/Edit City");
            }

        });


    }
}