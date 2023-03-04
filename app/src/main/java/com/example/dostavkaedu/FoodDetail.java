package com.example.dostavkaedu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dostavkaedu.Models.Category;
import com.example.dostavkaedu.Models.Food;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FoodDetail extends AppCompatActivity
{
    public static int ID = 0;
    private ImageView mainPhoto;
    private TextView price, foodFullName, foodMainName;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        mainPhoto = findViewById(R.id.mainPhoto);
        price = findViewById(R.id.price);
        foodFullName = findViewById(R.id.foodFullName);
        foodMainName = findViewById(R.id.foodMainName);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference table = database.getReference("Category");

        table.child(String.valueOf(ID)).addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                Category category = snapshot.getValue(Category.class); // получаем поля и записываем их

                if(category != null) // мне пришлось это сделать т.к. после кнопки назад(возвращаемся к списку)
                //  он хранит значение переменных, не очищая их, и выдает ошибку, приложение не закрывается, но все же
                // можете попробовать открыть например: Рыбу, назад, а потом ее удалить, без ифа будет ошибка
                {
                    foodMainName.setText(category.getName());
                    int id = getApplicationContext().getResources().getIdentifier("drawable/" + category.getImage(), null,
                            getApplicationContext().getPackageName());
                    mainPhoto.setImageResource(id);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                Toast.makeText(FoodDetail.this, "Нет соединения", Toast.LENGTH_SHORT).show();
            }
        });

        final DatabaseReference table_food = database.getReference("Food");
        table_food.child(String.valueOf(ID)).addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                Food foodItem = snapshot.getValue(Food.class);
                if(foodItem != null)
                {
                    foodFullName.setText(foodItem.getFull_text());
                    price.setText(foodItem.getPrice() + "грн");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                Toast.makeText(FoodDetail.this, "Нет соединения", Toast.LENGTH_SHORT).show();
            }
        });
    }
}