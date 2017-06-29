package lxy.jkbd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.io.Serializable;

import lxy.jkbd.R;
import lxy.jkbd.bean.ExamInfo;

/**
 * Created by Administrator on 2017/6/29.
 */

public class RandomTestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);

        Intent intent=this.getIntent();
        ExamInfo examInfo = (ExamInfo) intent.getSerializableExtra("ExamInfo");
        TextView t = (TextView)findViewById(R.id.btnexamtitle);
        //Log.e("main",examInfo+"----------------------------");
        t.setText(examInfo.toString());

    }
}
