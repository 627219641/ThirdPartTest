package com.cmcc.migutvtwo.thirdpart.share;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;

import com.cmcc.migutvtwo.R;
import com.cmcc.migutvtwo.thirdpart.APPConstant;
import com.cmcc.migutvtwo.thirdpart.Platform;
import com.cmcc.migutvtwo.thirdpart.login.AccessTokenKeeper;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.utils.Utility;

import java.io.File;

import static com.sina.weibo.sdk.constant.WBConstants.Base.APP_KEY;

/**
 * Created by caixiangyu on 2017/1/6.
 */

public class SinaShareImpl extends IShare implements APPConstant, WeiboAuthListener {

    public IWeiboShareAPI mWeiboShareAPI;
    private SendMultiMessageToWeiboRequest mRequest = new SendMultiMessageToWeiboRequest();
    WeiboMultiMessage mWeiboMessage = new WeiboMultiMessage();
    private AuthInfo mAuthInfo;


    public SinaShareImpl(Activity context) {
        super(context);
    }

    @Override
    public void register() {
        mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(mActivity, SINA_APP_KEY);
        mWeiboShareAPI.registerApp();
    }

    @Override
    public void share() {
        register();
        mAuthInfo = new AuthInfo(mActivity, APP_KEY, SINA_REDIRECT_URL, SINA_SCOPE);
        switch (mShareType) {
            case SHARE_TEXT:

                break;
            case SHARE_IMG:
                ImageObject obj = new ImageObject();
                obj.setImageObject(mBitmap);
                mWeiboMessage.imageObject = obj;
            case SHARE_IMG_TEXT:
                TextObject textObject = new TextObject();
                textObject.text = mTitle + mTargetUrl;
                mWeiboMessage.textObject = textObject;
            case SHARE_LINK:
                WebpageObject mediaObject = new WebpageObject();
                mediaObject.identify = Utility.generateGUID();
                mediaObject.title = mTitle; // 可以为空字符串
                mediaObject.description = mSubTitle; // 可以为空字符串
//
                mediaObject.setThumbImage(mBitmap);
                mediaObject.actionUrl = mTargetUrl;

                mWeiboMessage.mediaObject = mediaObject;
                break;
        }
        mRequest.transaction = String.valueOf(System.currentTimeMillis());
        mRequest.multiMessage = mWeiboMessage;
        Oauth2AccessToken accessToken = AccessTokenKeeper.readAccessToken(mActivity.getApplicationContext());
        String token = "";
        if (accessToken != null) {
            token = accessToken.getToken();
        }
        Log.e("cxy","aaaaaaaa");
        mWeiboShareAPI.sendRequest(mActivity, mRequest, mAuthInfo, token, this);
//        if (mBitmap != null) {
//            register();
//            mAuthInfo = new AuthInfo(mActivity, APP_KEY, SINA_REDIRECT_URL, SINA_SCOPE);
//
//            if (TextUtils.isEmpty(mShareUrl)) {
//                ImageObject obj = new ImageObject();
//                obj.setImageObject(mBitmap);
//                mWeiboMessage.imageObject = obj;
//            } else {
//                // 默认输入的文字
//                if (!TextUtils.isEmpty(getShareDefaultInput())) {
//                    TextObject textObject = new TextObject();
//                    textObject.text = getShareDefaultInput();
//                    mWeiboMessage.textObject = textObject;
//                }
//
//                WebpageObject mediaObject = new WebpageObject();
//                mediaObject.identify = Utility.generateGUID();
//
//                mediaObject.title = getShareTitle(); // 可以为空字符串
//                mediaObject.description = getShareSubTitle(); // 可以为空字符串
//
//                // 设置 Bitmap 类型的图片到视频对象里设置缩略图。 注意：最终压缩过的缩略图大小不得超过32kb。
//                mBitmap = BitmapUtil.getZoomImage(mBitmap, 30);
//                mediaObject.setThumbImage(mBitmap);
//                mediaObject.actionUrl = mShareUrl;
//
//                if (mContentType != CONTENT_LIVE && mContentType != CONTENT_TV_VIDEO
//                        && mContentType != CONTENT_TV_LIVE) {
//                    ImageObject obj = new ImageObject();
//                    obj.setImageObject(mBitmap);
//                    mWeiboMessage.imageObject = obj;
//                }
//                mWeiboMessage.mediaObject = mediaObject;
//            }
//            mRequest.transaction = String.valueOf(System.currentTimeMillis());
//            mRequest.multiMessage = mWeiboMessage;
//            Oauth2AccessToken accessToken = AccessTokenKeeper.readAccessToken(mActivity.getApplicationContext());
//            String token = "";
//            if (accessToken != null) {
//                token = accessToken.getToken();
//            }
//            Log.e("cxy", "tokenf" + token);
//            mWeiboShareAPI.sendRequest(mActivity, mRequest, mAuthInfo, token, this);
//        } else if (mBitmap == null && mImgUrl != null) {
//            loadHttpImg(mImgUrl);
//        } else {
//            if (mCallBackListener != null) {
//                mCallBackListener.onFaild(getPlatformCode());
//            }
//        }
    }

    @Override
    public Platform getPlatform() {
        return Platform.SINA;
    }

    public void clear() {
        mBitmap.recycle();
        mBitmap = null;
    }


    @Override
    public IShare withImg(File file) {
        mBitmap = BitmapFactory.decodeResource(mActivity.getResources(), R.mipmap.ic_launcher);

        return this;
    }


    public boolean isInstallApp() {
        return isAppAvilible("com.sina.weibo");
    }


    @Override
    public void onComplete(Bundle bundle) {
//        Oauth2AccessToken newToken = Oauth2AccessToken.parseAccessToken(bundle);
//        AccessTokenKeeper.writeAccessToken(mActivity, newToken);
//        SharedPreferencesHelper helper = new SharedPreferencesHelper(mActivity.getApplicationContext());
//        helper.setValue(Config.SINA_ACCESS_TOKEN, AccessTokenKeeper.readAccessToken(mActivity).getToken());
//
//        Log.e("cxy", "mWeiboShareAPI.sendRequest（）---完成");
    }

    @Override
    public void onWeiboException(WeiboException e) {
//        ShareException se = new ShareException();
//        se.setErroMsg(e.getMessage());
//        Log.e("cxy", "mWeiboShareAPI.sendRequest（）---异常");
    }

    @Override
    public void onCancel() {
//        Log.e("cxy", "mWeiboShareAPI.sendRequest（）---取消");
    }

}
