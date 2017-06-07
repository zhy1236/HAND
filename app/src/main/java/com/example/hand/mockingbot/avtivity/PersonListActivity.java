package com.example.hand.mockingbot.avtivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hand.mockingbot.R;
import com.example.hand.mockingbot.adapter.UsersAdapter;
import com.example.hand.mockingbot.datamanage.HttpManager;
import com.example.hand.mockingbot.entity.BaseItem;
import com.example.hand.mockingbot.entity.Entity;
import com.example.hand.mockingbot.entity.ItemBean1;
import com.example.hand.mockingbot.entity.ItemBean2;
import com.example.hand.mockingbot.entity.UsersEntity;
import com.example.hand.mockingbot.holder.ViewHolder1;
import com.example.hand.mockingbot.holder.ViewHolder2;
import com.example.hand.mockingbot.utils.CommonValues;
import com.example.hand.mockingbot.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by zhy on 2017/6/5.
 */

public class PersonListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private Set<String> set = new HashSet<>();
    private Toolbar toolbar;
    private ListView lv;
    private UsersAdapter myAdapter = null;
    private List<BaseItem> mData = new ArrayList<>();
    private RelativeLayout pb;
    private TextView num;
    private Button btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_list);
        initView();
        loadData();
    }

    private void initView() {
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
        TextView te = (TextView) findViewById(R.id.main_tv_title);
        te.setText("人员列表");
        lv = (ListView) findViewById(R.id.journal_receiver_lv);
        pb = (RelativeLayout) findViewById(R.id.journal_receiver_pb);
        num = (TextView) findViewById(R.id.attention_person_list_num);
        btn = (Button) findViewById(R.id.attention_person_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addattentionperson();
            }
        });
    }

    private void addattentionperson() {
        final Map<String, Object> getmap = CommonValues.getmap();
        String ids = "";
        for (String s : set) {
            ids += s + ",";
        }
        String substring = ids.substring(0, ids.length() - 1);
        getmap.put("memberIds",substring);
        HttpManager.getInstance().post(CommonValues.ADD_MEBER_USERS, getmap, Entity.class, new HttpManager.ResultCallback<Entity>() {
            @Override
            public void onSuccess(String json, Entity entity) throws InterruptedException {
                if (entity.getCode() == 100){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.showToast(getApplicationContext(),"添加成功");
                            PersonListActivity.this.finish();
                        }
                    });
                }
            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }

    private void loadData() {
        Map<String, Object> getmap = CommonValues.getmap();
        HttpManager.getInstance().post(CommonValues.QUERY_USERS, getmap, UsersEntity.class, new HttpManager.ResultCallback<UsersEntity>() {
            @Override
            public void onSuccess(String json, UsersEntity usersEntity) throws InterruptedException {
                final List<UsersEntity.DataBeanX> data = usersEntity.getData();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pb.setVisibility(View.GONE);
                        setlist(data);
                    }
                });

            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }

    private void setlist(List<UsersEntity.DataBeanX> data) {
        for (UsersEntity.DataBeanX dataBeanX : data) {
            mData.add(new ItemBean1(ViewHolder1.ITEM_VIEW_TYPE_1,dataBeanX.getMsg()));
            for (UsersEntity.DataBeanX.DataBean bean : dataBeanX.getData()) {
                mData.add(new ItemBean2(ViewHolder2.ITEM_VIEW_TYPE_2,bean.getFlag().equals("1"),bean.getRealname(),bean.getDeptName(),bean.getUserId()));
                if (bean.getFlag().equals("1")){
                    set.add(bean.getUserId() + "");
                }
            }
        }
        myAdapter = new UsersAdapter(this,mData);
        lv.setAdapter(this.myAdapter);
        lv.setOnItemClickListener(this);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                num.setText(set.size() + "人");
                btn.setText("确认(" + set.size() + ")");
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (mData.get(i).getItem_type() == ViewHolder2.ITEM_VIEW_TYPE_2){
            ItemBean2 baseItem = (ItemBean2) mData.get(i);
            if (baseItem.getChecked()){
                set.remove(baseItem.getUsetId() + "");
            }else {
                set.add(baseItem.getUsetId() + "");
            }
            baseItem.setChecked(!baseItem.getChecked());

        }else if (mData.get(i).getItem_type() == ViewHolder1.ITEM_VIEW_TYPE_1){
            ItemBean1 baseItem = (ItemBean1) mData.get(i);
            ToastUtil.showToast(getApplicationContext(),baseItem.getName());
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                myAdapter.notifyDataSetChanged();
                num.setText(set.size() + "人");
                btn.setText("确认(" + set.size() + ")");
            }
        });
    }

}
