package com.fmsh.temperature.fragment;

import android.annotation.SuppressLint;
import android.nfc.Tag;
import android.nfc.tech.NfcV;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.fmsh.temperature.R;
import com.fmsh.temperature.util.LogUtil;
import com.fmsh.temperature.util.NFCUtils;
import com.fmsh.temperature.util.UIUtils;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by wyj on 2018/7/9.
 */
public class ConfigurationFragment extends BaseFragment {
    @BindView(R.id.tvContent)
    TextView tvContent;
    Unbinder unbinder;

    @Override
    protected int setView() {
        return R.layout.fragment_configuration;
    }

    @Override
    protected void init(View view) {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }



    private int type = 0;
    @SuppressLint("HandlerLeak")
    private Handler mHandler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.obj instanceof  String){
                String reslut= (String) msg.obj;
                tvContent.setText(reslut);

            }
//            if(msg.what == 10){
//                if(reslut.contains(":")){
//                    String[] split = reslut.split(":");
//                    StringBuffer stringBuffer = new StringBuffer();
//                    for (int i = 0; i <split.length /4 ; i+=4) {
//                        String v = NFCUtils.strFromat(split[i] + split[i + 1])+"℃   ";
//                        stringBuffer.append(v);
//                    }
//
//                    tvContent.setText(stringBuffer.toString());
//                }
//            }else {
//
//                tvContent.setText(reslut);
//            }

        }
    };


    @OnClick({R.id.btn_status1, R.id.btn_status2, R.id.btn_status3,
            R.id.btn_status4, R.id.btn_status5, R.id.btn_status6,
            R.id.btn_status7, R.id.btn_status8,
            R.id.btn_status9, R.id.btn_status10,
            R.id.btn_status11, R.id.btn_status12,
            R.id.btn_status13, R.id.btn_status14,
            R.id.btn_status15, R.id.btn_status16,
            R.id.btn_status17,R.id.btn_status18,
            R.id.btn_status19, R.id.btn_status20,
            R.id.btn_status21, R.id.btn_status22,})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_status1:
                type =1;
                break;
            case R.id.btn_status2:
                type =2;
                break;
            case R.id.btn_status3:
                type =3;
                break;
            case R.id.btn_status4:
                type =4;
                break;
            case R.id.btn_status5:
                type =5;
                break;
            case R.id.btn_status6:
                type =6;
                break;
            case R.id.btn_status7:
                type =7;
                break;
            case R.id.btn_status8:
//                type =8;
                type = 35;
                break;
            case R.id.btn_status9:
                type =9;
                break;
            case R.id.btn_status10:
                type =10;
                break;
            case R.id.btn_status11:
                type =11;
                break;
            case R.id.btn_status12:
                type =12;
                break;
            case R.id.btn_status13:
                type =13;
                break;
            case R.id.btn_status14:
                type =14;
                break;
            case R.id.btn_status15:
                type =24;
                break;
            case R.id.btn_status16:
                type = 25;
                break;
            case R.id.btn_status17:
                type = 32;
                break;
            case R.id.btn_status18:
                type = 33;
                break;
            case R.id.btn_status19:
                type = 43;
                break;
            case R.id.btn_status20:
                type = 41;
                break;
            case R.id.btn_status21:
                type = 42;
                break;
            case R.id.btn_status22:
                type = 40;
                break;
        }
        UIUtils.setHandler(mHandler);
        if(mContext.mTag != null){
            String[] techList = mContext.mTag.getTechList();
            for (String  tech:techList) {
                LogUtil.d(tech);
                if(tech.contains("NfcV")){
                    NFCUtils.startV(mContext.mTag,type);
                }
                if (tech.contains("NfcA")){
                    NFCUtils.startA(mContext.mTag,type);
                }
            }
        }
    }
}
