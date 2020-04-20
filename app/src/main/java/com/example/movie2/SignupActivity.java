package com.example.movie2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class SignupActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button btnSignUp2;
    private EditText edtTxtFirstName, edtTxtLastName, edtTxtEmail, edtTxtPass, edtTxtConfPass;
    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        widgets();


        mAuth = FirebaseAuth.getInstance();

        btnSignUp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtTxtEmail.getText().toString().isEmpty() || edtTxtFirstName.getText().toString().isEmpty() || edtTxtLastName.getText().toString().isEmpty() || edtTxtPass.getText().toString().isEmpty() || edtTxtConfPass.getText().toString().isEmpty()) {
                    Toast.makeText(SignupActivity.this, "Empty Fields are not allowed", Toast.LENGTH_LONG).show();
                    return;
                } else if (Objects.equals(edtTxtPass.getText().toString(), edtTxtConfPass.getText().toString())) {
                    mAuth.createUserWithEmailAndPassword(edtTxtEmail.getText().toString(), edtTxtPass.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(SignupActivity.this, "User is registered", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                                        startActivity(intent);


                                    } else {
                                        Toast.makeText(SignupActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                } else {
                    Toast.makeText(SignupActivity.this, "Passwords must be same", Toast.LENGTH_LONG).show();
                }


            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    public void widgets() {
        btnSignUp2 = (Button) findViewById(R.id.btnSignUp2);

        edtTxtFirstName = (EditText) findViewById(R.id.edtTxtFirstName);
        edtTxtLastName = (EditText) findViewById(R.id.edtTxtLastName);
        edtTxtEmail = (EditText) findViewById(R.id.edtTxtEmailIdSignUp);
        edtTxtPass = (EditText) findViewById(R.id.edtTxtPassword);
        edtTxtConfPass = (EditText) findViewById(R.id.edtTxtConfPass);
        textView = (TextView) findViewById(R.id.txtViewSignIn);

    }



}
