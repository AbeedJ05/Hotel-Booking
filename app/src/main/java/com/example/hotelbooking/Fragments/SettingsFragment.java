package com.example.hotelbooking.Fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.hotelbooking.Api.RetrofitDeleteUser;
import com.example.hotelbooking.Model.Resultt;
import com.example.hotelbooking.Model.User;
import com.example.hotelbooking.R;
import com.example.hotelbooking.SharedPref.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsFragment extends Fragment {
  Button LogOut ;
    Button deleteBtn;
    int Id;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_settings_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {


        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);


        //Get the Id of current user to delete the account
        User user = SharedPrefManager.getInstance(getContext()).getUsers();

        Id = user.getId();

        deleteBtn = getView().findViewById(R.id.deleteAcc);

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Show a dialog to confirm the deletion
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Delete Account");
                builder.setMessage("Are you sure you want to delete this Account?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Delete the user
                        DeleteUser();
                    }
                });
                builder.setNegativeButton("Cancel", null);
                builder.show();
            }
        });


        LogOut = view.findViewById(R.id.settingLogOut);
        //log Out and delete user information it was saved in SharePref
        LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().finish();
                SharedPrefManager.getInstance(requireContext()).loggedOut();
            }

        });
    }
    public void DeleteUser(){

        Call<Resultt> call = RetrofitDeleteUser.getInstance().getMyApi().deleteUser(Id);
        call.enqueue(new Callback<Resultt>() {
            @Override
            public void onResponse(Call<Resultt> call, Response<Resultt> response) {

                if(response.body().getError() == false){

                    Toast.makeText(getContext(),"Response msg ---> "+ response.body().getMessage(),Toast.LENGTH_LONG).show();
                    getActivity().finish();
                    Log.d("msg ---> ",response.body().getMessage());
                    SharedPrefManager.getInstance(getContext()).loggedOut();
                }else if (response.body().getError() == true){
                    Toast.makeText(getContext(),"Response msg ---> "+ response.body().getMessage(),Toast.LENGTH_LONG).show();
                    Log.d("msg ---> ",response.body().getMessage());

                }
            }

            @Override
            public void onFailure(Call<Resultt> call, Throwable t) {

                Toast.makeText(getContext(),"Error ---> "+t.getMessage(),Toast.LENGTH_LONG).show();
                Log.d("Error ---> ",t.getMessage());

            }
        });
    }
}