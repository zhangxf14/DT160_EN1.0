package com.fmsh.temperature.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.fmsh.temperature.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wyj on 2018/7/9.
 */
public class ChartActivity extends AppCompatActivity {
    @BindView(R.id.graphView)
    GraphView graphView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView(){
        GridLabelRenderer gridLabelRenderer = graphView.getGridLabelRenderer();
//        gridLabelRenderer.setGridStyle(GridLabelRenderer.GridStyle.HORIZONTAL);

    }
    private void initData(){

    }
}
