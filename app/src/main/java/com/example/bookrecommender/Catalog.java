package com.example.bookrecommender;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class Catalog extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();// Убрать ActionBar
        super.onCreate(savedInstanceState);
        setContentView(R.layout.catalog );

        //Рвзвернуть игру на весь экран - нач
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        //Рвзвернуть игру на весь экран - кон

    }
    //Системная кнопка "Назад" - начало
    @Override
    public void onBackPressed(){
        try{
            Intent intent = new Intent (com.example.bookrecommender.Catalog.this, MainActivity.class);
            startActivity(intent); finish();
        }catch (Exception e){

        }
    }
    //Системная кнопка "Назад" - конец

}
