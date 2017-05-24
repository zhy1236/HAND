package com.example.hand.mockingbot.avtivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hand.mockingbot.R;
import com.example.hand.mockingbot.adapter.ListAdapter;
import com.example.hand.mockingbot.datamanage.HttpManager;
import com.example.hand.mockingbot.entity.AddAttauchEntity;
import com.example.hand.mockingbot.entity.AddJournalEntity;
import com.example.hand.mockingbot.entity.AttauchBean;
import com.example.hand.mockingbot.entity.JournalBean;
import com.example.hand.mockingbot.utils.CommonValues;
import com.example.hand.mockingbot.utils.DataUtil;
import com.example.hand.mockingbot.utils.GsonUtil;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by zhy on 2017/5/5.
 */

public class NewJournalActivity extends BasicActivity {

    public static final int CHOOSE_PICTURE = 1;
    private Toolbar toolbar;
    private boolean ispayment = false;
    private List<AttauchBean> attauchlist = new ArrayList<>();
    private EditText finishwork;
    private EditText unfinishwork;
    private EditText coordinationwork;
    private EditText remark;
    private CheckBox isPayment;
    private RelativeLayout bj;
    private TextView tv_vj;
    private List<String> data = new ArrayList<>();
    private String[] split;
    private DecimalFormat df;
    private ListAdapter<AttauchBean> attauchBeanListAdapter = new ListAdapter<AttauchBean>(attauchlist,R.layout.attauch_item) {
        @Override
        public void bindView(ViewHolder holder, final AttauchBean obj) {
//            if (obj.getFieldName().split("__") != null){
//                holder.setText(R.id.attauch_item_field_name, obj.getFieldName().split("__")[2]);
//            }else {
                holder.setText(R.id.attauch_item_field_name, obj.getFieldName());
//            }
            holder.setText(R.id.attauch_item_field_sise,obj.getSize());
            if (obj.getFieldName().endsWith(".jpg")||obj.getFieldName().endsWith(".jpeg")|| obj.getFieldName().endsWith(".png")||obj.getFieldName().endsWith(".bmp")|| obj.getFieldName().endsWith(".gif")){
                holder.setImageResource(R.id.attauch_item_iv,R.mipmap.ic_iv);
            }else if (obj.getFieldName().endsWith(".doc")){
                holder.setImageResource(R.id.attauch_item_iv,R.mipmap.ic_word);
            }else if (obj.getFieldName().endsWith(".ppt")){
                holder.setImageResource(R.id.attauch_item_iv,R.mipmap.ic_ppt);
            }
            holder.setVisibility(R.id.attauch_item_field_delete,View.VISIBLE);
            holder.setOnClickListener(R.id.attauch_item_field_delete, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (int i = 0; i < attauchlist.size(); i++) {
                        if (attauchlist.get(i).getFieldName().equals(obj.getFieldName())){
                            final int finalI = i;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    data.remove(attauchlist.get(finalI).getFieldName());
                                    attauchlist.remove(finalI);
                                    attauchBeanListAdapter.notifyDataSetChanged();
                                }
                            });
                            i--;
                        }
                    }
                }
            });
        }
    };
    private JournalBean mJournalBean;
    private int dailyId;
    private ListView lv_attauch;
    private Button bt;
    private RelativeLayout pb;
    private Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_journal);
        df = new DecimalFormat("######0.0");
        intent = getIntent();
        if (intent.getExtras() != null){
            dailyId = intent.getExtras().getInt("dailyId");
            if (dailyId != 0){
                loadData(dailyId);
            }
        }
        toolbar = (Toolbar) findViewById(R.id.id_toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.mipmap.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        initview();
    }

    private void loadData(int dailyId) {
        Map<String, Object> getmap = CommonValues.getmap();
        getmap.put("dailyId",dailyId);
        HttpManager.getInstance().post(CommonValues.GET_COMMENT, getmap, JournalBean.class, new HttpManager.ResultCallback<JournalBean>() {
            @Override
            public void onSuccess(String json, final JournalBean journalBean) throws InterruptedException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pb.setVisibility(View.GONE);
                        mJournalBean = journalBean;
                        String enclosureUrl = journalBean.getResult().getData().getEnclosureUrl();
                        if (enclosureUrl != null && enclosureUrl.length()>0){
                            split = enclosureUrl.split(",");
                            for (int i = 0; i < split.length; i++) {
                                data.add(split[i]);
                                String[] split1 = split[i].split("__");
                                AttauchBean attauchBean = new AttauchBean();
                                attauchBean.setFieldName(split[i]);
                                Double sise = new Double(split1[1]);
                                attauchBean.setSize(df.format(sise/1024) + "k");
                                attauchlist.add(attauchBean);
                                attauchBeanListAdapter.notifyDataSetChanged();
                            }
                        }
                        finishwork.setText(journalBean.getResult().getData().getFinishWork());
                        unfinishwork.setText(journalBean.getResult().getData().getUnfinishWork());
                        coordinationwork.setText(journalBean.getResult().getData().getCoordinationWork());
                        remark.setText(journalBean.getResult().getData().getRemark());
                    }
                });

            }

            @Override
            public void onFailure(String msg) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pb.setVisibility(View.GONE);
                    }
                });
            }
        });
    }

    private void initview() {
        pb = (RelativeLayout) findViewById(R.id.new_journal_pb);
        pb.setVisibility(View.GONE);
        LinearLayout btn = (LinearLayout) findViewById(R.id.new_journal_tv_fj);
        RelativeLayout viewById = (RelativeLayout) findViewById(R.id.new_journal_rl_bj_time);
        if (getIntent().getExtras() != null){
            viewById.setVisibility(View.GONE);
        }
        finishwork = (EditText) findViewById(R.id.new_journal_ed_finishwork);
        unfinishwork = (EditText) findViewById(R.id.new_journal_ed_unfinishwork);
        coordinationwork = (EditText) findViewById(R.id.new_journal_ed_coordinationwork);
        remark = (EditText) findViewById(R.id.new_journal_ed_remark);
        bj = (RelativeLayout) findViewById(R.id.new_journal_rl_bj);
        isPayment = (CheckBox) findViewById(R.id.new_journal_isPayment);
        isPayment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                bj.setVisibility(b?View.VISIBLE:View.GONE);
                ispayment = b;
            }
        });
        tv_vj = (TextView) findViewById(R.id.new_journal_tv_bj);
        tv_vj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateTimePicker(tv_vj,true);
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
        bt = (Button) findViewById(R.id.new_journal_tj);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addJournal();
            }
        });
        lv_attauch = (ListView) findViewById(R.id.new_journal_lv);
        lv_attauch.setAdapter(attauchBeanListAdapter);
        lv_attauch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (attauchlist.get(i).getPath() == null || attauchlist.get(i).getPath().isEmpty()){
                    downAndOpenAttauch(i);
                    pb.setVisibility(View.VISIBLE);
                }else {
                    String path = attauchlist.get(i).getPath();
                    File file = new File(path);
                    DataUtil.openFile(getApplicationContext(),file);
                }

            }
        });
    }

    private void addJournal() {
        pb.setVisibility(View.VISIBLE);
        bt.setEnabled(false);
        if (finishwork.getText().toString().replace(" ", "").isEmpty()){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),"请填写完成工作",Toast.LENGTH_SHORT).show();
                    bt.setEnabled(true);
                    pb.setVisibility(View.GONE);
                    return;
                }
            });
        }
        Map<String, Object> map = CommonValues.getmap();
        map.put("finishWork",finishwork.getText().toString());
        map.put("unfinishWork",unfinishwork.getText().toString());
        map.put("coordinationWork",coordinationwork.getText().toString());
        map.put("remark",remark.getText().toString());
        String stringBuffer = "";
        for (int i = 0; i < data.size(); i++) {
            if (i == 0){
                stringBuffer += data.get(i);
            }else {
                stringBuffer += ("," + data.get(i));
            }
        }
        map.put("enclosureUrl",stringBuffer);
        map.put("isAfterPayment",0);
        if (ispayment){
            map.put("isPayment",1);
//            if (intent.getExtras().getString("submitTimeDate") != null){
//                map.put("submitTimeDate",intent.getExtras().getString("submitTimeDate"));
//            }else {
                map.put("submitDate",tv_vj.getText());
//            }

        }else {
            map.put("isPayment",0);
            map.put("submitDate",getData(true));
        }
        if (getIntent().getExtras() == null){
            HttpManager.getInstance().post(CommonValues.NEW_JOURNAL,map,AddJournalEntity.class,new HttpManager.ResultCallback<AddJournalEntity>() {
                @Override
                public void onSuccess(String json, final AddJournalEntity add) throws InterruptedException {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (add.getResult().getData().equals("0")){
                                Toast.makeText(getApplicationContext(),"日报提交失败，请重新提交",Toast.LENGTH_SHORT).show();
                            }else if (add.getResult().getData().equals("1")){
                                finish();
                            }
                            bt.setEnabled(true);
                            pb.setVisibility(View.GONE);
                        }
                    });

                }

                @Override
                public void onFailure(String msg) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            bt.setEnabled(true);
                            pb.setVisibility(View.GONE);
                        }
                    });

                }
            });
        }else {
            map.put("dailyId",dailyId);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String str = formatter.format(mJournalBean.getResult().getData().getSubmitDate());
            map.put("submitDate",str);
            HttpManager.getInstance().post(CommonValues.UPDATE_DAILY,map,AddJournalEntity.class,new HttpManager.ResultCallback<AddJournalEntity>() {
                @Override
                public void onSuccess(String json, final AddJournalEntity add) throws InterruptedException {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (add.getResult().getData().equals("0")){
                                Toast.makeText(getApplicationContext(),"日报提交失败，请重新提交",Toast.LENGTH_SHORT).show();
                            }else if (add.getResult().getData().equals("1")){
                                Toast.makeText(getApplicationContext(),"日报提交成功",Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            pb.setVisibility(View.GONE);
                            bt.setEnabled(true);
                        }
                    });

                }

                @Override
                public void onFailure(String msg) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pb.setVisibility(View.GONE);
                            bt.setEnabled(true);
                        }
                    });
                }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == TAKE_PICTURE) {
                Bundle extras = data.getExtras();
                Bitmap b = (Bitmap) extras.get("data");
                String fileNmae = Environment.getExternalStorageDirectory().getAbsolutePath() + "GE" + System.currentTimeMillis() + ".jpg";
                File myCaptureFile = new File(fileNmae);
                try {
                    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                        if (!myCaptureFile.getParentFile().exists()) {
                            myCaptureFile.getParentFile().mkdirs();
                        }
                        BufferedOutputStream bos;
                        bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
                        b.compress(Bitmap.CompressFormat.JPEG, 80, bos);
                        Uri uri = Uri.fromFile(myCaptureFile);
                        bos.flush();
                        bos.close();
                        upAttauch(uri);
                        pb.setVisibility(View.VISIBLE);
                    } else {
                        Toast toast = Toast.makeText(NewJournalActivity.this, "保存失败，SD卡无效", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else if (requestCode == CHOOSE_PICTURE  || requestCode == CHOOSE_FILE){
                if(data.getData()!=null){
                    pb.setVisibility(View.VISIBLE);
                    upAttauch(data.getData());
                }
            }
        }
    }

    private void adduri(Uri uri) {
        AttauchBean attauchBean = new AttauchBean();
        Uri u = uri;
        String extension = null;
        String path = null;
        if("file".equals(uri.getScheme())){
            path = uri.getPath();
            extension = path.substring(path.lastIndexOf("."));
        }else {
            path = HttpManager.getRealPathFromUri(uri, getApplicationContext());
            if(path!=null){
                u = Uri.parse(path);
            }
            u = Uri.fromFile(new File(uri.toString()));
            extension = path.substring(path.lastIndexOf("."));
        }
        attauchBean.setPath(path);
        attauchBean.setExtension(extension);
        attauchBean.setFieldName(path.substring(path.lastIndexOf("/") + 1, path.length()));
        File file = new File(path);
        int available;
        try {
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            available = fis.available();
            attauchBean.setSize(available/1024 + "K");
        } catch (IOException e) {
            e.printStackTrace();
        }
        attauchlist.add(attauchBean);
        attauchBeanListAdapter.notifyDataSetChanged();

    }

    private void upAttauch(final Uri uri) {
        String realPathFromUri = HttpManager.getInstance().getRealPathFromUri(uri, getApplicationContext());
        File file = new File(realPathFromUri);
        HttpManager.getInstance().postAsynFile(file, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pb.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(),"附件上传失败，请重新选择！",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adduri(uri);
                        String string = null;
                        try {
                            string = response.body().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        AddAttauchEntity addAttauchEntity = GsonUtil.parseJsonToBean(string, AddAttauchEntity.class);
                        pb.setVisibility(View.GONE);
                        data.add(addAttauchEntity.getResult().getData().get(0));
                    }
                });

            }
        });
    }

    private void downAndOpenAttauch(final int i) {
        HttpManager.getUrl(CommonValues.DOWN_ATTAUCHMENT + "fileName=" + attauchlist.get(i).getFieldName(),attauchlist.get(i).getFieldName(), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        attauchlist.get(i).setPath(Environment.getExternalStorageDirectory().getAbsolutePath() + attauchlist.get(i).getFieldName());
                        pb.setVisibility(View.GONE);
                        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),attauchlist.get(i).getFieldName());
                        DataUtil.openFile(getApplicationContext(),file);
//                        Toast.makeText(getApplicationContext(),"下载完成，正在打开",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


}
