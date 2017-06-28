package lxy.jkbd.bean;

/**
 * Created by acer on 2017/6/28.
 */

public class Result {

    /**
     * error_code : 0
     * reason : ok
     * result : 0
     */

    private int error_code;
    private String reason;
    private Object result;

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

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
