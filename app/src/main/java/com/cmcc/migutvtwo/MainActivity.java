package com.cmcc.migutvtwo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cmcc.migutvtwo.thirdpart.Platform;
import com.cmcc.migutvtwo.thirdpart.ThirdPartContext;
import com.cmcc.migutvtwo.thirdpart.share.IShare;
import com.cmcc.migutvtwo.thirdpart.share.ShareCallBackListener;
import com.cmcc.migutvtwo.thirdpart.share.ShareType;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private android.widget.Button btnShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.btnShow = (Button) findViewById(R.id.btnShow);

        btnShow.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                IShare iShare = ThirdPartContext.getShareContext(MainActivity.this, Platform.SINA);
                iShare.withTitle("aaaaaaa").withSubTitle("bbbbbb").withImg(new File("")).withShareType(ShareType.SHARE_LINK).withTargetUrl("http://baidu.com")
                        .setCallBackListener(new ShareCallBackListener() {
                            @Override
                            public void onSuccess(Platform platform) {
                                Toast.makeText(MainActivity.this, "onSuccess", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onFaild(Platform platform) {
                                Toast.makeText(MainActivity.this, "onFaild", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onCancel(Platform platform) {
                                Toast.makeText(MainActivity.this, "onCancel", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onNotInstallApp(Platform platform) {
                                Toast.makeText(MainActivity.this, "onNotInstallApp", Toast.LENGTH_LONG).show();
                            }
                        }).share();
            }
        });
    }
}
