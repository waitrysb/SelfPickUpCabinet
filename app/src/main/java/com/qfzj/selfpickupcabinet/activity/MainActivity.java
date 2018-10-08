package com.qfzj.selfpickupcabinet.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.qfzj.selfpickupcabinet.R;
import com.qfzj.selfpickupcabinet.bean.BoxStatusBean;
import com.qfzj.selfpickupcabinet.bean.CabinetBean;
import com.qfzj.selfpickupcabinet.message.AllCabinetStatusMsg;
import com.qfzj.selfpickupcabinet.message.AllDoorStatusMsg;
import com.qfzj.selfpickupcabinet.message.CabinetStatusMsg;
import com.qfzj.selfpickupcabinet.message.DoorStatusMsg;
import com.qfzj.selfpickupcabinet.util.GlideImageLoader;
import com.qfzj.selfpickupcabinet.util.HttpUtil;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    @InjectView(R.id.addBtn)
    Button addBtn;
    @InjectView(R.id.takeBtn)
    Button takeBtn;
    @InjectView(R.id.setBtn)
    Button setBtn;
    @InjectView(R.id.banner)
    Banner banner;

    Map<String, BoxStatusBean> boxNoToBox;
    Map<String, String> orderNoToBoxNo;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        boxNoToBox = new HashMap<String, BoxStatusBean>();
        orderNoToBoxNo = new HashMap<String, String>();

        //更新箱子状态
        updateBoxStatus();

        //加载广告位的图片
        ArrayList<Integer> images = new ArrayList<Integer>();
        images.add(R.drawable.door_open);
        images.add(R.drawable.door_close);
        images.add(R.drawable.light_on);
        images.add(R.drawable.light_off);

        ArrayList<String> titles = new ArrayList<String>();
        titles.add("1");
        titles.add("2");
        titles.add("3");
        titles.add("4");
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(images);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
        banner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(1500);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
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
                                    final BoxStatusBean vacantCabinet = openVacantCabinet("0");

                                    if(vacantCabinet != null) {
                                        AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                                        builder1.setTitle(vacantCabinet.boxNo + "号柜已打开，请放入物品\n关闭柜门后请点击确定！").setIcon(android.R.drawable.ic_dialog_info)
                                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        //检查该柜门是否关闭
                                                        if(checkItem(vacantCabinet.boxNo) == 1 && checkDoor(vacantCabinet.boxNo) == 0) {
                                                            //将订单号与柜子号绑定
                                                            orderNoToBoxNo.put(orderNo, vacantCabinet.boxNo);

                                                            final Bundle mBundle = new Bundle();
                                                            mBundle.putString("orderNo", orderNo);
                                                            HttpUtil.sendStoreOkHttpRequest(mBundle, new okhttp3.Callback() {
                                                                @Override
                                                                public void onResponse(Call call, Response response) throws IOException {}

                                                                @Override
                                                                public void onFailure(Call call, IOException e) {
                                                                    Log.d(TAG, "onFailure: ERROR!");
                                                                    Toast.makeText(MainActivity.this, "连接服务器失败，请重新尝试！", Toast.LENGTH_LONG).show();
                                                                }
                                                            });
                                                            Toast.makeText(MainActivity.this, "存放成功！", Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            Toast.makeText(MainActivity.this, "存放失败！\n请检查箱门是否关闭或联系管理人员！", Toast.LENGTH_SHORT).show();
                                                        }
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

                                if(!orderNoToBoxNo.containsKey(reservationNo)) {
                                    Toast.makeText(MainActivity.this, "没有这个柜子，请重新操作", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    String cabinetNo = orderNoToBoxNo.get(reservationNo);
                                    if(cabinetNo != null) {
                                        BoxStatusBean boxStatusBean = boxNoToBox.get(cabinetNo);

                                        if(boxStatusBean != null) {
                                            if(boxStatusBean != null && openCabinet(cabinetNo)) {

                                                orderNoToBoxNo.remove(reservationNo);

                                                final Bundle mBundle = new Bundle();
                                                mBundle.putString("orderNo", reservationNo);
                                                HttpUtil.sendWithdrawOkHttpRequest(mBundle, new okhttp3.Callback() {
                                                    @Override
                                                    public void onResponse(Call call, Response response) throws IOException {}

                                                    @Override
                                                    public void onFailure(Call call, IOException e) {
                                                        Log.d(TAG, "onFailure: ERROR!");
                                                        Toast.makeText(MainActivity.this, "连接服务器失败，请重新尝试！", Toast.LENGTH_LONG).show();
                                                    }
                                                });

                                                AlertDialog.Builder builder3 = new AlertDialog.Builder(MainActivity.this);
                                                builder3.setTitle(cabinetNo + "号柜已打开\n取走您的物品后请关闭柜门！").setIcon(android.R.drawable.ic_dialog_info)
                                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                dialog.dismiss();
                                                            }
                                                        });
                                                builder3.show();
                                            } else  {
                                                Toast.makeText(MainActivity.this, "打开柜子失败！", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                }
                                dialog.dismiss();
                            }
                        });
                builder2.show();
                break;
            case R.id.setBtn:
                AlertDialog.Builder builder4 = new AlertDialog.Builder(MainActivity.this);
                builder4.setTitle("您好，请扫描二维码").setIcon(android.R.drawable.ic_dialog_info)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        //这里需要扫描二维码，还没写
                                        String userValidation = "aaa";

                                        if(userValidation.equals("aaa")) {
                                            updateBoxStatus();
                                            Intent intent=new Intent(MainActivity.this,BoxSettingActivity.class);
                                            intent.putExtra("BoxStatus", (Serializable) boxNoToBox);
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(MainActivity.this, "管理员验证失败！", Toast.LENGTH_SHORT).show();
                                        }
                                        dialog.dismiss();
                                    }
                                });
                builder4.show();
                break;
        }
    }

    public BoxStatusBean openVacantCabinet(String groupNo) {
        updateBoxStatus();

        BoxStatusBean res = null;

        for (Map.Entry<String, BoxStatusBean> entry: boxNoToBox.entrySet()) {
            BoxStatusBean temp = entry.getValue();
            if(temp.hasItem == 0) {
                res = temp;
                openCabinet(res.boxNo);
                break;
            }
        }
        return res;
    }

    public boolean openCabinet(String boxNo) {
        String result = "{\"errorCode\": 0, \"errorMessage\": \"success\", \"data\":{\"boxNo\": \"1\", \"doorStatus\": 1}}";
        DoorStatusMsg doorStatusMsg = new Gson().fromJson(result, DoorStatusMsg.class);

        if(doorStatusMsg.errorCode == 0) {
            if(doorStatusMsg.data.doorStatus == 0) {
                //openBox(boxNo);
                updateBoxStatus();
            }

            return true;
        }

        return false;
    }

    public int checkDoor(String boxNo) {
        String result = "{\"errorCode\": 0, \"errorMessage\": \"success\", \"data\":{\"boxNo\": \"1\", \"doorStatus\": 1}}";
        DoorStatusMsg doorStatusMsg = new Gson().fromJson(result, DoorStatusMsg.class);

        if(doorStatusMsg.errorCode == 0) {
            updateBoxStatus();
            return doorStatusMsg.data.doorStatus;
        }

        return -1;
    }

    public int checkItem(String boxNo) {
        String result = "{\"errorCode\": 0, \"errorMessage\": \"success\", \"data\":{\"boxNo\": \"1\", \"hasItem\": 1}}";
        CabinetStatusMsg cabinetStatusMsg = new Gson().fromJson(result, CabinetStatusMsg.class);

        if(cabinetStatusMsg.errorCode == 0) {
            updateBoxStatus();
            return cabinetStatusMsg.data.hasItem;
        }

        return -1;
    }

    public void updateBoxStatus() {
        String result1 = "{\"errorCode\": 0, \"errorMessage\": \"成功\",\"data\":[{\"boxNo\": \"1\", \"hasItem\": 1}, {\"boxNo\": \"2\", \"hasItem\": 0}]}";
        AllCabinetStatusMsg allCabinetStatusMsg = new Gson().fromJson(result1, AllCabinetStatusMsg.class);

        String result2 = "{\"errorCode\": 0, \"errorMessage\": \"success\", \"data\":[{\"boxNo\": \"1\", \"doorStatus\": 1}, {\"boxNo\": \"2\", \"doorStatus\": 0}]}";
        AllDoorStatusMsg allDoorStatusMsg = new Gson().fromJson(result2, AllDoorStatusMsg.class);

        if(allCabinetStatusMsg.errorCode == 0) {
            for(int i = 0; i < allCabinetStatusMsg.data.size(); i++) {
                String key = allCabinetStatusMsg.data.get(i).boxNo;
                BoxStatusBean value = new BoxStatusBean(allCabinetStatusMsg.data.get(i));

                if(boxNoToBox.containsKey(key)) {
                    value = boxNoToBox.get(key);
                    value.update(allCabinetStatusMsg.data.get(i));
                }
                boxNoToBox.put(key, value);
            }
        }

        if(allDoorStatusMsg.errorCode == 0) {
            for(int i = 0; i < allDoorStatusMsg.data.size(); i++) {
                String key = allDoorStatusMsg.data.get(i).boxNo;
                BoxStatusBean value = new BoxStatusBean(allDoorStatusMsg.data.get(i));

                if(boxNoToBox.containsKey(key)) {
                    value = boxNoToBox.get(key);
                    value.update(allDoorStatusMsg.data.get(i));
                }
                boxNoToBox.put(key, value);
            }
        }
    }
}
