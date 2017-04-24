package com.example.hand.mockingbot;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.hand.mockingbot.fagment.LookUpJournalFragment;
import com.example.hand.mockingbot.fagment.ManagerFragment;
import com.example.hand.mockingbot.fagment.MessagrFragment;
import com.example.hand.mockingbot.fagment.MyFragment;
import com.example.hand.mockingbot.fagment.NewJournalFragment;

public class MainActivity extends AppCompatActivity {
    private RadioGroup radioGroup;
    private RadioButton message;
    private RadioButton newjournal;
    private RadioButton lookup;
    private RadioButton newjournal1;
    private RadioButton my;
    private FragmentManager supportFragmentManager;
    private int lastIndex = -1, currentIndex;
    private Fragment[] fragments = new Fragment[5];
    private FrameLayout viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        supportFragmentManager = getSupportFragmentManager();
        initFragment();
    }

    private void initFragment() {
        if (supportFragmentManager.getFragments() != null && supportFragmentManager.getFragments().size() != 0) {
            supportFragmentManager.getFragments().clear();
        }
        FragmentTransaction transaction = supportFragmentManager.beginTransaction();
        fragments[0] = new MessagrFragment();
        fragments[1] = new NewJournalFragment();
        fragments[2] = new LookUpJournalFragment();
        fragments[3] = new ManagerFragment();
        fragments[4] = new MyFragment();
        for (int i = 0; i < fragments.length; i++) {
            transaction.add(R.id.fl_content, fragments[i]);
            transaction.hide(fragments[i]);
        }

        if(transaction!=null){
            transaction.show(fragments[0]);
            transaction.commitNow();
        }
        currentIndex = 0;

    }

    private void initView() {
        viewPager = (FrameLayout) findViewById(R.id.fl_content);
        radioGroup = (RadioGroup) findViewById(R.id.bottom_bar);
        message = (RadioButton) findViewById(R.id.rb_main_message);
        newjournal = (RadioButton) findViewById(R.id.rb_main_new_journal);
        lookup = (RadioButton) findViewById(R.id.rb_main_look_up_journal);
        newjournal1 = (RadioButton) findViewById(R.id.rb_main_manager);
        my = (RadioButton) findViewById(R.id.rb_main_my);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                lastIndex = currentIndex;
                if (checkedId == R.id.rb_main_message) {
                    currentIndex = 0;
                    changeFragment(fragments[0]);

                } else if (checkedId == R.id.rb_main_new_journal) {
                    currentIndex = 1;
                    changeFragment(fragments[1]);

                } else if (checkedId == R.id.rb_main_look_up_journal) {
                    currentIndex = 2;
                    changeFragment(fragments[2]);

                }else if (checkedId == R.id.rb_main_manager) {
                    currentIndex = 3;
                    changeFragment(fragments[3]);

                }else if (checkedId == R.id.rb_main_my) {
                    currentIndex = 4;
                    changeFragment(fragments[4]);

                }
            }
        });

    }

    public void changeFragment(Fragment frag) {
        if (frag == null) return;
        if (lastIndex == currentIndex) {
            return;
        }
        FragmentTransaction transaction = supportFragmentManager.beginTransaction();
        if (lastIndex < currentIndex) {
            transaction.setCustomAnimations(R.anim.fragment_slide_right_in, R.anim.fragment_slide_left_out, R.anim.fragment_slide_left_in, R.anim.fragment_slide_right_out);
        } else {
            transaction.setCustomAnimations(R.anim.fragment_slide_left_in, R.anim.fragment_slide_right_out, R.anim.fragment_slide_left_out, R.anim.fragment_slide_right_in);
        }
        transaction.hide(fragments[lastIndex]);
        transaction.show(frag);
        transaction.commitNow();

    }
}
