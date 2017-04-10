package meiling.mingjiang.appupdata;

import android.app.ProgressDialog;
import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * Created by kouzeping on 2016/3/15.
 * email：kouzeping@shmingjiang.org.cn
 */
public class DownLoadManager {
    public static File getFileFromServer(String path,Map<String,String> map, ProgressDialog pd) throws Exception{
        //如果相等的话表示当前的sdcard挂载在手机上并且是可用的
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            URL url = new URL(path);
            HttpURLConnection conn =  (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5000);
            //设置请求参数
            OutputStream os = conn.getOutputStream();
            String param = "";
            for (Map.Entry<String, String> entry : map.entrySet()) {
                param=param+entry.getKey()+"="+entry.getValue()+"&";
            }
            param = param.substring(0,param.length()-1);
            os.write(param.getBytes());
            //获取到文件的大小
            pd.setMax(conn.getContentLength()/1024);
            InputStream is = conn.getInputStream();
            File file = new File(Environment.getExternalStorageDirectory(), "updata.apk");
            FileOutputStream fos = new FileOutputStream(file);
            BufferedInputStream bis = new BufferedInputStream(is);
            byte[] buffer = new byte[1024];
            int len ;
            int total=0;
            while((len =bis.read(buffer))!=-1){
                fos.write(buffer, 0, len);
                total+= len;
                //获取当前下载量
                pd.setProgress(total/1024);
            }
            fos.close();
            bis.close();
            is.close();
            return file;
        }
        else{
            return null;
        }
    }

}
