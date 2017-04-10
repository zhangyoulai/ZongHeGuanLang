package com.mj.notes.bean;

import java.util.Arrays;

public class IceBox {
    private String R3code;
    private String iceBoxName;

    //送货单字段
    private String number;
    private String customer;
    private String company;
    private String address;
    private String phone;

    private String [] array;
    private boolean flag = false;

    public String [] ib2Arrary(){
        if(flag){
            array = new String[]{ R3code,iceBoxName,number,customer,company,address,phone};
            return  array;
        }else {
            return null;
        }
    }

    public IceBox(){
        flag = false;
    }

    public void setFlag(boolean b){
        flag = b ;
    }

    public IceBox( String r3code, String iceBoxName,String number,
                   String customer,String company,String address,String phone) {
        this.iceBoxName = iceBoxName;
        this.R3code = r3code;
        this.number = number;
        this.customer =customer;
        this.company =company;
        this.address = address;
        this.phone = phone;
        flag = true;
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

    public String getIceBoxName() {
        return iceBoxName;
    }

    public void setIceBoxName(String iceBoxName) {
        this.iceBoxName = iceBoxName;
    }

    public String getR3code() {
        return R3code;
    }

    public void setR3code(String r3code) {
        R3code = r3code;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "IceBox{" +
                "array=" + Arrays.toString(array) +
                ", R3code='" + R3code + '\'' +
                ", iceBoxName='" + iceBoxName + '\'' +
                ", number='" + number + '\'' +
                ", customer='" + customer + '\'' +
                ", company='" + company + '\'' +
                ", address='" + address + '\'' +
                ", phone='"+ phone + '\''+
                ", flag=" + flag +
                '}';
    }
}
