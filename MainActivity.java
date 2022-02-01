package com.example.sqlitedatabasetutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;


import java.util.List;

public class MainActivity extends AppCompatActivity {

    //reference to buttons and other controls on the layouts
    Button btn_add, btn_ViewAll;
    EditText et_name, et_age;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch sw_isActive;
    ListView listView_customerList;
    ArrayAdapter<CustomerModel> customerArrayAdapter;
    DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_add = findViewById(R.id.btn_add);
        btn_ViewAll = findViewById(R.id.btn_ViewAll);
        sw_isActive = findViewById(R.id.sw_Active);
        listView_customerList = findViewById(R.id.lv_customerList);

        dataBaseHelper = new DataBaseHelper(MainActivity.this);

        ShowCustomerOnListView(dataBaseHelper);

        //button Listeners for the add and ViewAll buttons

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CustomerModel customerModel;

                try{
                    customerModel = new CustomerModel(
                            1,
                            et_name.getText().toString(),
                            Integer.parseInt(et_age.getText().toString()),
                            sw_isActive.isChecked());

                    Toast.makeText(MainActivity.this, customerModel.toString(),
                            Toast.LENGTH_SHORT).show();
                }
                catch(Exception e){
                    Toast.makeText(MainActivity.this, "Error creating Customer",
                            Toast.LENGTH_SHORT).show();

                    customerModel = new CustomerModel(-1, "error", 0, false);
                }

                DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);

                boolean success = dataBaseHelper.addOne(customerModel);

                Toast.makeText(MainActivity.this, "Success: " + success, Toast.LENGTH_SHORT).show();

                ShowCustomerOnListView(dataBaseHelper);
            }
        });

        btn_ViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ShowCustomerOnListView(dataBaseHelper);

                DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);
                List<CustomerModel> everyone = dataBaseHelper.getEveryone();

                // Toast.makeText(MainActivity.this, everyone.toString(), Toast.LENGTH_SHORT).show();
            }

        });

        listView_customerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CustomerModel clickedCustomer = (CustomerModel) parent.getItemAtPosition(position);
                dataBaseHelper.deleteOne(clickedCustomer);
                ShowCustomerOnListView(dataBaseHelper);
                Toast.makeText(MainActivity.this, "Deleted" + clickedCustomer, Toast.LENGTH_SHORT).show();
            }
        });
    }




    private void ShowCustomerOnListView(DataBaseHelper dataBaseHelper2) {
        customerArrayAdapter = new ArrayAdapter<CustomerModel>(MainActivity.this,
                android.R.layout.simple_list_item_1, dataBaseHelper2.getEveryone());
        listView_customerList.setAdapter(customerArrayAdapter);

    }

    }
