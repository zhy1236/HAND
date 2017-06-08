package com.example.hand.mockingbot.avtivity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.hand.mockingbot.R;
import com.example.hand.mockingbot.datamanage.HttpManager;
import com.example.hand.mockingbot.entity.ResourceOccupationEntity;
import com.example.hand.mockingbot.utils.CommonValues;

import java.util.Map;

/**
 * Created by zhy on 2017/6/8.
 */

public class ResourcePersonActivity extends BasicActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesour_person);
        loadData();
    }

    private void loadData() {
        Map<String, Object> getmap = CommonValues.getmap();
        getmap.put("product","");
        getmap.put("dateTime","2017-06");
        getmap.put("pageNo",1);
        getmap.put("pageSize",10);
        getmap.put("resourceId",getIntent().getExtras().getInt("resourceId"));
        HttpManager.getInstance().post(CommonValues.RESOURCE_LIST, getmap, ResourceOccupationEntity.class, new HttpManager.ResultCallback<ResourceOccupationEntity>() {
            @Override
            public void onSuccess(String json, ResourceOccupationEntity resourceOccupationEntity) throws InterruptedException {

            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }
}
