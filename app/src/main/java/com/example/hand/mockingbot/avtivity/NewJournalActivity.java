package com.example.hand.mockingbot.avtivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hand.mockingbot.R;
import com.example.hand.mockingbot.datamanage.HttpManager;
import com.example.hand.mockingbot.entity.AttauchBean;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhy on 2017/5/5.
 */

public class NewJournalActivity extends BasicActivity {

    public static final int CHOOSE_PICTURE = 1;
    private Toolbar toolbar;
    private GridLayout gl;
    private List<AttauchBean> attauchlist = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_journal);
        toolbar = (Toolbar) findViewById(R.id.id_toolbar);
        toolbar.setTitle("");
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
        LinearLayout btn = (LinearLayout) findViewById(R.id.new_journal_tv_fj);
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
        AttauchBean attauchBean = new AttauchBean();
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
        attauchBean.setExtension(extension);
        attauchBean.setUri(u);
        attauchBean.setFieldName(path.substring(path.lastIndexOf("/") + 1, path.length()));
        File file = new File(path);
        int available;
        try {
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            available = fis.available();
            attauchBean.setSize(available/1024 + "K");
        } catch (IOException e) {
            e.printStackTrace();
        }

        final View inflate = View.inflate(getApplicationContext(), R.layout.attauch_item, null);
        TextView name = (TextView) inflate.findViewById(R.id.attauch_item_field_name);
        name.setText(path.substring(path.lastIndexOf("/") + 1, path.length()));
        ImageView viewById = (ImageView) inflate.findViewById(R.id.attauch_item_iv);
        if (extension.endsWith(".jpg")||extension.endsWith(".jpeg")|| extension.endsWith(".png")||extension.endsWith(".bmp")|| extension.endsWith(".gif")){
            viewById.setImageResource(R.mipmap.ic_iv);
        }else if (extension.endsWith(".doc")){
            viewById.setImageResource(R.mipmap.ic_word);
        }else if (extension.endsWith(".ppt")) {
            viewById.setImageResource(R.mipmap.ic_ppt);
        }
        TextView sise = (TextView) inflate.findViewById(R.id.attauch_item_field_sise);
        sise.setText(attauchBean.getSize());
        View delete = inflate.findViewById(R.id.attauch_item_field_delete);
        delete.setVisibility(View.VISIBLE);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gl.removeView(inflate);
            }
        });
        attauchlist.add(attauchBean);
        gl.addView(inflate);

    }

}
