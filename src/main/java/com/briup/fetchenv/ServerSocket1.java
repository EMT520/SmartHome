package com.briup.fetchenv;


import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Random;


public class ServerSocket1 {
    public static void main(String[] args) throws IOException {
        ServerSocket server=new ServerSocket(10001);
            while (true){
                //没连接客户端的时候处于阻塞状态
                Socket socket=server.accept();
                //解决多个客户端连接的问题，每连接一个客户端就开启一个线程
                new AcceptData1(socket).start();
            }
        }
    }

//继承线程
//往类里面传输数据有两种方式：1.new对象之后直接调用方法  2.new对象之后基于构造器（这里用的是构造器）
class AcceptData1 extends Thread{
    private Socket socket;
    public AcceptData1(Socket socket){
        this.socket=socket;
    }

    //重写run方法
    @Override
    public void run() {
        BufferedReader br=null;
        BufferedWriter bw=null;
        try {
            //BufferReader读取字符流
            //InputStreamReader将读取到的字节流转换为字符流
            br = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            Random random=new Random();
            String Co2=toHex(Integer.toHexString( random.nextInt(6000)+350),4) ;
            String Guangzhao=toHex(Integer.toHexString( random.nextInt(500)+100),4) ;
            int temperature=new Double(Math.ceil(random.nextInt(10)+30+46.85)/0.00268127).intValue();
            int humidity=new Double(Math.ceil(random.nextInt(10)+80)/0.00190735 +6).intValue();
            String Temperature=toHex(Integer.toHexString(temperature),4);
            String Humidity=toHex(Integer.toHexString(humidity),4);

            //温度和湿度
            String th = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" +
                    "<Message>\r\n" +
                    "        <SrcID>100</SrcID>\r\n" +
                    "        <DstID>101</DstID>\r\n" +
                    "        <DevID>2</DevID>\r\n" +
                    "        <SensorAddress>16</SensorAddress>\r\n" +
                    "        <Counter>0</Counter>\r\n" +
                    "        <Cmd>3</Cmd>\r\n" +
                    "        <Data>"+Temperature+Humidity+"02</Data>\r\n"+
                    "        <Status>1</Status>\r\n" +
                    "</Message>\r\n";
            //二氧化碳浓度
            String co2 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" +
                    "<Message>\r\n" +
                    "        <SrcID>100</SrcID>\r\n" +
                    "        <DstID>101</DstID>\r\n" +
                    "        <DevID>2</DevID>\r\n" +
                    "        <SensorAddress>1280</SensorAddress>\r\n" +
                    "        <Counter>0</Counter>\r\n" +
                    "        <Cmd>3</Cmd>\r\n" +
                    "        <Data>"+Co2+"03</Data>\r\n"+
                    "        <Status>1</Status>\r\n" +
                    "</Message>\r\n";
            //光照强度
            String lux = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" +
                    "<Message>\r\n" +
                    "        <SrcID>100</SrcID>\r\n" +
                    "        <DstID>101</DstID>\r\n" +
                    "        <DevID>2</DevID>\r\n" +
                    "        <SensorAddress>256</SensorAddress>\r\n" +
                    "        <Counter>0</Counter>\r\n" +
                    "        <Cmd>3</Cmd>\r\n" +
                    "        <Data>"+Guangzhao+"01</Data>\r\n"+
                    "        <Status>1</Status>\r\n" +
                    "</Message>\r\n";
            //PM2.5数据
//            String pm25 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" +
//                    "<Message>\r\n" +
//                    "        <SrcID>100</SrcID>\r\n" +
//                    "        <DstID>101</DstID>\r\n" +
//                    "        <DevID>4</DevID>\r\n" +
//                    "        <SensorAddress>1792</SensorAddress>\r\n" +
//                    "        <Counter>1</Counter>\r\n" +
//                    "        <Cmd>3</Cmd>\r\n" +
//                    "        <Data>038e01</Data>\r\n"+
//                    "        <Status>1</Status>\r\n" +
//                    "</Message>\r\n";
            //光敏，光照强度

            //甲烷数据
            //火光
//            String fire = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" +
//                    "<Message>\r\n" +
//                    "        <SrcID>100</SrcID>\r\n" +
//                    "        <DstID>101</DstID>\r\n" +
//                    "        <DevID>3</DevID>\r\n" +
//                    "        <SensorAddress>9</SensorAddress>\r\n" +
//                    "        <Counter>1</Counter>\r\n" +
//                    "        <Cmd>3</Cmd>\r\n" +
//                    "        <Data>&</Data>\r\n"+
//                    "        <Status>1</Status>\r\n" +
//                    "</Message>\r\n";

            //烟雾
//            String smog = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" +
//                    "<Message>\r\n" +
//                    "        <SrcID>100</SrcID>\r\n" +
//                    "        <DstID>101</DstID>\r\n" +
//                    "        <DevID>3</DevID>\r\n" +
//                    "        <SensorAddress>10</SensorAddress>\r\n" +
//                    "        <Counter>1</Counter>\r\n" +
//                    "        <Cmd>3</Cmd>\r\n" +
//                    "        <Data>&</Data>\r\n"+
//                    "        <Status>1</Status>\r\n" +
//                    "</Message>\r\n";

            //紫外线数据
//            String ultRay = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" +
//                    "<Message>\r\n" +
//                    "        <SrcID>100</SrcID>\r\n" +
//                    "        <DstID>101</DstID>\r\n" +
//                    "        <DevID>2</DevID>\r\n" +
//                    "        <SensorAddress>512</SensorAddress>\r\n" +
//                    "        <Counter>1</Counter>\r\n" +
//                    "        <Cmd>3</Cmd>\r\n" +
//                    "        <Data>&</Data>\r\n"+
//                    "        <Status>1</Status>\r\n" +
//                    "</Message>\r\n";
            String content=null;
            String str="";
            //读取网络流，永远不为空，死循环
            while ((str=br.readLine())!=null){
                content+=str;
                if (str.trim().equals("</Message>")){
                    break;
                }
            }
            System.out.println(content);
            //剔除前四个字符（null）
            content=content.substring(4);
            //将content转化为document
            Document document= DocumentHelper.parseText(content);
            //获取根标签
            Element root=document.getRootElement();
            //获取二级标签
            List<Element> first=root.elements();
            for (Element er:first){
                if ("SensorAddress".equals(er.getName())){
                    if ("16".equals(er.getText())){
                        bw=new BufferedWriter(
                                new OutputStreamWriter(socket.getOutputStream()));
                        //温度和湿度 SensorAddress 16
                        bw.write(th);
                        bw.flush();
                    }
                    if ("256".equals(er.getText())){
                        bw=new BufferedWriter(
                                new OutputStreamWriter(socket.getOutputStream()));
                        //光照强度 256
                        bw.write(lux);
                        bw.flush();
                    }
                    if ("1280".equals(er.getText())){
                        bw=new BufferedWriter(
                                new OutputStreamWriter(socket.getOutputStream()));
                        //二氧化碳浓度 1280
                        bw.write(co2);
                        bw.flush();
                    }

                }
            }
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
        //在finally关闭资源
        finally {
            try {
                if (bw!=null) bw.close();
                if (br!=null) br.close();
                if (socket!=null) socket.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private String toHex(String num,int size){
        if (num.length()<size){
            num="0"+num;
            num= toHex(num,size);
            return num;
        }else {
            return num;
        }
    }
}