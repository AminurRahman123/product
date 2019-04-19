package com.example.product;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class casemangement extends AppCompatActivity implements View.OnClickListener {


    private FirebaseAuth firebaseAuth;
    private Button btnadd;
    private Button btnview;
    private Button btnedit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_casemangement);
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()==null){
            finish();
            startActivity(new Intent(this,loginActivity.class));
        }

        btnadd = (Button) findViewById(R.id.btnadd);
        btnview = (Button) findViewById(R.id.btnview);
        btnedit = (Button) findViewById(R.id.btnedit);



        btnadd.setOnClickListener(this);
        btnview.setOnClickListener(this);
        btnedit.setOnClickListener(this);






    }

    @Override
    public void onClick(View v) {
        if (v == btnadd){
            startActivity(new Intent(this, Profile.class));
        }
        if (v == btnview){
            startActivity(new Intent(this, viewdb.class));
        }
        if (v == btnedit){
            startActivity(new Intent(this,amend.class) );
        }
    }
}
