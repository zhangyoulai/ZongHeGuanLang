package meiling.mingjiang.instructionpdf.Activity;
/**
 * Created by kouzeping on 2016/3/20.
 * email：kouzeping@shmingjiang.org.cn
 */
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.mingjiang.android.base.event.ComEvent;
import com.mingjiang.android.base.service.com.ComServiceUtils;
import com.mingjiang.android.base.util.Config;
import com.mingjiang.android.base.util.Constants;
import com.mingjiang.android.base.util.NetCheckUtils;
import com.mingjiang.android.base.api.WheelProgressApi;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;
import meiling.mingjiang.instructionpdf.InstructionPdfApp;
import meiling.mingjiang.instructionpdf.R;
import meiling.mingjiang.instructionpdf.entity.FilePath;
import meiling.mingjiang.instructionpdf.entity.FridgeParamsBean;
import meiling.mingjiang.instructionpdf.entity.FridgePostBean;
import meiling.mingjiang.instructionpdf.entity.FridgeResponse;
import meiling.mingjiang.instructionpdf.entity.YieldData;
import rx.functions.Action1;

public class InstructionPdfActivity extends Activity{
    protected final static String TAG = InstructionPdfActivity.class.getSimpleName();
    private String postCode = "";
    private String sessionId = "";
    //试用工厂
    TextView applyFactoryView;
    //岗位编号
    TextView postCodeView;
    //生产线
    TextView productLineView;
    //岗位指导书名称
    TextView directorNameView;
    ProgressDialog mPd;
    ListView mYieldLv;
    private String basePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/meiling";//本地保存数据
    private File baseDir = null;

    Map<String,WebView> mWebwiewMap;
    WebView mWebview1,mWebview2,mWebview3;
    String mWebview1FilePath;

    EditText editText;
    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction_pdf);
        postCode = getIntent().getStringExtra(Constants.SCAN_POST_CODE);
        sessionId = getIntent().getStringExtra(Constants.SESSION_ID);
        EventBus.getDefault().register(this);
        //开启服务，读取冰箱二维码
        ComServiceUtils.startService(this, ComEvent.ACTION_INSTRUCTION_PDF_SCAN);
//        showProgress();
        initView();
        getDataFromServer();
        mWebwiewMap=new HashMap<>();
        initWebViewSettings(mWebview1);
        initWebViewSettings(mWebview2);
        initWebViewSettings(mWebview3);
        //TODO 测试使用，用后删除下一行
