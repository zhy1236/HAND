package com.example.hand.mockingbot.fagment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.hand.mockingbot.R;
import com.example.hand.mockingbot.avtivity.LoginActivity;
import com.example.hand.mockingbot.utils.Fields;
import com.example.hand.mockingbot.utils.SpUtils;



/**
 * Created by zhy on 2017/4/24.
 */

public class MyFragment extends Fragment implements View.OnClickListener {


    private Button zx;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container,false);
        zx = (Button) view.findViewById(R.id.my_btn_zx);
        zx.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.my_btn_zx:
                SpUtils.saveString(getContext(),Fields.PASSWORD,"");
                SpUtils.saveisBoolean(getContext(),Fields.SAVE_PASSWORD,false);
                Intent intent = new Intent();
                intent.setClass(getContext(), LoginActivity.class);
                startActivity(intent);
                MyFragment.this.getActivity().finish();
        }
    }
}
