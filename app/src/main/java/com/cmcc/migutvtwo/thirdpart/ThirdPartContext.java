package com.cmcc.migutvtwo.thirdpart;

import android.app.Activity;

import com.cmcc.migutvtwo.thirdpart.share.IShare;
import com.cmcc.migutvtwo.thirdpart.share.QQShareImpl;
import com.cmcc.migutvtwo.thirdpart.share.QQZoneShareImpl;
import com.cmcc.migutvtwo.thirdpart.share.SinaShareImpl;
import com.cmcc.migutvtwo.thirdpart.share.WechatCircleShareImpl;
import com.cmcc.migutvtwo.thirdpart.share.WechatShareImpl;


/**
 * Created by caixiangyu on 2017/2/19.
 */

public class ThirdPartContext implements APPConstant{
    public static IShare getShareContext(Activity activity, Platform platform){
        IShare iShare=null;
        switch (platform){
            case SINA:
                iShare=new SinaShareImpl(activity);
                break;
            case WEICHATCIRLE:
                iShare=new WechatCircleShareImpl(activity);
                break;
            case WEICHAT:
                iShare=new WechatShareImpl(activity);
                break;
            case QQZONE:
                iShare=new QQZoneShareImpl(activity);
                break;
            case QQ:
                iShare=new QQShareImpl(activity);
                break;
        }
        return  iShare;
    }

//    public static ILogin getLoginContext(Activity activity,Platform platform){
//        {
//            ILogin iLogin=null;
//            switch (platform){
//                case SINA:
//                    iLogin=new SinaLoginImpl(activity);
//                    break;
//
//                case WECHAT:
//                    iLogin=new WechatLoginImple(activity);
//                    break;
//                case QQ:
//                    iLogin=new QQLoginImpl(activity);
//                    break;
//            }
//            return  iLogin;
//        }
//    }
}
