package com.example.hand.mockingbot.avtivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hand.mockingbot.R;
import com.example.hand.mockingbot.adapter.ListAdapter;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Oliver on 2016/11/15.
 */
public class ActionSheetActivity extends Activity {

    private static final String DATA = "DATA";
    private static final String DEFAULT_DATA = "DEFAULT_DATA";
    private static String mTitle;
    public static Boolean[] array;
    private int checkPosition = -1;
    private String[] data;

    public interface OnResult {
        void onResult(int index, String value);
    }

    private static OnResult mResult;

    public static void openActionSheet(Activity context, String title, String[] data, String defData, OnResult result) {
        Intent intent = new Intent(context, ActionSheetActivity.class);
        mResult = result;
        mTitle = title;
        intent.putExtra(DATA, data);
        intent.putExtra(DEFAULT_DATA, defData);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_sheet);
        TextView tv_title = (TextView) findViewById(R.id.action_sheet_title);
//        tv_title.setText(mTitle);
        View viewById = findViewById(R.id.action_sheet_rl);
        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        final ListView listView = (ListView) findViewById(R.id.lst_container);
        TextView btnCancel = (TextView) findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        data = getIntent().getExtras().getStringArray(DATA);
        String defData = getIntent().getExtras().getString(DEFAULT_DATA);
        listView.setFilterTouchesWhenObscured(false);
        if (data != null) {
            final List<String> mData = Arrays.asList(data);
            ListAdapter<String> adapter = new ListAdapter<String>(mData,R.layout.list_item_checked_text_color) {
                @Override
                public void bindView(ViewHolder holder, String obj,int position) {
                    holder.setText(R.id.text,obj);
                }
            };
            listView.setAdapter(adapter);
            listView.setTextFilterEnabled(true);
            if (defData != null) {
                int index = -1;
                for (int i = 0; i < data.length; i++) {
                    if (data[i].equals(defData)) {
                        index = i;
                    }
                }
                listView.setItemChecked(index, true);
                listView.setSelection(index);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        listView.setItemChecked(position, true);
                        if (checkPosition != -1){
                            listView.getChildAt(checkPosition).setBackgroundColor(0xffffffff);
                        }
                        listView.getChildAt(position).setBackgroundColor(0xfff4f4f4);
                        checkPosition = position;
                    }
                });
            }
        }
        TextView tv_add =  (TextView) findViewById(R.id.btn_add);
        tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkPosition == -1){
                    mResult.onResult(checkPosition, "");
                    finish();
                }else {
                    mResult.onResult(checkPosition, data[checkPosition]);
                    finish();
                }
            }
        });
    }

}
