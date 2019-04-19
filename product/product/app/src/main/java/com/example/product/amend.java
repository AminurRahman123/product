package com.example.product;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class amend extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth firebaseAuth;
    CrimeDbHelper crimeDbHelper;
    SQLiteDatabase sqLiteDatabase;
    String search_name;


    private TextView dname;
    private TextView dref;
    private TextView ddesc;
    private TextView dtype;

    private EditText sname;

    private Button btnsname;
    private Button btndelete;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amend);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()==null){
            finish();
            startActivity(new Intent(this,loginActivity.class));
        }


        sname = (EditText) findViewById(R.id.sname);

        dname = (TextView)findViewById(R.id.dname);
        dref = (TextView) findViewById(R.id.dref);
        ddesc = (TextView) findViewById(R.id.ddesc);
        dtype = (TextView) findViewById(R.id.dtype);

        btnsname = (Button) findViewById(R.id.btnsname);
        btndelete = (Button) findViewById(R.id.btndelete);

        btnsname.setOnClickListener(this);
        btndelete.setOnClickListener(this);

        dref.setVisibility(View.GONE);
        dname.setVisibility(View.GONE);
        ddesc.setVisibility(View.GONE);
        dtype.setVisibility(View.GONE);





    }

    @Override
    public void onClick(View v) {
        if (v == btnsname){

        search_name = sname.getText().toString();
        crimeDbHelper = new CrimeDbHelper(getApplicationContext());
        sqLiteDatabase = crimeDbHelper.getReadableDatabase();
        Cursor cursor = crimeDbHelper.getContact(search_name,sqLiteDatabase);

        if (cursor.moveToFirst()){
            String name = cursor.getString(0);
            String Ref = cursor.getString(1);
            String type = cursor.getString(2);
            String desc = cursor.getString(6);

            dref.setText(Ref);
            dname.setText(name);
            ddesc.setText(desc);
            dtype.setText(type);

            dname.setVisibility(View.VISIBLE);
            dref.setVisibility(View.VISIBLE);
            ddesc.setVisibility(View.VISIBLE);
            dtype.setVisibility(View.VISIBLE);
        }

        }
        if (v == btndelete){

            crimeDbHelper = new CrimeDbHelper(getApplicationContext());
            sqLiteDatabase = crimeDbHelper.getReadableDatabase();
            crimeDbHelper.deleteinfo(search_name,sqLiteDatabase);
            Toast.makeText(getBaseContext(),"Incident Deleted",Toast.LENGTH_LONG).show();
        }
    }

}
