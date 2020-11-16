package com.example.project4148;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    EditText etmail,etpass,etconfirmpass;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        auth = FirebaseAuth.getInstance();
        etmail = findViewById(R.id.etregisteremail);
        etpass = findViewById(R.id.etregisterpassword);
        etconfirmpass = findViewById(R.id.etregisterconfpass);

    }

    public  void nextbtnclicked(View view)
    {
        String mail,pass,confirmpass;
        mail = etmail.getText().toString().toLowerCase().trim();
        pass = etpass.getText().toString();
        confirmpass = etconfirmpass.getText().toString();
        final Loading_animation anim = new Loading_animation(this);
        if(pass.isEmpty() || mail.isEmpty() || confirmpass.isEmpty())
        {
            Toast.makeText(this, "Empty Feilds", Toast.LENGTH_SHORT).show();
        }
        else{
            if(pass.equals(confirmpass))
            {
                anim.startanimation();
                auth.createUserWithEmailAndPassword(mail,pass).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                            startActivity(intent);
                            anim.stopanimation();
                            finish();
                        }
                        else{
                            Toast.makeText(SignUpActivity.this, "Sign up Failed", Toast.LENGTH_SHORT).show();
                            anim.stopanimation();
                        }
                    }
                });
            }
            else {
                Toast.makeText(this, "Make sure the password is correct", Toast.LENGTH_SHORT).show();
            }
        }
    }
}