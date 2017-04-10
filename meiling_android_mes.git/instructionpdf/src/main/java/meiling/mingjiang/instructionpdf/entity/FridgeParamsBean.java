package meiling.mingjiang.instructionpdf.entity;

import com.google.gson.Gson;

/**
 * Created by kouzeping on 2016/3/20.
 * email：kouzeping@shmingjiang.org.cn
 */

public class FridgeParamsBean {
    public FridgePostBean params;

    public FridgePostBean getParams() {
        return params;
    }

    public void setParams(FridgePostBean params) {
        this.params = params;
    }
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
