package com.example.hand.mockingbot.avtivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.example.hand.mockingbot.R;

import java.util.List;

/**
 * Created by zhy on 2017/5/23.
 */

public class ItemActivity extends BasicActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow_detail_item);
        checkFragmentForLoading(getIntent().getIntExtra(PageConfig.PAGE_CODE, -1));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for (Fragment fragment : fragments) {
            fragment.onActivityResult(requestCode,resultCode,data);
        }
    }

    void checkFragmentForLoading(int pageCode) {
        switch (pageCode) {

//            case PageConfig.PAGE_APPROVE_UNIFIED:
//                checkFragment(ApproveUnified.newInstance(getIntent().getBundleExtra("data")));
//                break;
        }

    }

    private void checkFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }
}
