package com.cmcc.migutvtwo.thirdpart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.cmcc.migutvtwo.thirdpart.share.ShareCallBackEvent;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.greenrobot.eventbus.EventBus;

import static com.tencent.connect.common.Constants.REQUEST_QQ_SHARE;

/**
 * Created by caixiangyu on 2017/2/18.
 */

public class QQEntryActivity extends Activity implements APPConstant, IUiListener {

    private Tencent mTencent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int type = getIntent().getIntExtra(TYPE, 0);

        Bundle bundle = getIntent().getExtras();
        mTencent = Tencent.createInstance(QQ_APP_KEY, getApplicationContext());
        if (TYPE_SHARE == type) {
            mTencent.shareToQQ(this, bundle, this);
        } else if (TYPE_LOGIN == type) {
            mTencent.login(this, "all", this);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("cxy", getLocalClassName());
        Log.e("cxy", requestCode + "");
        if (requestCode == REQUEST_QQ_SHARE || requestCode == Constants.REQUEST_LOGIN) {
            Tencent.onActivityResultData(requestCode, resultCode, data, this);
        }
    }

    public void onComplete(Object o) {
        EventBus.getDefault().post(new ShareCallBackEvent(0));
//        EventBus.getDefault().post(new QQAuthEvent(0, o));
        finish();
    }

    @Override
    public void onError(UiError uiError) {
        EventBus.getDefault().post(new ShareCallBackEvent(2));
//        EventBus.getDefault().post(new QQAuthEvent(1, uiError));
        finish();
    }

    @Override
    public void onCancel() {
        EventBus.getDefault().post(new ShareCallBackEvent(1));
//        EventBus.getDefault().post(new QQAuthEvent(1, null));
        finish();
    }
}
