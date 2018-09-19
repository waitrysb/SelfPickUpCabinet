package com.qfzj.selfpickupcabinet.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qfzj.selfpickupcabinet.R;
import com.qfzj.selfpickupcabinet.bean.StatusBean;

import java.util.List;

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.ViewHolder>{
    private Context mContext;
    private List<StatusBean> mStatusList;
    public StatusAdapter(List<StatusBean> statusList){
        mStatusList = statusList;
    }
    @Override
    public StatusAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mContext==null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.setting_status_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final StatusAdapter.ViewHolder holder, int position) {
       final StatusBean statusBean = mStatusList.get(position);
       holder.boxNo.setText(statusBean.boxNo);
       holder.lightStatus.setImageResource(
          (statusBean.lightStatus==1)?
                  R.drawable.light_on:
                   R.drawable.light_off
       );
       holder.doorStatus.setImageResource(
          (statusBean.doorStatus==1)?
                  R.drawable.door_open:
                   R.drawable.door_close
       );
       holder.lightStatus.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               holder.lightStatus.setImageResource(
                       (statusBean.lightStatusChange()==1)?
                               R.drawable.light_on:
                                 R.drawable.light_off
               );
               /**调用开灯或关灯的函数，改变状态*/
               /**更新后台灯的状态存储*/
           }
       });
       holder.doorStatus.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               holder.doorStatus.setImageResource(
                       (statusBean.doorStatusChange()==1)?
                             R.drawable.door_open:
                               R.drawable.door_close
               );

           }
       });
    }

    @Override
    public int getItemCount() {
        return mStatusList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView boxNo;
        ImageView doorStatus,lightStatus;
        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            boxNo = itemView.findViewById(R.id.setting_boxNo);
            doorStatus = itemView.findViewById(R.id.setting_door);
            lightStatus = itemView.findViewById(R.id.setting_light);
        }
    }
}
