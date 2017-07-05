/*
 * 官网地站:http://www.mob.com
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 mob.com. All rights reserved.
 */

package com.cmcc.migutvtwo.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.cmcc.migutvtwo.thirdpart.APPConstant;
import com.cmcc.migutvtwo.thirdpart.share.ShareCallBackEvent;
import com.tencent.connect.common.Constants;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.greenrobot.eventbus.EventBus;


/**
 * 微信客户端回调activity示例
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler, APPConstant {
    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("cxy", this.getLocalClassName());
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, WECHAT_APP_KEY, false);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_QQ_SHARE) {
            Tencent mTencent = Tencent.createInstance(QQ_APP_KEY, this.getApplicationContext());
            mTencent.onActivityResultData(requestCode, resultCode, data, new IUiListener() {
                @Override
                public void onComplete(Object o) {
                    EventBus.getDefault().post(new ShareCallBackEvent(0));
                }

                @Override
                public void onError(UiError uiError) {
                    EventBus.getDefault().post(new ShareCallBackEvent(2));
                }

                @Override
                public void onCancel() {
                    EventBus.getDefault().post(new ShareCallBackEvent(1));
                }
            });
        }
    }

    @Override
    public void onReq(BaseReq baseReq) {
        Toast.makeText(this, "响应回来了", Toast.LENGTH_LONG).show();
    }


    @Override
    public void onResp(BaseResp resp) {

//        WechatAuthEvent envent = new WechatAuthEvent();
//        envent.code = resp.errCode;
//        envent.mResp = resp;

        String result = "";
        String status = "0";
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                result = "发送成功";
                status = "0";

                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = "取消发送";
                status = "1";
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = "发送拒绝";
                status = "1";
                break;
            default:
                result = "未知错误";
                status = "2";
                break;
        }

//        EventBus.getDefault().post(envent);
        EventBus.getDefault().post(new ShareCallBackEvent(Integer.parseInt(status)));
        finish();
    }

    /**
     * 处理微信发出的向第三方应用请求app message
     * <p>
     * 在微信客户端中的聊天页面有“添加工具”，可以将本应用的图标添加到其中
     * 此后点击图标，下面的代码会被执行。Demo仅仅只是打开自己而已，但你可
     * 做点其他的事情，包括根本不打开任何页面
     */
//	public void onGetMessageFromWXReq(WXMediaMessage msg) {
//		Intent iLaunchMyself = getPackageManager().getLaunchIntentForPackage(getPackageName());
//		startActivity(iLaunchMyself);
//	}
//
//	/**
//	 * 处理微信向第三方应用发起的消息
//	 * <p>
//	 * 此处用来接收从微信发送过来的消息，比方说本demo在wechatpage里面分享
//	 * 应用时可以不分享应用文件，而分享一段应用的自定义信息。接受方的微信
//	 * 客户端会通过这个方法，将这个信息发送回接收方手机上的本demo中，当作
//	 * 回调。
//	 * <p>
//	 * 本Demo只是将信息展示出来，但你可做点其他的事情，而不仅仅只是Toast
//	 */
//	public void onShowMessageFromWXReq(WXMediaMessage msg) {
//		if (msg != null && msg.mediaObject != null
//				&& (msg.mediaObject instanceof WXAppExtendObject)) {
//			WXAppExtendObject obj = (WXAppExtendObject) msg.mediaObject;
//			Toast.makeText(this, obj.extInfo, Toast.LENGTH_SHORT).show();
//		}
//	}

}
