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
import lxy.jkbd.biz.ExamBiz;
import lxy.jkbd.biz.IExamBiz;
import lxy.jkbd.utils.OkHttpUtils;
import lxy.jkbd.utils.ResultUtils;

/**
 * Created by Administrator on 2017/6/30.
 */

public class ExamApplication extends Application {
    public static  String LOAD_EXAM_INFO = "load_exam_info";
    public static  String LOAD_EXAM_QUESTION = "load_exam_question";
    public static  String LOAD_DATA_EXAM_SUCCESS = "load_data_exam_success";
    public static  String LOAD_DATA_Question_SUCCES = "load_data_question_success";
    public
    ExamInfo examInfo;
    List<Question> questionList;
    private static ExamApplication instance;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

    }
   public static  ExamApplication getInstance(){
        return instance;

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
