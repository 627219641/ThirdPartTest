package com.cmcc.migutvtwo.thirdpart.share;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;

import com.cmcc.migutvtwo.thirdpart.APPConstant;
import com.cmcc.migutvtwo.thirdpart.Platform;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.List;

/**
 * Created by caixiangyu on 2017/6/30.
 */

public abstract class IShare implements APPConstant {
    protected ShareCallBackListener mCallBackListener;
    protected ShareType mShareType;
    protected Activity mActivity;
    protected String mTitle;
    protected String mSubTitle;
    protected String mTargetUrl;
    protected Bitmap mBitmap;


    public IShare(Activity activity) {
        this.mActivity = activity;
    }

    public IShare withTargetUrl(String url) {
        this.mTargetUrl = url;
        return this;
    }

    public IShare withTitle(String title) {
        this.mTitle = title;
        return this;
    }

    public IShare withSubTitle(String subTitle) {
        this.mSubTitle = subTitle;
        return this;
    }

    public IShare withShareType(ShareType type) {
        this.mShareType = type;
        return this;
    }

    public abstract IShare withImg(File file);

    public abstract void share();

    public abstract Platform getPlatform();

    protected abstract void register();

    public IShare setCallBackListener(ShareCallBackListener callBackListener) {
        this.mCallBackListener = callBackListener;
        if (!EventBus.getDefault().isRegistered(IShare.this)) {
            EventBus.getDefault().register(IShare.this);
        }
        return this;
    }

    @Subscribe(threadMode= ThreadMode.MAIN)
    public void onEvent(ShareCallBackEvent event) {
        EventBus.getDefault().unregister(this);
        if (mCallBackListener != null) {
            if (event.status == 0) {
                mCallBackListener.onSuccess(getPlatform());
            }else if(event.status==1){
                mCallBackListener.onCancel(getPlatform());
            }else{
                mCallBackListener.onFaild(getPlatform());
            }
        }
    }


    public boolean isAppAvilible(String packageName) {
        if (mActivity != null
                && mActivity.getPackageManager() != null) {
            final PackageManager packageManager = mActivity.getPackageManager();
            List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
            for (int i = 0; i < pinfo.size(); i++) {
                if (pinfo != null
                        && pinfo.get(i) != null
                        && pinfo.get(i).packageName != null
                        && pinfo.get(i).packageName.equalsIgnoreCase(packageName)) {
                    return true;
                }
            }
        }
        return false;
    }
}
