package com.cmcc.migutvtwo.thirdpart.share;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.cmcc.migutvtwo.R;
import com.cmcc.migutvtwo.thirdpart.Platform;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXImageObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * Created by caixiangyu on 2017/7/2.
 */

public class WechatShareImpl extends IShare {

    private static final int THUMB_SIZE = 150;
    private IWXAPI mIWXAPI;


    public WechatShareImpl(Activity activity) {
        super(activity);
    }

    @Override
    public IShare withImg(File file) {
        mBitmap = BitmapFactory.decodeResource(mActivity.getResources(), R.mipmap.ic_launcher);
        return this;
    }

    @Override
    public void share() {
        register();
        if (mIWXAPI.isWXAppInstalled()) {
            mCallBackListener.onStart(Platform.WEICHAT);

            WXMediaMessage msg = null;
            switch (mShareType) {
                case SHARE_TEXT:

                    // 初始化一个WXTextObject对象
                    WXTextObject textObj = new WXTextObject();
                    textObj.text = mTitle;

                    // 用WXTextObject对象初始化一个WXMediaMessage对象
                    msg = new WXMediaMessage();
                    msg.mediaObject = textObj;
                    // 发送文本类型的消息时，title字段不起作用
                    // msg.title = "Will be ignored";
                    msg.description = mTitle;
                    break;
                case SHARE_IMG:
                    WXImageObject imgObj = new WXImageObject(mBitmap);

                    msg = new WXMediaMessage();
                    msg.mediaObject = imgObj;

                    Bitmap thumbBmp = Bitmap.createScaledBitmap(mBitmap, THUMB_SIZE, THUMB_SIZE, true);
                    msg.thumbData = bmpToByteArray(thumbBmp, true);  // 设置缩略图
                case SHARE_IMG_TEXT:
                case SHARE_LINK:
                    WXWebpageObject webpage = new WXWebpageObject();
                    webpage.webpageUrl = mTargetUrl;

                    msg = new WXMediaMessage(webpage);
                    msg.title = mTitle; // 可以为空字符串
                    msg.description = mSubTitle; // 可以为空字符串

                    Log.e("cxy", (mBitmap == null) + " mBitmap");
                    msg.thumbData = bmpToByteArray(mBitmap, true);

                    break;
            }
            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = buildTransaction("text"); // transaction字段用于唯一标识一个请求
            req.message = msg;
            req.scene = getWechatType();

            // 调用api接口发送数据到微信
            mIWXAPI.sendReq(req);
        } else {
            mCallBackListener.onNotInstallApp(Platform.WEICHAT);
        }
    }

    @Override
    public Platform getPlatform() {
        return Platform.WEICHAT;
    }

    @Override
    protected void register() {
        mIWXAPI = WXAPIFactory.createWXAPI(mActivity, WECHAT_APP_KEY);
        mIWXAPI.registerApp(WECHAT_APP_KEY);
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    //发送类型,会话框或者朋友圈
    protected int getWechatType() {
        return SendMessageToWX.Req.WXSceneSession;
    }

    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
