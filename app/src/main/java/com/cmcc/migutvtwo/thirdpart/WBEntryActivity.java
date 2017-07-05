package com.cmcc.migutvtwo.thirdpart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.cmcc.migutvtwo.thirdpart.share.ShareCallBackEvent;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.constant.WBConstants;
import com.sina.weibo.sdk.exception.WeiboException;

import org.greenrobot.eventbus.EventBus;


/**
 * Created by caixiangyu on 2017/2/15.
 * 由于发起sso授权的activity必须重写onactivityresult方法，所以只能跳到这个界面进行授权了
 */

public class WBEntryActivity extends Activity implements IWeiboHandler.Response, WeiboAuthListener, APPConstant {

    //sian微博分享
    private IWeiboShareAPI mWeiboShareAPI;
    private SsoHandler mSsoHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("cxy", this.getLocalClassName());
        int type = getIntent().getIntExtra(TYPE, 0);
        if (type == TYPE_LOGIN) {
            AuthInfo mAuthInfo = new AuthInfo(this, SINA_APP_KEY, SINA_REDIRECT_URL, SINA_SCOPE);
            mSsoHandler = new SsoHandler(this, mAuthInfo);
            mSsoHandler.authorize(this);
        } else {
            try {
//        // 创建微博分享接口实例
                mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(this, "59697319");
                mWeiboShareAPI.registerApp();
                mWeiboShareAPI.handleWeiboResponse(getIntent(), this);
                Log.e("cxy", this.getLocalClassName());
            } catch (Exception e) {
                Log.e("cxy", e.getMessage());
            }
        }
    }

    /**
     * @see {@link Activity#onNewIntent}
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        // 从当前应用唤起微博并进行分享后，返回到当前应用时，需要在此处调用该函数
        // 来接收微博客户端返回的数据；执行成功，返回 true，并调用
        // {@link IWeiboHandler.Response#onResponse}；失败返回 false，不调用上述回调
        mWeiboShareAPI.handleWeiboResponse(intent, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }

    @Override
    public void onResponse(BaseResponse baseResponse) {
        int status = 0;
        if (baseResponse != null) {
            switch (baseResponse.errCode) {
                case WBConstants.ErrorCode.ERR_OK:
                    status = 0;
//                    Helper.showMsg(this, "分享成功");
                    break;
                case WBConstants.ErrorCode.ERR_CANCEL:
//                    Helper.showMsg(this, "分享取消");
                    status = 1;
                    break;
                case WBConstants.ErrorCode.ERR_FAIL:
                    status = 2;
//                    Helper.showMsg(this,"分享失败"+ "Error Message: " + baseResponse.errMsg);
                    break;
            }
        }
        Log.e("cxy", "down");
        EventBus.getDefault().post(new ShareCallBackEvent(status));
        this.finish();
    }

    @Override
    public void onComplete(Bundle bundle) {

//        EventBus.getDefault().post(new SinaAuthEvent(0, bundle));
        this.finish();
    }

    @Override
    public void onWeiboException(WeiboException e) {
//        EventBus.getDefault().post(new SinaAuthEvent(0, null));
        finish();
    }

    @Override
    public void onCancel() {
//        EventBus.getDefault().post(new SinaAuthEvent(0, null));
        finish();
    }
}
