package com.example.hand.mockingbot.fagment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import com.example.hand.mockingbot.R;
import com.example.hand.mockingbot.avtivity.PersonListActivity;
import com.example.hand.mockingbot.datamanage.HttpManager;
import com.example.hand.mockingbot.entity.AttentionPerson;
import com.example.hand.mockingbot.utils.CommonValues;

import java.util.List;
import java.util.Map;

/**
 * Created by zhy on 2017/6/1.
 */

public class AttentionPersonFragment extends Fragment {

    public static final int CHOOSE_PERSON = 10011;
    private GridLayout gl;
    private View add;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        gl.removeAllViews();
        add = View.inflate(getContext(), R.layout.item_attention_person_add, null);
        gl.addView(add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goutopersonlist();

            }
        });
        loadData();
    }

    private void loadData() {
        Map<String, Object> getmap = CommonValues.getmap();
        getmap.put("state",1);
        HttpManager.getInstance().post(CommonValues.QUERY_USERS_ATTENTION, getmap, AttentionPerson.class, new HttpManager.ResultCallback<AttentionPerson>() {
            @Override
            public void onSuccess(String json, AttentionPerson attentionPerson) throws InterruptedException {
                final List<AttentionPerson.DataBean> data = attentionPerson.getData();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (AttentionPerson.DataBean dataBean : data) {
                            final View inflate = View.inflate(getContext(), R.layout.item_add_person, null);
                            TextView name = (TextView) inflate.findViewById(R.id.reader_name);
                            name.setText(dataBean.getRealname());
                            View delete = inflate.findViewById(R.id.item_has_look_delete);
                            delete.setVisibility(View.VISIBLE);
                            delete.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    gl.removeView(inflate);
                                }
                            });
                            gl.addView(inflate,0);
                        }
                    }
                });
            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.fragment_attention_person, null);
        gl = (GridLayout) view.findViewById(R.id.attention_person_gl);
        return view;
    }

    private void goutopersonlist() {
        Intent intent = new Intent();
        intent.setClass(getContext(),PersonListActivity.class);
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
