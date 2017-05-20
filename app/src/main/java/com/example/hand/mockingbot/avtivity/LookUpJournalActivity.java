package com.example.hand.mockingbot.avtivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hand.mockingbot.R;
import com.example.hand.mockingbot.adapter.ListAdapter;
import com.example.hand.mockingbot.datamanage.HttpManager;
import com.example.hand.mockingbot.entity.AttauchBean;
import com.example.hand.mockingbot.entity.Entity;
import com.example.hand.mockingbot.entity.JournalBean;
import com.example.hand.mockingbot.utils.CommonValues;
import com.example.hand.mockingbot.utils.DataUtil;
import com.example.hand.mockingbot.utils.GsonUtil;
import com.example.hand.mockingbot.utils.HandApp;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by zhy on 2017/5/8.
 */

public class LookUpJournalActivity extends BasicActivity {

    private Toolbar toolbar;
    private Button right;
    private JournalBean journalBean;
    private List<AttauchBean> attauchlist = new ArrayList<>();
    private Intent intent;
    private int dailyId;
    private ListView lv_attauch;
    private ListAdapter<AttauchBean> attauchlistadapter = new ListAdapter<AttauchBean>(attauchlist, R.layout.attauch_item) {
        @Override
        public void bindView(final ViewHolder holder, AttauchBean obj) {
            holder.setText(R.id.attauch_item_field_name,obj.getFieldName());
            holder.setText(R.id.attauch_item_field_sise,obj.getSize());
            if (obj.getFieldName().endsWith(".jpg")||obj.getFieldName().endsWith(".jpeg")|| obj.getFieldName().endsWith(".png")||obj.getFieldName().endsWith(".bmp")|| obj.getFieldName().endsWith(".gif")){
                holder.setImageResource(R.id.attauch_item_iv,R.mipmap.ic_iv);
            }else if (obj.getFieldName().endsWith(".doc")){
                holder.setImageResource(R.id.attauch_item_iv,R.mipmap.ic_word);
            }else if (obj.getFieldName().endsWith(".ppt")){
                holder.setImageResource(R.id.attauch_item_iv,R.mipmap.ic_ppt);
            }
        }
    };
    private String[] split;
    private TextView count;
    private EditText comment;
    private DecimalFormat df;
    private ListView lv_comment;
    private List<JournalBean.ResultBean.CommentBean> commentlist = new ArrayList<>();
    private ListAdapter<JournalBean.ResultBean.CommentBean> commentlistadapter = new ListAdapter<JournalBean.ResultBean.CommentBean>(commentlist, R.layout.item_content) {
        @Override
        public void bindView(ViewHolder holder, JournalBean.ResultBean.CommentBean obj) {
            holder.setText(R.id.pl_name,obj.getRealname());
            holder.setText(R.id.pl_content,obj.getContent());
            holder.setText(R.id.pl_time,obj.getCommentDate());
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        df = new DecimalFormat("######0.0");
        setContentView(R.layout.ativity_lookup_journal);
        intent = getIntent();
        dailyId = intent.getExtras().getInt("dailyId");
        initToolbar();
        initData();

    }

    private void initData() {
        String str = CommonValues.GET_COMMENT + "userId=" + HandApp.getLoginEntity().getResult().getData().getId() + "&dailyId=" + dailyId;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(str)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String message = e.getMessage();
                Log.e("asdasd",message);
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String string = null;
                        try {
                            string = response.body().string();
                            journalBean = GsonUtil.parseJsonToBean(string, JournalBean.class);
                            String enclosureUrl = journalBean.getResult().getData().getEnclosureUrl();
                            if (enclosureUrl != null && enclosureUrl.length()>0){
                                split = enclosureUrl.split(",");
                            }
                            setView();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });

            }
        });
    }


    private void setView() {
//        Button button = (Button) findViewById(R.id.lookup_journal_btn);
//        CheckBox checkBox = (CheckBox) findViewById(R.id.main_bt_right);
//        boolean my = intent.getExtras().getBoolean("my");
//        if (my){
//            button.setVisibility(View.VISIBLE);
//        }else {
////            checkBox.setVisibility(View.VISIBLE);
//        }
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intenttt = new Intent();
//                intenttt.setClass(getApplicationContext(),NewJournalActivity.class);
//                intenttt.putExtra("dailyId",dailyId);
//                startActivity(intenttt);
//            }
//        });
        TextView name = (TextView) findViewById(R.id.journal_item_tv_name);
        name.setText(intent.getExtras().getString("name"));
        TextView submittime = (TextView) findViewById(R.id.journal_item_tv_time);
        submittime.setText(intent.getExtras().getString("time"));
        TextView finishwork =  (TextView) findViewById(R.id.lookup_journal_finishwork);
        finishwork.setText(journalBean.getResult().getData().getFinishWork());
        TextView unfinishwork =  (TextView) findViewById(R.id.lookup_journal_unfinishwork);
        unfinishwork.setText(journalBean.getResult().getData().getUnfinishWork());
        TextView coordinationWork =  (TextView) findViewById(R.id.lookup_journal_coordinationWork);
        coordinationWork.setText(journalBean.getResult().getData().getCoordinationWork());
        TextView remark = (TextView) findViewById(R.id.lookup_journal_remark);
        remark.setText(journalBean.getResult().getData().getRemark());
        if (split != null && split.length > 0){
            for (int i = 0; i < split.length; i++) {
                String[] split1 = split[i].split("__");
                AttauchBean attauchBean = new AttauchBean();
                attauchBean.setFieldName(split1[2]);
                Double sise = new Double(split1[1]);
                attauchBean.setSize(df.format(sise/1024) + "k");
                attauchlist.add(attauchBean);
            }
        }

        lv_attauch = (ListView) findViewById(R.id.lookup_journal_attauch_lv);

        lv_attauch.setAdapter(attauchlistadapter);
        lv_attauch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                downAndOpenAttauch(split[i]);
            }
        });
        count = (TextView) findViewById(R.id.lookup_journal_tv_count);
        count.setText(journalBean.getResult().getCount() + "人");
        LinearLayout ll = (LinearLayout) findViewById(R.id.lookup_journal_ll_yd);
        List<JournalBean.ResultBean.ReaderBean> reader = journalBean.getResult().getReader();
        for (JournalBean.ResultBean.ReaderBean readerBean : reader) {
            View inflate = View.inflate(getApplicationContext(), R.layout.item_has_look, null);
            TextView reader_name = (TextView) inflate.findViewById(R.id.reader_name);
            reader_name.setText(readerBean.getRealname());
            ll.addView(inflate);
        }

        comment = (EditText) findViewById(R.id.lookup_journal_ed_comment);
        Button add_comment = (Button) findViewById(R.id.lookup_journal_btn_addcomment);
        add_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addComment();
            }
        });
        final List<JournalBean.ResultBean.CommentBean> comm = journalBean.getResult().getComment();
        if (comm.size() > 0){
            commentlist.addAll(journalBean.getResult().getComment());
        }

        TextView content_num = (TextView) findViewById(R.id.lookup_journal_tv_count_num);
        if (comm.size() > 0){
            content_num.setText("共有" + comm.size() + "条回复");
        }else {
            content_num.setText("暂无回复");
        }
        lv_comment = (ListView) findViewById(R.id.lookup_journal_lv_comment);
        lv_comment.setAdapter(commentlistadapter);
        lv_comment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String realname = commentlist.get(i).getRealname();
                        comment.setText("@" + realname + " ");
                    }
                });

            }
        });


