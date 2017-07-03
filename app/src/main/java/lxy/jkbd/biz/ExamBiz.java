package lxy.jkbd.biz;

import java.util.List;

import lxy.jkbd.ExamApplication;
import lxy.jkbd.bean.Question;
import lxy.jkbd.dao.ExamDao;
import lxy.jkbd.dao.IExamDao;

/**
 * Created by Administrator on 2017/6/30.
 */

public class ExamBiz implements IExamBiz {
    IExamDao dao;
    int questionIndex;
    List<Question> questionList = null;
    public ExamBiz() {
        this.dao = new ExamDao();
    }

    @Override
    public void beginExam() {
        questionIndex = 0;
        dao.loadExamInfo();
        dao.loadQuestionList();

    }

    @Override
    public Question nextQuestion() {
        if (questionList != null&&questionIndex<questionList.size()-1) {
            questionIndex++;
            return questionList.get(questionIndex);
        }else {
            return null;
        }
    }

    @Override
    public Question preQuestion() {
        if (questionList != null&&questionIndex<questionList.size()-1) {
            questionIndex--;
            return questionList.get(questionIndex);
        }else {
            return null;
        }
    }

    @Override
    public void commitExam() {

    }

    @Override
    public Question getQuestion() {
       questionList = ExamApplication.getInstance().getQuestionList();
       if(questionList != null){
           return  questionList.get(questionIndex);
       }else {
           return null;
       }
    }

    @Override
    public String getQusetionIndex() {
        return (questionIndex+1)+".";
    }
}
