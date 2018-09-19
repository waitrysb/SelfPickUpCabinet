package com.qfzj.selfpickupcabinet.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.qfzj.selfpickupcabinet.Adapter.StatusAdapter;
import com.qfzj.selfpickupcabinet.R;
import com.qfzj.selfpickupcabinet.bean.StatusBean;

import java.util.ArrayList;
import java.util.List;

public class SettingOfBox extends AppCompatActivity {
    private ArrayList<StatusBean> StatusList=new ArrayList<StatusBean>();
    private StatusAdapter statusAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_of_box);
        /**获取柜子状态的列表*/
        InitStatus();
        RecyclerView setting_recyclerView = findViewById(R.id.setting_recyclerView);
        GridLayoutManager layoutManager=new GridLayoutManager(this,1);
        setting_recyclerView.setLayoutManager(layoutManager);
        statusAdapter = new StatusAdapter(StatusList);
        setting_recyclerView.setAdapter(statusAdapter);
    }
    private void InitStatus(){
        StatusBean [] temp = {
                new StatusBean("1",0,0,0),
                new StatusBean("2",0,1,0),
                new StatusBean("3",0,0,1),
                new StatusBean("4",0,1,1),
                new StatusBean("1",0,0,0),
                new StatusBean("2",0,1,0),
                new StatusBean("3",0,0,1),
                new StatusBean("4",0,1,1),
                new StatusBean("1",0,0,0),
                new StatusBean("2",0,1,0),
                new StatusBean("3",0,0,1),
                new StatusBean("4",0,1,1),
                new StatusBean("1",0,0,0),
                new StatusBean("2",0,1,0),
                new StatusBean("3",0,0,1),
                new StatusBean("4",0,1,1),
                new StatusBean("1",0,0,0),
                new StatusBean("2",0,1,0),
                new StatusBean("3",0,0,1),
                new StatusBean("4",0,1,1),
        };
        for ( int i = 0;i < temp.length;i++ ){
            StatusList.add(temp[i]);
        }
    }
}
