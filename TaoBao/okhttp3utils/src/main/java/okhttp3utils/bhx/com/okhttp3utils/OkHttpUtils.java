package okhttp3utils.bhx.com.okhttp3utils;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * 1.类的用途
 * 2.@authorDell
 * 3.@date2017/9/6 10:57
 */

public class OkHttpUtils {
    //采用饿汉式的单例模式
    private static OkHttpClient client=null;

    public OkHttpUtils() {}

    public static OkHttpClient getInstance(){
        if(client==null){
            synchronized (OkHttpUtils.class){
                if(client==null){
                    //  File sdcache = getExternalCacheDir();
                    //缓存目录
                    File sdcache = new File(Environment.getExternalStorageDirectory(), "cache");
                    int cacheSize = 10 * 1024 * 1024;
                    //OkHttp3拦截器
                    HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                        @Override
                        public void log(String message) {
                            Log.i("信息", message.toString());
                        }
                    });
                    //Okhttp3的拦截器日志分类 4种
                    httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);


                    client = new OkHttpClient.Builder().connectTimeout(15, TimeUnit.SECONDS)
                            //添加OkHttp3的拦截器
                            .addInterceptor(httpLoggingInterceptor)
                            .writeTimeout(20, TimeUnit.SECONDS).readTimeout(20, TimeUnit.SECONDS)
                            .cache(new Cache(sdcache.getAbsoluteFile(), cacheSize)).build();
			
                }
            }
        }
        return client;
    }
    //Get请求
    public static void doGet(String url, Callback callback){
        Request request=new Request.Builder()
                .url(url)
                .build();
        Call call=getInstance().newCall(request);
        call.enqueue(callback);
    }
    //Post请求
    public static void doPost(String url, Map<String,String> params,Callback callback){
        FormBody.Builder builder=new FormBody.Builder();
        for (String key:params.keySet()) {
            builder.add(key,params.get(key));
        }
        Request request=new Request.Builder()
                .url(url)
                .post(builder.build())
                .build();
        Call call = getInstance().newCall(request);
        call.enqueue(callback);
    }
    //上传文件
    public static void doFile(String url,String pathName,String fileName,Callback callback){
        //判断文件的类型
        MediaType MEDIA_TYPE=MediaType.parse(judeType(pathName));
        //创建文件参数
        MultipartBody.Builder builder=new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(MEDIA_TYPE.type(),fileName,
                        RequestBody.create(MEDIA_TYPE,new File(pathName)));
        //发出请求的参数
        Request request=new Request.Builder()
                .header("Authorization", "Client-ID " + "9199fdef135c122")
                .url(url)
                .post(builder.build())
                .build();
        getInstance().newCall(request).enqueue(callback);
    }
    //根据文件路径判断MediaType
    private static String judeType(String path){
        FileNameMap fileNameMap= URLConnection.getFileNameMap();
        String contentTypeFor=fileNameMap.getContentTypeFor(path);
        if(contentTypeFor==null){
            contentTypeFor="pplication/octet-stream";
        }
        return contentTypeFor;
    }
    //下载文件
    public static void downFile(String url,final String fileDir,final String fileName){
        Request request=new Request.Builder()
                .url(url)
                .build();
        getInstance().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                try {
                    is = response.body().byteStream();
                    File file = new File(fileDir, fileName);
                    fos = new FileOutputStream(file);
                    //---增加的代码---
                    //计算进度
                    long totalSize = response.body().contentLength();
                    long sum = 0;
                    while ((len = is.read(buf)) != -1) {
                        sum += len;
                        //progress就是进度值
                        int progress = (int) (sum * 1.0f/totalSize * 100);
                        //---增加的代码---
                        fos.write(buf, 0, len);
                    }
                    fos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (is != null) is.close();
                    if (fos != null) fos.close();
                }
            }
        });
    }
   //发送json数据
    public static void doPost(String url, String jsonParams, Callback callback) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8")
                , jsonParams);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Call call = getInstance().newCall(request);
        call.enqueue(callback);
    }
}
