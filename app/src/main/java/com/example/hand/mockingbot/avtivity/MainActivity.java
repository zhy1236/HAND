package com.example.hand.mockingbot.avtivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.hand.mockingbot.R;
import com.example.hand.mockingbot.adapter.MyFragmentPagerAdapter;
import com.example.hand.mockingbot.fagment.LookUpJournalFragment;
import com.example.hand.mockingbot.fagment.ManagerFragment;
import com.example.hand.mockingbot.fagment.MessagrFragment;
import com.example.hand.mockingbot.fagment.MyFragment;
import com.example.hand.mockingbot.fagment.NewJournalFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RadioGroup radioGroup;
    private List<Fragment> fragments;
    private RadioButton message;
    private RadioButton newjournal;
    private RadioButton lookup;
    private RadioButton my;
    private ViewPager pager;
    private RadioButton manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        pager = (ViewPager) findViewById(R.id.fl_content);
        fragments = getData();
        FragmentManager fm = getSupportFragmentManager();
        MyFragmentPagerAdapter mfpa = new MyFragmentPagerAdapter(fm, fragments);
        pager.setAdapter(mfpa);
        pager.setOnPageChangeListener(new TabOnPageChangeListener());

        radioGroup=(RadioGroup) findViewById(R.id.bottom_bar);
        message=(RadioButton) findViewById(R.id.rb_main_message);
        newjournal=(RadioButton) findViewById(R.id.rb_main_new_journal);
        lookup=(RadioButton) findViewById(R.id.rb_main_look_up_journal);
        manager = (RadioButton) findViewById(R.id.rb_main_manager);
        my = (RadioButton) findViewById(R.id.rb_main_my);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_main_message:
                        pager.setCurrentItem(0);//选择某一页
                        break;
                    case R.id.rb_main_new_journal:
                        pager.setCurrentItem(1);
                        break;
                    case R.id.rb_main_look_up_journal:
                        pager.setCurrentItem(2);
                        break;
                    case R.id.rb_main_manager:
                        pager.setCurrentItem(3);
                        break;
                    case R.id.rb_main_my:
                        pager.setCurrentItem(4);
                        break;
                }
            }
        });

    }

    private List<Fragment> getData() {
        fragments = new ArrayList<Fragment>();
        fragments.add(new MessagrFragment());
        fragments.add(new NewJournalFragment());
        fragments.add(new LookUpJournalFragment());
        fragments.add(new ManagerFragment());
        fragments.add(new MyFragment());
        return fragments;
    }


    /**
     * 页卡改变事件
     */
    public class TabOnPageChangeListener implements ViewPager.OnPageChangeListener {

        //当滑动状态改变时调用
        public void onPageScrollStateChanged(int state) {

        }

        //当前页面被滑动时调用
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        //当新的页面被选中时调用
        public void onPageSelected(int position) {
            switch (position) {
                case 0:
                    message.setChecked(true);
                    break;
                case 1:
                    newjournal.setChecked(true);
                    break;
                case 2:
                    lookup.setChecked(true);
                    break;
                case 3:
                    manager.setChecked(true);
                    break;
                case 4:
                    my.setChecked(true);
                    break;
            }
        }
    }
}
