package com.qfzj.selfpickupcabinet.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.qfzj.selfpickupcabinet.adapter.StatusAdapter;
import com.qfzj.selfpickupcabinet.R;
import com.qfzj.selfpickupcabinet.bean.BoxStatusBean;

import java.util.ArrayList;
import java.util.Map;

public class BoxSettingActivity extends AppCompatActivity {

    private ArrayList<BoxStatusBean> boxStatusBeans = new ArrayList<BoxStatusBean>();
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

        statusAdapter = new StatusAdapter(boxStatusBeans);
        setting_recyclerView.setAdapter(statusAdapter);
    }

    private void InitStatus() {

        Map<String, BoxStatusBean> box = (Map<String, BoxStatusBean>) this.getIntent().getSerializableExtra("BoxStatus");

        for (Map.Entry<String, BoxStatusBean> entry: box.entrySet()) {
            boxStatusBeans.add(entry.getValue());
        }
    }
}
