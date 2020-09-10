package com.fmsh.temperature.activity;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.nfc.NfcManager;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.fmsh.temperature.R;
import com.fmsh.temperature.fragment.BaseFragment;
import com.fmsh.temperature.fragment.RecordFragment;
import com.fmsh.temperature.fragment.SettingFragment;
import com.fmsh.temperature.fragment.TemperatureMeasuremenFragment;
import com.fmsh.temperature.listener.OnUpdateFragmentListener;
import com.fmsh.temperature.tools.CommThread;
import com.fmsh.temperature.util.LogUtil;
import com.fmsh.temperature.util.ReadOrWriteNFCUtil;
import com.fmsh.temperature.util.TimeUitls;
import com.fmsh.temperature.util.ToastUtil;
import com.fmsh.temperature.util.UIUtils;
import com.fmsh.temperature.util.Util;

import java.text.ParseException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    private String[] title = {UIUtils.getString(R.string.text_main1), UIUtils.getString(R.string.text_main2), UIUtils.getString(R.string.text_main3)};
    private TemperatureMeasuremenFragment mTemperatureMeasuremenFragment;
    private ArrayList<Fragment> mFragments;
    private NfcAdapter mDefaultAdapter;
    private PendingIntent mPendingIntent;
    public String mStrId;
    public CommThread mCommThread;
    public Tag mTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        mDefaultAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mDefaultAdapter == null)
            ToastUtil.sToastUtil.shortDuration("Not Support nfc");
        mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

    }

    private void initView() {
        tablayout.setupWithViewPager(viewPager);
        mTemperatureMeasuremenFragment = new TemperatureMeasuremenFragment();
        RecordFragment recordFragment = new RecordFragment();
        SettingFragment settingFragment = new SettingFragment();
        mFragments = new ArrayList<>();
        mFragments.add(mTemperatureMeasuremenFragment);
        mFragments.add(recordFragment);
        mFragments.add(settingFragment);
        MyPagreFragment myPagreFragment = new MyPagreFragment(getSupportFragmentManager());
        viewPager.setAdapter(myPagreFragment);
        mCommThread = new CommThread();
        mCommThread.start();
        mCommThread.setContext(this);


    }

    private class MyPagreFragment extends FragmentPagerAdapter {
        public MyPagreFragment(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return title[position];
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mDefaultAdapter != null){
            mDefaultAdapter.enableForegroundDispatch(this, mPendingIntent, null, null);
            enableReaderMode();

        }

    }

    @TargetApi(19)
    public void enableReaderMode() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){ //19 4.4
            int READER_FLAGS = -1;
            Bundle option = new Bundle();
            option.putInt(NfcAdapter.EXTRA_READER_PRESENCE_CHECK_DELAY, 2000);// 延迟对卡片的检测
            if (mDefaultAdapter != null) {
                mDefaultAdapter.enableReaderMode(this,new MyReaderCallback(),READER_FLAGS,option);
              //-1代表所有类别的芯片都可以识别,如果只是单独的识别一种就填写对应的数值即可
            }

        }
    }

    @TargetApi(19)
    public void startReaderModeA(){
        mDefaultAdapter.enableReaderMode(this,new MyReaderCallback(),NfcAdapter.FLAG_READER_NFC_A,null);
    }

    private class  MyReaderCallback implements NfcAdapter.ReaderCallback {
        @Override
        public void onTagDiscovered(Tag tag) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            long[] duration = {15, 30, 60, 90};
            vibrator.vibrate(duration, -1);
            String[] techList = tag.getTechList();
            MainActivity.this.mTag = tag;
            Intent record = new Intent("record");
            sendBroadcast(record);
        }
    }

    public void startReaderModeV(){
        mDefaultAdapter.enableReaderMode(this,new MyReaderCallback(),NfcAdapter.FLAG_READER_NFC_V,null);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        //nfc标签靠近手机，建立连接后调用
        if(Build.VERSION.SDK_INT <  Build.VERSION_CODES.KITKAT){ //19 4.4
            tagShowMethod(intent);
        }
        ReadOrWriteNFCUtil.readNfcTag(intent);


    }
    private void tagShowMethod(Intent intent) {
        mTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        String[] techList = mTag.getTechList();
        for (String tech : techList) {
            LogUtil.d(tech);
        }
        if (mTag != null) {
            byte[] id = mTag.getId();
            mStrId = Util.bytesToHexString(id, ':');
            //           mStrId= NFCUtils.bytesToHexString(id,id.length);
        }
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        long[] duration = {15, 30, 60, 90};
        vibrator.vibrate(duration, -1);
        Intent record = new Intent("record");
        sendBroadcast(record);

    }

    @Override
    protected void onPause() {
        if (mDefaultAdapter != null){
            mDefaultAdapter.disableForegroundDispatch(this);
            mDefaultAdapter.disableReaderMode(this);
        }
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
