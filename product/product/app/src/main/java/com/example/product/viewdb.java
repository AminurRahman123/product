package com.example.product;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;

public class viewdb extends AppCompatActivity {



    ListView listView;
    SQLiteDatabase sqLiteDatabase;
    CrimeDbHelper crimeDbHelper;
    Cursor cursor;
    viewdbAdapter viewdbAdapter;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewdb);

        listView = (ListView)findViewById(R.id.listview);
        viewdbAdapter = new viewdbAdapter(getApplicationContext(),R.layout.rowlayout);
        listView.setAdapter(viewdbAdapter);


        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()==null){
            finish();
            startActivity(new Intent(this,loginActivity.class));
        }

        crimeDbHelper = new CrimeDbHelper(getApplicationContext());
        sqLiteDatabase = crimeDbHelper.getReadableDatabase();
        cursor = crimeDbHelper.getinformation(sqLiteDatabase);

        if (cursor.moveToFirst()){
            do {
                String name,reference,type,date,time,location,description;
                name = cursor.getString(0);
                reference = cursor.getString(1);
                type = cursor.getString(2);
                date = cursor.getString(3);
                time = cursor.getString(4);
                location = cursor.getString(5);
                description = cursor.getString(6);

               dbProvider dbprovider = new dbProvider(name,reference,type,date,time,location,description);
               viewdbAdapter.add(dbprovider);
            }

            while (cursor.moveToNext());
        }

    }
}
