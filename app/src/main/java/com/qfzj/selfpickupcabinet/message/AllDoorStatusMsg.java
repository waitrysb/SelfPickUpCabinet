package com.qfzj.selfpickupcabinet.message;

import com.qfzj.selfpickupcabinet.bean.DoorBean;

import java.util.ArrayList;

public class AllDoorStatusMsg {
    public int errorCode;
    public String errorMessage;
    public ArrayList<DoorBean> data;

    public int findDoorStatus(String boxNo) {
        for(int i = 0; i < data.size(); i++) {
            if(boxNo.equals(data.get(i).boxNo)) {
                return data.get(i).doorStatus;
            }
        }
        return -1;
    }
}
