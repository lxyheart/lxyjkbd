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
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
    TextView tvExamInfo,tvExamTitle,tvop1,tvop2,tvop3,tvop4,tload,tvnum;
    ImageView imageView;
    CheckBox cb01,cb02,cb03,cb04;
    LinearLayout layoutload,layout03,layout04;
    ProgressBar pdialog;
    IExamBiz biz;
    CheckBox cbs[] = new CheckBox[4];
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
        tvnum =  (TextView)findViewById(R.id.tv_question_no);
        tvop1 = (TextView)findViewById(R.id.tv_item1);
        tvop2 = (TextView)findViewById(R.id.tv_item2);
        tvop3 = (TextView)findViewById(R.id.tv_item3);
        tvop4 = (TextView)findViewById(R.id.tv_item4);
        tload = (TextView) findViewById(R.id.tv_load);
        cb01 = (CheckBox) findViewById(R.id.cb_01);
        cb02 = (CheckBox) findViewById(R.id.cb_02);
        cb03 = (CheckBox) findViewById(R.id.cb_03);
        cb04 = (CheckBox) findViewById(R.id.cb_04);
        cbs[0] = cb01;
        cbs[1] = cb02;
        cbs[2] = cb03;
        cbs[3] = cb04;
        layoutload = (LinearLayout) findViewById(R.id.l_load);
        layout03 = (LinearLayout) findViewById(R.id.layout_03);
        layout04 = (LinearLayout) findViewById(R.id.layout_04);
        pdialog = (ProgressBar) findViewById(R.id.load_dialog);
        imageView = (ImageView)findViewById(R.id.image_title);
        layoutload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               loadData();
            }
        });
        cb01.setOnCheckedChangeListener(listener);
        cb02.setOnCheckedChangeListener(listener);
        cb03.setOnCheckedChangeListener(listener);
        cb04.setOnCheckedChangeListener(listener);
    }
    CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked){
                int userAnswer = 0;
                switch (buttonView.getId()){
                    case R.id.cb_01:
                        userAnswer = 1;
                        break;
                    case R.id.cb_02:
                        userAnswer  = 2;
                        break;
                    case R.id.cb_03:
                        userAnswer  = 3;
                        break;
                    case R.id.cb_04:
                        userAnswer  = 4;
                        break;
                }
                if(userAnswer>0) {
                    for (CheckBox cb : cbs) {
                        cb.setChecked(false);
                    }
                    cbs[userAnswer-1].setChecked(true);
                }
            }
            }

    } ;

    private void initData() {
        if(isLoadQuestionReceiver && isLoadExamInfoReceiver) {
            if(isLoadExamInfo && isLoadQuestion){
                layoutload.setVisibility(View.GONE);
                ExamInfo examInfo =  ExamApplication.getInstance().getExamInfo();
                if(examInfo != null){
                    showDate(examInfo);
                }
                showExam( biz.getQuestion());
            }else {
                layoutload.setEnabled(true);
                pdialog.setVisibility(View.GONE);
                tload.setText("下载失败，点击重新下载");
            }
        }
 }

    private void showExam(Question question) {

        if(question != null){
            tvnum.setText(biz.getQusetionIndex());
            tvExamTitle.setText(question.getQuestion());
            tvop1.setText(question.getItem1());
            tvop2.setText(question.getItem2());
            tvop3.setText(question.getItem3());
            tvop4.setText(question.getItem4());
            cb04.setVisibility(question.getItem4().equals("")?View.GONE:View.VISIBLE);
            layout04.setVisibility(question.getItem4().equals("")?View.GONE:View.VISIBLE);
            layout03.setVisibility(question.getItem3().equals("")?View.GONE:View.VISIBLE);
            cb03.setVisibility(question.getItem3().equals("")?View.GONE:View.VISIBLE);
            if(question.getUrl()!=null && !question.getUrl().equals("")){
                imageView.setVisibility(View.VISIBLE);
                Picasso.with(RandomTestActivity.this)
                        .load(question.getUrl())
                        .into(imageView);
            }else {
                imageView.setVisibility(View.GONE);
            }
            restCheckBox();
        }
    }

    private void restCheckBox() {
        for(CheckBox cb:cbs) {
            cb.setChecked(false);
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

    public void preQuestion(View view) {
        showExam(biz.preQuestion());
    }

    public void nextQuestion(View view) {
        showExam(biz.nextQuestion());

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
            Log.e("LoadExamAndQuestion","LoadExamAndQuestionBroadcast,isSuccess="+isExamSuccess);
            Log.e("LoadExamAndQuestiont","LoadExamAndQuestionBroadcast,isSuccess="+isQuestionSuccess);
            if(intent.getAction().equals(ExamApplication.LOAD_EXAM_INFO)) {
                isLoadExamInfoReceiver = true;
            }
            if(intent.getAction().equals(ExamApplication.LOAD_EXAM_QUESTION)) {
                isLoadQuestionReceiver = true;
            }
            if(isExamSuccess) {
                isLoadExamInfo = true;
            }
            if(isQuestionSuccess) {
                isLoadQuestion = true;
            }
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
