package com.cmcc.migutvtwo.thirdpart;

/**
 * Created by caixiangyu on 2017/1/11.
 */

public interface APPConstant {

    int WECAHT=0;
    int WECHATCIRCLE=1;
    int QQ=2;
    int SINA=3;
    int QQZONE=4;

    String TYPE="type";
    int TYPE_SHARE = 0x1;
    int TYPE_LOGIN = 0x2;

    /*第三方登录信息咪咕平台*/
    String appid = "appid";
    String appkey = "appkey";//正式环境


    /*第三方登录信息其他平台*/
    String QQ_APP_KEY = "00000000";
    String WECHAT_APP_KEY = "appkey";

    String WECHAT_APP_SEC = "appsec";


    String SINA_APP_KEY = "0000000";
    String SINA_REDIRECT_URL = "http://sns.whalecloud.com/sina2/callback";
    String SINA_SCOPE =
            "email,direct_messages_read,direct_messages_write,"
                    + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
                    + "follow_app_official_microblog," + "invitation_write";
}
