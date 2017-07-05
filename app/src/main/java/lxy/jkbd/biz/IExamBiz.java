package lxy.jkbd.biz;

import lxy.jkbd.bean.Question;

/**
 * Created by Administrator on 2017/6/30.
 */

public interface IExamBiz {
    void beginExam();
    Question nextQuestion();
    Question preQuestion();
    int  commitExam();
    Question getQuestion();
    Question getQuestion(int index);
    String getQusetionIndex();
}
