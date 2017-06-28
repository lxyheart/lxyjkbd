package lxy.jkbd.bean;

import java.util.List;

/**
 * Created by acer on 2017/6/28.
 */

public class Result {


    /**
     * error_code : 0
     * reason : ok
     * result : []
     */

    private int error_code;
    private String reason;
    private List<ExamInfo> result;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List<ExamInfo> getResult() {
        return result;
    }

    public void setResult(List<ExamInfo> result) {
        this.result = result;
    }
}
