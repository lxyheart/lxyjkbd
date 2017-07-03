package lxy.jkbd.biz;

import lxy.jkbd.bean.Question;

/**
 * Created by Administrator on 2017/6/30.
 */

public interface IExamBiz {
    void beginExam();
    Question nextQuestion();
    Question preQuestion();
    void  commitExam();
    Question getQuestion();
    String getQusetionIndex();
}
