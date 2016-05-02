package com.acme.catalogue;

/**
 * Created by Djinodji on 2/25/2016.
 */
public class SoapParam {
    private String paramName;
    private String paramValue;
    private  Object type;

    public Object getType() {
        return type;
    }

    public void setType(Object type) {
        this.type = type;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }
}
