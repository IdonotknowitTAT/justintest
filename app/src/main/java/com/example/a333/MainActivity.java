package com.example.a333;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONException;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.widget.Toast;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;


public class MainActivity extends AppCompatActivity {
    SharedPreferences daliy;//存储临时数据
    protected EditText editText;
    Spinner spinner;
    TextView textView;
    Button button1;//今日运势
    Button button2;//占卜
    Button button3;//保存到手机
    Button button4;//设置
    Button button5;//六爻
    int buttonput;
    private static String last_click = "1";
    private static String last_get = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);//输入框
        spinner = findViewById(R.id.spinner);//牌阵列表
        textView = findViewById(R.id.textView);//输出
        button1 = findViewById(R.id.button1);//按钮是今日运势
        button2 = findViewById(R.id.button2);//按钮是询问问题
        button3 = findViewById(R.id.button3);//按钮是保存到手机
        button4 = findViewById(R.id.button4);//按钮是设置
        button5 = findViewById(R.id.button5);//按钮是六爻起挂
        daliy=getSharedPreferences("DailyDate",MODE_PRIVATE);//临时存储每日运势
        ToDeepseek.init(this);//传输上下文
        button3.setEnabled(false);//按钮3默认关闭

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");//获取时间，格式是年，月，日
                String todaydate = sdf.format(new Date());
                buttonput = 1;
                last_click = daliy.getString("lastclick", "");//从SharedPreferences获取上次的时间
                last_get = daliy.getString("lastget", "");//从SharedPreferences获取上次的回答
                if (last_click.equals(todaydate)) {//如果相同就直接使用上次的结果
                    textView.setTextColor(0xFF0000FF);
                    textView.setText(last_get);
                    return;
                }
                daliy.edit().remove("lastclick").apply();
                daliy.edit().remove("lastget").apply();
                StringBuilder str1 = new StringBuilder("你是一个占卜师，用户抽到");//写提示词
                taluopaijisuan today = new taluopaijisuan();
                String pai = today.fanyi(today.taluoapi1(3)) + "\n";//抽到的牌
                str1.append(pai);
                // str1.append("字数要在300左右");
                str1.append("请从1到5星评价一下今天的运势，该对话是一次性对话，请客观公正的处理");
                textView.setText("✨ 正在生成运势...");
                button1.setEnabled(false);//暂时关闭按钮
                button2.setEnabled(false);
                textView.setText(pai);
                //textView.setMovementMethod(new ScrollingMovementMethod());//滑框
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String ans = pai + ToDeepseek.callDeepSeek(str1.toString());//从todeepseek获取回答
                            daliy.edit().putString("lastclick", todaydate)//将时间戳和回答写入SharedPreferences
                                    .putString("lastget", ans)
                                    .apply();

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    textView.setTextColor(0xFF0000FF);
                                    textView.setText(ans);
                                    last_get = ans;
                                    button1.setEnabled(true);
                                    button2.setEnabled(true);
                                    button3.setEnabled(true);
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                            String errorMsg = "请求失败：" + e.getMessage();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    textView.setText(errorMsg);
                                    button1.setEnabled(true);
                                    button2.setEnabled(true);
                                }
                            });
                        }
                    }
                }).start();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonput = 2;
                String shuru = editText.getText().toString().trim(); //获取用户输入
                if (shuru.isEmpty()) {
                    textView.setText("⚠️ 请输入问题后再占卜");
                    return;  // 不继续执行后续抽牌和网络请求
                }
                String paizhe = spinner.getSelectedItem().toString();//获取牌阵
                String shumu = paizhe.replaceAll("\\D+", "");//获取牌阵中的数字
                int number = taluopaizheng.shumu(Integer.parseInt(shumu));//通过排阵上的数字确定抽几张
                String paizhejieshi = taluopaizheng.paizhengjieshi(Integer.parseInt(shumu));//通过排阵上的数字来获取排阵解释
                taluopaijisuan tlp = new taluopaijisuan();
                String pai = tlp.fanyi(tlp.taluoapi1(number));//通过数字抽取牌
                StringBuilder str2 = new StringBuilder();
                str2.append("你是一个占卜师,用户的问题是");
                str2.append(shuru);
                str2.append("牌阵是：");
                str2.append(paizhe);
                str2.append("解释是：");
                str2.append(paizhejieshi);
                str2.append("用户抽到的牌是：");
                str2.append(pai);
                str2.append("该对话是一次性对话，请客观公正的处理");
                textView.setText("✨ 正在生成运势...");
                button2.setEnabled(false);//暂时关闭按钮
                button1.setEnabled(false);
                textView.setText(pai);
                //textView.setMovementMethod(new ScrollingMovementMethod());//滑框
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String ans = pai + "\n" + ToDeepseek.callDeepSeek(str2.toString());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    textView.setTextColor(0xFF0000FF);
                                    textView.setText(ans);
                                    button2.setEnabled(true);//打开按钮
                                    button1.setEnabled(true);
                                    button3.setEnabled(true);
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                            String errorMsg = "请求失败：" + e.getMessage();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    textView.setText(errorMsg);
                                    button2.setEnabled(true);
                                }
                            });
                        }
                    }
                }).start();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String question=editText.getText().toString().trim();//获取问题
                String safeName = question.replaceAll("[\\\\/:*?\"<>| ]", "_");//替换问题中的非法符号
                String answer=textView.getText().toString().trim();//获取deepseek回答
                StringBuilder neiro=new StringBuilder();//写内容
                if (buttonput==1){
                    neiro.append("问题是").append("今日运势").append("\n\n");
                }
                if (buttonput==2){
                    neiro.append("问题是").append(question).append("\n\n");
                }
                neiro.append("回答是").append(answer).append("\n\n");
                neiro.append("导出时间是").append(new SimpleDateFormat("yyyyMMdd").format(new Date()));
                File dir=getExternalFilesDir(null);//
                if (dir == null) {
                    dir=getFilesDir();
                }
                String filename=question+ System.currentTimeMillis() + ".txt";
                File file=new File(dir,filename);
                try (FileOutputStream fos=new FileOutputStream(file)){
                    fos.write(neiro.toString().getBytes(StandardCharsets.UTF_8));
                    Toast.makeText(MainActivity.this,"已保存到："+file.getAbsolutePath(),Toast.LENGTH_LONG).show();
                }catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "保存失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                button3.setEnabled(false);
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        button5.setOnClickListener(new button5());






    }






}