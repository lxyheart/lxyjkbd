package lxy.jkbd.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import lxy.jkbd.R;
import lxy.jkbd.bean.ExamInfo;
import lxy.jkbd.utils.OkHttpUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void RandomTest(View view) {
        OkHttpUtils<ExamInfo> utils = new OkHttpUtils<>(getApplicationContext());
        String url = "http://101.251.196.90:8080/JztkServer/examInfo";
        utils.url(url)
                .targetClass(ExamInfo.class)
                .execute(new OkHttpUtils.OnCompleteListener<ExamInfo>(){

                    @Override
                    public void onSuccess(ExamInfo result) {
                        Log.e("main","result="+result);
                    }

                    @Override
                    public void onError(String error) {
                        Log.e("main","error="+error);
                    }
                });

        Intent intent = new Intent(MainActivity.this,RandomTestActivity.class);
        startActivity(intent);
    }

    public void exit(View view) {
        finish();
    }
}
