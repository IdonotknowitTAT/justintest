package com.example.a333;
import  java.util.Random;
public class liuyaodaima {
    public int dangeguanxiang() {
        // 模拟抛三枚硬币，统计正面（假设1为正面）朝上的个数
        int count = 0;
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            count += random.nextInt(2); // 0或1，累加即为正面个数
        }
        return count+6; // 返回值直接是 0,1,2,3
    }
    public String suangua(int a){//用于翻译挂像的，目前未填写
       String string= switch (a){
            case 0->"老阴";
            case 1 ->"少阳";
            case 2->"少阴";
            case 3->"老阳";
           default->"error";
        };
       return string;
    }
}
