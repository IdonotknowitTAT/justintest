package com.example.a333;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    Button buttonreturn;
    Button buttonkeep;
    SharedPreferences apidata;
    EditText deepseekapi;
    Switch switchshowreasoning;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        buttonreturn = findViewById(R.id.button5);
        buttonkeep=findViewById(R.id.buttonkeepall);
        deepseekapi=findViewById(R.id.editdeepseekapi);
        switchshowreasoning=findViewById(R.id.switch_showreasoning);
        apidata=getSharedPreferences("DailyDate",MODE_PRIVATE);
        switchshowreasoning.setChecked(apidata.getBoolean("showreasoning",false));
        buttonreturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        buttonkeep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String deepseek=deepseekapi.getText().toString().trim();
                if(deepseek.isEmpty()){
                    deepseekapi.setText("请输入api");
                    return;
                }
                apidata.edit().putString("deepseekapi",deepseek)
                        .putBoolean("showreasoning",switchshowreasoning.isChecked())
                        .apply();
            }
        });
    }
}
