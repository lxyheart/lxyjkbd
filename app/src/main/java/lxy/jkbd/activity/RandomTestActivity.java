package lxy.jkbd.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

import lxy.jkbd.ExamApplication;
import lxy.jkbd.R;
import lxy.jkbd.bean.ExamInfo;
import lxy.jkbd.bean.Question;
import lxy.jkbd.biz.ExamBiz;
import lxy.jkbd.biz.IExamBiz;

/**
 * Created by Administrator on 2017/6/29.
 */

public class RandomTestActivity extends AppCompatActivity {
    TextView tvExamInfo,tvExamTitle,tvop1,tvop2,tvop3,tvop4;
    ImageView imageView;
    IExamBiz biz;
    boolean isLoadExamInfo = false;
    boolean isLoadQuestion = false;

    LoadExamBroadcast mLoadExamBroadcast;
    LoadQuestionBroadcast mLoadQuestionBroadcast;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        mLoadExamBroadcast = new LoadExamBroadcast();
        biz = new ExamBiz();
        mLoadQuestionBroadcast = new LoadQuestionBroadcast();
        setListener();
        initView();
        loadData();

//        Intent intent=this.getIntent();
//        ExamInfo examInfo = (ExamInfo) intent.getSerializableExtra("ExamInfo");
//        TextView t = (TextView)findViewById(R.id.btnexamtitle);
//        //Log.e("main",examInfo+"----------------------------");
//        t.setText(examInfo.toString());

    }

    private void setListener() {
        registerReceiver(mLoadExamBroadcast,new IntentFilter(ExamApplication.LOAD_EXAM_INFO));
        registerReceiver(mLoadQuestionBroadcast,new IntentFilter(ExamApplication.LOAD_EXAM_QUESTION));

    }

    private void loadData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                biz.beginExam();
            }
        }).start();
    }

    private void initView() {

        tvExamInfo = (TextView)findViewById(R.id.btnexamtitle);
        tvExamTitle = (TextView)findViewById(R.id.tv_exam_title);
        tvop1 = (TextView)findViewById(R.id.tv_item1);
        tvop2 = (TextView)findViewById(R.id.tv_item2);
        tvop3 = (TextView)findViewById(R.id.tv_item3);
        tvop4 = (TextView)findViewById(R.id.tv_item4);
        imageView = (ImageView)findViewById(R.id.image_title);
    }

    private void initData() {
    if(isLoadExamInfo&&isLoadQuestion){
        ExamInfo examInfo =  ExamApplication.getInstance().getExamInfo();
        if(examInfo != null){
            showDate(examInfo);
        }
        List<Question> questionList =ExamApplication.getInstance().getQuestionList();
        if(questionList != null){
            showExam(questionList);
        }

    }
 }

    private void showExam(List<Question> questionList) {

        Question question = questionList.get(0);
        if(question != null){
            tvExamTitle.setText(question.getQuestion());
            tvop1.setText(question.getItem1());
            tvop2.setText(question.getItem2());
            tvop3.setText(question.getItem3());
            tvop4.setText(question.getItem4());
            Picasso.with(RandomTestActivity.this)
                    .load(question.getUrl())
                    .into(imageView);


        }
    }

    private void showDate(ExamInfo examInfo) {
        tvExamInfo.setText(examInfo.toString());
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mLoadExamBroadcast != null){
            unregisterReceiver(mLoadExamBroadcast);
        }
        if(mLoadQuestionBroadcast != null){
            unregisterReceiver(mLoadQuestionBroadcast);
        }
    }
    class LoadExamBroadcast extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            boolean isSuccess = intent.getBooleanExtra(ExamApplication.LOAD_EXAM_SUCCESS,false);
            Log.e("LoadExamBroadcast","LoadExamBroadcast,isSuccess="+isSuccess);
            if(isSuccess) {
               isLoadExamInfo = true;
            }
            initData();
        }
    }

    class LoadQuestionBroadcast extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            boolean isSuccess = intent.getBooleanExtra(ExamApplication.LOAD_EXAM_SUCCESS,false);
            Log.e("LoadQuestionBroadcast","LoadQuestionBroadcast,isSuccess="+isSuccess);
            if(isSuccess) {
                isLoadQuestion = true;
            }
            initData();
        }
    }
}
