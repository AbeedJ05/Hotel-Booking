package com.example.hotelbooking.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hotelbooking.Api.RetrofitSignIn;
import com.example.hotelbooking.Model.Resultt;
import com.example.hotelbooking.Model.User;
import com.example.hotelbooking.R;
import com.example.hotelbooking.SharedPref.SharedPrefManager;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Sign_In extends AppCompatActivity implements View.OnClickListener {
    private TextInputLayout EmailTextInputLayout, PasswordTextInputLayout;
    private Button buttonSignIn;
    private TextView signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        EmailTextInputLayout = (TextInputLayout) findViewById(R.id.emailSignin);
        PasswordTextInputLayout = (TextInputLayout) findViewById(R.id.passwordSignin);

        buttonSignIn = (Button) findViewById(R.id.signin);
        signUp =  findViewById(R.id.inSignup);


        buttonSignIn.setOnClickListener(this);
        signUp.setOnClickListener(this);
    }//end on create method
    private void userSignIn() {

        String email = EmailTextInputLayout.getEditText().getText().toString().trim();
        String password = PasswordTextInputLayout.getEditText().getText().toString().trim();

        // Validate email


        // Validate password
        boolean isValidPassword = isValidPassword(password);
        if (!isValidPassword) {
            PasswordTextInputLayout.setError("Password must be at least 6 characters long");
            return;
        }

        // Call the API
        Call<Resultt> call = RetrofitSignIn.getInstance().getMyApi().userLogin(email, password);
        call.enqueue(new Callback<Resultt>() {
            @Override
            public void onResponse(Call<Resultt> call, Response<Resultt> response) {

                if (!response.body().getError()) {

                    Intent intent = new Intent(Sign_In.this, MainActivity.class);
                    // for navigation drawer


                    startActivity(intent);
                    // toast message to say hello for user
                    Toast.makeText(getApplicationContext(), "Welcome " + response.body().getUser().getName(), Toast.LENGTH_LONG).show();


                    User user = new User(response.body().getUser().getId(), response.body().getUser().getName(),
                            response.body().getUser().getEmail(), response.body().getUser().getPassword(),
                            response.body().getUser().getPhone());

                    // to store  store data shared manager
                    SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                    finish();

                } else {
                    Toast.makeText(getApplicationContext(), "Invalid email or password", Toast.LENGTH_LONG).show();
                }

            }

            // if failed connection with server
            @Override
            public void onFailure(Call<Resultt> call, Throwable t) {


                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("Retrofit ERROR -->", t.getMessage());

            }
        });
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 6;
    }


    @Override
    public void onClick(View view) {

        if (view == buttonSignIn) {
            userSignIn();
        }else if(view == signUp){
            startActivity(new Intent(getApplicationContext(),Sign_Up.class));
        }
    }
}