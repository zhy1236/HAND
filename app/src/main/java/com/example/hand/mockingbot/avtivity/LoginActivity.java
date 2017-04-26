package com.example.hand.mockingbot.avtivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.hand.mockingbot.R;
import com.example.hand.mockingbot.datamanage.HttpManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by zhy on 2017/4/21.
 */

public class LoginActivity extends AppCompatActivity {

    private Button login;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
        setContentView(R.layout.actovity_login);
        initView();
    }

    private void initView() {
        login = (Button) findViewById(R.id.login_btn_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> param = new HashMap<>();
                param.put("account","xide.han");
                param.put("password","123123");
                String url = "http://192.168.11.198:8088/project-mg/login";
                try {
                    HttpManager.getInstance().post(url, param, loginentity.class, new HttpManager.ResultCallback<loginentity>() {
                        @Override
                        public void onSuccess(String json, loginentity loginentity) throws InterruptedException {
                            Intent intent = new Intent();
                            intent.setClass(getApplicationContext(),MainActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(String msg) {

                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
