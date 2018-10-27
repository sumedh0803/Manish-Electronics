package com.example.sumedh.manishelectronics;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    EditText email, password;
    Button submit;
    ProgressDialog pd;
    FirebaseAuth firebaseauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseauth = FirebaseAuth.getInstance();

        if(firebaseauth.getCurrentUser() != null) {
            Intent i = new Intent(MainActivity.this, location.class);
            startActivity(i);
            finish();
        }
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        submit = findViewById(R.id.submit);
        pd = new ProgressDialog(this);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.setMessage("Signing In");
                pd.show();
                firebaseauth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplication(), "Go to home activity", Toast.LENGTH_SHORT).show();

                                    pd.cancel();
                                    startActivity(new Intent(getBaseContext(),location.class));
                                    finish();
                                } else {
                                    pd.cancel();
                                    Toast.makeText(getBaseContext(),"Please check your credentials and try again!",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });



    }
}
