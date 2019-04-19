package com.example.product;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class loginActivity extends AppCompatActivity implements View.OnClickListener {
    private Button buttonlogin;
    private EditText editTextEmail;
    private EditText editTextpasswd;
    private TextView textViewSignup;
    private ProgressDialog Progressdialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() !=null){
            //profile activity here
            finish();
            startActivity(new Intent(getApplicationContext(),menu.class));
        }

        Progressdialog = new ProgressDialog(this);
        buttonlogin = (Button) findViewById(R.id.buttonlogin);

        editTextEmail = (EditText) findViewById(R.id.editTextemail);
        editTextpasswd = (EditText) findViewById(R.id.editTextpasswd);

        textViewSignup = (TextView) findViewById(R.id.textViewSignup);

        buttonlogin.setOnClickListener(this);
        textViewSignup.setOnClickListener(this);


    }
    private void userLogin(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextpasswd.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            //email is empty
            Toast.makeText(this, "Please enter valid Email", Toast.LENGTH_SHORT).show();
            //stop the execution
            return;
        }

        if (TextUtils.isEmpty(password)) {
            //password is empty
            Toast.makeText(this, "Please enter a valid password", Toast.LENGTH_SHORT).show();
            return;
        }
        Progressdialog.setMessage("Logging you in...");
        Progressdialog.show();

        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
            Progressdialog.dismiss();
            if(task.isSuccessful()){
                //start the profile activity
                finish();
            startActivity(new Intent(getApplicationContext(),menu.class));
            }
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view == buttonlogin) {
            userLogin();
        }

        if (view == textViewSignup) {
            finish();
            startActivity(new Intent(this,MainActivity.class));
        }
    }

    }

