package com.example.fragapp.activitys;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fragapp.R;
import com.example.fragapp.model.User;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    public static String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        FirebaseApp.initializeApp(this);

    }


    public String getUsername() {
        return username;
    }

    public void writeDataToDataBase() {

        EditText email = findViewById(R.id.txtMail2);
        EditText pass = findViewById(R.id.txtPass2);
        EditText name = findViewById(R.id.txtName2);

        FirebaseUser currentUser = mAuth.getCurrentUser();
        assert currentUser != null;
        String str = currentUser.getUid();

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users").child(name.getText().toString()); //ייחודי
        User user1 = new User(email.getText().toString(), pass.getText().toString(), name.getText().toString());
        Toast.makeText(this, user1.toString(), Toast.LENGTH_LONG).show();

        myRef.setValue(user1);
    }


    public void readFromDataBase() {
        // Read from the database
        EditText name = findViewById(R.id.txtName1);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users").child(name.getText().toString()); //ייחודי

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                User user = dataSnapshot.getValue(User.class);

                if (user != null) {
                    // Access the properties of the User object
                    String email = user.getEmail();
                    String password = user.getPassword();

                    // Now you can use email, password, or any other property as needed
                    Log.d(TAG, "Email: " + email);
                    Log.d(TAG, "Password: " + password);

                    Toast.makeText(MainActivity.this, user.toString(), Toast.LENGTH_LONG).show();


                } else {
                    Log.d(TAG, "User is null");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }


}