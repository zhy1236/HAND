package com.example.hand.mockingbot.uimanager;

import android.content.Context;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.hand.mockingbot.R;
import com.example.hand.mockingbot.utils.HandApp;
import com.example.hand.mockingbot.utils.Utils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sy_heima on 2016/7/22.
 */
// 当前的类是管理我们页面展示的
public abstract class LoaderPager extends FrameLayout
{
    
    @Bind(R.id.btn_reload)
    Button mBtnReload;
    
    private View mLoadingView;
    
    private View mFailView;
    
    private View mSuccessView;
    
    public LoaderPager(Context context)
    {
        this(context, null, 0);
        
    }
    
    public LoaderPager(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }
    
    public LoaderPager(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        // 初始化界面
        initLoaderPager();
    }
    
    /**
     * 初始化界面
     */
    private void initLoaderPager()
    {
        // 创建三个界面,分别加入到我们的布局
        // 加载的页面
        if (mLoadingView == null)
        {
            mLoadingView = View.inflate(HandApp.context, R.layout.page_loading, null);
        }
        
        // 加入到布局中
        addView(mLoadingView);
        
        // 失败的页面
        if (mFailView == null)
        {
            mFailView = View.inflate(HandApp.context, R.layout.page_error, null);
            ButterKnife.bind(this,mFailView);
        }
        
        // 加入到布局中
        addView(mFailView);
        
        // 加入成功的界面
        if (mSuccessView == null)
        {
            // 因为每个界面都不一样,我这类只是管理界面展示,我不管理你具体展示界面
            mSuccessView = createSuccessView();
        }
        
        // 加入到布局中
        addView(mSuccessView);
        
        // 调用切换view的方法
        showPager();
        
        // 根据网络的数据去展示不同状态的页面
        loadData();
        
    }
    
    /**
     * 根据加载的数据去展示不同的界面 如果数据失败,那么展示错误页面 如果数据成功,那么展示成功页面
     */
    public void loadData()
    {
        
        // 如果是网络的数据,那么这个必然是耗时
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                // 得到网络获取的数据
                // json获取的数据类型,只有两种,一种是bean,一种是集合
                
                // 睡一下
                SystemClock.sleep(600);
                
                // 得到网络数据解析后的结果
                Object obj = onSubDataAsync();
                
                // 检查当前的数据,检查只有两种结果,一个失败,一个成功
                currentStaut = checkData(obj);
                
                System.out.println("当前的集合状态:" + currentStaut);
                
                // 再次刷新数据
                Utils.runOnUIThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        // 更新UI
                        showPager();
                    }
                });
                
            }
        }).start();
        
    }
    
    // 检测当前的数据
    private int checkData(Object obj)
    {
        // 如果成功,返回success
        // 如果失败,返回error
        
        // 如果是对象,那么简单,null就可以
        if (obj == null)
        {
            return error;
        }
        else
        {
            // 不为空
            // 如果是集合呢,判断当前的数据是否大于0
            // 如果不是集合,那么就是成功
            if (obj instanceof List)
            {
                // 如果当前的集合数据大于0,为成功
                if (((List)obj).size() > 0)
                {
                    return success;
                }
                else
                {
                    return error;
                }
            }
            else
            {
                // 不是集合,那么就是成功
                return success;
            }
        }
        
    }
    
    // 这个具体去获取,解析json不是我们当前类该干的事情
    public abstract Object onSubDataAsync();
    
    // 这个是具体展示页面的时候去完成
    public abstract View createSuccessView();
    
    // 定义一个状态
    public static final int loading = 101;// 加载中状态
    
    public static final int success = 102;// 加载中状态
    
    public static final int error = 103;// 加载中状态
    
    // 记录当前状态
    private int currentStaut = loading;// 当前的状态应该加载中
    
    // 定义一个切换状态的方法
    public void showPager()
    {
        // 1. 把全部隐藏
        // 2. 然后根据当前的状态去切换显示
        mLoadingView.setVisibility(View.GONE);
        mFailView.setVisibility(View.GONE);
        mSuccessView.setVisibility(View.GONE);
        
        switch (currentStaut)
        {
            case loading:
                // 加载中
                mLoadingView.setVisibility(VISIBLE);
                
                break;
            case error:
                // 加载失败
                mFailView.setVisibility(VISIBLE);
                
                break;
            case success:
                // 加载中
                mSuccessView.setVisibility(VISIBLE);
                
                break;
                
            default:
                break;
                
        }
        
    }
    
    @OnClick(R.id.btn_reload)
    public void onClick() {
        System.out.println("重新加载了");

        currentStaut = loading;

        Utils.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                showPager();
            }
        });

        loadData();
    }
}
