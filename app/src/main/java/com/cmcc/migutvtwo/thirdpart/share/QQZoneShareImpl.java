package com.cmcc.migutvtwo.thirdpart.share;

import android.app.Activity;

import com.tencent.connect.share.QQShare;
import com.tencent.tauth.IUiListener;


/**
 * Created by caixiangyu on 2017/1/7.
 */

public class QQZoneShareImpl extends QQShareImpl implements IUiListener {

    public QQZoneShareImpl(Activity activity) {
        super(activity);
    }

    @Override
    public int getQQType() {
        return 0x00 | QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN;
    }
}
