package com.example.hand.mockingbot.avtivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hand.mockingbot.R;

/**
 * Created by zhy on 2017/5/8.
 */

public class LookUpJournalActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Button right;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ativity_lookup_journal);
        initToolbar();
        initView();

    }

    private void initView() {
        GridLayout gl = (GridLayout) findViewById(R.id.lookup_journal_gl);
        final View inflate = View.inflate(getApplicationContext(), R.layout.attauch_item, null);
        TextView name = (TextView) inflate.findViewById(R.id.attauch_item_field_name);
        name.setText("我是图片");
        ImageView viewById = (ImageView) inflate.findViewById(R.id.attauch_item_iv);
        viewById.setImageResource(R.mipmap.ic_iv);
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
        final View inflate2 = View.inflate(getApplicationContext(), R.layout.attauch_item, null);
        TextView name2 = (TextView) inflate.findViewById(R.id.attauch_item_field_name);
        name2.setText("我是文档");
        ImageView viewById2 = (ImageView) inflate.findViewById(R.id.attauch_item_iv);
        viewById2.setImageResource(R.mipmap.ic_word);
//        }else if (extension.endsWith(".doc")){
//            viewById.setImageResource(R.mipmap.ic_word);
//        }else if (extension.endsWith(".ppt")) {
//            viewById.setImageResource(R.mipmap.ic_ppt);
//        }
        TextView sise2 = (TextView) inflate2.findViewById(R.id.attauch_item_field_sise);
        sise2.setText("123k");
        final Button btn2 = (Button) inflate2.findViewById(R.id.attauvh_item_btn);
        btn2.setVisibility(View.VISIBLE);
        final ProgressBar pb2 = (ProgressBar) inflate2.findViewById(R.id.attauch_item_pb);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btn2.getText().equals("下载")){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),"正在下载",Toast.LENGTH_SHORT).show();
                            btn2.setVisibility(View.GONE);
                            btn2.setText("打开");
                            pb2.setVisibility(View.VISIBLE);
                        }
                    });
                }else if (btn2.getText().equals("打开")){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),"打开附件",Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });
        pb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        btn2.setVisibility(View.VISIBLE);
                        pb2.setVisibility(View.GONE);
                    }
                });
            }
        });
        gl.addView(inflate2,0);
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
