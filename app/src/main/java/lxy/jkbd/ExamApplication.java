package lxy.jkbd;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import java.util.List;

import lxy.jkbd.activity.MainActivity;
import lxy.jkbd.activity.RandomTestActivity;
import lxy.jkbd.bean.ExamInfo;
import lxy.jkbd.bean.Question;
import lxy.jkbd.bean.Result;
import lxy.jkbd.utils.OkHttpUtils;
import lxy.jkbd.utils.ResultUtils;

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
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpUtils<ExamInfo> utils = new OkHttpUtils<>(instance);
                String url = "http://101.251.196.90:8080/JztkServer/examInfo";

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
                OkHttpUtils<String> utils1 = new OkHttpUtils<>(instance);
                String url1 = "http://101.251.196.90:8080/JztkServer/getQuestions?testType=rand";
                utils1.url(url1)
                        .targetClass(String.class)
                        .execute(new OkHttpUtils.OnCompleteListener<String>() {
                            @Override
                            public void onSuccess(String result) {
                                Result result1 = ResultUtils.getListResultFromJson(result);
                                if(result != null&& result1.getError_code()==0){
                                    List<Question> list = result1.getResult();
                                    if (list!=null&&list.size()>0){
                                        questionList = list;
                                    }
                                }
                            }

                            @Override
                            public void onError(String error) {
                                Log.e("main","error="+error);
                            }
                        });
            }
        }).start();




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
