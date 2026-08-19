package com.example.a333;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class button5 implements View.OnClickListener {
    @Override
    public void onClick(View view) {
        int[]a=new int[6];
        liuyaodaima liuyaodaima = new liuyaodaima();
        EditText editText = view.getRootView().findViewById(R.id.editText);//从根处获取输入框
        TextView textView = view.getRootView().findViewById(R.id.textView);//获取输出框
        String shuru = editText.getText().toString();//从输入框内获取文字
        StringBuilder shuruci = new StringBuilder();
        shuruci.append("你是解读六爻的人，用户的问题是");
        shuruci.append(shuru);
        shuruci.append("用户的挂像是");
        shuruci.append("\n");
        for (int i = 0; i <= 5; i++) {
            a[i]=liuyaodaima.dangeguanxiang();
        }
        shuruci.append(GuaGenerator.getGuaResult(a));
        shuruci.append("\n");
        shuruci.append("由于deepseek在识别六爻卦象会消耗大量token");
        shuruci.append("请自行复制去询问deepseek");
        shuruci.append("提示词也已经给予");
        textView.setText(shuruci.toString());
        /*for(int c=0;c<=5;c++){
            shuruci.append(liuyaodaima.suangua(liuyaodaima.dangeguanxiang()));
        }*/
       /* textView.setText(GuaGenerator.getGuaResult(a));//先显示等待文案
        new Thread(new Runnable() {//网络请求放到子线程，避免阻塞主线程
            @Override
            public void run() {
                try {
                    String shuchu = ToDeepseek.callDeepSeek(shuruci.toString());//请求deepseek，返回解读结果
                    textView.post(new Runnable() {//post保证切回主线程更新界面
                        @Override
                        public void run() {
                            textView.setText(shuchu);//显示解读结果
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    textView.post(new Runnable() {//post切回主线程提示错误
                        @Override
                        public void run() {
                            textView.setText("请求失败：" + e.getMessage());//显示错误信息
                        }
                    });
                }
            }
        }).start();*/
    }
}