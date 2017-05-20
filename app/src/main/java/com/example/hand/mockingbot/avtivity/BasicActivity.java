package com.example.hand.mockingbot.avtivity;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.hand.mockingbot.R;
import com.example.hand.mockingbot.utils.SystemBarTintManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by zhy on 2017/5/16.
 */

public class BasicActivity extends AppCompatActivity {

    public AlertDialog dialog;
    public static final int TAKE_PICTURE = 0;
    public static final int CHOOSE_PICTURE = 1;
    public static final int CHOOSE_FILE = 2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { //系统版本大于19
            setTranslucentStatus(true);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.colorAccent);
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;        // a|=b的意思就是把a和b按位或然后赋值给a   按位或的意思就是先把a和b都换成2进制，然后用或操作，相当于a=a|b
        } else {
            winParams.flags &= ~bits;        //&是位运算里面，与运算  a&=b相当于 a = a&b  ~非运算符
        }
        win.setAttributes(winParams);
    }

    public String getData(boolean b){
        if (b){
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date curDate = new Date(System.currentTimeMillis());//获取当前时间
            String str = formatter.format(curDate);
            return str;
        }else {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date curDate = new Date(System.currentTimeMillis());//获取当前时间
            String str = formatter.format(curDate);
            return str;

        }
    }

    public String getTime(long submitDate){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = formatter.format(submitDate);
        return str;
    }

    public void showDialog() {
//        if (dialog == null) {
//            dialog = new AlertDialog.Builder(this).setItems(new CharSequence[]{"相机","相册","手机文件"}, new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    if (which == 0) {
//                        Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                        startActivityForResult(it, 0);
//                    } else if (which == 1) {
                        choosePicture();
//                    } else {
//                        chooseFile();
//                    }
//                }
//            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.dismiss();
//                }
//            }).create();
//        }
//        dialog.show();
    }

    private void choosePicture() {
        Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
        startActivityForResult(intent, CHOOSE_PICTURE);
    }

    private void chooseFile() {
        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult(Intent.createChooser(intent,"请选择要上传的文件"),CHOOSE_FILE);
        }catch (ActivityNotFoundException ex){
        }
    }

    public enum FileType {
        IMAGE,WORD,EXCEL,RAR
    }

    public void showDateTimePicker(final TextView view, boolean bolean) {
        final Calendar select = Calendar.getInstance();
        try {
            select.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(view.getText().toString()));
        } catch (Exception e) {
            select.setTime(new Date());
            select.set(Calendar.HOUR_OF_DAY, 0);
            select.set(Calendar.MINUTE, 0);
            select.set(Calendar.SECOND, 0);
        }
        final DatePickerDialog datePickerDialog = new DatePickerDialog(
                this, null, select.get(Calendar.YEAR), select.get(Calendar.MONTH), select.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                select.set(Calendar.YEAR, datePickerDialog.getDatePicker().getYear());
                select.set(Calendar.MONTH, datePickerDialog.getDatePicker().getMonth());
                select.set(Calendar.DAY_OF_MONTH, datePickerDialog.getDatePicker().getDayOfMonth());
                view.setText(new SimpleDateFormat("yyyy-MM-dd").format(select.getTime()));
            }
        });
        datePickerDialog.show();

    }

}

