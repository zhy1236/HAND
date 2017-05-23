package com.example.hand.mockingbot.fagment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hand.mockingbot.uimanager.LoaderPager;
import com.example.hand.mockingbot.utils.HandApp;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by sy_heima on 2016/7/23.
 */
public abstract class BasicFragment extends Fragment
{


    
    protected LoaderPager mLoaderPager;
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        // 当前的页面销毁了,变量数据还没有被销毁
        if (mLoaderPager == null)
        {
            
            mLoaderPager = new LoaderPager(HandApp.context)
            {
                @Override
                public Object onSubDataAsync()
                {
                    return threadData();
                }
                
                @Override
                public View createSuccessView()
                {
                    return createView();
                }
            };
            
        }
        return mLoaderPager;
    }
    
    // 子线程去获取数据
    public abstract Object threadData();
    
    // 小白实现布局
    public abstract View createView();
    public String getData(boolean b){
        if (b){
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date curDate = new Date(System.currentTimeMillis());//获取当前时间
            String str = formatter.format(curDate);
            return str;
        }else {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date curDate = new Date(System.currentTimeMillis());//获取当前时间
            String str = formatter.format(curDate);
            return str;

        }
    }

    public String getTime(long submitDate){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = formatter.format(submitDate);
        return str;
    }
}
