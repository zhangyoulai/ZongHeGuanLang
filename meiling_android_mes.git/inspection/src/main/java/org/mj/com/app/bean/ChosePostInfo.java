package org.mj.com.app.bean;

/**
 * Created by kouzeping on 2016/4/15.
 * email：kouzeping@shmingjiang.org.cn
 */
public class ChosePostInfo {
    int result=0;
    int position=0;

    public ChosePostInfo(int result, int position) {
        this.result = result;
        this.position = position;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
