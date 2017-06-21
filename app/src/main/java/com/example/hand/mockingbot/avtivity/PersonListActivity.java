package com.example.hand.mockingbot.avtivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hand.mockingbot.R;
import com.example.hand.mockingbot.adapter.ListAdapter;
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

public class PersonListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    private Set<String> set = new HashSet<>();
    private Toolbar toolbar;
    private ListView lv;
    private UsersAdapter myAdapter = null;
    private List<BaseItem> mData = new ArrayList<>();
    private List<BaseItem> baseEntityList = new ArrayList<>();
    private List<String> strings = new ArrayList<>();
    private ListAdapter listAdapter = new ListAdapter(strings,R.layout.item_string) {
        @Override
        public void bindView(ViewHolder holder, Object ob, int position) {
            holder.setText(R.id.item_string_tv,ob.toString());
        }
    };
    private RelativeLayout pb;
    private TextView num;
    private Button btn;
    private ListView listView;
    private SearchView etSearch;
    private EditText textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_list);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
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
        listView = (ListView) findViewById(R.id.person_list_lv);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String s = strings.get(i);
                for (int j = 0; j < mData.size(); j++) {
                    if (mData.get(j).getItem_type() == ViewHolder1.ITEM_VIEW_TYPE_1){
                        ItemBean1 baseItem = (ItemBean1) mData.get(j);
                        if (baseItem.getName().equals(s)){
                            if (j > lv.getFirstVisiblePosition()){
                                int sise = (mData.size() - j > 7) ? 7 : (mData.size() - j);
                                lv.smoothScrollToPosition(j + sise);
                            }else {
                                lv.smoothScrollToPosition(j);
                            }
                            return;
                        }
                    }
                }
            }
        });
        etSearch = (SearchView) findViewById(R.id.et_search);
        etSearch.setSubmitButtonEnabled(false);
        etSearch.setIconifiedByDefault(false);
        etSearch.setFocusableInTouchMode(false);
        etSearch.setFocusable(false);
        etSearch.setOnClickListener(this);
        textView = (EditText) etSearch.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        textView.setHintTextColor(0xffbfbdbd);
        textView.setTextColor(0xff333333);
        etSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                doFilter(newText);
                return true;
            }
        });
    }

    private void doFilter(String key) {
        if (mData != null && !key.equals("")) {
            if (baseEntityList.size() == 0){
                baseEntityList.addAll(mData);
            }
            List<BaseItem> list = new ArrayList<>();
            for (int i = 0; i < baseEntityList.size(); i++) {
                if (baseEntityList.get(i).getItem_type() == ViewHolder2.ITEM_VIEW_TYPE_2){
                    ItemBean2 baseItem = (ItemBean2) baseEntityList.get(i);
                    if (baseItem.getName() != null && baseItem.getName().contains(key)){
                        list.add(baseItem);
                    }
                    if (baseItem.getZw() != null && baseItem.getZw().contains(key)){
                        list.add(baseItem);
                    }
                    if (baseItem.getStr() != null && baseItem.getStr().toLowerCase().contains(key.toLowerCase())){
                        list.add(baseItem);
                    }
                }
            }
            mData.clear();
            mData.addAll(list);
            myAdapter.notifyDataSetChanged();
        } else if (mData != null) {
            if (baseEntityList != null) {
                mData.clear();
                mData.addAll(baseEntityList);
                baseEntityList.clear();
                myAdapter.notifyDataSetChanged();
            }

        }
    }

    private void addattentionperson() {
        btn.setEnabled(false);
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
                if (entity.getCode().equals("100")){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.showToast(getApplicationContext(),"添加成功");
                            PersonListActivity.this.finish();
                            btn.setEnabled(true);
                        }
                    });
                }
            }

            @Override
            public void onFailure(String msg) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        btn.setEnabled(true);
                    }
                });
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
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast(getApplicationContext(),"获取数据失败");
                        finish();
                    }
                });
            }
        });
    }

    private void setlist(List<UsersEntity.DataBeanX> data) {
        for (UsersEntity.DataBeanX dataBeanX : data) {
            mData.add(new ItemBean1(ViewHolder1.ITEM_VIEW_TYPE_1,dataBeanX.getMsg()));
            strings.add(dataBeanX.getMsg());
            for (UsersEntity.DataBeanX.DataBean bean : dataBeanX.getData()) {
                mData.add(new ItemBean2(ViewHolder2.ITEM_VIEW_TYPE_2,bean.getFlag().equals("1"),bean.getRealname(),bean.getPosition(),bean.getUserId(),bean.getLetter()));
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
                listAdapter.notifyDataSetChanged();
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

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.et_search) {
            etSearch.setFocusable(true);
            toggleSearchBox(true);
        }
    }

    private void toggleSearchBox(boolean enable) {
        if (enable) {
            etSearch.setFocusableInTouchMode(true);
            etSearch.requestFocus();
        } else {
            etSearch.setFocusableInTouchMode(false);
            etSearch.clearFocus();
        }
    }
}
