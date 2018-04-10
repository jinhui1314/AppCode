package cc.mystory.android.base;

/**
 * Create by Lei on 2016/11/25
 */

public class BaseBean<T> {
    private int code;
    private String errorInfo;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    private T dataList;

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public T getDataList() {
        return dataList;
    }

    public void setDataList(T data) {
        this.dataList = data;
    }

    @Override
    public String toString() {
        return "BaseBean{" +
                "code=" + code +
                ", errorInfo='" + errorInfo + '\'' +
                ", message='" + message + '\'' +
                ", data=" + dataList +
                '}';
    }
}
