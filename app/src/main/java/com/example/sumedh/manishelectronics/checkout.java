package com.example.sumedh.manishelectronics;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class checkout extends AppCompatActivity {

    TextView datetv,totamt,cashmsg;
    RadioGroup method;
    int total,temp;
    Button save,logout;
    ProgressDialog pd;

    FirebaseAuth firebaseAuth;
    DatabaseReference dref;
    String shopname,latitude,longitude,region,location,product1,product2,product3,qty1,qty2,qty3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);



         shopname = getIntent().getStringExtra("shopname");
         latitude = getIntent().getStringExtra("latitude");
         longitude = getIntent().getStringExtra("longitude");
         region = getIntent().getStringExtra("region");
         location = getIntent().getStringExtra("location");
         product1 = getIntent().getStringExtra("product1");
         product2 = getIntent().getStringExtra("product2");
         product3 = getIntent().getStringExtra("product3");
         qty1 = getIntent().getStringExtra("qty1");
         qty2 = getIntent().getStringExtra("qty2");
         qty3 = getIntent().getStringExtra("qty3");
        total = getIntent().getIntExtra("total",0);

        pd = new ProgressDialog(this);

        datetv = findViewById(R.id.datetv);
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);
        datetv.setText("Date: "+formattedDate);

        method = findViewById(R.id.method);
        method.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.cash)
                {
                    temp = total * 90 /100;
                    cashmsg = findViewById(R.id.cashmsg);
                    cashmsg.setVisibility(View.VISIBLE);
                    totamt = findViewById(R.id.totamt);
                    totamt.setText("Total amount: "+Integer.toString(temp));
                }
                else
                {
                    temp = total;
                    cashmsg = findViewById(R.id.cashmsg);
                    cashmsg.setVisibility(View.GONE);
                    totamt = findViewById(R.id.totamt);
                    totamt.setText("Total amount: "+Integer.toString(temp));
                }
            }
        });

        save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.setMessage("Saving Details");
                pd.show();
                String uid = FirebaseAuth.getInstance().getUid();
                dref = FirebaseDatabase.getInstance().getReference("sales").child(uid);
                Long tsLong = System.currentTimeMillis()/1000;
                String ts = tsLong.toString();
                dref.child(ts).child("Shop Name").setValue(shopname);
                dref.child(ts).child("Latitude").setValue(latitude);
                dref.child(ts).child("Longitude").setValue(longitude);
                dref.child(ts).child("Region").setValue(region);
                dref.child(ts).child("Location").setValue(location);
                dref.child(ts).child("Product 1").setValue(product1);
                dref.child(ts).child("Qty 1").setValue(qty1);
                dref.child(ts).child("Product 2").setValue(product2);
                dref.child(ts).child("Qty 2").setValue(qty2);
                dref.child(ts).child("Product 3").setValue(product3);
                dref.child(ts).child("Qty 3").setValue(qty3);
                dref.child(ts).child("Total").setValue(temp);
                pd.cancel();
                Toast.makeText(getBaseContext(),"Sales Details saved",Toast.LENGTH_SHORT).show();
            }
        });

        logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(checkout.this,MainActivity.class));
                finish();
            }
        });

    }
}
