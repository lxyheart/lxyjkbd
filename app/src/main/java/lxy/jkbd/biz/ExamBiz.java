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
        if (questionList != null) {
            if(questionIndex<questionList.size()-1){
                questionIndex++;
            }
            return questionList.get(questionIndex);
        }else {
            return null;
        }
    }

    @Override
    public Question preQuestion() {
        if (questionList != null) {
            if (questionIndex>0){
                questionIndex--;
            }
            return questionList.get(questionIndex);
        }else {
            return null;
        }
    }

    @Override
    public int commitExam() {
        int s = 0;
        for (Question question : questionList) {
            String userAnswer = question.getUserAnswer();
            if (question.getAnswer().equals(userAnswer)) {
                s++;
            }
        }
        return  s;
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
    public Question getQuestion(int index) {
        questionList = ExamApplication.getInstance().getQuestionList();
        questionIndex = index;
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
