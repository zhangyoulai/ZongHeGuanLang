package com.mingjiang.android.app.bean;

/**
 * Created by kouzeping on 2016/2/25.
 * email：kouzeping@shmingjiang.org.cn
 *
 *
 */
public class BadnessItem {
    public String spec ="";//冰箱型号
    public String name1="";//第一类不良名字
    public int value1=0;//第一类不良数量
    public String name2="";//第二类不良名称
    public int value2=0;//第二类不良数量
    public String name3="";
    public int value3=0;
    public String name4="";
    public int value4=0;
    public String name5="";
    public int value5=0;
    public int badcount=1;//不合格产品总数
    public int badtotal=1;//不合格记录总数
    public int count=1;//总共生产产品数

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public int getValue1() {
        return value1;
    }

    public void setValue1(int value1) {
        this.value1 = value1;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public int getValue2() {
        return value2;
    }

    public void setValue2(int value2) {
        this.value2 = value2;
    }

    public String getName3() {
        return name3;
    }

    public void setName3(String name3) {
        this.name3 = name3;
    }

    public int getValue3() {
        return value3;
    }

    public void setValue3(int value3) {
        this.value3 = value3;
    }

    public String getName4() {
        return name4;
    }

    public void setName4(String name4) {
        this.name4 = name4;
    }

    public int getValue4() {
        return value4;
    }

    public void setValue4(int value4) {
        this.value4 = value4;
    }

    public String getName5() {
        return name5;
    }

    public void setName5(String name5) {
        this.name5 = name5;
    }

    public int getValue5() {
        return value5;
    }

    public void setValue5(int value5) {
        this.value5 = value5;
    }

    public int getBadcount() {
        return badcount;
    }

    public void setBadcount(int badcount) {
        this.badcount = badcount;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
