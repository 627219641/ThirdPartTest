package com.cmcc.migutvtwo.thirdpart.share;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.cmcc.migutvtwo.R;
import com.cmcc.migutvtwo.thirdpart.Platform;
import com.cmcc.migutvtwo.thirdpart.QQEntryActivity;
import com.tencent.connect.share.QQShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;

import static com.tencent.connect.share.QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE;


/**
 * Created by caixiangyu on 2017/1/7.
 */

public class QQShareImpl extends IShare implements IUiListener {
    private static final int SHARE_TYPE = 0x00 | SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE; //这个是用来区分分享到QQ好友还是QQ空间的参数

    private Tencent mTencent;
    //用来存放待发送的信息
    private Bundle mBundle = new Bundle();
    // 默认是文本发送类型，这里暂时支持两种模式，以后有需要再扩充。
    // 第一种是SHARE_TO_QZONE_TYPE_IMAGE_TEXT（连接模式）
    // 第二种是QZoneShare_radioBtn_image_share（说说模式）
    private static final int TYPE_DEFAULT = QQShare.SHARE_TO_QQ_TYPE_DEFAULT;


    private ArrayList<String> mPics = new ArrayList<>();

    public QQShareImpl(Activity activity) {
        super(activity);
    }

    @Override
    public void register() {
        mTencent = Tencent.createInstance(QQ_APP_KEY, mActivity.getApplicationContext());
    }

    @Override
    public void share() {
        if (isInstallApp()) {
            register();
            Intent intent = new Intent(mActivity, QQEntryActivity.class);
            switch (mShareType) {
                case SHARE_TEXT:
                    mBundle.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, TYPE_DEFAULT);
                    mBundle.putInt(QQShare.SHARE_TO_QQ_EXT_INT, getQQType());

                    mBundle.putString(QQShare.SHARE_TO_QQ_TITLE, mTitle); // 不可以为空字符串


                case SHARE_IMG:
                    //此参数除了图片特殊外其他都是default
                    mBundle.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
                    mBundle.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, mPics.get(0));
                    mBundle.putString(QQShare.SHARE_TO_QQ_APP_NAME, mActivity.getResources().getString(R.string.app_name));
                    mBundle.putInt(QQShare.SHARE_TO_QQ_EXT_INT, getQQType());

                    break;
                case SHARE_IMG_TEXT:
                case SHARE_LINK:
//                    mBundle.putString(mPics.get(0).startsWith("http") ? QQShare.SHARE_TO_QQ_IMAGE_URL : QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, mPics.get(0));

                    if (mPics.get(0).startsWith("http")) {
                        mBundle.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, mPics.get(0));
                        mBundle.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
                    } else {
                        mBundle.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, mPics.get(0));
                    }

                    mBundle.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, TYPE_DEFAULT);
                    mBundle.putInt(QQShare.SHARE_TO_QQ_EXT_INT, getQQType());

                    mBundle.putString(QQShare.SHARE_TO_QQ_TITLE, mTitle); // 不可以为空字符串
                    mBundle.putString(QQShare.SHARE_TO_QQ_SUMMARY, mSubTitle); // 可以为空字符串
                    mBundle.putString(QQShare.SHARE_TO_QQ_TARGET_URL, mTargetUrl);
                    break;
            }

            intent.putExtra(TYPE, TYPE_SHARE);
            intent.putExtras(mBundle);
            mActivity.startActivity(intent);
//
//            mBundle.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, mShareType);
//            mBundle.putInt(QQShare.SHARE_TO_QQ_EXT_INT, getQQType());
//
//            mBundle.putString(QQShare.SHARE_TO_QQ_TITLE, getShareTitle()); // 不可以为空字符串
//            mBundle.putString(QQShare.SHARE_TO_QQ_SUMMARY, getShareSubTitle()); // 可以为空字符串
//            mBundle.putString(QQShare.SHARE_TO_QQ_TARGET_URL, mUrl);
//
//            if (!mPics.isEmpty()) {
//                mBundle.putString(mPics.get(0).startsWith("http") ? QQShare.SHARE_TO_QQ_IMAGE_URL : QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, mPics.get(0));
//
//                if (mPics.get(0).startsWith("http")) {
//                    mBundle.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, mPics.get(0));
//                    mBundle.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
//                } else {
//                    mBundle.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, mPics.get(0));
//                }
//            }
//
//            mBundle.putString(QQShare.SHARE_TO_QQ_APP_NAME, mActivity.getResources().getString(R.string.app_name));
//
//            if (TextUtils.isEmpty(mUrl)) {
//                mBundle.clear();
//                mBundle.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
//                mBundle.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, mPics.get(0));
//                mBundle.putString(QQShare.SHARE_TO_QQ_APP_NAME, mActivity.getResources().getString(R.string.app_name));
//                mBundle.putInt(QQShare.SHARE_TO_QQ_EXT_INT, getShareType());
//            }
//
//            Intent intent = new Intent(mActivity, QQEntryActivity.class);
//            intent.putExtras(mBundle);
//            intent.putExtra(TYPE, TYPE_SHARE);
//
//            mActivity.startActivity(intent);
        } else {
            mCallBackListener.onNotInstallApp(getPlatform());
        }
    }

    @Override
    public Platform getPlatform() {
        return Platform.QQ;
    }

    @Override
    public IShare withImg(File file) {
//        if (file.exists()) {
            mPics.add("http://img.blog.csdn.net/20170704093309726?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQveXloMzUyMDkxNjI2/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast");
//        }
        return this;
    }


    public int getQQType() {
        return 0x00 | SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE;//隐藏QQ空间，就是分享到好友
    }

    public boolean isInstallApp() {
        return isAppAvilible("com.tencent.mobileqq") || isAppAvilible("com.tencent.mobileqqi") || isAppAvilible("com.tencent.minihd.qq") || isAppAvilible("com.tencent.qq.kddi");
    }

    @Override
    public void onComplete(Object o) {
        EventBus.getDefault().post(new ShareCallBackEvent(1));
    }

    @Override
    public void onError(UiError uiError) {
        EventBus.getDefault().post(new ShareCallBackEvent(1));
    }

    @Override
    public void onCancel() {
        EventBus.getDefault().post(new ShareCallBackEvent(1));
    }
}
