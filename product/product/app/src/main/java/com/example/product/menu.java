package com.example.product;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class menu extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth firebaseAuth;

    private Button buttoncase;
    private Button buttonimage;
    private Button buttonother;
    private Button buttonlogout;
    private Button buttonsupport;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()==null){
            finish();
            startActivity(new Intent(this,loginActivity.class));
        }

        //buttons
        buttoncase = (Button) findViewById(R.id.buttoncase);
        buttonimage = (Button) findViewById(R.id.buttonimage);
        buttonother = (Button) findViewById(R.id.buttonother);
        buttonlogout = (Button) findViewById(R.id.buttonlogout);
        buttonsupport = (Button) findViewById(R.id.buttonsupport);

        //button listener
        buttonlogout.setOnClickListener(this);
        buttoncase.setOnClickListener(this);
        buttonimage.setOnClickListener(this);
        buttonother.setOnClickListener(this);
        buttonsupport.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v == buttonlogout){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this,loginActivity.class));
        }

        if (v == buttoncase) {
            startActivity(new Intent(this,casemangement.class));
        }
        if (v == buttonimage){
            startActivity(new Intent(this,imagemanagement.class));
        }
        if (v == buttonother){
            startActivity(new Intent(this,textrecogniser.class));
        }
        if (v == buttonsupport){
            startActivity(new Intent(this,supportpage.class));
        }
        }
    }

