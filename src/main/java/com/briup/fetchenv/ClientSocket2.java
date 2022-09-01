package com.briup.fetchenv;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

/**
二氧化碳浓度
 */
public class ClientSocket2 {
    public static void main(String[] args) {
        Timer timer=new Timer();
        timer.schedule(new MyTask2(),1000,1000);
    }
}
class MyTask2 extends TimerTask {

    @Override
    public void run() {
        Socket socket=null;
        BufferedWriter bw=null;
        BufferedReader br=null;
        BufferedWriter bw2=null;
        try {
            String str = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" +
                    "<Message>\r\n" +
                    "        <SrcID>100</SrcID>\r\n" +
                    "        <DstID>101</DstID>\r\n" +
                    "        <DevID>2</DevID>\r\n" +
                    "        <SensorAddress>1280</SensorAddress>\r\n" +
                    "        <Counter>1</Counter>\r\n" +
                    "        <Cmd>3</Cmd>\r\n" +
                    "        <Status>1</Status>\r\n" +
                    "</Message>\r\n";
            socket =new Socket("localhost",10001);
            OutputStream os=socket.getOutputStream();
            bw=new BufferedWriter(
                    new OutputStreamWriter(os));
            bw.write(str);
            bw.flush();

            InputStream is=socket.getInputStream();
            br=new BufferedReader(
                    new InputStreamReader(is));
            String s=null;
            String content=null;
            while ((s= br.readLine())!=null){
                content+=s;
                if (s.equals("</Message>")){
                    break;
                }
            }
            System.out.println("*******"+content);
            content=content.substring(4);
            Document document= DocumentHelper.parseText(content);
            Element root=document.getRootElement();
            String s2="";
            for (Iterator i = root.elementIterator(); i.hasNext();){
                Element jd=(Element) i.next();
                String name=jd.getName();
                String value="";
                if (name.equals("SensorAddress")){
                    value="1280";
                }else {
                    value=jd.getText();
                }
                if (name.equals("Counter")){
                    value="1";
                }
                s2+=value+"|";
            }
            Long time=System.currentTimeMillis();
            s2=s2+time;
            System.out.println(s2);
            bw2=new BufferedWriter(new FileWriter("data/radwtmp2",true));
            bw2.write(s2+"\r\n");
            bw.flush();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }finally {
            try {
                if (bw2!=null)bw2.close();
                if (br!=null)br.close();
                if (bw!=null)bw.close();
                if (socket!=null)socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
