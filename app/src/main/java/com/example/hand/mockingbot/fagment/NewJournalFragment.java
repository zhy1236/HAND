package com.example.hand.mockingbot.fagment;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.hand.mockingbot.R;
import com.example.hand.mockingbot.datamanage.HttpManager;

import java.io.File;

import static android.app.Activity.RESULT_OK;


/**
 * Created by zhy on 2017/4/24.
 */

public class NewJournalFragment extends Fragment {

    public static final int CHOOSE_PICTURE = 1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.activity_fragment_new_journal, container,false);
        TextView btn = (TextView) inflate.findViewById(R.id.new_journal_tv_fj);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                HttpManager.getInstance().postAsynFile();
                Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
                startActivityForResult(intent, CHOOSE_PICTURE);
            }
        });
        Button bt = (Button) inflate.findViewById(R.id.new_journal_tj);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HttpManager.getInstance().getUrl("http://192.168.11.198:8088/project-mg/daily/downloadAttachment?fileName=1493187497054__1464__Test0d.jpj");

            }
        });
        return inflate;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == CHOOSE_PICTURE ){
                if(data.getData()!=null){
                    String path = HttpManager.getRealPathFromUri(data.getData(), getContext());
                    File file = new File(path);
                    HttpManager.getInstance().postAsynFile(file);
                }
            }
        }
    }
}
