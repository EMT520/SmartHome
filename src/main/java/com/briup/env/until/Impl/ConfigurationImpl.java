package com.briup.env.until.Impl;

import com.briup.env.client.Client;
import com.briup.env.client.Gather;
import com.briup.env.server.DBStore;
import com.briup.env.server.Server;
import com.briup.env.until.BackUP;
import com.briup.env.until.Configuration;
import com.briup.env.until.Logger;
import com.briup.env.until.WossModule;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;


    public class ConfigurationImpl implements Configuration {
        private Map<String ,WossModule> map=new HashMap<>();
        public ConfigurationImpl(){
            this("config/env.xml");
        }
        public ConfigurationImpl(String conf_path){
            SAXReader rax=new SAXReader();
            Properties pro=new Properties();
            try {
               Document doc= rax.read(conf_path);
                Element root=doc.getRootElement();

                //遍历二级标签
                for (Element ers:root.elements()){
    //                //获取二级标签的名字
                    String er_name=ers.getName();
    //                //获取二级标签的属性
                    String er_class=ers.attributeValue("class");
    //                //Class.forName(er_class)反射获取JVM中的模板
                    WossModule en= (WossModule) Class.forName(er_class).newInstance();
    //                //遍历三级标签
                    for (Element san:ers.elements()){
                        String san_name=san.getName();
                        String san_val=san.getText().trim();
                        pro.setProperty(san_name,san_val);

                    }

                    //对所有的模块对象初始化
                    en.init(pro);
                    //判断当前类是否是ConfigurationAware接口的实现类，是=》注入配置模块
                    if (en instanceof ConfigurationAware){
                        ((ConfigurationAware) en).setConfiguration(this);
                    }
                    map.put(er_name,en);


                }
                //获取二级标签的名字

                //获取二级标签中的属性
    //        } catch (DocumentException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
    //            e.printStackTrace();
    //        }
            } catch (DocumentException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        @Override
        public Logger getLogger() {
            return (Logger) map.get("logger");
        }

        @Override
        public BackUP getBackup() {
            return (BackUP) map.get("backUp");
        }

        @Override
        public Gather getGather() {
            return (Gather) map.get("gather");
        }

        @Override
        public Client getClient() {
            return (Client) map.get("client");
        }

        @Override
        public Server getServer() {
            return (Server) map.get("server");
        }

        @Override
        public DBStore getDBStore() {
            return (DBStore) map.get("dbStore");
        }

        public static void main(String[] args) {
          ConfigurationImpl con=  new ConfigurationImpl();
          Gather g=con.getGather();
           g.gather().forEach(x-> System.out.println(x));
        }
    }
