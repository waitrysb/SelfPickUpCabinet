package com.qfzj.selfpickupcabinet.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.qfzj.selfpickupcabinet.R;
import com.qfzj.selfpickupcabinet.message.AllCabinetStatusMsg;
import com.qfzj.selfpickupcabinet.message.AllDoorStatusMsg;
import com.qfzj.selfpickupcabinet.message.DoorStatusMsg;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @InjectView(R.id.addBtn)
    Button addBtn;
    @InjectView(R.id.takeBtn)
    Button takeBtn;
    @InjectView(R.id.setBtn)
    Button setBtn;

    AllCabinetStatusMsg allCabinetStatusMsg;
    AllDoorStatusMsg allDoorStatusMsg;
    Map<String, String> cabinetMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        cabinetMap = new HashMap<String, String>();
    }

    @OnClick({R.id.addBtn, R.id.takeBtn, R.id.setBtn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addBtn:
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("您好，请扫描二维码").setIcon(android.R.drawable.ic_dialog_info)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //这里需要扫描二维码，还没写
                                final String orderNo = "fffff";

                                if(!orderNo.equals("")) {
                                    String vacantCabinetNo = openVacantCabinet("0");

                                    if(!vacantCabinetNo.equals("")) {
                                        //将订单号与柜子号绑定
                                        cabinetMap.put(orderNo, vacantCabinetNo);

                                        AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                                        builder1.setTitle(vacantCabinetNo + "号柜已打开\n放入物品后请关闭柜门！").setIcon(android.R.drawable.ic_dialog_info)
                                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                    }
                                                });
                                        builder1.show();
                                    } else {
                                        Toast.makeText(MainActivity.this, "柜子已满，无法放入！", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            }
                        });
                builder.show();
                break;
            case R.id.takeBtn:
                AlertDialog.Builder builder2 = new AlertDialog.Builder(MainActivity.this);
                builder2.setTitle("您好，请扫描二维码").setIcon(android.R.drawable.ic_dialog_info)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //这里需要扫描二维码，还没写
                                final String reservationNo = "fffff";

                                if(!cabinetMap.containsKey(reservationNo)) {
                                    Toast.makeText(MainActivity.this, "没有这个柜子，请重新操作", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    String cabinetNo = cabinetMap.get(reservationNo);
                                    if(openCabinet(cabinetNo)) {

                                        cabinetMap.remove(reservationNo);

                                        AlertDialog.Builder builder3 = new AlertDialog.Builder(MainActivity.this);
                                        builder3.setTitle(cabinetNo + "号柜已打开\n取走您的物品后请关闭柜门！").setIcon(android.R.drawable.ic_dialog_info)
                                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                    }
                                                });
                                        builder3.show();
                                    } else  {
                                        Toast.makeText(MainActivity.this, "打开柜子失败", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                dialog.dismiss();
                            }
                        });
                builder2.show();
                break;
            case R.id.setBtn:
                Intent intent=new Intent(this,SettingOfBox.class);
                startActivity(intent);
                Toast.makeText(MainActivity.this, "设置", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public String openVacantCabinet(String groupNo) {
        String result1 = "{\"errorCode\": 0, \"errorMessage\": \"成功\",\"data\":[{\"boxNo\": \"1\", \"hasItem\": 1}, {\"boxNo\": \"2\", \"hasItem\": 0}]}";
        allCabinetStatusMsg = new Gson().fromJson(result1, AllCabinetStatusMsg.class);

        String result2 = "{\"errorCode\": 0, \"errorMessage\": \"success\", \"data\":[{\"boxNo\": \"1\", \"doorStatus\": 1}, {\"boxNo\": \"2\", \"doorStatus\": 0}]}";
        allDoorStatusMsg = new Gson().fromJson(result2, AllDoorStatusMsg.class);

        String boxNo = "";

        if(allCabinetStatusMsg.errorCode == 0) {
            boxNo = allCabinetStatusMsg.findVacantCabinet();
            if(allDoorStatusMsg.findDoorStatus(boxNo) == 0) {
                //openBox(boxNo);
            }
        }

        return boxNo;
    }

    public boolean openCabinet(String boxNo) {
        String result = "{\"errorCode\": 0, \"errorMessage\": \"success\", \"data\":{\"boxNo\": \"1\", \"doorStatus\": 1}}";
        DoorStatusMsg doorStatusMsg = new Gson().fromJson(result, DoorStatusMsg.class);

        if(doorStatusMsg.errorCode == 0) {
            if(doorStatusMsg.data.doorStatus == 0) {
                //openBox(boxNo);
            }
            return true;
        }

        return false;
    }
}
