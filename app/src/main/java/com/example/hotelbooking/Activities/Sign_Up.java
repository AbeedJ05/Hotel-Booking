package com.example.hotelbooking.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hotelbooking.Api.RetrofitSignUp;
import com.example.hotelbooking.Model.Resultt;
import com.example.hotelbooking.R;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Sign_Up extends AppCompatActivity {

    public TextInputLayout name,password,email,phone;
    public Button RegBtn;
    public String Name,Pass,Email,Phone;
    ImageView showPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);



        name = (TextInputLayout) findViewById(R.id.usernamesignup);
        password = (TextInputLayout) findViewById(R.id.passwordsignup);
        email = (TextInputLayout) findViewById(R.id.emailsignup);
        phone = (TextInputLayout) findViewById(R.id.phonesignup);

        RegBtn = (Button) findViewById(R.id.signupBtn);


        //check camera btn

        RegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //Call insertUser method
                insertUser();


            }
        });

        Button return_signin = findViewById(R.id.return_log_in);
        return_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Sign_In.class);
                startActivity(intent);
            }
        });
    }//End onCreate method


    //methode insert user from api
    private void insertUser() {
        // Retrieve data from EditText fields
        String Name = name.getEditText().getText().toString().trim();
        String Pass = password.getEditText().getText().toString().trim();
        String Email = email.getEditText().getText().toString().trim();
        String Phone = phone.getEditText().getText().toString().trim();


        // Check if any field is empty
        if (Name.isEmpty() || Pass.isEmpty() || Email.isEmpty() || Phone.isEmpty()) {
            String errorMessage = "Please enter all of the required fields";
            name.setError(errorMessage);
            password.setError(errorMessage);
            email.setError(errorMessage);
            phone.setError(errorMessage);
            return;
        }


        // Validate email
        boolean isValidEmail = android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches();
        if (!isValidEmail) {
            email.setError("Invalid email address");
            email.requestFocus();
            return;
        }
        boolean isValidpass = Pass.length() >= 6;
        if (!isValidpass) {
            password.setError("Must be at least 6 character");
            password.requestFocus();
            return;
        }
        // Validate phone number
        boolean isValidPhone = Phone.length() >= 10;
        if (!isValidPhone) {
            phone.setError("Invalid phone number");
            phone.requestFocus();
            return;
        }

        // Make call with Api
        Call<Resultt> call = RetrofitSignUp.getInstance().getMyApi().insertUser(Name, Pass,Email, Phone);
        call.enqueue(new Callback<Resultt>() {
            @Override
            public void onResponse(Call<Resultt> call, Response<Resultt> response) {
                if (response.isSuccessful()) {
                    Resultt result = response.body();
                    if (result != null && result.getError()) {
                        Log.d("Something went wrong", result.getMessage());
                        Toast.makeText(Sign_Up.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Log.d("Response", "User registered successfully");
                        Toast.makeText(getApplicationContext(), result != null ? result.getMessage() : "", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(), Sign_In.class));
                    }
                } else {
                    Log.d("Response", "Unsuccessful");
                    Toast.makeText(getApplicationContext(), "Failed to Insert Data", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Resultt> call, Throwable t) {
                Log.d("Failed to Insert Data Rerrofit Error", t.getMessage());
                Toast.makeText(getApplicationContext(), "Failed to Insert Data: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
//end inserrt user methode
}//end class