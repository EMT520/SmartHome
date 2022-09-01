package com.briup.fetchenv;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/*
测试
 */
public class t {
    public static void main(String[] args) {
        long m=1516323603959L;
//        String str="2022-05-01";
        SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
        SimpleDateFormat sd1=new SimpleDateFormat("dd");
//        try {
////            Date d=sd.parse(str);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        String date=sd1.
        Random random=new Random();
        double temperature=Math.ceil(random.nextInt(10)+30)/0.00268127+46.85;
        int i=new Double(temperature).intValue();
        int b=new Double(Math.ceil(random.nextInt(10)+30)/0.00268127+46.85).intValue();
        double humidity=(random.nextInt(10)+70)/0.00190735+6;
        String data=sd.format(new Date(m));
        System.out.println(i);
        System.out.println(humidity);
        System.out.println(b);

        String Guangzhao=toHex(Integer.toHexString( random.nextInt(500)+100),4) ;

        String Temp=toHex(Integer.toHexString(b),4) ;



        System.out.println(Temp);
        float sh= (float)((Integer.parseInt(Temp,
                16)*0.00190735)-6);
        System.out.println(sh);
//        Random r =new Random();
//        int n=r.nextInt(6000)+350;
//        String er=Integer.toHexString(n);
//
//        System.out.println(toHex(er,4));
//
    }
    public static String toHex(String num,int size){
        //16进制有4位，如果产生的数据小于4位就在前面补0，递归调用直到补全
        if (num.length()<size){
            num="0"+num;
              num= toHex(num,size);
              return num;
        }else {
            return num;
        }
    }
}
/*

 */