package com.example.hand.mockingbot.fagment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hand.mockingbot.R;
import com.example.hand.mockingbot.avtivity.LoginActivity;
import com.example.hand.mockingbot.avtivity.MyAttentionActivity;
import com.example.hand.mockingbot.avtivity.MyCollectionActivity;
import com.example.hand.mockingbot.datamanage.HttpManager;
import com.example.hand.mockingbot.entity.LoginEntity;
import com.example.hand.mockingbot.utils.Fields;
import com.example.hand.mockingbot.utils.HandApp;
import com.example.hand.mockingbot.utils.SpUtils;
import com.example.hand.mockingbot.utils.ToastUtil;


/**
 * Created by zhy on 2017/4/24.
 */

public class MyFragment extends Fragment implements View.OnClickListener {

    public static final int CHOOSE_PICTURE = 1;
    private Button zx;
    private LinearLayout account_security;
    private LinearLayout modify_password;
    private LinearLayout my_concern;
    private LinearLayout my_attention;
    private LinearLayout help_feedback;
    private LinearLayout about;
    private TextView my_name;
    private LoginEntity.ResultBean.DataBean data;
    private TextView position;
    private TextView department;
    private ImageView my_photo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container,false);
        my_photo = (ImageView) view.findViewById(R.id.my_igv);
        my_photo.setImageURI(HandApp.getPhotoUri());
        data = HandApp.getLoginEntity().getResult().getData();
        my_name = (TextView) view.findViewById(R.id.my_name);
        my_name.setText(data.getRealname());
        position = (TextView) view.findViewById(R.id.my_tv_zw);
        position.setText(data.getPosition());
        department = (TextView) view.findViewById(R.id.my_tv_bm);
        department.setText(data.getDepartment());
        zx = (Button) view.findViewById(R.id.my_btn_zx);
        zx.setOnClickListener(this);
        account_security = (LinearLayout) view.findViewById(R.id.my_account_and_security);
        account_security.setOnClickListener(this);
        modify_password = (LinearLayout) view.findViewById(R.id.my_modify_password);
        modify_password.setOnClickListener(this);
        my_attention = (LinearLayout) view.findViewById(R.id.my_attention);
        my_attention.setOnClickListener(this);
        my_concern = (LinearLayout) view.findViewById(R.id.my_concern);
        my_concern.setOnClickListener(this);
        help_feedback = (LinearLayout) view.findViewById(R.id.my_help_and_feedback);
        help_feedback.setOnClickListener(this);
        about = (LinearLayout) view.findViewById(R.id.my_about);
        about.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.my_account_and_security:
                ToastUtil.showToast(getContext(),"此功能正在开发中，敬请期待!");
                break;
            case R.id.my_modify_password:
                ToastUtil.showToast(getContext(),"此功能正在开发中，敬请期待!");
                break;
            case R.id.my_attention:
                intent.setClass(getContext(), MyAttentionActivity.class);
                startActivity(intent);
                break;
            case R.id.my_concern:
                intent.setClass(getContext(), MyCollectionActivity.class);
                startActivity(intent);
                break;
            case R.id.my_help_and_feedback:
                ToastUtil.showToast(getContext(),"此功能正在开发中，敬请期待!");
                break;
            case R.id.my_about:
                ToastUtil.showToast(getContext(),"此功能正在开发中，敬请期待!");
                break;
            case R.id.my_btn_zx:
                SpUtils.saveString(getContext(),Fields.PASSWORD,"");
                SpUtils.saveisBoolean(getContext(),Fields.SAVE_PASSWORD,false);
                intent.setClass(getContext(), LoginActivity.class);
                startActivity(intent);
                MyFragment.this.getActivity().finish();
        }
    }

    public interface ActivityResultCallback {
        void onActivityResult(int requestCode, int resultCode, Intent data);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if (resultCode == 0){
            if (requestCode == CHOOSE_PICTURE){
                if (null != data) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            my_photo.setImageURI(data.getData());
                            HandApp.setPhotoUri(data.getData());
                            SpUtils.saveString(getContext(),Fields.PHOTO_PATH, HttpManager.getRealPathFromUri(data.getData(), getContext()));
                        }
                    });
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
