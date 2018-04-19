package com.example.xiangwan.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void GameActivity(View view){
        Intent intent = new Intent(this,GameActivity.class);
        startActivity(intent);
    }
    public void GameProgressActivity(View view){
        Intent intent = new Intent(this, GameProgressActivity.class);
        startActivity(intent);
    }
    public void SettingActivity(View view){
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
    }
    public void LoginActivity(View view){

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

}
