package com.example.springsecuritysecond.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonResult {
    private Boolean success;
    private Integer code;
    private HashMap<String,Object> data;
    private String message;
    public static CommonResult success(){
        CommonResult commonResult = new CommonResult();
        commonResult.setSuccess(true);
        commonResult.setCode(2000);
        return commonResult;
    }
    public static CommonResult fail(){
        CommonResult commonResult = new CommonResult();
        commonResult.setSuccess(false);
        commonResult.setCode(2001);
        return commonResult;
    }
    public  CommonResult data(String key,Object value){
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put(key, value);
        this.setData(hashMap);
        return this;
    }
    public  CommonResult message(String message){
      this.setMessage(message);
        return this;
    }

}
