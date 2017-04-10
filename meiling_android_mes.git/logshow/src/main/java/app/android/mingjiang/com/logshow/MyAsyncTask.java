package app.android.mingjiang.com.logshow;

import android.os.AsyncTask;

/**
 * Created by kouzeping on 2016/4/12.
 * email：kouzeping@shmingjiang.org.cn
 */
public class MyAsyncTask extends AsyncTask<String,String,Boolean> {

    OnResultListener onResultListener;

    public MyAsyncTask(OnResultListener onResultListener) {
        this.onResultListener = onResultListener;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        boolean result= HttpClientUtils.postData(params[0],params[1]);
        return result;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        onResultListener.onResultListener(aBoolean);
    }

    interface OnResultListener{
        public void onResultListener(boolean result);
    }
}
