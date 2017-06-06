package com.example.hand.mockingbot.avtivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hand.mockingbot.R;
import com.example.hand.mockingbot.adapter.ListAdapter;
import com.example.hand.mockingbot.datamanage.HttpManager;
import com.example.hand.mockingbot.entity.UsersEntity;
import com.example.hand.mockingbot.entity.UsersShowEntity;
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

public class PersonListActivity extends BasicActivity implements AdapterView.OnItemClickListener {

    private Set<String> set = new HashSet<>();
    private Toolbar toolbar;
    private ListView listView;
    private List<UsersShowEntity> usersShowEntities = new ArrayList<>();
    private ListAdapter<UsersShowEntity> adapter = new ListAdapter<UsersShowEntity>(usersShowEntities,R.layout.item_attention_user) {
        @Override
        public void bindView(ViewHolder holder, final UsersShowEntity ob, int i) {
            if (ob.getMsg() != null && !ob.getMsg().isEmpty()){
                holder.setVisibility(R.id.item_attention_ll,View.VISIBLE);
                holder.setVisibility(R.id.item_attention_rl,View.GONE);
                holder.setText(R.id.item_users_title,ob.getMsg());
            }else {
                holder.setVisibility(R.id.item_attention_ll,View.GONE);
                holder.setVisibility(R.id.item_attention_rl,View.VISIBLE);
                holder.setText(R.id.item_users_name,ob.getBean().getRealname());
                holder.setText(R.id.item_users_zw,ob.getBean().getDeptName());
                CheckBox check = holder.getView(R.id.item_users_cb);
                check.setChecked(ob.getBean().getFlag().equals("1"));
            }

        }
    };
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
        listView = (ListView) findViewById(R.id.journal_receiver_lv);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        pb = (RelativeLayout) findViewById(R.id.journal_receiver_pb);
        num = (TextView) findViewById(R.id.attention_person_list_num);
        btn = (Button) findViewById(R.id.attention_person_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.showToast(getApplicationContext(),"选中了" + set.toString());
            }
        });
    }

    private void loadData() {
        Map<String, Object> getmap = CommonValues.getmap();
        HttpManager.getInstance().post(CommonValues.QUERY_USERS, getmap, UsersEntity.class, new HttpManager.ResultCallback<UsersEntity>() {
            @Override
            public void onSuccess(String json, UsersEntity usersEntity) throws InterruptedException {
                final List<UsersEntity.DataBeanX> data = usersEntity.getData();
                setlist(data);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pb.setVisibility(View.GONE);
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
            UsersShowEntity usersShowEntity = new UsersShowEntity();
            usersShowEntity.setMsg(dataBeanX.getMsg());
            usersShowEntities.add(usersShowEntity);
            for (UsersEntity.DataBeanX.DataBean bean : dataBeanX.getData()) {
                UsersShowEntity beanX = new UsersShowEntity();
                beanX.setBean(bean);
                usersShowEntities.add(beanX);
                if (bean.getFlag().equals("1")){
                    set.add(bean.getUserId() + "");
                }
            }
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
                num.setText(set.size() + "人");
                btn.setText("确定(" + set.size() + ")");
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (usersShowEntities.get(i).getBean() != null){
            ToastUtil.showToast(getApplicationContext(),"第" + i + "条被点击了");
            if (usersShowEntities.get(i).getBean().getFlag().equals("1")){
                usersShowEntities.get(i).getBean().setFlag("0");
                set.remove(usersShowEntities.get(i).getBean().getUserId() + "");
                CheckBox viewById = (CheckBox) view.findViewById(R.id.item_users_cb);
                viewById.setChecked(false);
            }else if (usersShowEntities.get(i).getBean().getFlag().equals("0")){
                usersShowEntities.get(i).getBean().setFlag("1");
                set.add(usersShowEntities.get(i).getBean().getUserId() + "");
                CheckBox viewById = (CheckBox) view.findViewById(R.id.item_users_cb);
                viewById.setChecked(true);
            }
            adapter.notifyDataSetChanged();
        }

    }
}
