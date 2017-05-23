package com.example.hand.mockingbot.avtivity;

import android.annotation.TargetApi;
import android.content.Intent;
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
import com.example.hand.mockingbot.fagment.JournalFragment;
import com.example.hand.mockingbot.fagment.MyFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RadioGroup radioGroup;
    private List<Fragment> fragments;
    private RadioButton project;
    private RadioButton message;
    private RadioButton journal;
    private RadioButton my;
    private ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { //系统版本大于19
//            setTranslucentStatus(true);
//        }
//        SystemBarTintManager tintManager = new SystemBarTintManager(this);
//        tintManager.setStatusBarTintEnabled(true);
//        tintManager.setStatusBarTintResource(R.color.colorAccent);//设置标题栏颜色，此颜色在color中声明
        setContentView(R.layout.activity_main);
//        StatusBarUtils.setWindowStatusBarColor(this,R.color.colorAccent);
        initView();
    }
    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;        // a|=b的意思就是把a和b按位或然后赋值给a   按位或的意思就是先把a和b都换成2进制，然后用或操作，相当于a=a|b
        } else {
            winParams.flags &= ~bits;        //&是位运算里面，与运算  a&=b相当于 a = a&b  ~非运算符
        }
        win.setAttributes(winParams);
    }



    private void initView() {
        pager = (ViewPager) findViewById(R.id.fl_content);
        fragments = getData();
        FragmentManager fm = getSupportFragmentManager();
        MyFragmentPagerAdapter mfpa = new MyFragmentPagerAdapter(fm, fragments);
        pager.setAdapter(mfpa);
        pager.setOnPageChangeListener(new TabOnPageChangeListener());

        radioGroup=(RadioGroup) findViewById(R.id.bottom_bar);
        project = (RadioButton) findViewById(R.id.rb_main_project);
        message=(RadioButton) findViewById(R.id.rb_main_message);
        journal=(RadioButton) findViewById(R.id.rb_main_journal);
        journal.setChecked(true);
        my = (RadioButton) findViewById(R.id.rb_main_my);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
//                    case R.id.rb_main_project:
//                        pager.setCurrentItem(0);
//                        break;
//                    case R.id.rb_main_message:
//                        pager.setCurrentItem(1);
//                        break;
                    case R.id.rb_main_journal:
                        pager.setCurrentItem(0);
                        break;
                    case R.id.rb_main_my:
                        pager.setCurrentItem(1);
                        break;
                }
            }
        });


    }

    private List<Fragment> getData() {
        fragments = new ArrayList<Fragment>();
//        fragments.add(new ProjectFragment());
//        fragments.add(new MessagrFragment());
        fragments.add(new JournalFragment());
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
//            case 0:
//                project.setChecked(true);
//                break;
//            case 1:
//                message.setChecked(true);
//                break;
                case 0:
                    journal.setChecked(true);
                    break;
                case 1:
                    my.setChecked(true);
                    break;

            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            fragments.get(fragments.size()-1).onActivityResult(requestCode,resultCode,data);
    }
}
