package org.honor.sniper;

import com.alibaba.fastjson.JSONObject;
import com.wlqq.shannon.client.okhttp.ClientConfig;
import com.wlqq.shannon.client.okhttp.ClientHelperSingleton;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.commons.lang3.time.DateUtils;
import org.honor.sniper.interceptor.LoggingInterceptor;
import org.omg.CORBA.SystemException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class OkHttpDemo1 {
    private static byte[] getBytes(String filePath){
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
	    //close stream.
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    public static void main(String[] args) throws IOException {

        //File file = new File("");
        //MultipartFile
        //ClientConfig config = new ClientConfig();

        //ClientHelperSingleton.init(config);

        OkHttpClient okHttpClient = new OkHttpClient.Builder().addNetworkInterceptor(new LoggingInterceptor()).build();
        //OkHttpClient okHttpClient = getInstance();
        //MultipartBody多功能的请求实体对象,,,formBody只能传表单形式的数据
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        String remoteUrl = "http://tms.dev-ag.56qq.com/uc/web/e/file/upload";
        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                //.addFormDataPart(BIZ_DEPT_NAME, BIZ_DEPT_VALUE)
                .addFormDataPart("fileBizType", "audit")
                .addFormDataPart("st", "0E6B719868B787CB")
                .addFormDataPart("sid", "2170315922")
                .addFormDataPart("file", "file",
                        RequestBody.create(null, getBytes("C:\\tmp\\test.png")))
                .build();

        //创建Request
        Request request = new Request.Builder().url(remoteUrl).post(body).build();

        //得到Call
        Call call = okHttpClient.newCall(request);

        Response execute = call.execute();
        System.out.println(execute.body().string());

        //执行请求
      /*  call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println( response.body().string());
            }
        });*/
        //call.enqueue();
        call.execute();

        //String url1 = "http://www.56qq.com";

        //BitSet set;

        //doGet(url);
        //doGet(url1);

        //System.out.println(Math.round(816.5));
         /* returns the double value represented by this object
             converted to type long */
        //Double obj = new Double("543267.90");
        //long l = obj.longValue();
        //System.out.println("Value = " + l);
        //obj = new Double("30.0");
        //l = obj.longValue();
        //System.out.println("Value = " + l);
        //
        //Date now = new Date();
        //Date date = DateUtils.addDays(now, -30);
        //SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //System.out.println(dayFormat.format(date));
    }

    protected static Map<String, String> logsForException(Throwable throwable) {
        Map<String, String> errorLog = new HashMap(3);
        errorLog.put("event", "event12");
        String message = throwable.getCause() != null?throwable.getCause().getMessage():throwable.getMessage();
        if(message != null) {
            errorLog.put("message", message);
        }

        StringWriter sw = new StringWriter();
        throwable.printStackTrace(new PrintWriter(sw));
        errorLog.put("stack", sw.toString());
        return errorLog;
    }

    private static  void  doGet( String url){

        OkHttpClient okHttpClient = new OkHttpClient.Builder().addNetworkInterceptor(new LoggingInterceptor()).build();
        Request request = new Request.Builder()
                .header("User-Agent", "OkHttp Example")
                .url(url)
                .get()
                .build();

        Call call = okHttpClient.newCall(request);
        //okHttpClient

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Map<String, String> map = logsForException(e);
                System.out.println(JSONObject.toJSONString(map));
                //throw  new RuntimeException(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //System.out.println("success ->" + response.body().string());
            }
        });
    }
}
