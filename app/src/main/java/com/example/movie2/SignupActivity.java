package com.example.movie2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class SignupActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private MaterialButton signUpButton;
    private EditText nameEditText, emailEditText, passwordEditText, confPassEditText;
    private TextView signInTextView;
    private boolean isPasswordVisible = false;
    private boolean isConfPassVisible = false;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        widgets();


        mAuth = FirebaseAuth.getInstance();

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (emailEditText.getText().toString().isEmpty() || nameEditText.getText().toString().isEmpty()
                        || passwordEditText.getText().toString().isEmpty() || confPassEditText.getText().toString().isEmpty()) {
                    Toast.makeText(SignupActivity.this, "Empty Fields are not allowed", Toast.LENGTH_LONG).show();
                } else if (Objects.equals(passwordEditText.getText().toString(), confPassEditText.getText().toString())) {
                    mAuth.createUserWithEmailAndPassword(emailEditText.getText().toString(), passwordEditText.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(SignupActivity.this, "User is registered", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                                        startActivity(intent);


                                    } else {
                                        Toast.makeText(SignupActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                } else {
                    Toast.makeText(SignupActivity.this, "Passwords must be same", Toast.LENGTH_LONG).show();
                }

            }
        });
        signInTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        passwordEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (passwordEditText.getRight() - passwordEditText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        if (isPasswordVisible) {
                            passwordEditText.setCompoundDrawablesWithIntrinsicBounds(null, null, getDrawable(R.drawable.ic_outline_remove_red_eye_24), null);
                            passwordEditText.setTransformationMethod(new PasswordTransformationMethod());
                            isPasswordVisible = false;
                        } else {
                            passwordEditText.setCompoundDrawablesWithIntrinsicBounds(null, null, getDrawable(R.drawable.ic_baseline_remove_red_eye_24), null);
                            passwordEditText.setTransformationMethod(null);
                            isPasswordVisible = true;
                        }
                        return true;
                    }
                }
                return false;
            }
        });

        confPassEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (confPassEditText.getRight() - confPassEditText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // your action here
                        if (isConfPassVisible) {
                            confPassEditText.setCompoundDrawablesWithIntrinsicBounds(null, null, getDrawable(R.drawable.ic_outline_remove_red_eye_24), null);
                            confPassEditText.setTransformationMethod(new PasswordTransformationMethod());
                            isConfPassVisible = false;
                        } else {
                            confPassEditText.setCompoundDrawablesWithIntrinsicBounds(null, null, getDrawable(R.drawable.ic_baseline_remove_red_eye_24), null);
                            confPassEditText.setTransformationMethod(null);
                            isConfPassVisible = true;
                        }
                        return true;
                    }
                }
                return false;
            }
        });


    }

    public void widgets() {
        signUpButton = findViewById(R.id.signUpButton);
        nameEditText = findViewById(R.id.editTxtName);
        emailEditText = findViewById(R.id.editTxtEmail);
        passwordEditText = findViewById(R.id.editTextPass);
        confPassEditText = findViewById(R.id.editTextConfPass);
        signInTextView = findViewById(R.id.signInTV);

    }



}
