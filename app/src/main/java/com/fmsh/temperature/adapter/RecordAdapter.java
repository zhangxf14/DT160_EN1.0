package com.fmsh.temperature.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fmsh.temperature.R;
import com.fmsh.temperature.listener.OnItemClickListener;
import com.fmsh.temperature.util.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wyj on 2018/7/9.
 */
public class RecordAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;
    public RecordAdapter(Context context){
        this.mContext =context;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = UIUtils.getLayoutView(parent, R.layout.item_record);
        return new ViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ViewHolder vh = (ViewHolder) holder;
        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnItemClickListener != null){
                    mOnItemClickListener.itemClickListener(position);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return 10;
    }

    static class ViewHolder  extends RecyclerView.ViewHolder{
        @BindView(R.id.tv)
        TextView tv;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }
}
