package lxy.jkbd.dao;

import android.content.Intent;
import android.util.Log;

import java.util.List;

import lxy.jkbd.ExamApplication;
import lxy.jkbd.bean.ExamInfo;
import lxy.jkbd.bean.Question;
import lxy.jkbd.bean.Result;
import lxy.jkbd.utils.OkHttpUtils;
import lxy.jkbd.utils.ResultUtils;

/**
 * Created by Administrator on 2017/6/30.
 */

public class ExamDao implements IExamDao {

    @Override
    public void loadExamInfo() {
        OkHttpUtils<ExamInfo> utils = new OkHttpUtils<>(ExamApplication.getInstance());
        String url = "http://101.251.196.90:8080/JztkServer/examInfo";

        utils.url(url)
                .targetClass(ExamInfo.class)
                .execute(new OkHttpUtils.OnCompleteListener<ExamInfo>(){

                    @Override
                    public void onSuccess(ExamInfo result) {
                        Log.e("main","result="+result);
                        ExamApplication.getInstance().setExamInfo(result);
                        ExamApplication.getInstance()
                                .sendBroadcast(new Intent(ExamApplication.LOAD_EXAM_INFO)
                                 .putExtra(ExamApplication.LOAD_EXAM_SUCCESS,true));

                    }
                    @Override
                    public void onError(String error) {

                        Log.e("main","error="+error);
                        ExamApplication.getInstance()
                                .sendBroadcast(new Intent(ExamApplication.LOAD_EXAM_INFO)
                                        .putExtra(ExamApplication.LOAD_EXAM_SUCCESS,false));
                    }
                });
    }

    @Override
    public void loadQuestionList() {
        OkHttpUtils<String> utils1 = new OkHttpUtils<>(ExamApplication.getInstance());
        String url1 = "http://101.251.196.90:8080/JztkServer/getQuestions?testType=rand";
        utils1.url(url1)
                .targetClass(String.class)
                .execute(new OkHttpUtils.OnCompleteListener<String>() {
                    @Override
                    public void onSuccess(String result) {
                        boolean success = false;
                        Result result1 = ResultUtils.getListResultFromJson(result);
                        if(result != null&& result1.getError_code()==0){
                            List<Question> list = result1.getResult();
                            if (list!=null&&list.size()>0){
                               ExamApplication.getInstance().setQuestionList(list);
                                success = true;

                            }
                        }
                        ExamApplication.getInstance()
                                .sendBroadcast(new Intent(ExamApplication.LOAD_EXAM_QUESTION)
                                        .putExtra(ExamApplication.LOAD_EXAM_SUCCESS,success));
                    }

                    @Override
                    public void onError(String error) {
                        Log.e("main","error="+error);
                        ExamApplication.getInstance()
                                .sendBroadcast(new Intent(ExamApplication.LOAD_EXAM_QUESTION)
                                .putExtra(ExamApplication.LOAD_EXAM_SUCCESS,false));
                    }
                });
    }
}
