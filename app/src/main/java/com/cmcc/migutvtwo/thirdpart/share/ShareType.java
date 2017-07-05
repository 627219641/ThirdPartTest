package com.cmcc.migutvtwo.thirdpart.share;

/**
 * Created by caixiangyu on 2017/7/2.
 */

public enum ShareType {
    SHARE_TEXT, //单纯文字
    SHARE_IMG,  //单纯图片
    SHARE_IMG_TEXT,  //图文（如果有链接URL，则拼在文字后面）
    SHARE_LINK;  // 链接分享

}
