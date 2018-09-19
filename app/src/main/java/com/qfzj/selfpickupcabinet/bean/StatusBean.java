package com.qfzj.selfpickupcabinet.bean;

public class StatusBean {

    public String boxNo;
    public int hasItem;
    public int doorStatus;
    public int lightStatus;

    public StatusBean(String boxNo, int hasItem, int doorStatus, int lightStatus) {
        this.boxNo = boxNo;
        this.hasItem = hasItem;
        this.doorStatus = doorStatus;
        this.lightStatus = lightStatus;

    }

    public int doorStatusChange(){
        if(doorStatus==1){
            doorStatus = 0;
        }
        else{
            doorStatus=1;
        }
        return doorStatus;
    }
    public int lightStatusChange(){
        if(lightStatus==1)
            lightStatus = 0;
        else{
            lightStatus = 1;
        }
        return lightStatus;
    }
}
