package com.example.dostavkaedu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.dostavkaedu.Models.Category;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class FoodListAdapter extends ArrayAdapter<Category>
{
    private LayoutInflater layoutInflater;
    private List<Category> categories;

    private ArrayList<String> allKeys;
    private int layaoutListRow;
    private Context context;

    public FoodListAdapter(@NonNull Context context, int resource, @NonNull List<Category> objects, ArrayList<String> allKeys)
    {
        super(context, resource, objects); // контекст на какой странице создаем
        // потом id для формирования списка // и список объектов для списка
        categories = objects;
        layaoutListRow = resource; // храним айди
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        this.allKeys = allKeys;
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        convertView = layoutInflater.inflate(layaoutListRow, null);
        Category category = categories.get(position);
        if (category != null)
        {
            TextView foodName = convertView.findViewById(R.id.foodMainName);
            ImageView photo = convertView.findViewById(R.id.mainPhoto);
            if (foodName != null)
                foodName.setText(category.getName());
            if (photo != null)
            {
                int id = getContext().getResources().getIdentifier("drawable/" + category.getImage(), null,
                        getContext().getPackageName());
                photo.setImageResource(id);

                photo.setOnClickListener(new View.OnClickListener() // срабатывает при нажатии на фото
                {
                    @Override
                    public void onClick(View view)
                    {
                        context.startActivity(new Intent(context, FoodDetail.class)); // надо принимать контекст что бы использовать старт активити
                        FoodDetail.ID = Integer.parseInt(allKeys.get(position)); // устанавливаем айди того элемента на который нажали, +1 потому что в таблице с 1 начинается, а не с 0
                        System.out.println(FoodDetail.ID);
                        // Toast.makeText(getContext(), foodName.getText().toString(), Toast.LENGTH_LONG).show();
                    }
                });


                FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference table = database.getReference("Category");
                final DatabaseReference table_food = database.getReference("Food");
                photo.setOnLongClickListener(new View.OnLongClickListener()
                {
                    @Override
                    public boolean onLongClick(View view)
                    {
                        FoodDetail.ID = Integer.parseInt(allKeys.get(position));
                        table.child(String.valueOf(FoodDetail.ID)).removeValue();
                        table_food.child(String.valueOf(FoodDetail.ID)).removeValue();
                        Toast.makeText(getContext(), "Успешно удален", Toast.LENGTH_LONG).show();
                        return false;
                    }
                });

            }
        }
        return convertView;
    }

}
