package com.example.sumedh.manishelectronics;

import android.content.Intent;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class Order extends AppCompatActivity {
    Spinner prod1,prod2,prod3,shop;
    EditText qty1,qty2,qty3;
    Button place,cancelorder;
    Location l;
    FirebaseAuth firebaseAuth;
    DatabaseReference dref,dref1;
    String location,region,shopname,product1,product2,product3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        region = getIntent().getStringExtra("region");
        location = getIntent().getStringExtra("location");

        MyService ms = new MyService(getBaseContext());

        l = ms.getLocation();

        final List<String> shops = new ArrayList<String>();
        shops.add("--Select a Shop--");
        dref = FirebaseDatabase.getInstance().getReference().child("Shops").child(region).child(location);
        dref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                shops.add(dataSnapshot.getValue(String.class));
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                shops.add(dataSnapshot.getValue(String.class));
            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                shops.remove(dataSnapshot.getValue(String.class));
            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        shop = findViewById(R.id.shopspinner);
        ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, shops);
        areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        shop.setAdapter(areasAdapter);
        shop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                shopname = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final String[] items = new String[]{
                "Samsung 42 inch TV - 40000Rs",
                "Nvidia GTX 2070 - 120000",
                "Intel i9 - 80000"
        };
        final int[] cost = new int[]{40000,120000,80000};
        final int[] code = new int[]{0,0,0};
        //int[] amt = new int[]{0,0,0};
        final int total;

        ArrayAdapter<String> prodAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, items
        );
        prodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        prod1 = findViewById(R.id.prod1);
        prod2 = findViewById(R.id.prod2);
        prod3 = findViewById(R.id.prod3);

        prod1.setAdapter(prodAdapter);
        prod2.setAdapter(prodAdapter);
        prod3.setAdapter(prodAdapter);

        qty1 = findViewById(R.id.qty1);
        qty2 = findViewById(R.id.qty2);
        qty3 = findViewById(R.id.qty3);
        qty1.setText("0");
        qty2.setText("0");
        qty3.setText("0");

        prod1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position)
                {
                    case 0: code[0] = 0; break;
                    case 1: code[0] = 1; break;
                    case 2: code[0] = 2; break;
                    default: break;
                }
                product1 = items[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        prod2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                product2 = items[position];
                switch (position)
                {
                    case 0: code[1] = 0; break;
                    case 1: code[1] = 1; break;
                    case 2: code[1] = 2; break;
                    default: break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        prod3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                product3 = items[position];
                switch (position)
                {
                    case 0: code[2] = 0; break;
                    case 1: code[2] = 1; break;
                    case 2: code[2] = 2; break;
                    default: break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        place = findViewById(R.id.place);
        place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(),Integer.toString(cost[code[0]] * Integer.parseInt(qty1.getText().toString()) +
                        cost[code[1]] * Integer.parseInt(qty2.getText().toString()) +
                        cost[code[2]] * Integer.parseInt(qty3.getText().toString())),Toast.LENGTH_SHORT).show();

                Intent i = new Intent(Order.this,checkout.class);
                i.putExtra("shopname",shopname);
                i.putExtra("latitude",String.valueOf(l.getLatitude()));
                i.putExtra("longitude",String.valueOf(l.getLongitude()));
                i.putExtra("region",region);
                i.putExtra("location",location);
                i.putExtra("product1",product1);
                i.putExtra("product2",product2);
                i.putExtra("product3",product3);
                i.putExtra("qty1",qty1.getText().toString());
                i.putExtra("qty2",qty2.getText().toString());
                i.putExtra("qty3",qty3.getText().toString());
                i.putExtra("total",cost[code[0]] * Integer.parseInt(qty1.getText().toString()) +
                        cost[code[1]] * Integer.parseInt(qty2.getText().toString()) +
                        cost[code[2]] * Integer.parseInt(qty3.getText().toString()));
                startActivity(i);


            }
        });

        cancelorder = findViewById(R.id.cancelorder);
        cancelorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



















    }
}
