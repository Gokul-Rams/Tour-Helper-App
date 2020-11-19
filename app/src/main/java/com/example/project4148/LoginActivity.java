package com.example.project4148;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText etmail,etpassword;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        etmail = findViewById(R.id.etemail);
        etpassword = findViewById(R.id.etpasssword);
    }

    public void loginbtnclicked(View view)
    {
        final Loading_animation anim = new Loading_animation(this);
        String mail,pass;
        mail = etmail.getText().toString();
        pass = etpassword.getText().toString();
        Boolean flag;

        if(mail.isEmpty() || pass.isEmpty())
        {
            flag = true;
        }
        else{
            flag = false;
        }

        if(!flag){
            anim.startanimation();
            auth.signInWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(LoginActivity.this, "Login Sucessful", Toast.LENGTH_SHORT).show();
                        anim.stopanimation();
                        Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Toast.makeText(LoginActivity.this, "There is an problem with Login check your details", Toast.LENGTH_SHORT).show();
                        anim.stopanimation();
                    }
                }
            });
        }
        else {
            Toast.makeText(this, "Email or password cannot be empty!...", Toast.LENGTH_SHORT).show();
        }
    }

    public void registerbtnclicked(View view){
        Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
        startActivity(intent);
        finish();
    }
}