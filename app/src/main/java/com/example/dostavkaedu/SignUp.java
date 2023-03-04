package com.example.dostavkaedu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dostavkaedu.Models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUp extends AppCompatActivity
{

    private Button btnSignUp;
    private EditText editPhone, editPassword, editName;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        btnSignUp = findViewById(R.id.btnSignUp);
        editPhone = findViewById(R.id.editTextTextPhone);
        editPassword = findViewById(R.id.editTextTextPassword);
        editName = findViewById(R.id.editTextName);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table = database.getReference("User");

        btnSignUp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                table.addValueEventListener(new ValueEventListener() // проверяем подключение к бд
                {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot)
                    {
                        if (snapshot.child(editPhone.getText().toString()).exists())
                        {
                            Toast.makeText(SignUp.this, "Такой пользователь уже есть", Toast.LENGTH_LONG).show();
                        } else
                        {
                            User user = new User(editName.getText().toString(), editPassword.getText().toString()); // создаем пользователя из полей
                            table.child(editPhone.getText().toString()).setValue(user); // регистрируем с ключем
                            Toast.makeText(SignUp.this, "Успешная регистрация", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error)
                    {
                        Toast.makeText(SignUp.this, "Нет соединения", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}