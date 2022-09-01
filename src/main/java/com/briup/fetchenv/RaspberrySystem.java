package com.briup.fetchenv;

/**
 * @JiangXunqi
 * @date 2022/6/19 21:08
 */

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Random;


public class RaspberrySystem {
    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket(10011);
        while (true) {
            Socket accept = socket.accept();
            new AcceptData(accept).start();

        }
    }
}
class AcceptData extends Thread {
    private Socket socket;
    public AcceptData(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader br = null;
        BufferedWriter bw = null;
        try {
            br = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            //二氧化碳浓度
            String co2 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" +
                    "<Message>\r\n" +
                    "        <SrcID>100</SrcID>\r\n" +
                    "        <DstID>101</DstID>\r\n" +
                    "        <DevID>2</DevID>\r\n" +
                    "        <SensorAddress>1280</SensorAddress>\r\n" +
                    "        <Counter>1</Counter>\r\n" +
                    "        <Cmd>3</Cmd>\r\n" +
                    "        <Data>&</Data>\r\n"+
                    "        <Status>1</Status>\r\n" +
                    "</Message>\r\n";
            //PM2.5数据
            String pm25 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" +
                    "<Message>\r\n" +
                    "        <SrcID>100</SrcID>\r\n" +
                    "        <DstID>101</DstID>\r\n" +
                    "        <DevID>4</DevID>\r\n" +
                    "        <SensorAddress>1792</SensorAddress>\r\n" +
                    "        <Counter>1</Counter>\r\n" +
                    "        <Cmd>3</Cmd>\r\n" +
                    "        <Data>&</Data>\r\n"+
                    "        <Status>1</Status>\r\n" +
                    "</Message>\r\n";
            //光敏，光照强度
            String lux = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" +
                    "<Message>\r\n" +
                    "        <SrcID>100</SrcID>\r\n" +
                    "        <DstID>101</DstID>\r\n" +
                    "        <DevID>2</DevID>\r\n" +
                    "        <SensorAddress>256</SensorAddress>\r\n" +
                    "        <Counter>1</Counter>\r\n" +
                    "        <Cmd>3</Cmd>\r\n" +
                    "        <Data>&</Data>\r\n"+
                    "        <Status>1</Status>\r\n" +
                    "</Message>\r\n";
            //甲烷数据
            String ch4 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" +
                    "<Message>\r\n" +
                    "        <SrcID>100</SrcID>\r\n" +
                    "        <DstID>101</DstID>\r\n" +
                    "        <DevID>3</DevID>\r\n" +
                    "        <SensorAddress>1024</SensorAddress>\r\n" +
                    "        <Counter>1</Counter>\r\n" +
                    "        <Cmd>3</Cmd>\r\n" +
                    "        <Data>&</Data>\r\n"+
                    "        <Status>1</Status>\r\n" +
                    "</Message>\r\n";
            //温湿度
            String humi = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" +
                    "<Message>\r\n" +
                    "        <SrcID>100</SrcID>\r\n" +
                    "        <DstID>101</DstID>\r\n" +
                    "        <DevID>2</DevID>\r\n" +
                    "        <SensorAddress>16</SensorAddress>\r\n" +
                    "        <Counter>1</Counter>\r\n" +
                    "        <Cmd>3</Cmd>\r\n" +
                    "        <Data>&</Data>\r\n"+
                    "        <Status>1</Status>\r\n" +
                    "</Message>\r\n";

            //火光
            String fire = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" +
                    "<Message>\r\n" +
                    "        <SrcID>100</SrcID>\r\n" +
                    "        <DstID>101</DstID>\r\n" +
                    "        <DevID>3</DevID>\r\n" +
                    "        <SensorAddress>9</SensorAddress>\r\n" +
                    "        <Counter>1</Counter>\r\n" +
                    "        <Cmd>3</Cmd>\r\n" +
                    "        <Data>&</Data>\r\n"+
                    "        <Status>1</Status>\r\n" +
                    "</Message>\r\n";

            //烟雾
            String smog = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" +
                    "<Message>\r\n" +
                    "        <SrcID>100</SrcID>\r\n" +
                    "        <DstID>101</DstID>\r\n" +
                    "        <DevID>3</DevID>\r\n" +
                    "        <SensorAddress>10</SensorAddress>\r\n" +
                    "        <Counter>1</Counter>\r\n" +
                    "        <Cmd>3</Cmd>\r\n" +
                    "        <Data>&</Data>\r\n"+
                    "        <Status>1</Status>\r\n" +
                    "</Message>\r\n";

            //紫外线数据
            String ultRay = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" +
                    "<Message>\r\n" +
                    "        <SrcID>100</SrcID>\r\n" +
                    "        <DstID>101</DstID>\r\n" +
                    "        <DevID>2</DevID>\r\n" +
                    "        <SensorAddress>512</SensorAddress>\r\n" +
                    "        <Counter>1</Counter>\r\n" +
                    "        <Cmd>3</Cmd>\r\n" +
                    "        <Data>&</Data>\r\n"+
                    "        <Status>1</Status>\r\n" +
                    "</Message>\r\n";
/*


 */

            StringBuffer stringBuffer = new StringBuffer();
            String str="";
            while ((str=br.readLine())!=null){
                stringBuffer.append(str.trim()).append("\r\n");
                if ("</Message>".equals(str.trim()))break;
            }
            Document document = DocumentHelper.parseText(stringBuffer.toString());
            stringBuffer.setLength(0);
            Element root=document.getRootElement();
            List<Element> first=root.elements();
            for (Element element : first) {
                if ("SensorAddr ess".equals(element.getName())){
                    Random random = new Random();
                    bw=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    switch (element.getTextTrim()){
                        case "1280"://二氧化碳
                            int i = Integer.parseInt(String.valueOf(random.nextInt(6000) + 350), 16);
                            bw.write(co2.split("&")[0]+i+co2.split("&")[1]);
                            bw.flush();
                            break;
                        case "1279"://PM2.5
                            int i1 = Integer.parseInt(String.valueOf(random.nextInt(270)+10), 16);
                            bw.write(pm25.split("&")[0]+i1+pm25.split("&")[1]);
                            bw.flush();
                            break;
                        case "256"://光敏
                            int i2 = Integer.parseInt(String.valueOf(random.nextInt(100000)), 16);
                            bw.write(lux.split("&")[0]+i2+lux.split("&")[1]);
                            bw.flush();
                            break;
                        case "1024"://甲烷
                            int i3 = Integer.parseInt(String.valueOf(random.nextInt(1300)+774), 16);
                            bw.write(ch4.split("&")[0]+i3+ch4.split("&")[1]);
                            bw.flush();
                            break;
                        case "16"://温湿度
                            int i4 = Integer.parseInt(String.valueOf((int)( (random.nextInt(30)+46.85)/0.00268127/100)), 16);
                            String i5=Integer.toHexString((int) ((random.nextInt(50)+6)/0.00190735/100));
                            String values=toHex(String.valueOf(i4),4)+toHex(String.valueOf(i5),4);
                            bw.write(humi.split("&")[0]+values+humi.split("&")[1]);
                            bw.flush();
                            break;
                        case "9"://火光
                            bw.write(fire);
                            bw.flush();
                            break;
                        case "10"://烟雾数据
                            bw.write(smog);
                            bw.flush();
                            break;
                        case "512"://紫外线
                            bw.write(ultRay);
                            bw.flush();
                            break;
                        default:
                            bw.write("");
                            bw.flush();
                    }
                }
            }


        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }finally {
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
        if(num.length()<size){
            num="0"+num;
            num=toHex(num,size);
            return num;
        }else {
            return num;
        }
    }
}
