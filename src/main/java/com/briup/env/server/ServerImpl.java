package com.briup.env.server;

import com.briup.env.bean.Environment;
import com.briup.env.client.Impl.DBStoreImpl;
import com.briup.env.client.Impl.LoggerImpl;
import com.briup.env.until.Configuration;
import com.briup.env.until.Impl.ConfigurationAware;
import com.briup.env.until.Impl.ConfigurationImpl;
import com.briup.env.until.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.Properties;


public class ServerImpl implements Server, ConfigurationAware {
    private boolean flag = true;
    private int serverPort;
    private Configuration conf;

    @Override
    public void init(Properties p) {
        this.serverPort=Integer.parseInt(p.getProperty("serverPort"));
    }
    @Override
    public  Collection<Environment> revicer() {
        Logger logger=conf.getLogger();
        DBStore store=conf.getDBStore();
        ServerSocket server = null;
        try {
            logger.info("服务器开启");
            server = new ServerSocket(serverPort);
            while (flag) {
                Socket socket = server.accept();
                logger.info("连入客户端");
                //new handleEnv(socket).start()
                handleEnv h = new handleEnv();
                h.setLogger(logger);
                h.setStore(store);
                h.setSocket(socket);
                h.start();
//                 new Thread(){
//
//                 }.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void shutdown(

    ) {
        this.flag = false;
    }


    @Override
    public void setConfiguration(Configuration conf) {
        this.conf=conf;
    }

    public static void main(String[] args) {
        Configuration conf=new ConfigurationImpl();
        conf.getServer().revicer();
    }
}

class handleEnv extends Thread {
    private Socket socket;
    private Logger logger;
    private DBStore store;

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public DBStore getStore() {
        return store;
    }

    public void setStore(DBStore store) {
        this.store = store;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public handleEnv(Socket socket,Logger logger,DBStore store) {
        this.socket = socket;
        this.logger=logger;
        this.store=store;
    }

    public handleEnv() {

    }

    @Override
    public void run() {
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(
                    socket.getInputStream());

            Collection coll = (Collection) ois.readObject();
            logger.info("接收数据"+coll.size()+"条");
            //入库操作
            store.saveDB(coll);
            logger.info("数据入库");
            logger.info("数据入库结束");

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {

            try {
                if (ois != null) ois.close();
                if (socket!=null)socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}