//        sendFridgePostBean("HC5901342710000608190001", postCode, sessionId);
    }

    private void initView() {
        applyFactoryView = (TextView) findViewById(R.id.apply_factory);
        postCodeView = (TextView) findViewById(R.id.postCode);
        productLineView = (TextView) findViewById(R.id.productLine);
        directorNameView = (TextView) findViewById(R.id.directorNameView);
        mWebview1=(WebView)findViewById(R.id.webview1);
        mWebview2=(WebView)findViewById(R.id.webview2);
        mWebview3=(WebView)findViewById(R.id.webview3);
        mYieldLv=(ListView)findViewById(R.id.yield_listview);
        //http://192.168.0.143:9007/common_api/static/pdf/web/viewer.html?file=%2Fweb%2Fbinary%2Fsaveas%3Fmodel%3Doperation.instruction%26field%3Dfile_path%26id%3D1
        editText = (EditText) findViewById(R.id.et_type);
        button = (Button) findViewById(R.id.btn_query_pdf);
        button.setOnClickListener(new QueryClick());
    }

    //执行查询操作。
    class QueryClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {

        }
    }

    /**
     * 显示pdf文件
     */
    private void showPdf(File file) {

//        try {
//            String url="file://"+file.getAbsolutePath();
//            mWebview.loadUrl(url);
//        }catch (Exception e){
//            Exception w=e;
//        }
    }
    private void showPdfWeb(WebView webView,String id) {
        Toast.makeText(InstructionPdfActivity.this, "正在显示岗位指导书", Toast.LENGTH_SHORT).show();
        String constant="api/attachments/preview/pdf?mode=padInteractionMode&file=%2Fweb%2Fbinary%2Fsaveas%3Fmodel%3Doperation.instruction%26field%3Dfile_path%26id%3D";
        String url=Config.getBaseUrl(InstructionPdfActivity.this)+constant+id;
        syncCookie(InstructionPdfActivity.this, url);
        webView.setHorizontalScrollBarEnabled(false);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl(url);
    }
    private void syncCookie(Context context, String url){
        try{
//            Log.d("Nat: webView.syncCookie.url", url);
            CookieSyncManager.createInstance(context);
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
//            cookieManager.removeSessionCookie();// 移除
//            cookieManager.removeAllCookie();
//            String oldCookie = cookieManager.getCookie(url);
//            if(oldCookie != null){
//                Log.d("Nat: webView.syncCookieOutter.oldCookie", oldCookie);
//            }
            StringBuilder sbCookie = new StringBuilder();
            sbCookie.append(String.format("session_id=%s", sessionId));
            sbCookie.append(";path=/");
            //;domain=domain;
            String cookieValue = sbCookie.toString();

            cookieManager.setCookie(url, cookieValue);
            CookieSyncManager.getInstance().sync();
            String newCookie = cookieManager.getCookie(url);
            if(newCookie != null){
//                Log.d("Nat: webView.syncCookie.newCookie", newCookie);
            }
        }catch(Exception e){
//            Log.e("Nat: webView.syncCookie failed", e.toString());
        }
    }
    private void initWebViewSettings(WebView webView){
//        myWebView.getSettings().setSupportZoom(true);
//        myWebView.getSettings().setBuiltInZoomControls(true);
//        myWebView.getSettings().setDefaultFontSize(12);
//        myWebView.getSettings().setLoadWithOverviewMode(true);
        // 设置可以访问文件
        webView.getSettings().setAllowFileAccess(true);
        //如果访问的页面中有Javascript，则webview必须设置支持Javascript
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
    }
    /**
     * 显示进度框。
     */
    private void showProgress() {
        /*mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle(getResources().getString(R.string.tip_title));
        mProgressDialog.setMessage(getResources().getString(R.string.load_data_tip));
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();*/
        WheelProgressApi.showProgress(this, "", false);
    }
    /**
     * 获取服务端数据。
     */
    private void getDataFromServer() {
        //获取岗位编码
        postCode = getIntent().getStringExtra(Constants.SCAN_POST_CODE);
        sessionId = getIntent().getStringExtra(Constants.SESSION_ID);
        //网络校验
        boolean isConnect = NetCheckUtils.isNetworkAvaiable(this);
        if (isConnect) {
            //下载服务端json数据
            Toast.makeText(InstructionPdfActivity.this, "请扫描冰箱二维码", Toast.LENGTH_SHORT).show();
        } else {
            //mProgressDialog.dismiss();
            WheelProgressApi.closeProgress(this);
            Toast.makeText(this, "网络连接有误！", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 下载pdf文件
     */
    private File downloadPdf(File file, String id, ProgressDialog pd) {
        URL url = null;
        try {
            url = new URL(Config.getBaseUrl(InstructionPdfActivity.this) + "web/binary/saveas");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.addRequestProperty("X-Openerp-Session-Id", sessionId);
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5000);
            //设置请求参数
            OutputStream os = conn.getOutputStream();
            String param = "model=operation.instruction&field=file_path&id=" + id;
            os.write(param.getBytes());
            //获取到文件的大小
            InputStream is = conn.getInputStream();
            FileOutputStream fos = new FileOutputStream(file);
            BufferedInputStream bis = new BufferedInputStream(is);
            byte[] buffer = new byte[1024];
            int len;
            int total = 0;
            while ((len = bis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
                total += len;
                //获取当前下载量
                pd.incrementProgressBy(total / conn.getContentLength()*100);
            }
            fos.close();
            bis.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        //停止服务
        ComServiceUtils.stopService(this);
    }

    public void onEventMainThread(ComEvent event) {
        if (ComEvent.ACTION_INSTRUCTION_PDF_SCAN == event.getActionType()) {
            //从串口接收到数据，判断
            String str = event.getMessage();
            if (str.contains("bj")){
                Toast.makeText(InstructionPdfActivity.this, "没有扫到二维码", Toast.LENGTH_SHORT).show();
                EventBus.getDefault().post(new ComEvent("D", ComEvent.ACTION_SEND_GUN));
            }else if (str.contains("Noread")){
                Log.i(TAG,"Noread");
            }else if (str.contains("kq")){
                Log.i(TAG,"kq");
                EventBus.getDefault().post(new ComEvent("E", ComEvent.ACTION_SEND_GUN));
            }
            else {//扫描到二维码
                //关闭扫描
                EventBus.getDefault().post(new ComEvent("D", ComEvent.ACTION_SEND_GUN));
                //通知板卡
                EventBus.getDefault().post(new ComEvent("U", ComEvent.ACTION_SEND_PCI));
                //回传到服务器
                String fridgeCode = event.getMessage();
                sendFridgePostBean(fridgeCode, postCode, sessionId);
            }
        } else if (ComEvent.PDF_UPLOAD_SUCCEED == event.getActionType()) {
            FridgeResponse fridgeResponse = (FridgeResponse) event.getObjectMsg();
            //刷洗描述显示
            dealDataToView(fridgeResponse);
            //校验是否下载pdf数据
            FilePath filePath = fridgeResponse.getResult().getOperation_instruction().getFile_path();
            String fileName = filePath.getId() + (filePath.get__last_update().replace(" ", "").replace(":", "")) + ".pdf";
            /**
             * 使用PdfView显示
             */
//            validLocalData(fridgeResponse, fileName);
            /**
             * 使用WebView显示
             */
            setWebViewShow(fileName,fridgeResponse.getResult().getOperation_instruction().getId());
        } else if (ComEvent.PDF_UPLOAD_ERROR == event.getActionType()) {
            String result = event.getMessage();
            Toast.makeText(InstructionPdfActivity.this, result, Toast.LENGTH_SHORT).show();
        } else if (ComEvent.PDF_DOWNLOAD_SUCCEED == event.getActionType()) {
            mPd.dismiss(); //结束掉进度条对话框
            File file = (File) event.getObjectMsg();
            showPdf(file);
        } else if (ComEvent.PDF_DOWNLOAD_ERROR == event.getActionType()) {
            mPd.dismiss(); //结束掉进度条对话框
            String result = event.getMessage();
            Toast.makeText(InstructionPdfActivity.this, result, Toast.LENGTH_SHORT).show();
        }else if (ComEvent.GET_YIELF_SUCCEED == event.getActionType()) {
            List<YieldData> yieldDatas= (List<YieldData>) event.getObjectMsg();
            MyAdapter myAdapter=new MyAdapter(yieldDatas,InstructionPdfActivity.this);
            mYieldLv.setAdapter(myAdapter);
        }else if (ComEvent.GET_YIELD_ERROR == event.getActionType()) {
            String result = event.getMessage();
            Toast.makeText(InstructionPdfActivity.this, result, Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * 校验要下载的数据是否在本地存在。
     */
    private void validLocalData(final FridgeResponse fridgeResponse, String fileName) {
        baseDir = new File(basePath);
        boolean flag = false;
        if (!baseDir.exists()) {
            flag = baseDir.mkdirs();
            int a = 0;
        }
        File postOperPdf = null;
        postOperPdf = new File(baseDir.getAbsolutePath(), fileName);//岗位编号文件夹
        if (!postOperPdf.exists()) {
            mPd = new ProgressDialog(InstructionPdfActivity.this);
            mPd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mPd.setMessage("正在下载岗位指导书");
            mPd.setMax(100);
            mPd.show();
            //1,保存pdf的文件，2，pdf的标识， 3，下载进度显示
            final File finalPostOperPdf = postOperPdf;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        File file = downloadPdf(finalPostOperPdf, fridgeResponse.getResult().getOperation_instruction().getId(), mPd);
                        EventBus.getDefault().post(new ComEvent(file, ComEvent.PDF_DOWNLOAD_SUCCEED));
                    } catch (Exception e) {
                        EventBus.getDefault().post(new ComEvent("加载岗位指导书失败", ComEvent.PDF_DOWNLOAD_ERROR));
                    }
                }
            }).start();

        } else {
            //显示pdf
            showPdf(postOperPdf);
        }
    }

    private void dealDataToView(FridgeResponse fridgeResponse) {
        //适用工厂
        String applyFactory = "";
        if ("false".equals(applyFactory) || "".equals(applyFactory)) {
            applyFactoryView.setVisibility(View.GONE);
        } else {
            applyFactoryView.setText(applyFactory);
        }
        //生产线
        String productLine = "";
        if ("false".equals(productLine) || "".equals(productLine)) {
            productLineView.setVisibility(View.GONE);
        } else {
            productLineView.setText(productLine);
        }
        try {
            //岗位指导书名称
            String directorName = fridgeResponse.getResult().getOperation_instruction().getName();
            if ("false".equals(directorName) || "".equals(directorName)) {
                directorNameView.setVisibility(View.GONE);
            } else {
                directorNameView.setText(directorName);
            }
            //岗位编号

            String postName = fridgeResponse.getResult().getOperation_instruction().getOperation_id();
            if ("false".equals(directorName) || "".equals(directorName)) {
                postCodeView.setText(postName);
            } else {
                postCodeView.setText(postName);
            }
        }catch (Exception e){
            Toast.makeText(InstructionPdfActivity.this, "获取岗位指导书出错", Toast.LENGTH_SHORT).show();
        }



    }
    /**
     * 上传冰箱与岗位代码信息
     *
     * @param fridgeCode
     * @param postCode
     */
    private void sendFridgePostBean(final String fridgeCode, final String postCode, final String sessionId) {

        FridgePostBean fridgePostBean = new FridgePostBean(fridgeCode, postCode);
        FridgeParamsBean fridgeParamsBean = new FridgeParamsBean();

        fridgeParamsBean.setParams(fridgePostBean);

        InstructionPdfApp.getApp().getNetService(this).sendFridgePostBean(fridgeParamsBean, sessionId).subscribe(new Action1<FridgeResponse>() {
            @Override
            public void call(FridgeResponse fridgeResponse) {
                //加载数据成功
                String sendMsg = "岗位编号为：" + postCode + "-冰箱编号为：" + fridgeCode + "-上传成功！";
                Log.d(sendMsg, "Yeah!!!!!");
                  EventBus.getDefault().post(new ComEvent(fridgeResponse, ComEvent.PDF_UPLOAD_SUCCEED));
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                //加载数据失败
                String sendMsg = "岗位编号为：" + postCode + "-冰箱编号为：" + fridgeCode + "-上传失败！";
                Log.d(sendMsg, throwable.getMessage());
                EventBus.getDefault().post(new ComEvent(sendMsg, ComEvent.PDF_UPLOAD_ERROR));
            }
        });
        //获取过岗数量
        getYieldData(postCode,fridgeCode);
    }

    private void getYieldData(String produce_post,String serial_number){
        Map<String,String> map=new HashMap<>();
        map.put("produce_post",produce_post);
        map.put("serial_number",serial_number);
        Log.i(TAG,produce_post + serial_number);
        InstructionPdfApp.getApp().getNetService(this).getYieldData(map, sessionId).subscribe(new Action1<List<YieldData>>() {
            @Override
            public void call(List<YieldData> yieldDatas) {
                //加载数据成功
                EventBus.getDefault().post(new ComEvent(yieldDatas, ComEvent.GET_YIELF_SUCCEED));
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                //加载数据失败
                String sendMsg = "获取产量信息失败";
                EventBus.getDefault().post(new ComEvent(sendMsg, ComEvent.GET_YIELD_ERROR));
            }
        });
    }

    public  void setWebViewShow(String filePath,String id){
        if (mWebwiewMap.get(filePath)!=null){
            for (String key : mWebwiewMap.keySet()) {
                mWebwiewMap.get(key).setVisibility(View.GONE);
            }
            mWebwiewMap.get(filePath).setVisibility(View.VISIBLE);
        }else{
            if (mWebwiewMap.size()==0){
                mWebview1FilePath=filePath;
                mWebwiewMap.put(filePath,mWebview1);
                showPdfWeb(mWebview1, id);
                mWebview1.setVisibility(View.VISIBLE);
                mWebview2.setVisibility(View.GONE);
                mWebview3.setVisibility(View.GONE);
            }else if (mWebwiewMap.size()==1){
                mWebwiewMap.put(filePath,mWebview2);
                showPdfWeb(mWebview2, id);
                mWebview2.setVisibility(View.VISIBLE);
                mWebview1.setVisibility(View.GONE);
                mWebview3.setVisibility(View.GONE);
            }else if (mWebwiewMap.size()==2){
                mWebwiewMap.put(filePath,mWebview3);
                showPdfWeb(mWebview3, id);
                mWebview3.setVisibility(View.VISIBLE);
                mWebview1.setVisibility(View.GONE);
                mWebview2.setVisibility(View.GONE);
            }else {
                mWebwiewMap.remove(mWebview1FilePath);
                mWebview1FilePath=filePath;
                mWebwiewMap.put(filePath,mWebview1);
                showPdfWeb(mWebview1, id);
                mWebview1.setVisibility(View.VISIBLE);
                mWebview2.setVisibility(View.GONE);
                mWebview3.setVisibility(View.GONE);
            }
        }
    }
}
