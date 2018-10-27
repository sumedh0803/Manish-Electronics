package com.example.sumedh.manishelectronics;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class location extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    DatabaseReference dref,dref1;
    Spinner spin1,spin2,spin3;
    ProgressDialog pd,pd1;
    String regionspin = "default", locationspin;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);


        pd = new ProgressDialog(this);
        pd.setMessage("Fetching regions");
        pd.show();
        final List<String> areas = new ArrayList<String>();
        areas.add("--Select a Region--");//done
        final List<String> location = new ArrayList<String>(); //done


        dref = FirebaseDatabase.getInstance().getReference().child("Region");

            dref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                areas.add(dataSnapshot.getValue(String.class));
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                areas.add(dataSnapshot.getValue(String.class));
            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                areas.remove(dataSnapshot.getValue(String.class));
            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        spin1 = findViewById(R.id.spin1);
        ArrayAdapter<String> areasAdapter = new ArrayAdapter<String>(location.this, android.R.layout.simple_spinner_item, areas);
        areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin1.setAdapter(areasAdapter);
        pd.cancel();
        spin1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                regionspin = parent.getItemAtPosition(position).toString();

                location.clear();
                location.add("--Select a Location--");//done
                dref1 = FirebaseDatabase.getInstance().getReference().child("Location").child(regionspin);
                dref1.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        location.add(dataSnapshot.getValue(String.class));

                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        location.add(dataSnapshot.getValue(String.class));
                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                        location.remove(dataSnapshot.getValue(String.class));


                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                spin2 = findViewById(R.id.spin2);
                ArrayAdapter<String> areasAdapter2 = new ArrayAdapter<String>(location.this, android.R.layout.simple_spinner_item, location);
                areasAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spin2.setAdapter(areasAdapter2);
                spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        locationspin = parent.getItemAtPosition(position).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(location.this, Order.class);
                intent.putExtra("region",regionspin);
                intent.putExtra("location",locationspin);
                startActivity(intent);
                finish();
            }
        });








    }
}
