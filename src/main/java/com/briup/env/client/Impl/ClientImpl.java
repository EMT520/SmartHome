package com.briup.env.client.Impl;

import com.briup.env.bean.Environment;
import com.briup.env.client.Client;
import com.briup.env.client.Gather;
import com.briup.env.until.BackUP;
import com.briup.env.until.Configuration;
import com.briup.env.until.Impl.BackImpl;
import com.briup.env.until.Impl.ConfigurationAware;
import com.briup.env.until.Impl.ConfigurationImpl;
import com.briup.env.until.Logger;
import com.sun.corba.se.impl.orbutil.graph.GraphImpl;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Collection;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;


public class ClientImpl implements Client, ConfigurationAware {
     private String clientIp;
     private int clientPort;
     private String clientBak;
    private Configuration conf;

    @Override
    public void init(Properties p) {
        this.clientIp=p.getProperty("clientIp");
        this.clientPort=Integer.parseInt(p.getProperty("clientPort"));
        this.clientBak=p.getProperty("clientBak");
    }

    @Override
    public void send(Collection<Environment> c) throws IOException {
        Logger logger=conf.getLogger();
        Socket socket = null;
        ObjectOutputStream oos = null;
        BackUP back=conf.getBackup();
        try {
            logger.info("开始构建客户端");
            socket = new Socket(clientIp, clientPort);
            logger.info("构建客户端完成");
            back.load("config/client.bak",true);
            oos = new ObjectOutputStream(socket.getOutputStream());
            Collection<Environment> b_c= (Collection<Environment>)
                    back.load(clientBak,true);
            if (b_c!=null)c.addAll(b_c);
            oos.writeObject(c);
            oos.flush();
            logger.info("客户端发送数据:"+c.size()+"成功");
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage(),e);
            //备份
            back.store(clientBak,c,false);
        } finally {
            try {
                if (oos != null) oos.close();
                if (socket!=null)socket.close();
                logger.info("关闭客户端");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    @Override
    public void setConfiguration(Configuration conf) {
        this.conf=conf;
    }
    public static void main(String[] args) {
        Timer t =new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                Configuration conf=new ConfigurationImpl();
                try {
                conf.getClient().send(
                        conf.getGather().gather()
                );
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        },1000,3600000);
    }
}
