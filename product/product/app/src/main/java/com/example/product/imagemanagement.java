package com.example.product;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.net.URI;

public class imagemanagement extends AppCompatActivity {


    private FirebaseAuth firebaseAuth;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private StorageTask supload;

    private  static final int PICK_IMAGE_REQUEST = 1;
    private Button btnchoose;
    private Button btnshow;

    private TextView TextViewshowupload;

    private EditText editTextfilename;

    private ImageView imageview;
    private ProgressBar progress_bar;

    private Uri imageuri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagemanagement);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()==null){
            finish();
            startActivity(new Intent(this,loginActivity.class));
        }

        storageReference = FirebaseStorage .getInstance().getReference("uploads");
        databaseReference = FirebaseDatabase.getInstance().getReference("uploads");

        btnchoose = (Button) findViewById(R.id.btnchoose);
        btnshow = (Button) findViewById(R.id.btnshow);

        btnchoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        btnshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(supload != null && supload.isInProgress()){
                Toast.makeText(imagemanagement.this, "Upload Already In Progress", Toast.LENGTH_LONG).show();
            }else{
            uploadFile();
            }}
        });



        TextViewshowupload = (TextView) findViewById(R.id.TextViewshowupload);
        TextViewshowupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImagesActivity();
            }
        });

        editTextfilename = (EditText) findViewById(R.id.editTextfilename);

        imageview = (ImageView) findViewById(R.id.imageview);
        progress_bar = (ProgressBar) findViewById(R.id.progress_bar);

    }
    private void openFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){

        imageuri = data.getData();
            Picasso.with(this).load(imageuri).into(imageview);
        }
            }

            private String getFileExtension (Uri uri){
                ContentResolver conRes = getContentResolver();
                MimeTypeMap mtp = MimeTypeMap.getSingleton();
                return mtp.getExtensionFromMimeType(conRes.getType(uri));
    }

            private void uploadFile(){
                if (imageuri != null){
                    StorageReference fileRef = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(imageuri));

                    supload = fileRef.putFile(imageuri)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            progress_bar.setProgress(0);
                                        }
                                    }, 3000);

                                    Toast.makeText(imagemanagement.this, "Image Upload Complete", Toast.LENGTH_LONG).show();
                                    upload Upload = new upload(editTextfilename.getText().toString().trim(), taskSnapshot.getUploadSessionUri().toString());
                                    String imageID = databaseReference.push().getKey(); //creates database entry
                                    databaseReference.child(imageID).setValue(Upload);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(imagemanagement.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount()); //calculating progress of upload
                            progress_bar.setProgress((int)progress);
                        }
                    });

                } else {
                    Toast.makeText(this, "No Image Selected",Toast.LENGTH_SHORT).show();
                }
            }

            private void openImagesActivity(){
        Intent intent = new Intent(this,upload2.class);
        startActivity(intent);
            }
}
