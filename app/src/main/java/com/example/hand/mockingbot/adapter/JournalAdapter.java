package com.example.hand.mockingbot.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.hand.mockingbot.R;
import com.example.hand.mockingbot.entity.JournalBean;
import com.example.hand.mockingbot.utils.GeelyApp;

import java.util.List;

/**
 * Created by zhy on 2017/5/5.
 */

public class JournalAdapter extends BaseAdapter {

    private List<JournalBean> show;
    public JournalAdapter(List<JournalBean> list) {
        show = list;
    }

    @Override
    public int getCount() {
        return show.size();
    }

    @Override
    public Object getItem(int i) {
        return show.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View inflate = View.inflate(GeelyApp.context, R.layout.journal_item, viewGroup);
        return inflate;
    }
}
