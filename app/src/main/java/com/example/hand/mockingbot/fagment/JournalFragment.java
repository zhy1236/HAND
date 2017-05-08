package com.example.hand.mockingbot.fagment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.hand.mockingbot.R;
import com.example.hand.mockingbot.avtivity.CommentJournalActivity;
import com.example.hand.mockingbot.avtivity.DefectJournalActivity;
import com.example.hand.mockingbot.avtivity.NewJournalActivity;
import com.example.hand.mockingbot.avtivity.ReceivedJournalAtivity;
import com.example.hand.mockingbot.avtivity.SendJournalActivity;

import static com.example.hand.mockingbot.R.id.journal_receiver;

/**
 * Created by zhy on 2017/5/3.
 */

public class JournalFragment extends Fragment implements View.OnClickListener {

    private RelativeLayout mJournal_receiver, mJournal_new, mJournal_send, mJournal_defect,mJournal_comment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_journal, container, false);
        mJournal_receiver = (RelativeLayout) view.findViewById(journal_receiver);
        mJournal_receiver.setOnClickListener(this);
        mJournal_new = (RelativeLayout) view.findViewById(R.id.journal_new);
        mJournal_new.setOnClickListener(this);
        mJournal_send = (RelativeLayout) view.findViewById(R.id.journal_send);
        mJournal_send.setOnClickListener(this);
        mJournal_defect = (RelativeLayout) view.findViewById(R.id.journal_defect);
        mJournal_defect.setOnClickListener(this);
        mJournal_comment = (RelativeLayout) view.findViewById(R.id.journal_comment);
        mJournal_comment.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.journal_receiver:
                intent.setClass(getContext(), ReceivedJournalAtivity.class);
                break;
            case R.id.journal_new:
                intent.setClass(getContext(), NewJournalActivity.class);
                break;
            case R.id.journal_send:
                intent.setClass(getContext(), SendJournalActivity.class);
                break;
            case R.id.journal_defect:
                intent.setClass(getContext(), DefectJournalActivity.class);
                break;
            case R.id.journal_comment:
                intent.setClass(getContext(), CommentJournalActivity.class);
                break;
        }
        startActivity(intent);
    }
}