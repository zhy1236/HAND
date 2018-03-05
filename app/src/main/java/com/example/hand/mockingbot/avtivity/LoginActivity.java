package com.example.hand.mockingbot.avtivity;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AlertDialog;
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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.hand.mockingbot.R;
import com.example.hand.mockingbot.datamanage.HttpManager;
import com.example.hand.mockingbot.entity.LoginEntity;
import com.example.hand.mockingbot.utils.CommonValues;
import com.example.hand.mockingbot.utils.Fields;
import com.example.hand.mockingbot.utils.HandApp;
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

    public static final int CHOOSE_PICTURE = 1;
    private static final int MY_PERMISSION_REQUEST_CODE = 10000;
    private Button login;
    private EditText mUsername;
    private EditText mPassword;
    private CheckBox savepassword;
    private RelativeLayout rl;
    private ObjectAnimator rotation;
    private CheckBox save;
    private ImageView iv_photo;

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
        boolean isAllGranted = checkPermissionAllGranted(
                new String[] {
                        Manifest.permission.INTERNET,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }
        );
        if (isAllGranted){
            upData();
        }else {
            ActivityCompat.requestPermissions(
                    this,
                    new String[] {
                            Manifest.permission.INTERNET,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    },MY_PERMISSION_REQUEST_CODE
            );
        }
        initView();
    }

    private void upData() {
        PgyUpdateManager.register(this, "");
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
                login(mUsername.getText().toString(), mPassword.getText().toString());

            }
        });
        rl = (RelativeLayout) findViewById(R.id.login_rl);
        iv_photo = (ImageView) findViewById(R.id.login_iv);
        String string = SpUtils.getString(getApplicationContext(), Fields.PHOTO_PATH);
        HandApp.setPhotoUri(getPathForUri(string));
        iv_photo.setImageURI(HandApp.getPhotoUri());
        iv_photo.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                choosePicture();
                return true;
            }
        });
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                Picasso.with(getApplicationContext()).load("http://edb2.hand-china.com:8080/project-mg-app/img/dailyImg/addDaily.png").into(iv_photo);
//            }
//        });

    }

    private void choosePicture() {
        Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
        startActivityForResult(intent, CHOOSE_PICTURE);
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
                        savepassword.setEnabled(true);
                        login.setEnabled(true);
                        mUsername.setEnabled(true);
                        mPassword.setEnabled(true);
                        rl.clearAnimation();
                        HandApp.setLoginEntity(loginentity);
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
                        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK){
            if (requestCode == CHOOSE_PICTURE){
                if (data.getData() != null) {
                    iv_photo.setImageURI(data.getData());
                    HandApp.setPhotoUri(data.getData());
                    SpUtils.saveString(getApplicationContext(),Fields.PHOTO_PATH,HttpManager.getRealPathFromUri(data.getData(), getApplicationContext()));
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private Uri getPathForUri(String path) {
        Uri mUri = Uri.parse("content://media/external/images/media");
        Uri mImageUri = null;
        Cursor cursor = managedQuery(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null,
                null, MediaStore.Images.Media.DEFAULT_SORT_ORDER);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String str = cursor.getString(cursor
                    .getColumnIndex(MediaStore.MediaColumns.DATA));
            if (path.equals(str)) {
                int ringtoneID = cursor.getInt(cursor
                        .getColumnIndex(MediaStore.MediaColumns._ID));
                mImageUri = Uri.withAppendedPath(mUri, ""+ ringtoneID);
                break;
            }
            cursor.moveToNext();
        }
        return mImageUri;
    }

    private boolean checkPermissionAllGranted(String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                // 只要有一个权限没有被授予, 则直接返回 false
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSION_REQUEST_CODE) {
            boolean isAllGranted = true;

            // 判断是否所有的权限都已经授予了
            for (int grant : grantResults) {
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    isAllGranted = false;
                    break;
                }
            }

            if (isAllGranted) {
                upData();
            }else {
                openAppDetails();
            }
        }
    }

    private void openAppDetails() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("APP更新需要访问 “网络” ，请到 “应用信息 -> 权限” 中授予！");
        builder.setPositiveButton("去手动授权", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.setData(Uri.parse("package:" + getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }
}
