package com.briup.env.client.Impl;

import com.briup.env.bean.Environment;
import com.briup.env.server.DBStore;
import com.briup.env.until.BackUP;
import com.briup.env.until.Configuration;
import com.briup.env.until.Impl.BackImpl;
import com.briup.env.until.Impl.ConfigurationAware;
import com.briup.env.until.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;


public class DBStoreImpl implements DBStore , ConfigurationAware {
    private String driver ;
    private String url ;
    private String user;
    private String passwd ;
    private String serverBak;
    private int batchSize;
    private Configuration conf;

    @Override
    public void init(Properties p) {
        this.driver=p.getProperty("driver");
        this.url=p.getProperty("url");
        this.user=p.getProperty("user");
        this.passwd=p.getProperty("passwd");
        this.serverBak=p.getProperty("serverBak");
        this.batchSize=Integer.parseInt(p.getProperty("batchSize"));
    }

    @Override
    public void saveDB(Collection<Environment> coll) throws IOException {
        BackUP back=conf.getBackup();
        Logger logger=conf.getLogger();
        Map<Integer, List<Environment>> map = new HashMap<>();
        SimpleDateFormat sd = new SimpleDateFormat("dd");
        Connection conn = null;

        PreparedStatement ps=null;
        logger.info("数据开始按照天归类");
        coll.forEach(x -> {
            long gt = x.getGatherDate().getTime();
                Integer day = Integer.parseInt( sd.format(new Date(gt)));
            if (map.containsKey(day)) {
                List<Environment> list = map.get(day);
                list.add(x);
                map.put(day, list);
            } else {
                List<Environment> list = new ArrayList<>();
                list.add(x);
                map.put(day, list);
            }
        });
        Map<Integer,List<Environment>> b_map=
                (Map<Integer, List<Environment>>) back.load(serverBak,true);
        if (b_map!=null){
           for (Map.Entry<Integer,List<Environment>> en:b_map.entrySet()){
               if (map.containsKey(en.getKey())){
                    List<Environment> nlist=map.get(en.getKey());
                    nlist.addAll(en.getValue());
                    map.put(en.getKey(),nlist);
               }else{
                   map.put(en.getKey(),en.getValue());
               }
           }
        }
//        try {
//            Class.forName(driver);
//            final Connection conn= DriverManager.getConnection(url,user,passwd);
//            map.forEach((key,val)->{
//                String sql="insert into s_sev_"+key+" values(?,?,?,?,?,?,?,?,?,?)";
//                try {
//                    PreparedStatement ps= conn.prepareStatement(sql);
//                    val.forEach(env->{
//                        try {
//                            ps.setString(1,env.getName());
//                        } catch (SQLException throwables) {
//                            throwables.printStackTrace();
//                        }
//                    });
//                } catch (SQLException throwables) {
//                    throwables.printStackTrace();
//                }
//            });
//        } catch (ClassNotFoundException | SQLException e) {
//            e.printStackTrace();
//        }
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, passwd);
            conn.setAutoCommit(false);
            for (Map.Entry<Integer, List<Environment>> en : map.entrySet()) {
                String sql = "insert into s_sev_" + en.getKey() + " values(?,?,?,?,?,?,?,?,?,?)";
                ps = conn.prepareStatement(sql);
                int i = 0;
                for (Environment env : en.getValue()) {
                    ps.setString(1, env.getName());
                    ps.setString(2, env.getSrcID());
                    ps.setString(3, env.getDstID());
                    ps.setString(4, env.getDevID());
                    ps.setString(5, env.getSensorAddress());
                    ps.setInt(6, env.getCounter());
                    ps.setString(7, env.getCmd());
                    ps.setFloat(8, env.getData());
                    ps.setInt(9, env.getStatus());
                    ps.setTimestamp(10, env.getGatherDate());
                    //将当前拼接好的数据放入缓存中
                    ps.addBatch();
                    i++;
                    if (i % batchSize == 0) ps.executeBatch();

                }
                ps.executeBatch();
                //提交数据，数据持久化保存到数据库
                conn.commit();
                map.remove(en.getKey());
                if (ps!=null)ps.close();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            back.store(serverBak,map,false);
            try {
                conn.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } finally {
            try {

                if (conn != null) conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    @Override
    public void setConfiguration(Configuration conf) {
        this.conf=conf;
    }
}
//    create table s_sev_17(
//        name varchar(20),
//    srcID varchar(20),
//    dstID varchar(20),
//    devID varchar(20),
//    sensorAddress varchar(20),
//counter int,
//        cmd varchar(20),
//        data float,
//        status int,
//        gatherDate timestamp
//        );