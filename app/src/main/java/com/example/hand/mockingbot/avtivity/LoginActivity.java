package com.example.hand.mockingbot.avtivity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.hand.mockingbot.R;
import com.example.hand.mockingbot.datamanage.HttpManager;
import com.example.hand.mockingbot.entity.LoginEntity;
import com.example.hand.mockingbot.utils.CommonValues;
import com.example.hand.mockingbot.utils.Fields;
import com.example.hand.mockingbot.utils.SpUtils;
import com.pgyersdk.update.PgyUpdateManager;

import java.util.HashMap;
import java.util.Map;

import static android.animation.ObjectAnimator.ofFloat;
import static com.example.hand.mockingbot.utils.Fields.SAVE_PASSWORD;
import static com.example.hand.mockingbot.utils.Fields.USERID;


/**
 * Created by zhy on 2017/4/21.
 */

public class LoginActivity extends AppCompatActivity {

    private Button login;
    private EditText mUsername;
    private EditText mPassword;
    private CheckBox savepassword;
    private RelativeLayout rl;
    private ObjectAnimator rotation;
    private CheckBox save;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        ViewGroup mContentView = (ViewGroup) findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            ViewCompat.setFitsSystemWindows(mChildView, false);
        }
        setContentView(R.layout.actovity_login);
        upData();
        initView();
    }

    private void upData() {
        PgyUpdateManager.register(this);
    }

    private void initView() {
        login = (Button) this.findViewById(R.id.login_btn_login);
        save = (CheckBox) findViewById(R.id.login_cb_savepassword);
        savepassword = (CheckBox) findViewById(R.id.login_cb_savepassword);
        mUsername = (EditText) findViewById(R.id.login_en_username);
        mPassword = (EditText) findViewById(R.id.login__ed_password);
        mUsername.setText(SpUtils.getString(getApplicationContext(),Fields.USERID));
        mPassword.setText(SpUtils.getString(getApplicationContext(),Fields.PASSWORD));
        save.setChecked(SpUtils.getisBoolean_false(getApplicationContext(),Fields.SAVE_PASSWORD,false));
        onUsernameChanged();
        mPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                onPasswordChanged();
            }
        });
        mUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                onUsernameChanged();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rotation = ofFloat(rl, "rotation", 0.0F, 360.0F);
                rotation.setDuration(1000);
                rotation.setRepeatMode(ValueAnimator.RESTART);
                rotation.setRepeatCount(ObjectAnimator.INFINITE);
                rotation.start();
                SpUtils.saveisBoolean(getApplicationContext(), SAVE_PASSWORD,savepassword.isChecked());
                SpUtils.saveString(getApplicationContext(), USERID, mUsername.getText().toString());
                if (savepassword.isChecked()){
                    SpUtils.saveString(getApplicationContext(), Fields.PASSWORD,mPassword.getText().toString());
                    SpUtils.saveisBoolean(getApplicationContext(),Fields.SAVE_PASSWORD,true);
                }else {
                    SpUtils.saveString(getApplicationContext(),Fields.PASSWORD,"");
                    SpUtils.saveisBoolean(getApplicationContext(),Fields.SAVE_PASSWORD,false);
                }
                savepassword.setEnabled(false);
                login.setEnabled(false);
                mUsername.setEnabled(false);
                mPassword.setEnabled(false);
                login.setEnabled(false);
                login(mUsername.getText().toString(), mPassword.getText().toString());

            }
        });
        rl = (RelativeLayout) findViewById(R.id.login_rl);

    }

    private void login(String name, String pass) {
        Map<String, Object> param = new HashMap<>();
        param.put("account",name);
        param.put("password",pass);
        HttpManager.getInstance().post(CommonValues.LOGIN, param, LoginEntity.class, new HttpManager.ResultCallback<LoginEntity>() {
            @Override
            public void onSuccess(final String json, final LoginEntity loginentity) throws InterruptedException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        rotation.end();
                        login.setEnabled(true);
                        savepassword.setEnabled(true);
                        login.setEnabled(true);
                        mUsername.setEnabled(true);
                        mPassword.setEnabled(true);
                        rl.clearAnimation();
                        if (loginentity.getResult() != null){
                            Intent intent = new Intent();
                            intent.setClass(getApplicationContext(),MainActivity.class);
                            startActivity(intent);
                            LoginActivity.this.finish();
                        }else {
                            Toast.makeText(getApplicationContext(),loginentity.getError().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }

            @Override
            public void onFailure(final String msg) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        rotation.end();
                        savepassword.setEnabled(true);
                        login.setEnabled(true);
                        mUsername.setEnabled(true);
                        mPassword.setEnabled(true);
//                        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        intent.setClass(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                        LoginActivity.this.finish();
                    }
                });
            }
        });
    }

    private void onUsernameChanged() {
        if (mUsername.length() > 0 && mPassword.length() > 0) {
            login.setEnabled(true);
        } else {
            login.setEnabled(false);
        }
    }

    private void onPasswordChanged() {
        onUsernameChanged();
    }

}