//        NoScrollListview gl =  (NoScrollListview) findViewById(R.id.lookup_journal_gl);
//        for (int i = 0; i < split.length; i++) {
//            final View inflate = View.inflate(getApplicationContext(), R.layout.attauch_item, null);
//            TextView fieldname = (TextView) inflate.findViewById(R.id.attauch_item_field_name);
//            fieldname.setText(split[i].split("_")[2]);
//            ImageView viewById = (ImageView) inflate.findViewById(R.id.attauch_item_iv);
//            viewById.setBackgroundResource(R.mipmap.ic_word);
//        }else if (extension.endsWith(".doc")){
//            viewById.setImageResource(R.mipmap.ic_word);
//        }else if (extension.endsWith(".ppt")) {
//            viewById.setImageResource(R.mipmap.ic_ppt);
//        }
//            TextView sise = (TextView) inflate.findViewById(R.id.attauch_item_field_sise);
//            Integer integer = new Integer(split[i].split("_")[1]);
//            sise.setText(integer.intValue()/1024 + "k");
//            final Button btn = (Button) inflate.findViewById(R.id.attauvh_item_btn);
//            btn.setVisibility(View.VISIBLE);
//            final ProgressBar pb = (ProgressBar) inflate.findViewById(R.id.attauch_item_pb);
//            btn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (btn.getText().equals("下载")){
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Toast.makeText(getApplicationContext(),"正在下载",Toast.LENGTH_SHORT).show();
//                                btn.setVisibility(View.GONE);
//                                btn.setText("打开");
//                                pb.setVisibility(View.VISIBLE);
//                            }
//                        });
//                    }else if (btn.getText().equals("打开")){
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Toast.makeText(getApplicationContext(),"打开附件",Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                    }
//
//                }
//            });
//            pb.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            btn.setVisibility(View.VISIBLE);
//                            pb.setVisibility(View.GONE);
//                        }
//                    });
//                }
//            });
//            gl.addView(inflate,0);
//        }
        journalBean.getResult().getComment();
        journalBean.getResult().getCount();


    }

    private void addComment() {
        final String cont = comment.getText().toString();
        Map<String, Object> map = CommonValues.getmap();
        map.put("dailyId",dailyId);
        map.put("content",cont);
        map.put("isReadFlag",0);
        comment.setText("");
        HttpManager.getInstance().post(CommonValues.COMMENT, map, Entity.class, new HttpManager.ResultCallback<Entity>() {
            @Override
            public void onSuccess(String json, Entity entity) throws InterruptedException {
                if (entity.getResult().getData().equals("1")){
                    addcontent(cont);
                }
            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }

    private void addcontent(final String cont) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                JournalBean.ResultBean.CommentBean commentBean = new JournalBean.ResultBean.CommentBean();
                commentBean.setRealname(HandApp.getLoginEntity().getResult().getData().getRealname());
                commentBean.setContent(cont);
                commentBean.setCommentDate(getData(false));
                commentlist.add(commentBean);
                commentlistadapter.notifyDataSetChanged();
            }
        });

    }

    private void downAndOpenAttauch(final String attauchuri) {
        HttpManager.getUrl(CommonValues.DOWN_ATTAUCHMENT + "fileName=" + attauchuri,attauchuri, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),attauchuri);
                        DataUtil.openFile(getApplicationContext(),file);
//                        Toast.makeText(getApplicationContext(),"下载完成，正在打开",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
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
