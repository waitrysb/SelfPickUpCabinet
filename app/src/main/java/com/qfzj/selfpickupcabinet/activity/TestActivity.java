//package com.qfzj.selfpickupcabinet.activity;
//
//import android.content.ComponentName;
//import android.content.Context;
//import android.content.Intent;
//import android.content.ServiceConnection;
//import android.os.Bundle;
//import android.os.IBinder;
//import android.os.RemoteException;
//import android.support.annotation.Nullable;
//import android.support.v7.app.AppCompatActivity;
//import android.text.TextUtils;
//import android.widget.EditText;
//import android.widget.Toast;
//
//public class TestActivity extends AppCompatActivity {
//    private IMainController mainController; //主控制层
//    private ILockerController slaveController; //开门板控制层
//    MainConnection mainConnection; //ServiceConnection接口实现类
//    class MainConnection implements ServiceConnection {
//        @Override
//        public void onServiceConnected(ComponentName name, IBinder service) {
//            try {
//                mainController = IMainController.Stub.asInterface(service);
//                slaveController = ILockerController.Stub .asInterface(mainController.getLockerService());
//            } catch (RemoteException e) { e.printStackTrace();
//            } }
//        @Override
//        public void onServiceDisconnected(ComponentName name) {
//        } }
//    /**
//     *绑定函数实现
//     *@param service 服务名称
//     *@parm connection连接类
//     */
//    private void bindService(String service, ServiceConnection connection)
//            throws ClassNotFoundException {
//        try {
//            Intent intent = new Intent(service); intent.setPackage("com.zhilai.terminal");
//            bindService(intent, connection, Context.BIND_AUTO_CREATE);
//            Toast.makeText(this, "bind Success", Toast.LENGTH_SHORT).show();
//        } catch (Error e) {
//            e.printStackTrace(); }
//    }
//    @Override
//    protected void onResume() {
//        super.onResume();
//        try {
//            mainConnection = new MainConnection(); bindService("android.intent.action.jd.CoreService", mainConnection);
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace(); }
//    }
//    @Override
//    protected void onPause() {
//        super.onPause();
//        unbindService(mainConnection); mainConnection = null;
//        slaveController = null;
//    }
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        onResume();
//    }
//
//    void openCabinet(EditText editText) {
//        final String box = editText.getText().toString(); if (TextUtils.isEmpty(box)) {
//            Toast.makeText(this, "参数不能为空", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    //通过 aidl 方式调用，打开对应格口 slaveController.openBox(box);
//                } catch (RemoteException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//    }
//}
