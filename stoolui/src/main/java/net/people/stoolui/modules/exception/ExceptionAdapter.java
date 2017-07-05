package net.people.stoolui.modules.exception;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.people.stoolui.R;

import java.util.List;

/**
 * Title: ExceptionAdapter$ <br> Description: <br> Copyright (c) 传化物流版权所有 2016 <br> Created
 * DateTime: 2017/1/4$ 13:37$ <br> Created by  nongwenxue.
 */
public class ExceptionAdapter extends RecyclerView.Adapter<ExceptionAdapter.ViewHolder>{

    private List<ExceptionBean> mList;
    private Context mContext;

    public ExceptionAdapter(List<ExceptionBean> list) {
        mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext==null){
            mContext = parent.getContext();
        }
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.stool_item_exception_info,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ExceptionBean bean = mList.get(position);
        holder.txtException.setText(bean.getSimpleMessage());
        holder.txtExceptionTime.setText(bean.getTime());
        holder.llayoutExceptionInfoItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,ExceptionInfoDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(ExceptionInfoDetailActivity.EXCEPTION_BEAN, bean);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtException;
        TextView txtExceptionTime;
        LinearLayout llayoutExceptionInfoItem;
    public ViewHolder(View itemView) {
        super(itemView);
        txtException = (TextView) itemView.findViewById(R.id.txt_exception);
        txtExceptionTime = (TextView) itemView.findViewById(R.id.txt_exception_time);
        llayoutExceptionInfoItem = (LinearLayout) itemView.findViewById(R.id.llayout_exception_info_item);
    }
}





}
