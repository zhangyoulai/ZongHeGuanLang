package com.mingjiang.android.app.asynctask;
import android.os.AsyncTask;

import com.mingjiang.android.app.util.HttpClientUtils;


/**
 * Created by kouzeping on 2016/1/26.
 * email：kouzeping@shmingjiang.org.cn
 */
public class RequireAsyncTask extends AsyncTask {
    OnResultListener onResultListener;
    public RequireAsyncTask(OnResultListener onResultListener){
        this.onResultListener=onResultListener;
    }
    @Override
    protected Object doInBackground(Object[] params) {
        String data=null;
        data= HttpClientUtils.getData((String)params[0],(String)params[1]);
        return data;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        onResultListener.onResultListener((String)o);
    }
   public interface OnResultListener{
        public void onResultListener(String result);
    }
}

