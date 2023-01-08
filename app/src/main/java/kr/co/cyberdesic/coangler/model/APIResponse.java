package kr.co.cyberdesic.coangler.model;

import android.util.Log;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Retrofit HTTP API APIResponse Template
 * Created by alex.han on 2018-03-20.
 */
public class APIResponse<T> {

    @SerializedName("code")
    @Expose
    public String code = "-1";
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("data")
    @Expose
    public List<T> data;

    /**
     * API 호출결과의 성공여부
     * @return 성공여부
     */
    public boolean success() {
        return (code != null && code.equals("0") );
    }

    /**
     * API 호출결과가 실패한 경우 오류 메시지를 되돌린다.
     * @return
     */
    public String getMessage() {
        return message;
    }

    /**
     * API를 통해 요청한 데이터 또는 데이터 목록
     * @return
     */
    public List<T> getData() {
        return data;
    }

    /**
     * JSON string 으로부터 ProxyRequest 또는 그의 상속 클래스의 객체 인스턴스로 변환
     * @param jsonString
     * @param className
     * @param <T>
     * @return
     */
    public static <T> T fromJson(String jsonString, @Nullable Class<?> className) {
        Gson gson = new Gson();

        if (className == null) {
            return (T)gson.fromJson(jsonString, APIResponse.class);
        } else {
            return (T)gson.fromJson(jsonString, className);
        }
    }

    /**
     * 네트워크 오류로 인해 API 요청이 실패한 경우 메시지 지정
     * @param msg
     */
    public void setNetworkError(String msg) {
        code = "99";
        message = "네트워크 오류: " + msg;

        Log.d("API CALL", "RES:" + message);
    }
}

