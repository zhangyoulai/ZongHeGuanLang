package com.example.hasee.compare.bean;

/**
 * Created by root on 16-4-8.
 */
public class CodeAndSpec {
    private String code;
    private String spec;
    private String [] array;
    private boolean flag = false;

    public CodeAndSpec(){
        flag = false;
    }

    public CodeAndSpec(String code,String spec){
        this.code = code;
        this.spec = spec;
        flag = true;
    }

    public String [] ib2Arrary(){
        if(flag){
            array = new String[]{ code,spec};
            return array;
        }else {
            return null;
        }
    }

    public String[] getArray() {
        return array;
    }

    public void setArray(String[] array) {
        this.array = array;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean b) {
        flag = b;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }
}
