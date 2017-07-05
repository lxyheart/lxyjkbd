package lxy.jkbd.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import lxy.jkbd.ExamApplication;
import lxy.jkbd.R;
import lxy.jkbd.bean.Question;

/**
 * Created by Administrator on 2017/7/4.
 */

public class QuestionAdapter extends BaseAdapter {
    Context mContext;
    List<Question> questionList;
    public QuestionAdapter(Context mContext) {
        this.mContext = mContext;
        questionList = ExamApplication.getInstance().getQuestionList();
    }

    @Override
    public int getCount() {
        return questionList==null?0:questionList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view =  View.inflate(mContext, R.layout.question_item,null);
        TextView tvNum = (TextView) view.findViewById(R.id.tv_num);
        ImageView ivQuestion = (ImageView) view.findViewById(R.id.iv_question);
        if(questionList.get(position).getUserAnswer() != null && !questionList.get(position).getUserAnswer().equals("")) {
            ivQuestion.setImageResource(R.mipmap.answer24x24);
        }else {
            ivQuestion.setImageResource(R.mipmap.ques24x24);
        }
        tvNum.setText("第"+(position+1)+"题");
        return view;
    }
}
