package lxy.jkbd.activity;

import android.content.Intent;
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

/**
 * Created by Administrator on 2017/6/29.
 */

public class RandomTestActivity extends AppCompatActivity {
    TextView tvExamInfo,tvExamTitle,tvop1,tvop2,tvop3,tvop4;
    ImageView imageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        initView();
        initDate();

//        Intent intent=this.getIntent();
//        ExamInfo examInfo = (ExamInfo) intent.getSerializableExtra("ExamInfo");
//        TextView t = (TextView)findViewById(R.id.btnexamtitle);
//        //Log.e("main",examInfo+"----------------------------");
//        t.setText(examInfo.toString());

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

    private void initDate() {

      ExamInfo examInfo =  ExamApplication.getInstance().getExamInfo();
        if(examInfo != null){
        showDate(examInfo);
    }
        List<Question> questionList =ExamApplication.getInstance().getQuestionList();
        if(questionList != null){
            showExam(questionList);
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
}
