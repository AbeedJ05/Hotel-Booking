package com.example.hotelbooking.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.hotelbooking.Api.RetrofitUpdate;
import com.example.hotelbooking.Model.Resultt;
import com.example.hotelbooking.Model.User;
import com.example.hotelbooking.R;
import com.example.hotelbooking.SharedPref.SharedPrefManager;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {
    TextInputLayout name, password, email, phone;
    TextView navUsername1, navEmail1, navPhone1;
    Button updateBtn;

    String newName, newEmail, newPassword, newPhone;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_profile_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);

        // Initialize views
        name = view.findViewById(R.id.TextInputUsername);
        email = view.findViewById(R.id.TextInputEmail);
        password = view.findViewById(R.id.TextInputPassword);
        phone = view.findViewById(R.id.TextInputPhone);

        // Get the info of the current user and set it to the corresponding fields
        User user = SharedPrefManager.getInstance(getContext()).getUsers();
        name.getEditText().setText(user.getName());
        password.getEditText().setText(user.getPassword());
        email.getEditText().setText(user.getEmail());
        phone.getEditText().setText(user.getPhone());

        // Initialize header info views
        navUsername1 = view.findViewById(R.id.user_name_pro);
        navEmail1 = view.findViewById(R.id.Email_pro);
        navPhone1 = view.findViewById(R.id.Phone_pro);

        // Set header info
        setInfoHeader();

        // Initialize update button
        updateBtn = getView().findViewById(R.id.updateBtn);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Update Account");
                builder.setMessage("Are you sure you want to update this Account?");

                builder.setPositiveButton("update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Delete the user

                        updateUser(user);
                    }
                });
                builder.setNegativeButton("Cancel", null);
                builder.show();
            }


        });

    }
    // Set the header info based on the user stored in shared preferences
    private void setInfoHeader() {
        User user = SharedPrefManager.getInstance(getContext()).getUsers();
        navUsername1.setText(user.getName());
        navEmail1.setText(user.getEmail());
        navPhone1.setText(user.getPhone());
    }
    private void updateUser(User user){
        newName = name.getEditText().getText().toString();
        newEmail = email.getEditText().getText().toString();
        newPassword = password.getEditText().getText().toString();
        newPhone = phone.getEditText().getText().toString();

        // Validate the input fields
        if (newName.isEmpty()) {
            name.setError("Name is required");
            name.requestFocus();
            return;
        }

        if (newEmail.isEmpty()) {
            email.setError("Email is required");
            email.requestFocus();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(newEmail).matches()) {
            email.setError("Invalid email");
            email.requestFocus();
            return;
        }

        if (newPassword.isEmpty()) {
            password.setError("Password is required");
            password.requestFocus();
            return;
        }

        if (newPassword.length() < 6) {
            password.setError("Password should be at least 6 characters");
            password.requestFocus();
            return;
        }

        if (newPhone.isEmpty()) {
            phone.setError("Phone number is required");
            phone.requestFocus();
            return;
        }
        if (newName.equals(user.getName()) && newEmail.equals(user.getEmail())
                && newPassword.equals(user.getPassword()) && newPhone.equals(user.getPhone())){

              Toast.makeText(getContext(), "No changes to update.", Toast.LENGTH_LONG).show();

        }else {
            // Make API call to update the user
            Call<Resultt> call = RetrofitUpdate.getInstance()
                    .getMyApi().updateUser(user.getId(), newName, newEmail, newPassword, newPhone);
            call.enqueue(new Callback<Resultt>() {
                @Override
                public void onResponse(Call<Resultt> call, Response<Resultt> response) {
                    if (response.isSuccessful()) {
                        Resultt result = response.body();
                        if (result.getError() || result.getUser().getName() == null) {
                            // Update failed, show error message
                            Toast.makeText(getContext(), "Update failed: " + result.getMessage(), Toast.LENGTH_LONG).show();
                        } else {
                            // Update successful, update user in shared preferences and header info
                            User updatedUser = result.getUser();
                            SharedPrefManager.getInstance(getContext()).userUpdate(updatedUser);
                            SharedPrefManager.getInstance(getContext()).loggedOut();

                            Toast.makeText(getContext(), "Update successful: " + result.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        // Request failed, show error message
                        Toast.makeText(getContext(), "Request failed: " + response.message(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Resultt> call, Throwable t) {
                    // Request failed, show error message
                    Toast.makeText(getContext(), "Request failed: " + t.getMessage(), Toast.LENGTH_LONG).show();
                    Log.e("ProfileFragment", "onFailure: ", t);
                }
            });
        }

    }
}
