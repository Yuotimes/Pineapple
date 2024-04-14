package com.example.pineapple.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.pineapple.R;

public class ScenesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scenes);
        View scene_1 = findViewById(R.id.scene_1);
        scene_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScenesActivity.this, Scene_1Activity.class);
                startActivity(intent);
            }
        });
        View scene_2 = findViewById(R.id.scene_2);
        scene_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScenesActivity.this, Scene_2Activity.class);
                startActivity(intent);
            }
        });
        View scene_3 = findViewById(R.id.scene_3);
        scene_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScenesActivity.this, Scene_3Activity.class);
                startActivity(intent);
            }
        });
        View scene_4 = findViewById(R.id.scene_4);
        scene_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScenesActivity.this, Scene_4Activity.class);
                startActivity(intent);
            }
        });
        View scene_5 = findViewById(R.id.scene_5);
        scene_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScenesActivity.this, Scene_5Activity.class);
                startActivity(intent);
            }
        });
        View scene_6 = findViewById(R.id.scene_6);
        scene_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScenesActivity.this, Scene_6Activity.class);
                startActivity(intent);
            }
        });
        Button my_scenes = (Button) findViewById(R.id.my_scenes);
        my_scenes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScenesActivity.this, ShowMyScenesActivity.class);
                startActivity(intent);
            }
        });
    }
}