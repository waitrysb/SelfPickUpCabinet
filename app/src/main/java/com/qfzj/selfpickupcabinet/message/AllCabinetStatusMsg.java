package com.qfzj.selfpickupcabinet.message;

import com.qfzj.selfpickupcabinet.bean.CabinetBean;

import java.util.ArrayList;

public class AllCabinetStatusMsg {

    public int errorCode;
    public String errorMessage;
    public ArrayList<CabinetBean> data;

    public String findVacantCabinet() {
        for(int i = 0; i < data.size(); i++) {
            if(data.get(i).hasItem == 0)
                return data.get(i).boxNo;
        }
        return "";
    }
}
