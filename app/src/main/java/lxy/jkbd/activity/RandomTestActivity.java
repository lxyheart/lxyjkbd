package lxy.jkbd.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
    TextView tvExamInfo,tvExamTitle,tvop1,tvop2,tvop3,tvop4,tload;
    ImageView imageView;
    LinearLayout layoutload;
    ProgressBar pdialog;
    IExamBiz biz;
    boolean isLoadExamInfo = false;
    boolean isLoadQuestion = false;

    boolean isLoadExamInfoReceiver = false;
    boolean isLoadQuestionReceiver = false;

    LoadExamAndQuestionBroadcast mLoadExamAnQuestiondBroadcast;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        mLoadExamAnQuestiondBroadcast = new LoadExamAndQuestionBroadcast();
        biz = new ExamBiz();
      //  mLoadQuestionBroadcast = new LoadQuestionBroadcast();
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
        IntentFilter filter= new IntentFilter(ExamApplication.LOAD_EXAM_QUESTION);
        filter.addAction(ExamApplication.LOAD_EXAM_INFO);
        registerReceiver(mLoadExamAnQuestiondBroadcast,filter);
    }

    private void loadData() {
        pdialog.setVisibility(View.VISIBLE);
        layoutload.setEnabled(false);
        tload.setText("下载数据...");
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
        tload = (TextView) findViewById(R.id.tv_load);
        layoutload = (LinearLayout) findViewById(R.id.l_load);
        pdialog = (ProgressBar) findViewById(R.id.load_dialog);
        imageView = (ImageView)findViewById(R.id.image_title);
        layoutload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               loadData();
            }
        });
    }

    private void initData() {
        if(isLoadQuestionReceiver && isLoadExamInfoReceiver) {
            if(isLoadExamInfo && isLoadQuestion){
                layoutload.setVisibility(View.GONE);
                ExamInfo examInfo =  ExamApplication.getInstance().getExamInfo();
                if(examInfo != null){
                    showDate(examInfo);
                }
                List<Question> questionList =ExamApplication.getInstance().getQuestionList();
                if(questionList != null){
                    showExam(questionList);
                }
            }
        }else {
            layoutload.setEnabled(true);
            pdialog.setVisibility(View.GONE);
            tload.setText("下载失败，点击重新下载");
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
        if(mLoadExamAnQuestiondBroadcast != null){
            unregisterReceiver(mLoadExamAnQuestiondBroadcast);
        }
//        if(mLoadQuestionBroadcast != null){
//            unregisterReceiver(mLoadQuestionBroadcast);
//        }
    }
    class LoadExamAndQuestionBroadcast extends BroadcastReceiver{
        boolean isExamSuccess = false;
        boolean isQuestionSuccess = false;
        @Override
        public void onReceive(Context context, Intent intent) {
            if(isExamSuccess!=true){
                isExamSuccess = intent.getBooleanExtra(ExamApplication.LOAD_DATA_EXAM_SUCCESS,false);
            }
            if(isQuestionSuccess!=true){
                isQuestionSuccess = intent.getBooleanExtra(ExamApplication.LOAD_DATA_Question_SUCCES,false);
            }

            Log.e("LoadExamBroadcast","LoadExamBroadcast,isSuccess="+isExamSuccess);
            Log.e("LoadQuestionBroadcast","LoadQuestionBroadcast,isSuccess="+isQuestionSuccess);
            if(isExamSuccess ) {
               isLoadExamInfo = true;
            }
            if(isQuestionSuccess) {
                isLoadQuestion = true;
            }
            isLoadExamInfoReceiver = true;
            isLoadQuestionReceiver = true;
            initData();
        }
    }

//    class LoadQuestionBroadcast extends BroadcastReceiver{
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            boolean isSuccess = intent.getBooleanExtra(ExamApplication.LOAD_EXAM_SUCCESS,false);
//            Log.e("LoadQuestionBroadcast","LoadQuestionBroadcast,isSuccess="+isSuccess);
//            if(isSuccess) {
//                isLoadQuestion = true;
//            }
//            initData();
//        }
//    }
}
