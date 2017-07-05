package com.cmcc.migutvtwo.thirdpart.share;

import android.app.Activity;

import com.cmcc.migutvtwo.thirdpart.Platform;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;

/**
 * Created by caixiangyu on 2017/7/2.
 */

public class WechatCircleShareImpl extends WechatShareImpl {



    public WechatCircleShareImpl(Activity activity) {
        super(activity);
    }

    @Override
    protected int getWechatType() {
        return SendMessageToWX.Req.WXSceneTimeline;
    }

    @Override
    public Platform getPlatform() {
        return Platform.WEICHATCIRLE;
    }
}
