package com.example.hand.mockingbot.fagment;

import android.view.View;

import com.example.hand.mockingbot.R;

/**
 * Created by zhy on 2017/5/23.
 */

public class ReceivedJournalFragment extends BasicFragment {
    @Override
    public Object threadData() {
        return null;
    }

    @Override
    public View createView() {
        View inflate = View.inflate(getContext(), R.layout.activity_received_journal, null);
        return inflate;
    }


}
