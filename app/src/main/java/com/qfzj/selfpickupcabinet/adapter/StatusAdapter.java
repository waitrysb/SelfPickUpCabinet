package com.qfzj.selfpickupcabinet.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qfzj.selfpickupcabinet.R;
import com.qfzj.selfpickupcabinet.bean.BoxStatusBean;

import java.util.List;

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.ViewHolder>{
    private Context mContext;
    private List<BoxStatusBean> mStatusList;

    public StatusAdapter(List<BoxStatusBean> statusList){
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
       final BoxStatusBean boxStatusBean = mStatusList.get(position);
       holder.boxNo.setText(boxStatusBean.boxNo);
       holder.itemStatus.setImageResource(
          (boxStatusBean.hasItem==1)?
                  R.drawable.light_on:
                   R.drawable.light_off
       );
       holder.doorStatus.setImageResource(
          (boxStatusBean.doorStatus==1)?
                  R.drawable.door_open:
                   R.drawable.door_close
       );

       holder.doorStatus.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               holder.doorStatus.setImageResource(
                       (boxStatusBean.doorStatusChange()==1)?
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
        ImageView doorStatus,itemStatus;
        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            boxNo = itemView.findViewById(R.id.setting_boxNo);
            doorStatus = itemView.findViewById(R.id.setting_door);
            itemStatus = itemView.findViewById(R.id.setting_light);
        }
    }
}
