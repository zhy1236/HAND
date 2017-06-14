package com.example.hand.mockingbot.avtivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hand.mockingbot.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Oliver on 2016/11/15.
 */
public class ActionSheetActivity2 extends Activity {

    private static String mTitle;
    private static String startTime;
    private static String endTime;
    private static boolean checked;
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private CheckBox ch;
    private EditText editText;

    public interface OnResult {
        void onResult(String startTime, String endTime,String Keyword,boolean isOrNot);
    }

    private static OnResult mResult;
    private static String hintstr;

    public static void openActionSheet(Activity context,String starttime,String endtime,boolean check, String hint, String data, OnResult result) {
        Intent intent = new Intent(context, ActionSheetActivity2.class);
        mResult = result;
        mTitle = data;
        hintstr = hint;
        startTime = starttime;
        endTime = endtime;
        checked = check;
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_sheet2);
        initView();

    }

    private void initView() {
        tv1 = (TextView) findViewById(R.id.action_sheet_tv1);
        tv1.setText(startTime);
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateTimePicker(tv1);
            }
        });
        tv2 = (TextView) findViewById(R.id.action_sheet_tv2);
        tv2.setText(endTime);
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateTimePicker(tv2);
            }
        });
        tv3 = (TextView) findViewById(R.id.action_sheet_tv3);
        tv3.setText(mTitle);
        ch = (CheckBox) findViewById(R.id.action_sheet_cb);
        ch.setChecked(checked);
        editText = (EditText) findViewById(R.id.action_sheet_edt);
        editText.setHint(hintstr);
        ch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                checked = b;
            }
        });
        Button btn1 = (Button) findViewById(R.id.action_sheet_but1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mResult.onResult("","","",checked);
                finish();
            }
        });
        Button btn2 = (Button) findViewById(R.id.action_sheet_but2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mResult.onResult(tv1.getText().toString(),tv2.getText().toString(),editText.getText().toString(),checked);
                finish();
            }
        });
    }

    public void showDateTimePicker(final TextView view) {
        final Calendar select = Calendar.getInstance();
        try {
            select.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(view.getText().toString()));
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
