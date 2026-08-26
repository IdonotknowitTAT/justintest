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
            tlp[b][0]=deck.get(b);//取第b个数字
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
    public String quanfanyi(int type, int inde){
        String[] Major_Arcana = {//牌库
                "愚者", "魔术师", "女祭司", "皇后", "皇帝", "教皇", "恋人", "战车","力量", "隐者", "命运之轮", "正义", "倒吊人", "死神", "节制", "恶魔",
                "塔", "星星", "月亮", "太阳", "审判", "世界"
        };
        String[] Minor_Arcana_Wands={"权杖一","权杖二","权杖三","权杖四","权杖五","权杖六","权杖七"
                ,"权杖八","权杖九","权杖十","权杖侍从","权杖骑士","权杖皇后","权杖国王"//14个
        };
        String[] Minor_Arcana_Cups={"圣杯一","圣杯二","圣杯三","圣杯四","圣杯五","圣杯六",
                "圣杯七","圣杯八","圣杯九","圣杯十","圣杯侍从","圣杯骑士","圣杯皇后","圣杯国王"
        };
        String[] Minor_Arcana_Swords={"宝剑一","宝剑二","宝剑三","宝剑四","宝剑五","宝剑六"
                ,"宝剑七","宝剑八","宝剑九","宝剑十","宝剑侍从","宝剑骑士","宝剑皇后","宝剑国王"
        };
        String[] Minor_Arcana_Pentacles={"星币一","星币二","星币三","星币四","星币五","星币六"
                ,"星币七","星币八","星币九","星币十","星币侍从","星币骑士","星币皇后","星币国王"
        };
        if(type==0){
            return Major_Arcana[inde];//返回大阿卡纳
        }
        if(type==1){
            return Minor_Arcana_Wands[inde];//返回权杖
        }
        if(type==2){
            return Minor_Arcana_Cups[inde];//返回圣杯
        }
        if(type==3){
            return Minor_Arcana_Swords[inde];//返回宝剑
        }
        if(type==4){
            return Minor_Arcana_Pentacles[inde];//返回星币
        }
        return "error";
    }
    public String quanpai(int a){//用于抽取全部的阿卡纳牌
        if (a>78){
            return null;
        }
        StringBuilder pai=new StringBuilder();
        Random random=new Random();
        List<Integer>deck= new ArrayList<>();// 新建一个数组
        for (int i=0;i<78;i++){//将0~78写入数组中
            deck.add(i);
        }
        Collections.shuffle(deck);//打乱
        for(int i=0;i<a;i++){
            int b=deck.get(i);//取第i个数
            if (b<22){
                pai.append(quanfanyi(0,b));
            }
            else {
                int type=(b-22)/14+1;
                int inde=(b-22)%14;
                pai.append(quanfanyi(type,inde));
            }
            pai.append(",");
            int c=random.nextInt(2);
            if (c==0){
                pai.append("正位");
            }else {
                pai.append("反位");
            }
        }
        return pai.toString();
    }
}

