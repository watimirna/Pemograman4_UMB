package com.rnld.loginsqlite;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Register extends AppCompatActivity {

    private EditText edtName, edtEmail, edtPassword, edtConfirmPassword;
    private Button btnRegister, btnToLogin;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);
        btnToLogin = findViewById(R.id.btnToLogin);

        db = new DatabaseHelper(this);

        btnRegister.setOnClickListener(v -> {
            String name = edtName.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();
            String password = edtPassword.getText().toString();
            String confirmPassword = edtConfirmPassword.getText().toString();

            if (name.isEmpty()) {
                edtName.setError("Nama wajib diisi");
                edtName.requestFocus();
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                edtEmail.setError("Email tidak valid");
                edtEmail.requestFocus();
            } else if (password.length() < 6) {
                edtPassword.setError("Password minimal 6 karakter");
                edtPassword.requestFocus();
            } else if (!password.equals(confirmPassword)) {
                edtConfirmPassword.setError("Konfirmasi password tidak cocok");
                edtConfirmPassword.requestFocus();
            } else {
                boolean registered = db.registerUser(name, email, password);
                if (registered) {
                    Toast.makeText(this, "Registrasi berhasil", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, Login.class));
                    finish();
                } else {
                    Toast.makeText(this, "Email sudah digunakan", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnToLogin.setOnClickListener(v -> {
            startActivity(new Intent(this, Login.class));
            finish();
        });
    }
}
