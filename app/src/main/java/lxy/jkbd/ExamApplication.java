package lxy.jkbd;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import java.util.List;

import lxy.jkbd.activity.MainActivity;
import lxy.jkbd.activity.RandomTestActivity;
import lxy.jkbd.bean.ExamInfo;
import lxy.jkbd.bean.Question;
import lxy.jkbd.utils.OkHttpUtils;

/**
 * Created by Administrator on 2017/6/30.
 */

public class ExamApplication extends Application {
    ExamInfo examInfo;
    List<Question> questionList;
    private static ExamApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initData();
    }
   public static  ExamApplication getInstance(){
        return instance;

    }

    private void initData() {
        OkHttpUtils<ExamInfo> utils = new OkHttpUtils<>(instance);
        String url = "http://101.251.196.90:8080/JztkServer/examInfo";
        String url1 = "http://101.251.196.90:8080/JztkServer/getQuestions?testType=rand";
        utils.url(url)
                .targetClass(ExamInfo.class)
                .execute(new OkHttpUtils.OnCompleteListener<ExamInfo>(){

                    @Override
                    public void onSuccess(ExamInfo result) {
                        Log.e("main","result="+result);
                        examInfo = result;


                    }
                    @Override
                    public void onError(String error) {

                        Log.e("main","error="+error);
                    }
                });



    }
    public ExamInfo getExamInfo() {
        return examInfo;
    }

    public void setExamInfo(ExamInfo examInfo) {
        this.examInfo = examInfo;
    }

    public List<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }
}
