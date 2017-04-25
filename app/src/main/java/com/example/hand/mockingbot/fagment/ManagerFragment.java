package com.example.hand.mockingbot.fagment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.example.hand.mockingbot.R;

import java.util.List;


/**
 * Created by zhy on 2017/4/24.
 */

public class ManagerFragment extends Fragment {

    private GridLayout gridLayout;
    private List showlist;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment_manager, container,false);
        gridLayout = (GridLayout) view.findViewById(R.id.manager_gl);
        gridLayout.setColumnCount(6);
        ImageView imageView = new ImageView(getContext());
        imageView.setBackgroundResource(R.mipmap.ic_launcher);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ImageView imageView1 = new ImageView(getContext());
                imageView1.setBackgroundResource(R.mipmap.ic_launcher);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        gridLayout.addView(imageView1,0);
                    }
                });
            }
        });
        gridLayout.addView(imageView);
        return view;
    }
}
