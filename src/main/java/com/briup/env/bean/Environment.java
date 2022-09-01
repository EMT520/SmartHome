package com.briup.env.bean;

import java.io.Serializable;
import java.sql.Timestamp;

/*
实时采集模块数据的采集，每一行数据封装成一个Environment
1.读取数据的时候，前一次采集的数据不处理
 */
//环境指标
    /*
BEGIN
FOR i IN 1..31 LOOP
EXECUTE IMMEDIATE
create table s_sev_17(
name varchar(20),
srcID varchar(20),
dstID varchar(20),
devID varchar(20),
sensorAddress varchar(20),
counter int,
cmd varchar(20),
data float,
status int,
gatherDate timestamp
);
END LOOP;
END;
/

     */
public class Environment implements Serializable {
    private String name;//环境指标的名字
    private String srcID;//采集器编号
    private String dstID;//树莓派系统的编号
    private String devID;//板子模块的编号
    private String sensorAddress;//传感器标识
    private int counter;//传感器个数
    private String cmd;//接收数据的标识
    private float data;//采集的指标值
    private int status;//采集数据的状态
    private Timestamp gatherDate; //采集器采集数据的时间

    public Environment(String name, String srcID, String dstID, String devID, String sensorAddress, int counter, String cmd, float data, int status, Timestamp gatherDate) {
        this.name = name;
        this.srcID = srcID;
        this.dstID = dstID;
        this.devID = devID;
        this.sensorAddress = sensorAddress;
        this.counter = counter;
        this.cmd = cmd;
        this.data = data;
        this.status = status;
        this.gatherDate = gatherDate;
    }

    public Environment() {
    }

    public Environment(String srcID, String dstID, String devID, String sensorAddress, int counter, String cmd, int status, Timestamp gatherDate) {
        this.srcID = srcID;
        this.dstID = dstID;
        this.devID = devID;
        this.sensorAddress = sensorAddress;
        this.counter = counter;
        this.cmd = cmd;
        this.status = status;
        this.gatherDate = gatherDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSrcID() {
        return srcID;
    }

    public void setSrcID(String srcID) {
        this.srcID = srcID;
    }

    public String getDstID() {
        return dstID;
    }

    public void setDstID(String dstID) {
        this.dstID = dstID;
    }

    public String getDevID() {
        return devID;
    }

    public void setDevID(String devID) {
        this.devID = devID;
    }

    public String getSensorAddress() {
        return sensorAddress;
    }

    public void setSensorAddress(String sensorAddress) {
        this.sensorAddress = sensorAddress;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public float getData() {
        return data;
    }

    public void setData(float data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Timestamp getGatherDate() {
        return gatherDate;
    }

    public void setGatherDate(Timestamp gatherDate) {
        this.gatherDate = gatherDate;
    }

    @Override
    public String toString() {
        return "Environment{" +
                "name='" + name + '\'' +
                ", srcID='" + srcID + '\'' +
                ", dstID='" + dstID + '\'' +
                ", devID='" + devID + '\'' +
                ", sensorAddress='" + sensorAddress + '\'' +
                ", counter=" + counter +
                ", cmd='" + cmd + '\'' +
                ", data=" + data +
                ", status=" + status +
                ", gatherDate=" + gatherDate +
                '}';
    }
}
