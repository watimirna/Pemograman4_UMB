package com.rnld.loginsqlite;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {

    EditText edtEmail, edtPassword;
    Button btnLogin, btnToRegister;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnToRegister = findViewById(R.id.btnToRegister);

        db = new DatabaseHelper(this);

        btnLogin.setOnClickListener(v -> {
            String email = edtEmail.getText().toString().trim();
            String password = edtPassword.getText().toString();

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                edtEmail.setError("Email tidak valid");
            } else if (password.length() < 6) {
                edtPassword.setError("Password minimal 6 karakter");
            } else {
                if (db.checkUser(email, password)) {
                    String name = db.getNameByEmail(email);
                    if (name == null || name.isEmpty()) name = email;
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.putExtra("user_name", name);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(this, "Email atau password salah", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnToRegister.setOnClickListener(v -> {
            startActivity(new Intent(this, Register.class));
            finish();
        });
    }
}
