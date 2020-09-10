package com.fmsh.temperature.fragment;

import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fmsh.temperature.R;
import com.fmsh.temperature.listener.OnUpdateFragmentListener;
import com.fmsh.temperature.util.LogUtil;
import com.fmsh.temperature.util.NFCUtils;
import com.fmsh.temperature.util.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by wyj on 2018/7/2.
 */
public class TemperatureMeasuremenFragment extends BaseFragment implements OnUpdateFragmentListener {


    @BindView(R.id.tv_instruct)
    TextView tvInstruct;
    @BindView(R.id.tv_config)
    TextView tvConfig;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    Unbinder unbinder;
    @BindView(R.id.ll_fragment)
    LinearLayout llFragment;
    private ConfigurationFragment mConfigurationFragment;
    private InstructFragment mInstructFragment;

    @Override
    protected int setView() {
        return R.layout.fragment_temperature_measurement;
    }

    @Override
    protected void init(View view) {
        tvConfig.setOnClickListener(this);
        tvInstruct.setOnClickListener(this);
        if (mInstructFragment == null)
            mInstructFragment = new InstructFragment();
        if (!mInstructFragment.isAdded()) {

            FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.ll_fragment, mInstructFragment).commit();
            tvInstruct.setTextColor(UIUtils.getColor(R.color.colorPrimary));
        }
//        linearLayout.setVisibility(View.GONE);
        if(mInstructFragment.isHidden()){
            tvInstruct.setTextColor(UIUtils.getColor(R.color.black));
            if(mConfigurationFragment != null)
                tvConfig.setTextColor(UIUtils.getColor(R.color.colorPrimary));
        }else {
            if(mConfigurationFragment != null)
                tvConfig.setTextColor(UIUtils.getColor(R.color.black));
        }



    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_config:
                tvConfig.setTextColor(UIUtils.getColor(R.color.colorPrimary));
                tvInstruct.setTextColor(UIUtils.getColor(R.color.black));
                if (mConfigurationFragment == null)
                    mConfigurationFragment = new ConfigurationFragment();
                switchFragment(mInstructFragment, mConfigurationFragment);

                break;
            case R.id.tv_instruct:
                tvInstruct.setTextColor(UIUtils.getColor(R.color.colorPrimary));
                tvConfig.setTextColor(UIUtils.getColor(R.color.black));
                if (mInstructFragment == null)
                    mInstructFragment = new InstructFragment();
                switchFragment(mConfigurationFragment, mInstructFragment);

                break;
        }
    }

    private void switchFragment(Fragment fromFragment, BaseFragment nextFragment) {
        if (nextFragment != null) {
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            //判断nextFragment是否添加
            if (!nextFragment.isAdded()) {
                //隐藏当前Fragment
                if (fromFragment != null) {
                    transaction.hide(fromFragment);
                }
                transaction.add(R.id.ll_fragment, nextFragment).commit();
            } else {
                //隐藏当前Fragment
                if (fromFragment != null) {
                    transaction.hide(fromFragment);
                }
                transaction.show(nextFragment).commit();
            }
        }
    }


    @Override
    public void shouwId(String uid) {

    }

    @Override
    public void tagData(Tag tag) {


    }


}
