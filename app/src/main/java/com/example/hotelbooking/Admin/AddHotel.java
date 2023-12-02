package com.example.hotelbooking.Admin;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hotelbooking.Api.APIUrl;
import com.example.hotelbooking.R;

import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AddHotel extends AppCompatActivity {


    private Button insertHotelBtn , backBtn;
    ImageView imageView;
    Bitmap bitmap;
    TextInputLayout hotelNameEditText,countryEditText,ratingEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hotel);

        imageView = findViewById(R.id.clickToUploadImage);
        backBtn = findViewById(R.id.Back_hotel);
        insertHotelBtn = findViewById(R.id.insertHotel);
        // Initialize TextInputLayout objects
        hotelNameEditText = findViewById(R.id.hnadmin);
        countryEditText = findViewById(R.id.cadmin);
        ratingEditText = findViewById(R.id.rattingadmin);

        //to make imageview display gallery and choose one and display it
        ActivityResultLauncher<Intent> activityResultCaller = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
            if(result.getResultCode() == Activity.RESULT_OK){
                Intent data = result.getData();
                Uri uri = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                    imageView.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activityResultCaller.launch(intent);
    }
});//to here i can display this image


      //insert Hotelbtn
        insertHotelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hotelName = hotelNameEditText.getEditText().getText().toString().trim();
                String country = countryEditText.getEditText().getText().toString().trim();

                String ratingString = ratingEditText.getEditText().getText().toString().trim();
                if (hotelName.isEmpty() || country.isEmpty() || ratingString.isEmpty() || bitmap == null) {
                    Toast.makeText(getApplicationContext(), "Please fill in all the fields and select an image", Toast.LENGTH_SHORT).show();
                    return;
                }


                // Convert the ratingString to an integer
                int rating;
                try {
                    rating = Integer.parseInt(ratingString);
                } catch (NumberFormatException e) {
                    rating = 0; // Set a default value if parsing fails
                }
                // Check if the rating is within the valid range (1 to 5)
                if (rating < 1 || rating > 5) {
                    Toast.makeText(getApplicationContext(), "Rating must be between 1 and 5", Toast.LENGTH_SHORT).show();
                    return;
                }

                ByteArrayOutputStream byteArrayOutputStream;
                byteArrayOutputStream = new ByteArrayOutputStream();
                if(bitmap != null) {
                    bitmap.compress (Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    byte[] bytes = byteArrayOutputStream.toByteArray();
                    final String base64Image = Base64.encodeToString (bytes, Base64.DEFAULT);
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

                    //from command prompot get ipconfig


                    int finalRating = rating;
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, APIUrl.addHotelUrl,
                            new com.android.volley.Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    //success must be same in restApi
                                   if (response.equals("success")){
                                     Toast.makeText(getApplicationContext(),"Hotel inserted successfully",Toast.LENGTH_SHORT).show();
                                   }else{
                                       Toast.makeText(getApplicationContext(),"failed",Toast.LENGTH_SHORT).show();
                                   }
                                }
                            }, new com.android.volley.Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("error volley",error.getLocalizedMessage());
                            Toast.makeText(getApplicationContext(), error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }){
                        protected Map<String, String> getParams(){
                            Map<String, String> paramV = new HashMap<>();
                            //image key  must be same in restApi
                            paramV.put("image", base64Image);
                            paramV.put("hotelName", hotelName);
                            paramV.put("country", country);
                            paramV.put("rating",String.valueOf(finalRating));
                            return paramV;
                        }
                    };
                    queue.add(stringRequest);
                }
                else {
                    Toast.makeText(getApplicationContext(),  "Select the image first", Toast.LENGTH_SHORT).show();
                }
            }

        });


        backBtn.setOnClickListener(v -> onBackPressed());

    }//end on Create Method


    }








