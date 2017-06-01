package com.example.hand.mockingbot.fagment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

import com.example.hand.mockingbot.R;

/**
 * Created by zhy on 2017/6/1.
 */

public class AttentionPersonFragment extends Fragment {

    private GridLayout gl;
    private View add;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.fragment_attention_person, null);
        gl = (GridLayout) view.findViewById(R.id.attention_person_gl);
        add = View.inflate(getContext(), R.layout.item_attention_person_add, null);
        gl.addView(add);
        for (int i = 0; i < 6; i++) {
            final View inflate = View.inflate(getContext(), R.layout.item_add_person, null);
            View delete = inflate.findViewById(R.id.item_has_look_delete);
            delete.setVisibility(View.VISIBLE);
            gl.addView(inflate,0);
        }

        return view;
    }

}
