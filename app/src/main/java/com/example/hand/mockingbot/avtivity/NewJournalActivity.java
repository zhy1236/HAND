package com.example.hand.mockingbot.avtivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hand.mockingbot.R;
import com.example.hand.mockingbot.datamanage.HttpManager;
import com.example.hand.mockingbot.utils.DataUtil;
import com.example.hand.mockingbot.utils.RoundRecTransform;
import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * Created by zhy on 2017/5/5.
 */

public class NewJournalActivity extends AppCompatActivity {

    public static final int CHOOSE_PICTURE = 1;
    private Toolbar toolbar;
    private GridLayout gl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_journal);
        toolbar = (Toolbar) findViewById(R.id.id_toolbar);
        toolbar.setTitle("");
//        设置左侧图标和点击事件
        toolbar.setNavigationIcon(R.mipmap.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        initview();
    }

    private void initview() {
        TextView btn = (TextView) findViewById(R.id.new_journal_tv_fj);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
                startActivityForResult(intent, CHOOSE_PICTURE);
            }
        });
        Button bt = (Button) findViewById(R.id.new_journal_tj);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HttpManager.getInstance().getUrl("http://192.168.11.198:8088/project-mg/daily/downloadAttachment?fileName=1493187497054__1464__Test0d.jpj");

            }
        });
        gl = (GridLayout) findViewById(R.id.new_journal_gl);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == CHOOSE_PICTURE ){
                if(data.getData()!=null){
                    adduri(data.getData());
//                    String path = HttpManager.getRealPathFromUri(data.getData(), getApplicationContext());
//                    File file = new File(path);
//                    HttpManager.getInstance().postAsynFile(file);
                }
            }
        }
    }

    private void adduri(Uri uri) {
        Uri u = uri;
        String extension = null;
        String path = null;
        if("file".equals(uri.getScheme())){
            path = uri.getPath();
            extension = path.substring(path.lastIndexOf("."));
        }else {
            path = HttpManager.getRealPathFromUri(uri, getApplicationContext());
            if(path!=null){
                u = Uri.parse(path);
            }
            u = Uri.fromFile(new File(uri.toString()));
            extension = path.substring(path.lastIndexOf("."));
        }
        ImageView imageView = new ImageView(getApplicationContext());
        imageView.setPadding(5,5,5,5);
        Picasso.with(this).load(uri).resize(200, 200).centerCrop().transform(new RoundRecTransform()).into(imageView);
        final Uri finalU = u;
        final String finalExtension = extension;
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataUtil.openFile(getApplicationContext(), finalU, finalExtension);
            }
        });
        gl.addView(imageView,0);

    }
}
