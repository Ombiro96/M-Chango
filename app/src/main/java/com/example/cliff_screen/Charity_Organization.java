package com.example.cliff_screen;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Charity_Organization extends AppCompatActivity {

    private TextView backButton;
    private CircleImageView profile_image_C;
    private TextInputEditText registerFullNameC, nationalID_C, registerPhoneNumber, registerEmail, registerPassword;
    private Button registerButton;
    private Uri resultUri;
    private ProgressDialog loader;
    private FirebaseAuth mAuth;
    private DatabaseReference userDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charity_organization);

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Charity_Organization.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        profile_image_C=findViewById(R.id.profile_image_C);
        registerFullNameC=findViewById(R.id.registerFullNameC);
        nationalID_C=findViewById(R.id.nationalID_C);
        registerEmail=findViewById(R.id.email_C);
        registerPhoneNumber=findViewById(R.id.phoneNumber_C);
        registerButton=findViewById(R.id.registerButton);
        registerPassword=findViewById(R.id.password_a_C);
        loader=new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();

        profile_image_C.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = registerEmail.getText().toString().trim();
                final String password =registerPassword.getText().toString().trim();
                final String fullName =registerFullNameC.getText().toString().trim();
                final String idNumber=nationalID_C.getText().toString().trim();
                final String phoneNumber=registerPhoneNumber.getText().toString().trim();


                if (TextUtils.isEmpty(email))
                {
                    registerEmail.setError("Email is required");
                    return;
                }
                if (TextUtils.isEmpty(password))
                {
                    registerPassword.setError("Password is required");
                    return;
                }
                if (TextUtils.isEmpty(fullName))
                {
                    registerFullNameC.setError("Full name is required");
                    return;
                }
                if (TextUtils.isEmpty(idNumber))
                {
                    nationalID_C.setError("ID is required");
                    return;
                }
                if (TextUtils.isEmpty(phoneNumber))
                {
                    registerPhoneNumber.setError("Phone number is required");
                    return;
                }
                else{
                    loader.setMessage("Registering you...");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();

                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful())
                            {
                                String error=task.getException().toString();
                                Toast.makeText(Charity_Organization.this, "Error" + error, Toast.LENGTH_SHORT).show();
                            }
                            else{
                                String currentUserId=mAuth.getCurrentUser().getUid();
                                userDatabaseRef= FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId);
                                HashMap userInfo= new HashMap();
                                userInfo.put("id", currentUserId);
                                userInfo.put("name", fullName);
                                userInfo.put("email", email);
                                userInfo.put("idNumber", idNumber);
                                userInfo.put("phoneNumber", phoneNumber);
                                userInfo.put("type", "Charity");

                                userDatabaseRef.updateChildren(userInfo).addOnCompleteListener(new OnCompleteListener() {
                                    @Override
                                    public void onComplete(@NonNull Task task) {
                                        if (task.isSuccessful())
                                        {
                                            Toast.makeText(Charity_Organization.this, "Data saved successfully", Toast.LENGTH_SHORT).show();
                                        }else{
                                            Toast.makeText(Charity_Organization.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                        }
                                        finish();
                                    }
                                });
                                if (resultUri !=null){
                                    final StorageReference filePath= FirebaseStorage.getInstance().getReference().child("Profile pictures_dps").child(currentUserId);
                                    Bitmap bitmap = null;
                                    try {
                                        bitmap = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), resultUri);
                                    }catch (IOException e){
                                        e.printStackTrace();
                                    }
                                    ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
                                    bitmap.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream);
                                    byte[] data =byteArrayOutputStream.toByteArray();
                                    UploadTask uploadTask= filePath.putBytes(data);

                                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            if (taskSnapshot.getMetadata() != null && taskSnapshot.getMetadata().getReference() !=null){
                                                Task<Uri> result= taskSnapshot.getStorage().getDownloadUrl();
                                                result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                    @Override
                                                    public void onSuccess(Uri uri) {
                                                        String imageUrl= uri.toString();
                                                        Map newImageMap= new HashMap();
                                                        newImageMap.put("Profile_picture_url", imageUrl);

                                                        userDatabaseRef.updateChildren(newImageMap).addOnCompleteListener(new OnCompleteListener() {
                                                            @Override
                                                            public void onComplete(@NonNull Task task) {
                                                                if (task.isSuccessful()){
                                                                    Toast.makeText(Charity_Organization.this, "Image url added successful", Toast.LENGTH_SHORT).show();
                                                                }else{
                                                                    Toast.makeText(Charity_Organization.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });
                                                        finish();
                                                    }
                                                });
                                            }
                                        }
                                    });
                                    uploadTask.addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(Charity_Organization.this, "Upload failed", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    Intent intent=new Intent(Charity_Organization.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                    loader.dismiss();
                                }
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode ==1 && resultCode == RESULT_OK && data!=null){
            resultUri=data.getData();
            profile_image_C.setImageURI(resultUri);
        }
    }
    }
