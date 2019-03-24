package com.hncy.platform.managerservice.common.msg;

import org.springframework.http.HttpStatus;
import java.io.Serializable;

/**
 * 系统内部数据返回封装类。
 * @author puddor
 * @date 2016/9/19 14:22
 */
public class ResultBean<T> implements Serializable{


    private static  String SUCCESS_MESSAGE = "操作成功!";

    /**
     * 业务处理结果代码。
     */
    private int code;

    /**
     * 业务处理消息。
     */
    private String message;

    /**
     * 业务处理异常时候的错误日志数据，非关键数据
     */
    private Throwable exception;

    /**
     * 返回的数据。
     */
    private T data = null;

    public static <T> ResultBean<T> success(){
        return new ResultBean<T>().setCode(HttpStatus.OK.value()).setMessage(SUCCESS_MESSAGE);
    }



    public static <T> ResultBean<T> result(int code ,String message){
        return getInstance().setCode(code).setMessage(message).setData(null);
    }

    public static <T> ResultBean<T> result(int code ,String message,T data){
        return getInstance().setCode(code).setMessage(message).setData(data);
    }

    public static <T> ResultBean<T> success(T t){
        return new ResultBean<T>().setCode(HttpStatus.OK.value()).setMessage(SUCCESS_MESSAGE).setData(t);
    }

    public static <T> ResultBean<T> failure(int code,Throwable e){
        return getInstance().setCode(code).setException(e).setData(e.getMessage()).setCode(code);
    }

    public boolean isSuccess() {
        return  this.code ==HttpStatus.OK.value() ? true:false;
    }

    public int getCode() {
        return code;
    }

    public ResultBean<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ResultBean<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return data;
    }

    public ResultBean<T> setData(T data) {
        this.data = data;
        return this;
    }



    public Throwable getException() {
        return exception;
    }

    public ResultBean<T> setException(Throwable exception) {
        this.exception = exception;
        return this;
    }

    public static ResultBean getInstance(){
        return new ResultBean();
    }
}
