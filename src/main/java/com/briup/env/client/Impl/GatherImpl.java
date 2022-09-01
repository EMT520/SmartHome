package com.briup.env.client.Impl;

import com.briup.env.bean.Environment;
import com.briup.env.client.Gather;
import com.briup.env.until.Configuration;
import com.briup.env.until.Impl.ConfigurationAware;
import com.briup.env.until.Logger;

import java.io.*;
import java.sql.Timestamp;
import java.util.*;


public class GatherImpl implements Gather, ConfigurationAware{
    private String dataSource;
    private String pointPath;

    private Configuration conf;

    @Override
    public void init(Properties p) {
        this.dataSource=p.getProperty("dataSoure");
        this.pointPath=p.getProperty("pointPath");
    }
    @Override
    public Collection<Environment> gather() {
        Logger logger=conf.getLogger();
        List<Environment> list=new ArrayList<Environment>();
        RandomAccessFile ra=null;

        try {
            logger.debug("构建读取采集文件的流对象:"+System.currentTimeMillis());
            ra= new RandomAccessFile(dataSource,"rw");
            logger.debug("构建读取采集文件的流对象介绍:"+System.currentTimeMillis());
            String str="";
            //湿度温度
            int count1=0;
            //光照强度
            int count2=0;
            //二氧化碳
            int count3=0;
            //获取上一次读取的最后一次的位置
            long point= loadPoint(pointPath);
            logger.info("文件读取位置："+point);
            ra.seek(point);
            while ((str=ra.readLine())!=null){
                String[] strs=str.split("[|]");
                Environment env=new Environment(strs[0],strs[1],strs[2],strs[3],Integer.parseInt(strs[4]) ,strs[5],Integer.parseInt(strs[7])
                ,new Timestamp(Long.parseLong(strs[8])));
                if ("16".equals(strs[3])){
                    //温度和湿度
                  float temp= (float)((Integer.parseInt(strs[6].substring(0,4),
                          16)*0.00268127)-46.85);
                  float sh= (float)((Integer.parseInt(strs[6].substring(4,8),
                          16)*0.00190735)-6);
                  Environment tmpenv=new Environment(strs[0],strs[1],strs[2],strs[3]
                          ,Integer.parseInt(strs[4]) ,strs[5],Integer.parseInt(strs[7])
                          ,new Timestamp(Long.parseLong(strs[8])));
                  tmpenv.setName("温度");
                  tmpenv.setData(temp);
                  list.add(tmpenv);
                  list.add(env);

                  env.setName("湿度");
                  env.setData(sh);
                  list.add(env);
                  count1++;
                }else {
                    float v=Integer.valueOf(strs[6].substring(0,4),16);
                    if ("256".equals(strs[3])){

                        env.setName("光照强度");
                        count2++;

                    }else if ("1280".equals(strs[3])){

                        env.setName("二氧化碳");
                        count3++;

                    }
                    env.setData(v);
                    list.add(env);

                }
            }
            logger.info("文件读取介绍");
            logger.info("温度湿度"+count1);
            logger.info("光照强度"+count2);
            logger.info("二氧化碳"+count3);
            point=ra.getFilePointer();
            //保存本次读取的最后位置
            savePoint(point,pointPath);
            logger.info("本次读取数据结束位置"+point);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            logger.error(e.getMessage(),e);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage(),e);
        }finally {
                try {
                    if (ra!=null)ra.close();
                    logger.info("close RandomAccessFile");
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return list;
    }



    private void savePoint(long point,String pointPath) throws IOException {
        File file=new File(pointPath);
        if (!file.exists())file.createNewFile();
        DataOutputStream dos=new DataOutputStream(new FileOutputStream(file));
        dos.writeLong(point);
        dos.flush();
        if (dos!=null)dos.close();
    }

    private long loadPoint(String pointPath) throws IOException {
        File file=new File(pointPath);
        long point=0L;
        if (file.exists()){
            DataInputStream dis=new DataInputStream(new FileInputStream(file));
            point=dis.readLong();
        }
        return point;
    }
    public static void main(String[] args) {
        new GatherImpl().gather().forEach(x-> System.out.println(x));
    }

    @Override
    public void setConfiguration(Configuration conf) {
        this.conf=conf;
    }
}
