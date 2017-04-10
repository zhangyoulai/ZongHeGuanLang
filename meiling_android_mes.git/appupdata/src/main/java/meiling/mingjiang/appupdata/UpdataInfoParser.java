package meiling.mingjiang.appupdata;
import com.google.gson.Gson;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by kouzeping on 2016/3/15.
 * email：kouzeping@shmingjiang.org.cn
 */
public class UpdataInfoParser {
//    public static UpdataInfo getUpdataInfo(InputStream is) throws Exception{
//        XmlPullParser parser = Xml.newPullParser();
//        parser.setInput(is, "utf-8");
//        int type = parser.getEventType();
//        UpdataInfo info = new UpdataInfo();
//        while(type != XmlPullParser.END_DOCUMENT ){
//            switch (type) {
//                case XmlPullParser.START_TAG:
//                    if("version".equals(parser.getName())){
//                        info.setVersion(parser.nextText());
//                    }else if ("url".equals(parser.getName())){
//                        info.setUrl(parser.nextText());
//                    }else if ("description".equals(parser.getName())){
//
//                        info.setDescription(parser.nextText());
//                    }
//                    break;
//            }
//            type = parser.next();
//        }
//        return info;
//    }

    public static UpdataInfo getUpdataInfo(InputStream is) throws Exception {

        UpdataInfo info = new UpdataInfo();
        String str= ConvertStream2Json(is);
        Gson gson = new Gson();
        info = gson.fromJson(str, UpdataInfo.class);
        return info;
    }

    private static String ConvertStream2Json(InputStream inputStream)
    {
        String jsonStr = "";
        // ByteArrayOutputStream相当于内存输出流
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        // 将输入流转移到内存输出流中
        try
        {
            while ((len = inputStream.read(buffer, 0, buffer.length)) != -1)
            {
                out.write(buffer, 0, len);
            }
            // 将内存流转换为字符串
            jsonStr = new String(out.toByteArray());
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jsonStr;
    }
}
