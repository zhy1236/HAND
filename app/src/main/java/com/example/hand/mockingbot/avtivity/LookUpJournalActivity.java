package com.example.hand.mockingbot.avtivity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hand.mockingbot.R;
import com.example.hand.mockingbot.utils.SystemBarTintManager;

/**
 * Created by zhy on 2017/5/8.
 */

public class LookUpJournalActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Button right;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { //系统版本大于19
            setTranslucentStatus(true);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.colorAccent);
        setContentView(R.layout.ativity_lookup_journal);
        initToolbar();
        initView();

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

    private void initView() {
        GridLayout gl = (GridLayout) findViewById(R.id.lookup_journal_gl);
        final View inflate = View.inflate(getApplicationContext(), R.layout.attauch_item, null);
        TextView name = (TextView) inflate.findViewById(R.id.attauch_item_field_name);
        name.setText("我是文档");
        ImageView viewById = (ImageView) inflate.findViewById(R.id.attauch_item_iv);
        viewById.setBackgroundResource(R.mipmap.ic_word);
//        }else if (extension.endsWith(".doc")){
//            viewById.setImageResource(R.mipmap.ic_word);
//        }else if (extension.endsWith(".ppt")) {
//            viewById.setImageResource(R.mipmap.ic_ppt);
//        }
        TextView sise = (TextView) inflate.findViewById(R.id.attauch_item_field_sise);
        sise.setText("123k");
        final Button btn = (Button) inflate.findViewById(R.id.attauvh_item_btn);
        btn.setVisibility(View.VISIBLE);
        final ProgressBar pb = (ProgressBar) inflate.findViewById(R.id.attauch_item_pb);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btn.getText().equals("下载")){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),"正在下载",Toast.LENGTH_SHORT).show();
                            btn.setVisibility(View.GONE);
                            btn.setText("打开");
                            pb.setVisibility(View.VISIBLE);
                        }
                    });
                }else if (btn.getText().equals("打开")){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),"打开附件",Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });
        pb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        btn.setVisibility(View.VISIBLE);
                        pb.setVisibility(View.GONE);
                    }
                });
            }
        });
        gl.addView(inflate,0);

    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.id_toolbar);
        toolbar.setTitle("");
//        设置左侧图标和点击事件
        toolbar.setNavigationIcon(R.mipmap.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        right = (Button) findViewById(R.id.main_bt_right);
    }
}
