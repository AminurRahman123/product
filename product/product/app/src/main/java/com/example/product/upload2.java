package com.example.product;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class upload2 extends AppCompatActivity {
    private RecyclerView recyclerView;
    private upload2Adapter u2Adapter;
    private FirebaseAuth firebaseAuth;

    private DatabaseReference databaseReference;
    private List<upload> muploads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload2);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()==null){
            finish();
            startActivity(new Intent(this,loginActivity.class));
        }

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        muploads = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("uploads");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    upload Upload = postSnapshot.getValue(upload.class);
                    muploads.add(Upload);
                }

                u2Adapter = new upload2Adapter(upload2.this, muploads);

                recyclerView.setAdapter(u2Adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(upload2.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
