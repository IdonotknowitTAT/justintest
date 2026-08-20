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
    Switch switchliuyaousedeepseekapi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        buttonreturn = findViewById(R.id.button5);
        buttonkeep=findViewById(R.id.buttonkeepall);
        deepseekapi=findViewById(R.id.editdeepseekapi);
        switchshowreasoning=findViewById(R.id.switch_showreasoning);
        switchliuyaousedeepseekapi=findViewById(R.id.switch_liuyaousedeepseekapi);
        apidata=getSharedPreferences("DailyDate",MODE_PRIVATE);
        deepseekapi.setText(apidata.getString("deepseekapi",""));
        switchshowreasoning.setChecked(apidata.getBoolean("showreasoning",false));
        switchliuyaousedeepseekapi.setChecked(apidata.getBoolean("useaitoknow",false));
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
                SharedPreferences.Editor editor=apidata.edit();
                if(!deepseek.isEmpty()){
                    editor.putString("deepseekapi",deepseek);
                }
                editor.putBoolean("showreasoning",switchshowreasoning.isChecked())
                        .putBoolean("useaitoknow",switchliuyaousedeepseekapi.isChecked())
                        .apply();
            }
        });
    }
}
