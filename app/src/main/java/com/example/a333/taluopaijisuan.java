package com.example.a333;

import java.util.ArrayList;
import  java.util.Random;
import java.util.Collections;
import java.util.List;

public class taluopaijisuan {
    public int[][] taluoapi1(int a){//不会生成重复的数字的函数
        if (a>=22){
            return null;
        }
        int[][] tlp=new int [a][2];//新建一个数组当牌阵，
        List<Integer>deck= new ArrayList<>();// 新建一个数组
        for (int i=0;i<22;i++){//将0~21写入数组中
            deck.add(i);
        }
        Collections.shuffle(deck);//随机排列
        Random random=new Random();
        for (int b=0;b<a;b++){//在写入相应的长度中
            tlp[b][0]=deck.get(b);
        }
        for (int b=0;b<a;b++){
            tlp[b][1]=random.nextInt(2);//生成牌的正反
        }
        return tlp;
    }
    public int [] []taluoapi2(int a){//会生成重复的数字的函数
        if (a>=22){
            return null;
        }
        int[] []tlp=new int[a][2];
        Random random =new Random();
        for(int i=0;i<a;i++){
            tlp[i][0]=random.nextInt(22);//随机生成数字，会产生重复
        }
        for (int b=0;b<a;b++){
            tlp[b][1]=random.nextInt(2);//生成牌的正反
        }
        return tlp;
    }
    public String fanyi(int [] [] a){
        if (a==null) {
            return ("error");
        }
        String[] names = {//牌库
                "愚者", "魔术师", "女祭司", "皇后", "皇帝", "教皇", "恋人", "战车",
                "力量", "隐者", "命运之轮", "正义", "倒吊人", "死神", "节制", "恶魔",
                "塔", "星星", "月亮", "太阳", "审判", "世界"
        };
        String[] zhengfan = {//正反
                "正位","倒位"
        };
        StringBuilder sb=new StringBuilder();
        for (int i=0;i<a.length;i++){
            sb.append(names[a[i][0]]);//输入牌
            sb.append(" ");
            sb.append(zhengfan[a[i][1]]);//输入正反
            sb.append(",");
        }
        return sb.toString();//
    }
}

