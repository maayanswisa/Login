package com.example.fragapp.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.fragapp.R;
import com.example.fragapp.activitys.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class HomeFragment extends Fragment {

    private FirebaseAuth mAuth;
    private EditText emailEditText;
    private EditText passEditText;
    private EditText nameEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mAuth = FirebaseAuth.getInstance();

        Button login = view.findViewById(R.id.btnLogin);
        Button reg = view.findViewById(R.id.btnReg);
        emailEditText = view.findViewById(R.id.txtMail1);
        passEditText = view.findViewById(R.id.txtPassword1);
        nameEditText = view.findViewById(R.id.txtName1);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call loginFunc when the login button is clicked
                loginFunc(view);
            }
        });

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(requireView()).navigate(R.id.action_HomeFragment_to_registerFragment);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public void loginFunc(View view) {
        // Use the class-level references
        String txtEmail = emailEditText.getText().toString().trim();
        String txtPass = passEditText.getText().toString().trim();
            if (!txtEmail.isEmpty() && !txtPass.isEmpty()) { // Corrected variable names here
                mAuth.signInWithEmailAndPassword(txtEmail, txtPass)
                        .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    MainActivity mainActivity = (MainActivity) getActivity();
                                    assert mainActivity != null;
                                    mainActivity.readFromDataBase();

                                    Toast.makeText(requireContext(), "Login Success!", Toast.LENGTH_SHORT).show();

                                    Navigation.findNavController(requireView()).navigate(R.id.action_HomeFragment_to_additemFragment);
                                } else {
                                    Toast.makeText(requireContext(), "Login failed: " + task.getException(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            } else {
                Toast.makeText(requireContext(), "Please enter email and password", Toast.LENGTH_SHORT).show();
            }
        }



}
