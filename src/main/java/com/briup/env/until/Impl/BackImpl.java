package com.briup.env.until.Impl;

import com.briup.env.until.BackUP;

import java.io.*;
import java.util.Properties;


public class BackImpl implements BackUP {
    @Override
    public void store(String filePath, Object obj, boolean append)  {
        File f=new File(filePath);
        try { if (!f.exists())f.createNewFile();
            ObjectOutputStream oos=
                    new ObjectOutputStream(new FileOutputStream(f,append));
            oos.writeObject(obj);
            oos.flush();
            if (oos!=null)oos.close();
        }catch (IOException e){
            e.printStackTrace();
        }


    }

    @Override
    public Object load(String filePath, boolean del)  {
        Object obj=null;
        File f=new File(filePath);
        try{  if (!f.exists()){
            ObjectInputStream ois=
                    new ObjectInputStream(new FileInputStream(f));
            obj=ois.readObject();
            if (del)f.delete();
            if (ois!=null)ois.close();
        }} catch (IOException |ClassNotFoundException e){
            e.printStackTrace();
        }
        return obj;
    }

    @Override
    public void init(Properties p) {

    }
}
