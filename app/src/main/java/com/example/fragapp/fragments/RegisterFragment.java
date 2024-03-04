package com.example.fragapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.fragapp.R;
import com.example.fragapp.activitys.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterFragment extends Fragment {

    private FirebaseAuth mAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);


        Button btnLoginAfterReg = view.findViewById(R.id.btnLoginAfterReg);

        btnLoginAfterReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regFunc1(view);
            }
        });

        return view;
    }

    public void regFunc1(View view) {
        EditText txtMail2 = view.findViewById(R.id.txtMail2);
        EditText txtPass2 = view.findViewById(R.id.txtPass2);
        String email = txtMail2.getText().toString().trim();
        String password = txtPass2.getText().toString().trim();
        if (!email.isEmpty() && !password.isEmpty()) {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                MainActivity mainActivity = (MainActivity) getActivity();
                                assert mainActivity != null;
                                mainActivity.writeDataToDataBase();
                                Toast.makeText(requireContext(), "Registration Success", Toast.LENGTH_LONG).show();
                                Navigation.findNavController(requireView()).navigate(R.id.action_registerFragment_to_additemFragment);
                            } else {
                                Toast.makeText(requireContext(), "Registration failed", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        } else {
            Toast.makeText(requireContext(), "Please enter email and password", Toast.LENGTH_SHORT).show();
        }
    }
}
