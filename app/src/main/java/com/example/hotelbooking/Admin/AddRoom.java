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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hotelbooking.Api.APIUrl;
import com.example.hotelbooking.R;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AddRoom extends AppCompatActivity implements View.OnClickListener {

    ImageView imageView;
    private EditText etHotelId, etRoomType, etPrice, etDescription;
    Bitmap bitmap;
    Button btnAddRoom, backBtn;
    ActivityResultLauncher<Intent> activityResultCaller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);

        // Initialize views
        imageView = findViewById(R.id.uploadRoomImg);
        btnAddRoom = findViewById(R.id.btnAddRoom);
        etHotelId = findViewById(R.id.etHotelId);
        etRoomType = findViewById(R.id.etRoomType);
        etPrice = findViewById(R.id.etPrice);
        etDescription = findViewById(R.id.etDescription);
        backBtn = findViewById(R.id.back_ROOM);

          activityResultCaller = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    Uri uri = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        imageView.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        // Set click listeners for buttons
        imageView.setOnClickListener(this);
        btnAddRoom.setOnClickListener(this);
        backBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.uploadRoomImg:
                openImagePicker();
                break;
            case R.id.btnAddRoom:
                addRoomToDatabase();
                break;
            case R.id.back_ROOM:
                onBackPressed();
                break;
        }
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activityResultCaller.launch(intent);
    }

    private void addRoomToDatabase() {
        String hotelId = etHotelId.getText().toString().trim();
        String roomType = etRoomType.getText().toString().trim();
        String price = etPrice.getText().toString().trim();
        String description = etDescription.getText().toString().trim();

        if (hotelId.isEmpty() || roomType.isEmpty() || price.isEmpty() || description.isEmpty() || bitmap == null) {
            Toast.makeText(getApplicationContext(), "Please fill in all the fields and select an image", Toast.LENGTH_SHORT).show();
            return;
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        final String base64Image = Base64.encodeToString(bytes, Base64.DEFAULT);

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, APIUrl.INSERT_ROOM_URL,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //success must be same in restApi
                        if (response.equals("success")) {
                            Toast.makeText(getApplicationContext(), "Room inserted successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Hotel ID not found.", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error volley", error.getLocalizedMessage());
                Toast.makeText(getApplicationContext(), error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> paramV = new HashMap<>();
                //image key  must be same in restApi
                paramV.put("image", base64Image);
                paramV.put("hotel_id", hotelId);
                paramV.put("room_type", roomType);
                paramV.put("price", price);
                paramV.put("description", description);
                return paramV;
            }
        };
        queue.add(stringRequest);
    }
}