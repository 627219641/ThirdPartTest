package com.cmcc.migutvtwo.thirdpart.share;


import com.cmcc.migutvtwo.thirdpart.Platform;

public abstract class ShareCallBackListener {
    public void onStart(Platform platform){

    }
    public void onFinish(Platform platform){

    }
    public abstract void onSuccess(Platform platform);
    public abstract void onFaild(Platform platform);
    public abstract void onCancel(Platform platform);
    public void onNotInstallApp(Platform platform){

    }
}